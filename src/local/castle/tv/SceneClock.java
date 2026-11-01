package local.castle.tv;

/** Local clock cycle, not astronomical sunrise: dawn 06-08, dusk 17-20. */
public final class SceneClock {
  static float smooth(float t){t=Math.max(0,Math.min(1,t));return t*t*(3-2*t);}
  public static float daylight(float hour){
    hour=((hour%24)+24)%24;
    if(hour<6||hour>=20)return 0;
    if(hour<8)return smooth((hour-6)/2);
    if(hour<=17)return 1;
    return 1-smooth((hour-17)/3);
  }
  public static boolean sunVisible(float hour){return hour>=6&&hour<20;}
  public static float gateClosed(float hour){return 1-daylight(hour);}
  public static float twilight(float hour){return Math.max(0,1-Math.abs(hour-6.7f)/1.7f)*.65f+Math.max(0,1-Math.abs(hour-18.3f)/1.7f)*.8f;}
  public static int mix(int a,int b,float t){t=Math.max(0,Math.min(1,t));int r=Math.round(((a>>16)&255)*(1-t)+((b>>16)&255)*t),g=Math.round(((a>>8)&255)*(1-t)+((b>>8)&255)*t),bl=Math.round((a&255)*(1-t)+(b&255)*t);return 0xff000000|(r<<16)|(g<<8)|bl;}
}
