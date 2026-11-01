package local.castle.tv;
/** Display-clock lighting, independent of clouds and rain darkening the palette. */
public final class RealmAmbience {
 static float hour(float h){return ((h%24)+24)%24;}
 public static float sceneHour(float clock,int lighting,Float preview){return hour(preview!=null?preview:lighting==1?10:lighting==2?23:clock);}
 public static float lamps(float h){h=hour(h);if(h<6||h>=20)return 1;if(h<17)return 0;float t=(h-17)/3;return t*t*(3-2*t);}
 public static boolean butterflies(float h){return SceneClock.sunVisible(hour(h));}
 public static boolean fireflies(float h){return !butterflies(h);}
 public static int glass(float amount){return SceneClock.mix(0xff26343d,0xffffdb8c,amount);}
}
