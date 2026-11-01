package local.castle.tv;
import java.util.Random;

/** Cup identities are conserved through visible pair swaps; the ball never changes cup. */
public final class ShellGame {
  public int selected=0,ballCup,swaps,duration;public boolean running=false,finished=false,won=false;public long started;public int[] left=new int[14],right=new int[14];
  final Random random;public ShellGame(Random r){random=r;}
  public void begin(long now,int streak){selected=0;ballCup=random.nextInt(3);swaps=Math.min(14,4+streak/5);duration=Math.max(180,620-streak*5);for(int i=0;i<swaps;i++){left[i]=random.nextInt(3);right[i]=(left[i]+1+random.nextInt(2))%3;}started=now;running=true;finished=false;}
  public long guessAt(){return 1400L+swaps*duration;}
  public boolean ready(long now){return running&&!finished&&now-started>=guessAt();}
  public float cupSlot(int identity,long elapsed){float slot=identity;for(int i=0;i<swaps;i++){long age=elapsed-1400L-i*duration;if(age<=0)break;float target=slot==left[i]?right[i]:slot==right[i]?left[i]:slot;if(age>=duration)slot=target;else{float t=age/(float)duration;t=t*t*(3-2*t);return slot+(target-slot)*t;}}return slot;}
  public float cupArc(int identity,long elapsed){int index=(int)((elapsed-1400)/Math.max(1,duration));if(elapsed<1400||index>=swaps)return 0;float t=(elapsed-1400-index*duration)/(float)duration;float before=cupSlot(identity,1400L+index*duration);if(before!=left[index]&&before!=right[index])return 0;return (before==left[index]?-1:1)*(float)Math.sin(t*Math.PI)*18;}
  public int winningSlot(){return Math.round(cupSlot(ballCup,guessAt()+1));}
  public boolean guess(long now){if(!ready(now))return false;won=selected==winningSlot();finished=true;return true;}
}
