package local.castle.tv;
public final class DashMotion {
  public static int duration(int setting){return setting>=400?320:260;}
  public static float ease(float t){t=Math.max(0,Math.min(1,t));float left=1-t;return 1-left*left*left;}
  public static float y(float from,float to,float t){return from+(to-from)*ease(t);}
}
