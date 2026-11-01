package local.castle.tv;

/** Mutually exclusive quiet gestures with a blink independent of gestures. */
public final class MerchantIdle {
  public static long age(long elapsed){return Math.floorMod(elapsed,36000);}
  public static int kind(long elapsed){long t=age(elapsed);return t>=6000&&t<8300?1:t>=17000&&t<20300?2:t>=29000&&t<31800?3:0;}
  public static float progress(long elapsed){long t=age(elapsed);return kind(elapsed)==1?(t-6000)/2300f:kind(elapsed)==2?(t-17000)/3300f:kind(elapsed)==3?(t-29000)/2800f:0;}
  public static float lift(long elapsed){float t=progress(elapsed);return Math.min(1,Math.min(t*4,(1-t)*4));}
  public static boolean blink(long elapsed){long t=Math.floorMod(elapsed,5900);return t>=4100&&t<4320;}
  public static boolean moving(long elapsed){return kind(elapsed)!=0||blink(elapsed);}
}
