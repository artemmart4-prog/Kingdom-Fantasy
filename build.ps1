param(
  [string]$SdkRoot = $env:ANDROID_SDK_ROOT,
  [string]$JdkRoot = $env:JAVA_HOME,
  [string]$BuildToolsPath,
  [string]$AndroidJar,
  [switch]$UnsignedOnly
)
$ErrorActionPreference = 'Stop'
if (!$JdkRoot) { throw 'Set JAVA_HOME to JDK 17 or pass -JdkRoot.' }
if (!$BuildToolsPath) { $BuildToolsPath = (Get-ChildItem (Join-Path $SdkRoot 'build-tools') -Directory | Sort-Object Name -Descending | Select-Object -First 1).FullName }
if (!$AndroidJar) { $AndroidJar = Join-Path $SdkRoot 'platforms/android-35/android.jar' }
if (!(Test-Path $AndroidJar)) { throw 'Install Android SDK Platform 35 or pass -AndroidJar.' }
$env:JAVA_HOME = $JdkRoot
$env:Path = "$JdkRoot/bin;$env:Path"
$out = Join-Path $PSScriptRoot 'build'
New-Item -ItemType Directory -Force -Path "$out/classes","$out/dex","$out/res" | Out-Null
function CheckExit { if ($LASTEXITCODE -ne 0) { throw "Build failed: $LASTEXITCODE" } }
$sources = @(Get-ChildItem "$PSScriptRoot/src" -Recurse -Filter '*.java' | ForEach-Object FullName)
& "$JdkRoot/bin/javac.exe" -encoding UTF-8 -source 8 -target 8 -classpath $AndroidJar -d "$out/classes" @sources
CheckExit
$classes = @(Get-ChildItem "$out/classes" -Recurse -Filter '*.class' | ForEach-Object { $_.FullName.Replace('\','/') })
[IO.File]::WriteAllLines("$out/classes.args", $classes, [Text.UTF8Encoding]::new($false))
& "$BuildToolsPath/d8.bat" --min-api 26 --lib $AndroidJar --output "$out/dex" "@$out/classes.args"
CheckExit
& "$BuildToolsPath/aapt.exe" package -f -M "$PSScriptRoot/AndroidManifest.xml" -I $AndroidJar -S "$out/res" -A "$PSScriptRoot/assets" -F "$out/raw.apk"
CheckExit
& "$JdkRoot/bin/jar.exe" uf "$out/raw.apk" -C "$out/dex" classes.dex
CheckExit
& "$BuildToolsPath/zipalign.exe" -f 4 "$out/raw.apk" "$out/kingdom-fantasy-unsigned.apk"
CheckExit
if ($UnsignedOnly) { Write-Output "$out/kingdom-fantasy-unsigned.apk"; exit }
# Local development signer only. Production signing material is never part of this repository.
$debugKey = Join-Path $out 'debug.keystore'
if (!(Test-Path $debugKey)) {
  & "$JdkRoot/bin/keytool.exe" -genkeypair -keystore $debugKey -alias androiddebugkey -keyalg RSA -keysize 2048 -validity 10000 -storepass android -keypass android -dname 'CN=Android Debug' -noprompt
  CheckExit
}
& "$BuildToolsPath/apksigner.bat" sign --ks $debugKey --ks-key-alias androiddebugkey --ks-pass pass:android --out "$out/kingdom-fantasy.apk" "$out/kingdom-fantasy-unsigned.apk"
CheckExit
& "$BuildToolsPath/apksigner.bat" verify "$out/kingdom-fantasy.apk"
CheckExit
Write-Output "$out/kingdom-fantasy.apk"
