package local.castle.tv;
import java.util.Arrays;
/** Every impact heals automatically, independent of where the king is standing. */
public final class TileDamage {
  public static final long HEAL_MS=2200,HOLD_MS=300;
  final long[] landed=new long[6];
  public TileDamage(){Arrays.fill(landed,-1);}
  public void depart(long now){} // Leaving does not restart or postpone healing.
  public void land(int tile,long now){if(tile>=0&&tile<6)landed[tile]=now;}
  public void cancelPending(int tile,long now){if(tile>=0&&tile<6&&landed[tile]>now)landed[tile]=-1;}
  public float strength(int tile,long now){long age=age(tile,now);if(age==Long.MAX_VALUE)return 0;if(age<HOLD_MS)return 1;return Math.max(0,1-(age-HOLD_MS)/(float)(HEAL_MS-HOLD_MS));}
  public long age(int tile,long now){return landed[tile]<0||now<landed[tile]?Long.MAX_VALUE:now-landed[tile];}
}
