package local.castle.tv;
public final class BirdCycle {
  public static final long PERIOD=30000,DURATION=6000;
  public static float progress(long time){long t=Math.floorMod(time,PERIOD);return t<DURATION?t/(float)DURATION:-1;}
  /** Decorative migration direction, not live bird tracking or a geographic compass. */
  public static int direction(int month){return month>=3&&month<=8?1:-1;}
}
