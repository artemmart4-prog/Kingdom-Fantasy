package local.castle.tv;
/** Staggered minion jumps; commander does not follow selection. */
public final class SpectralMotion {
 public static final int DURATION=480,DELAY=85,MINIONS=3;
 public static float progress(long elapsed,int minion){return JumpMotion.progress(elapsed-minion*DELAY,DURATION);}
 public static float x(float from,float to,long elapsed,int minion){return from+(to-from)*JumpMotion.ease(progress(elapsed,minion));}
 public static float y(float from,float to,long elapsed,int minion){float t=progress(elapsed,minion);return from+(to-from)*JumpMotion.ease(t)-JumpMotion.lift(t);}
 public static boolean moving(long elapsed){return elapsed>=0&&elapsed<DURATION+(MINIONS-1)*DELAY;}
}
