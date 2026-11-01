package local.castle.tv;
import java.util.Random;

/** Endless original phrase generator, no recorded loop or growing audio buffer. */
public final class LivingScore {
  public static final int RATE=22050;
  static final int SIZE=4096; static final double[] SINE=new double[SIZE];
  static {for(int i=0;i<SIZE;i++)SINE[i]=Math.sin(i*Math.PI*2/SIZE);}
  ChipScore festival,moonlight;
  final Random random; long sample=0,noteStart=0,noteEnd=0; int theme,last=-1,step=0,bar=-1;
  double phase=0,freq=220,bassPhase=0,padPhase=0,bass=110,pad=165,gain=0; boolean rest=false,minor=false;
  static final int[] MAJOR={0,2,4,5,7,9,11},MINOR={0,2,3,5,7,8,10};
  public LivingScore(long seed){random=new Random(seed);}
  static double sine(double phase){return SINE[((int)(phase*SIZE))&(SIZE-1)];}
  static double hz(int midi){return 440*Math.pow(2,(midi-69)/12.0);}
  public void fill(short[] out,int mode,float volume){
    if(mode==0){if(festival==null)festival=new ChipScore(false);festival.fill(out,volume);return;}if(mode==2){if(moonlight==null)moonlight=new ChipScore(true);moonlight.fill(out,volume);return;}
    theme=Math.max(0,Math.min(2,mode)); double beat=theme==0?.43:theme==1?.78:1.18;
    for(int i=0;i<out.length;i++,sample++){
      double t=sample/(double)RATE; int currentBar=(int)(t/(beat*4));
      if(currentBar!=bar){bar=currentBar;if(bar%8==0)minor=theme==2||random.nextInt(theme==0?6:2)==0;int root=new int[]{50,55,47,45}[bar%4];bass=hz(root);pad=hz(root+(minor?15:16));}
      if(sample>=noteEnd){noteStart=sample;int[] scale=minor?MINOR:MAJOR;
        step=Math.max(0,Math.min(10,step+random.nextInt(5)-2));if(step==last)step=(step+2)%10;last=step;
        freq=hz(62+scale[step%7]+12*(step/7)-(theme==2?12:0));
        double beats=new double[]{.5,1,1.5,2}[random.nextInt(4)];if(theme==2)beats=3+random.nextInt(4);
        noteEnd=sample+(long)(beats*beat*RATE);rest=random.nextInt(theme==0?12:theme==1?6:3)==0;
      }
      double age=(sample-noteStart)/(double)RATE,remaining=(noteEnd-sample)/(double)RATE;
      double env=Math.min(1,age/(theme==2?.65:.09))*Math.min(1,remaining/(theme==2?.95:.22));
      phase=(phase+freq/RATE)%1;bassPhase=(bassPhase+bass/RATE)%1;padPhase=(padPhase+pad/RATE)%1;
      // Soft sustained strings with occasional plucked articulation; no percussive repeated ostinato.
      double voice=sine(phase)+(theme==0?.32:theme==1?.15:.045)*sine(phase*2)+(theme==0?.15:theme==1?.05:0)*sine(phase*3);
      double phrase=.72+.28*sine(t/19);double articulation=theme==0?.65+.35*Math.exp(-age*3):1;
      double lead=rest?0:voice*env*(theme==2?.20:.34)*articulation;
      double chordFade=Math.min(1,(t%(beat*4))/.35)*Math.min(1,(beat*4-t%(beat*4))/.45);
      double backing=(sine(bassPhase)*(theme==2?.065:.11)+sine(padPhase)*(theme==2?.045:.08))*chordFade;
      gain+=(volume-gain)*.0006;
      double value=(lead+backing)*phrase*gain*(theme==2?.65:1)*Math.min(1,t/(theme==2?3:1.5));
      out[i]=(short)(Math.max(-.95,Math.min(.95,value))*28000);
    }
  }
}
