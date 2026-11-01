package local.castle.tv;
public final class HulkImpact {
 public static float shake(long age){return age<0||age>=360?0:(float)Math.sin(age/19.0)*3*(1-age/360f);}
}
