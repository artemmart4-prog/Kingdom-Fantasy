package local.castle.tv;

/** Polyphonic score player: original festival dance or Beethoven's actual first movement. */
final class ChipScore {
  static final int RATE=LivingScore.RATE, N=4096;
  static final double[] CHIP=new double[N],SOFT=new double[N],FREQ=new double[128];
  static {for(int i=0;i<N;i++){double a=i*Math.PI*2/N;CHIP[i]=(Math.sin(a)+Math.sin(a*3)/3+Math.sin(a*5)/5+Math.sin(a*7)/7)*.7;SOFT[i]=(Math.sin(a)-Math.sin(a*3)/9+Math.sin(a*5)/25)*.85;}for(int i=0;i<128;i++)FREQ[i]=440*Math.pow(2,(i-69)/12.0)/RATE;}
  final int[][] data;final boolean night; final Voice[] voices=new Voice[24];
  int part=0,index=0;long sample=0,end=0;double gain=0;
  static final class Voice {long start,end;int midi,velocity;double phase;boolean active;}
  ChipScore(boolean n){night=n;data=n?MoonlightNotes.PARTS:FestivalNotes.PARTS;for(int i=0;i<voices.length;i++)voices[i]=new Voice();for(int[] p:data)for(int i=0;i<p.length;i+=4)end=Math.max(end,(long)(p[i]+p[i+1])*RATE/1000);end+=RATE*5;}
  void fill(short[] out,float volume){
    double[] table=night?SOFT:CHIP;
    for(int i=0;i<out.length;i++,sample++){
      if(sample>=end){sample=0;part=0;index=0;for(Voice v:voices)v.active=false;}
      while(part<data.length){int[] p=data[part];if(index>=p.length){part++;index=0;continue;}if((long)p[index]*RATE/1000>sample)break;
        Voice use=null;for(Voice v:voices)if(!v.active){use=v;break;}if(use==null){use=voices[0];for(Voice v:voices)if(v.end<use.end)use=v;}
        use.start=sample;use.end=sample+(long)p[index+1]*RATE/1000;use.midi=p[index+2];use.velocity=p[index+3];use.phase=0;use.active=true;index+=4;
      }
      double value=0;
      for(Voice v:voices){if(!v.active)continue;double age=(sample-v.start)/(double)RATE,left=(v.end-sample)/(double)RATE;
        double release=night?.45:.06;if(left< -release){v.active=false;continue;}
        double attack=Math.min(1,age/(night?.035:.008));double envelope=attack*(left>=0?1:Math.max(0,1+left/release));
        envelope*=night?.55+.45*Math.exp(-age*1.7):.5+.5*Math.exp(-age*6);
        v.phase=(v.phase+FREQ[v.midi])%1;value+=table[(int)(v.phase*N)]*envelope*v.velocity/127.0;
      }
      gain+=(volume-gain)*.0007;double fade=Math.min(1,sample/(double)(RATE*(night?2:1)));
      out[i]=(short)(Math.max(-.95,Math.min(.95,value*(night?.045:.20)*gain*fade))*28000);
    }
  }
}
