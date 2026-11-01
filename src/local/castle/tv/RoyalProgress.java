package local.castle.tv;

/** Sequential cosmetic milestones. No currency, purchases or network state. */
public final class RoyalProgress {
  public int stage,streak,best;public boolean challenge,whiteLocked;
  public static final int[] GOALS={25,50,99,100};
  public int result(boolean won){int award=-1;if(won){streak++;best=Math.max(best,streak);if(stage==0&&streak>=25){award=3;stage=1;streak=0;}else if(stage==1&&streak>=50){award=2;stage=2;streak=0;}else if(stage==2&&streak>99){streak=0;}else if(stage==3&&streak>=100){award=5;stage=4;streak=0;}}else{if(stage==2&&streak==99){award=4;stage=3;}streak=0;}return award;}
  public boolean unlocked(int skin){return !challenge||skin==0||skin==1&&!whiteLocked||skin==3&&stage>=1||skin==2&&stage>=2||skin==4&&stage>=3||skin==5&&stage>=4;}
  public boolean cheat(String code){if(code==null)return false;code=code.trim();if("Kingdom".equalsIgnoreCase(code)){challenge=true;whiteLocked=false;stage=4;streak=0;return true;}if("modgniK".equalsIgnoreCase(code)){challenge=true;whiteLocked=true;stage=0;streak=0;return true;}return false;}
  public int goal(){return stage<4?GOALS[stage]:0;}
}
