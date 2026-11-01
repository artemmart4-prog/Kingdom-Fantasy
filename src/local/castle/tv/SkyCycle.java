package local.castle.tv;
public final class SkyCycle {
  public static final long COMET_PERIOD=120000,COMET_DURATION=2400;
  public static float comet(long elapsed){long phase=Math.floorMod(elapsed,COMET_PERIOD);return phase<COMET_DURATION?phase/(float)COMET_DURATION:-1;}
  public static float twinkle(long elapsed,int star){return .55f+.45f*(float)Math.sin(elapsed/(900.0+(star%7)*210)+star*2.31);}
}
