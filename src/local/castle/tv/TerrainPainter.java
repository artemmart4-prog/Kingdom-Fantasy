package local.castle.tv;
import android.graphics.*;
import java.util.Random;

/** Detailed pixel geology and vegetation, drawn only when the cached landscape changes. */
final class TerrainPainter {
  final Paint p=new Paint();final Path path=new Path();float light;int season=SeasonCycle.SUMMER;
  int color(int day){return SceneClock.mix(SceneClock.mix(day,0xff050d22,.66f),day,light);}
  void rect(Canvas c,float x,float y,float w,float h,int col){p.setStyle(Paint.Style.FILL);p.setAntiAlias(false);p.setColor(color(SeasonPalette.foliage(col)?SeasonCycle.vegetation(col,season):col));c.drawRect(x,y,x+w,y+h,p);}
  void poly(Canvas c,int col,float... xy){path.reset();path.moveTo(Math.round(xy[0]),Math.round(xy[1]));for(int i=2;i<xy.length;i+=2)path.lineTo(Math.round(xy[i]),Math.round(xy[i+1]));path.close();p.setColor(color(col));p.setStyle(Paint.Style.FILL);p.setAntiAlias(false);c.drawPath(path,p);}
  void peak(Canvas c,int x,int y,int w,int h,int base,boolean snow,int seed){
    poly(c,base,x-w*.5f,y+h,x-w*.36f,y+h*.65f,x-w*.24f,y+h*.48f,x-w*.18f,y+h*.53f,x-w*.05f,y+h*.13f,x,y,x+w*.08f,y+h*.18f,x+w*.15f,y+h*.22f,x+w*.22f,y+h*.43f,x+w*.30f,y+h*.38f,x+w*.5f,y+h);
    c.save();c.clipPath(path);
    int bright=SceneClock.mix(base,0xffb5c9c3,.27f),dark=SceneClock.mix(base,0xff34495f,.42f);
    poly(c,bright,x,y,x-w*.10f,y+h*.44f,x-w*.30f,y+h*.77f,x-w*.5f,y+h,x+w*.03f,y+h);
    poly(c,dark,x,y,x+w*.06f,y+h*.28f,x+w*.02f,y+h*.46f,x+w*.13f,y+h*.63f,x+w*.09f,y+h*.79f,x+w*.20f,y+h,x+w*.5f,y+h);
    poly(c,SceneClock.mix(base,0xffc8d4cc,.15f),x+w*.30f,y+h*.38f,x+w*.22f,y+h*.76f,x+w*.36f,y+h,x+w*.5f,y+h);
    // Split rock faces, ledges and gullies follow the ridge instead of flat triangle fills.
    Random r=new Random(seed);for(int i=0;i<65;i++){int yy=y+18+r.nextInt(Math.max(1,h-18));int xx=x-w/2+r.nextInt(w);rect(c,xx,yy,3+r.nextInt(14),1+r.nextInt(2),i%3==0?bright:dark);}
    for(int j=18;j<h;j+=5){float t=j/(float)h;rect(c,x-(float)Math.sin(t*8)*w*.035f-j*.09f,y+j,2,5,dark);rect(c,x+w*.17f+j*.09f,y+j+9,2,5,bright);}
    if(snow){poly(c,0xffedf1e2,x,y,x-w*.05f,y+h*.13f,x-w*.13f,y+h*.39f,x-w*.07f,y+h*.30f,x-w*.04f,y+h*.43f,x,y+h*.24f,x+w*.035f,y+h*.32f,x+w*.07f,y+h*.26f,x+w*.15f,y+h*.36f,x+w*.08f,y+h*.18f);
      poly(c,0xffb7d1d9,x,y,x+w*.02f,y+h*.22f,x+w*.07f,y+h*.26f,x+w*.15f,y+h*.36f,x+w*.08f,y+h*.18f);
      for(int j=9;j<h*.3f;j+=7)rect(c,x-j*.16f,y+j,2,4,0xffb7d1d9);
    }c.restore();
  }
  void mountains(Canvas c,float lighting,boolean snow){light=lighting;
    peak(c,20,94,165,88,0xff9db7c7,false,101);peak(c,296,47,235,132,0xff96acbe,snow,102);peak(c,462,83,185,100,0xff9eb7c3,snow,103);
    peak(c,118,57,237,123,0xff7898a7,snow,201);peak(c,208,76,185,106,0xff688596,snow,202);peak(c,388,61,262,125,0xff7e9da8,snow,203);peak(c,520,112,165,78,0xff92aeb6,snow,204);
    // A little cave and a narrow meltwater cascade add landmarks to the massif.
    rect(c,73,149,9,2,0xff9fb1b0);rect(c,75,145,6,6,0xff364b5c);rect(c,76,144,4,2,0xff364b5c);
    rect(c,430,139,3,28,0xffb5d7d9);rect(c,432,147,2,20,0xffd4e7df);rect(c,427,165,10,2,0xffaacdd2);
  }
  void tree(Canvas c,int x,int base,int height){rect(c,x-2,base-height+15,5,height-15,0xff765840);rect(c,x+2,base-24,7,3,0xff765840);rect(c,x-8,base-33,8,3,0xff765840);
    int top=base-height;if(season==SeasonCycle.WINTER){rect(c,x-14,top+19,14,2,0xffe4edf0);rect(c,x+1,base-25,9,2,0xffe4edf0);rect(c,x-9,base-35,10,2,0xffe4edf0);rect(c,x-2,top+3,4,19,0xff765840);rect(c,x-3,top+2,6,2,0xfff1f4ed);rect(c,x-5,base,13,2,0xffd2e2e7);return;}rect(c,x-17,top+9,35,19,0xff3c674a);rect(c,x-11,top,23,35,0xff497b4f);rect(c,x-23,top+17,15,16,0xff578a51);rect(c,x+9,top+15,15,16,0xff3c674a);
    for(int i=0;i<14;i++)rect(c,x-13+(i*11)%29,top+5+(i*7)%23,4,2,i%2==0?0xff80a85f:0xff325e45);
    if(season==SeasonCycle.SPRING)for(int i=0;i<12;i++){int xx=x-16+(i*13)%33,yy=top+4+(i*7)%27;rect(c,xx,yy,4,3,i%3==0?0xfff5e8dd:0xffedbdd2);rect(c,xx+1,yy+1,1,1,0xffd996b3);}
    rect(c,x-4,base,10,2,0xff496d47);
  }
  void bush(Canvas c,int x,int y){rect(c,x-8,y-6,17,7,0xff3c754c);rect(c,x-5,y-10,11,10,0xff558b52);rect(c,x-9,y-3,19,3,0xff365c44);rect(c,x-3,y-9,5,2,0xff88ac62);}
  void tracks(Canvas c,float lighting){light=lighting;for(int x=0;x<512;x+=8){int bend=(int)(Math.sin(x/70.0)*1.4);rect(c,x,285+bend,7,1,0xff6f7550);rect(c,x,292+bend,7,1,0xff6f7550);if(x%24==0){rect(c,x,286+bend,3,1,0xff9ba16d);rect(c,x,293+bend,3,1,0xff9ba16d);}}}
  void foreground(Canvas c,float lighting){light=lighting;tree(c,65,273,58);tree(c,466,279,56);
    for(int[] a:new int[][]{{17,253},{112,253},{162,274},{337,252},{409,270},{496,300},{42,310},{371,306}})bush(c,a[0],a[1]);
    if(season==SeasonCycle.WINTER){for(int i=0;i<18;i++){int x=16+(i*67)%483,y=245+(i*23)%68;if(x>210&&x<294&&y<273)continue;rect(c,x,y,8+i%7,1,0xffeef4f2);rect(c,x+2,y-1,4+i%4,1,0xffe7f0f1);}}
    Random r=new Random(319);for(int i=0;i<(season==SeasonCycle.WINTER?0:52);i++){int x=8+r.nextInt(495),y=243+r.nextInt(72);if(x>210&&x<294&&y<273)continue;if(y>=278&&y<=297)continue;rect(c,x,y,1,4,0xff365e45);int col=season==SeasonCycle.AUTUMN?(i%2==0?0xffd69b48:0xffbe713d):i%4==0?0xfff0d57d:i%4==1?0xffe5b6c9:i%4==2?0xffeae5cd:0xffa6bfde;rect(c,x-1,y-1,3,2,col);rect(c,x,y-2,1,4,col);}
    // Small shore stones, a mossy log and mushrooms stay clear of the cart's route.
    for(int[] a:new int[][]{{133,262},{306,304},{393,252}}){rect(c,a[0]-4,a[1]-3,9,4,0xff73897d);rect(c,a[0]-2,a[1]-5,5,2,0xffa3ae94);}
    rect(c,327,305,19,5,0xff785b3f);rect(c,329,304,16,2,0xff90a46a);rect(c,327,306,3,4,0xffc0a372);
    rect(c,184,255,8,8,0xff795d43);rect(c,182,254,12,2,0xffb69c68);
    for(int[] a:new int[][]{{28,270},{353,260}}){rect(c,a[0],a[1],2,4,0xffd3c7a7);rect(c,a[0]-2,a[1]-2,6,2,0xffb96e66);rect(c,a[0],a[1]-3,2,1,0xffd6b28d);}
  }
}
