package local.castle.tv;
public final class BoatMotion {
 public static float heave(long time,int index){return (float)Math.sin(time/1600.0+index*1.9)*.9f;}
 public static float roll(long time,int index){return (float)Math.sin(time/2100.0+index*1.9+.4)*1.25f;}
 public static float mist(long age){return Math.max(0,1-Math.max(0,age)/1400f);}
}
