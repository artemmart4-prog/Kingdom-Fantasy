package local.castle.tv;
public final class CastleGate {public static int bottom(float hour){return 138+Math.round(SceneClock.gateClosed(hour)*27);}public static int left(int y){return y<143?249:246;}public static int right(int y){return y<143?258:261;}public static boolean guard(float hour){return !SceneClock.sunVisible(hour);}}
