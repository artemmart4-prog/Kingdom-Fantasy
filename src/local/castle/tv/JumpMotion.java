package local.castle.tv;

/** Floating-point time interpolation shared by rendering and regression tests. */
public final class JumpMotion {
  public static float progress(long elapsedMillis,int durationMillis){return Math.max(0f,Math.min(1f,elapsedMillis/(float)Math.max(1,durationMillis)));}
  public static float ease(float t){return t*t*t*(t*(t*6-15)+10);}
  public static float lift(float t){float s=(float)Math.sin(Math.PI*t);return s*s*55f;}
}
