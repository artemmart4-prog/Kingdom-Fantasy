package local.castle.tv;
/** Virtual 1024px screen units per second; immediate step plus bounded acceleration. */
public final class CursorMotion {
  public static float speed(long heldMillis){return 380+Math.min(1,Math.max(0,heldMillis/900f))*620;}
  public static float delta(long frameMillis,long heldMillis){return speed(heldMillis)*Math.max(0,Math.min(32,frameMillis))/1000f;}
}
