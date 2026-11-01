package local.castle.tv;
public final class SeaHunt {
  public static boolean fishVisible(long age){return age>=500&&age<1700;}
  public static float fishX(long age){return 120+40*(age-500)/2000f;}
  public static float fishY(long age){return 223-38*(float)Math.sin(Math.PI*(age-500)/2000.0);}
  public static float height(long age){if(age<1000||age>=3900)return 0;if(age<1700)return 36*ease((age-1000)/700f);if(age<2200)return 36;return 36*(1-ease((age-2200)/1700f));}
  static float ease(float t){return t*t*(3-2*t);}
}
