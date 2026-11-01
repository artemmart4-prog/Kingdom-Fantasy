package local.castle.tv;
public final class TeleportMotion {
  public static final int DURATION=280;
  public static float position(float from,float to,float t){return t<.5f?from:to;}
  public static float opacity(float t){return t<.42f?Math.max(0,1-t/.42f):Math.min(1,Math.max(0,(t-.58f)/.42f));}
}
