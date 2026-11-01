package local.castle.tv;

/** A single finite scene: arrive, draw the deck, deal, then approach the table. */
public final class MerchantMotion {
  public static final long READY=3700;
  public static float unit(long elapsed,long start,long duration){return Math.max(0,Math.min(1,(elapsed-start)/(float)duration));}
  public static float smooth(float t){return t*t*(3-2*t);}
  public static float arrival(long age){return smooth(unit(age,0,900));}
  public static float hand(long age){return smooth(unit(age,1000,450))*(1-smooth(unit(age,2650,450)));}
  public static float card(long age,int index){return smooth(unit(age,1550+index*260,420));}
  public static float camera(long age){return smooth(unit(age,2750,950));}
  public static boolean ready(long age){return age>=READY;}
  public static int nextCard(int selected,int direction){return Math.floorMod(selected+direction,4);}
  public static final long REFRESH_END=3900;
  public static float refreshCamera(long age){return age<600?1-.7f*smooth(unit(age,0,600)):age<2900?.3f:.3f+.7f*smooth(unit(age,2900,1000));}
  public static float refreshCard(long age,int index){return age<600?1:age<1450?1-smooth(unit(age,600+index*80,450)):age<1900?0:smooth(unit(age,1900+index*220,360));}
}
