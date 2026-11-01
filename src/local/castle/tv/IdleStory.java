package local.castle.tv;

/** Deterministic elapsed-time story; no timers, allocations or wall-clock dependency. */
public final class IdleStory {
  public static final long CYCLE=124000;
  public static int kind(long elapsed){long t=Math.max(0,elapsed)%CYCLE;return t<18000?1:t>=36000&&t<46000?2:t>=58000&&t<70000?3:t>=84000&&t<94000?4:0;}
  public static long age(long elapsed){long t=Math.max(0,elapsed)%CYCLE;return t<18000?t:t>=36000&&t<46000?t-36000:t>=58000&&t<70000?t-58000:t>=84000&&t<94000?t-84000:0;}
  public static boolean watching(long elapsed){int k=kind(elapsed);long a=age(elapsed);return k==2?a>=3500&&a<5500:k==3&&a>=4500&&a<7500;}
  public static float x(long elapsed){int k=kind(elapsed);long a=age(elapsed);if(k==1)return -180+1460*a/18000f;
    long enter=k==2?3500:4500,leave=k==2?5500:7500,end=k==2?10000:12000;
    if(a<enter)return -80+580*a/(float)enter;if(a<leave)return 500;return 500+620*(a-leave)/(float)(end-leave);
  }
}
