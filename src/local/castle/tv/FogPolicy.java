package local.castle.tv;
/** Fog is a separate opt-in landscape effect; weather data and badge remain intact. */
public final class FogPolicy {
 public static final int OFF=0,NIGHT_WEATHER=1,WEATHER=2;
 public static boolean visible(int mode,int weather,float hour){if(weather!=WeatherKind.FOG||mode==OFF)return false;hour=((hour%24)+24)%24;return mode==WEATHER||mode==NIGHT_WEATHER&&!SceneClock.sunVisible(hour);}
}
