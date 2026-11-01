package local.castle.tv;
import android.content.*;
import android.os.*;
import org.json.*;
import java.net.*;
import java.io.*;

/** One small HTTPS request per half-hour while Home is foreground. No location permission. */
final class WeatherSource {
  static final String CITY="Berlin";
  static final String URL="https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.405&current=temperature_2m,weather_code&timezone=Europe%2FBerlin&forecast_days=1";
  final SharedPreferences prefs;final Handler main=new Handler(Looper.getMainLooper());final Runnable changed;
  int code;float temperature;long fetched,lastAttempt;String sampleTime="";boolean inFlight=false,failed=false;volatile boolean closed=false;
  WeatherSource(Context c,Runnable r){prefs=c.getSharedPreferences("castle",Context.MODE_PRIVATE);changed=r;code=prefs.getInt("weatherCode",0);temperature=prefs.getFloat("weatherTemperature",0);fetched=prefs.getLong("weatherFetched",0);sampleTime=prefs.getString("weatherSample","");}
  boolean fresh(){return WeatherKind.fresh(fetched,System.currentTimeMillis());}
  int kind(){return fresh()?WeatherKind.fromWmo(code):WeatherKind.CLEAR;}
  String summary(){return fetched==0?inFlight?"Загрузка…":"Нет данных":I18n.tr(WeatherKind.NAMES[WeatherKind.fromWmo(code)])+" · "+Math.round(temperature)+"°C"+(fresh()?(failed?" · кэш":""):" · устарело");}
  void request(boolean force){long now=System.currentTimeMillis();if(closed||inFlight)return;if(!force&&fresh()&&now-fetched<1800000)return;if(lastAttempt>0&&now-lastAttempt<(force?30000:300000))return;lastAttempt=now;inFlight=true;
    new Thread(new Runnable(){public void run(){HttpURLConnection connection=null;try{
      connection=(HttpURLConnection)new java.net.URL(URL).openConnection();connection.setConnectTimeout(5000);connection.setReadTimeout(5000);connection.setUseCaches(false);if(connection.getResponseCode()!=200)throw new IOException("Weather response");
      StringBuilder body=new StringBuilder();try(BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"))){char[] buffer=new char[2048];int n;while((n=reader.read(buffer))!=-1){body.append(buffer,0,n);if(body.length()>32768)throw new IOException("Weather response too large");}}
      JSONObject current=new JSONObject(body.toString()).getJSONObject("current");final int nextCode=current.getInt("weather_code");final float nextTemp=(float)current.getDouble("temperature_2m");final String nextTime=current.getString("time");if(!Float.isFinite(nextTemp)||nextTemp< -100||nextTemp>70)throw new IOException("Invalid temperature");
      main.post(new Runnable(){public void run(){if(closed)return;inFlight=false;failed=false;code=nextCode;temperature=nextTemp;sampleTime=nextTime;fetched=System.currentTimeMillis();prefs.edit().putInt("weatherCode",code).putFloat("weatherTemperature",temperature).putLong("weatherFetched",fetched).putString("weatherSample",sampleTime).apply();changed.run();}});
    }catch(Exception e){main.post(new Runnable(){public void run(){if(closed)return;inFlight=false;failed=true;changed.run();}});}finally{if(connection!=null)connection.disconnect();}}},"Castle weather").start();
  }
  void close(){closed=true;main.removeCallbacksAndMessages(null);}
}
