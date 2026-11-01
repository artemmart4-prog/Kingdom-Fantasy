package local.castle.tv;
import android.graphics.*;

/** Small code-drawn sprites. Landscape coordinates 512x320; story coordinates 1024x576. */
final class SceneMotion {
  final Paint p=new Paint();float light=1,lampStrength=0;final int[] starX=new int[65],starY=new int[65];
  SceneMotion(){java.util.Random r=new java.util.Random(18);for(int i=0;i<65;i++){starX[i]=r.nextInt(512);starY[i]=r.nextInt(111);}}
  void rawBox(Canvas c,float x,float y,float w,float h,int col){p.setStyle(Paint.Style.FILL);p.setAntiAlias(false);p.setColor(col);c.drawRect(x,y,x+w,y+h,p);}
  void birds(Canvas c,long now,int month,float daylight){float t=BirdCycle.progress(now);if(t<0||daylight<.25f)return;int direction=BirdCycle.direction(month);float lead=direction>0?-35+600*t:547-600*t;
    p.setColor(SceneClock.mix(0xff374253,0xff58616a,1-daylight));p.setStrokeWidth(1);p.setAntiAlias(false);
    for(int i=0;i<5;i++){float x=lead-direction*i*8,y=31+i%3*5+(float)Math.sin(now/1300.0+i)*1.5f;float wing=(float)Math.sin(now/150.0+i)*2;c.drawLine(x-3,y-wing,x,y+1,p);c.drawLine(x,y+1,x+3,y-wing,p);}
  }
  void sea(Canvas c,long age,float lighting){if(age<0||age>6500)return;light=lighting;c.save();c.clipRect(83,168,184,237);
    float height=SeaHunt.height(age);if(height>0){
      // Coiled back, curved neck, open snout, then a clean dive below the surface.
      box(c,112,222,24,4,0xff397f83);box(c,115,219,14,4,0xff4d9992);
      for(int j=0;j<height;j+=2){float x=125+(float)Math.sin(j/height*Math.PI)*6;box(c,x,223-j,7,3,0xff398e86);box(c,x+1,223-j,2,3,0xff99c6aa);if(j%6==0)box(c,x-2,223-j,2,2,0xff426d84);}
      float y=223-height;box(c,125,y-6,13,9,0xff4ca38f);box(c,134,y-4,12,5,0xff4ca38f);box(c,127,y-9,2,5,0xff8cc7ad);box(c,132,y-8,2,3,0xff8cc7ad);rawBox(c,134,y-4,1,1,0xffe4c67e);
      if(age>=1400&&age<1800){box(c,135,y+1,12,4,0xff142e39);box(c,134,y+4,12,2,0xff91c4a5);}else box(c,135,y+1,11,2,0xff91c4a5);
    }
    if(SeaHunt.fishVisible(age)){float x=SeaHunt.fishX(age),y=SeaHunt.fishY(age);box(c,x-3,y-1,3,5,0xffdcad67);box(c,x,y-2,7,5,0xffe7c876);box(c,x+1,y+2,4,1,0xffffe3a4);box(c,x+5,y-1,1,1,0xff263949);}
    if(age>=3800&&age<4900){float t=(age-3800)/1100f;for(int i=-3;i<=3;i++)if(i!=0)box(c,129+i*(3+t*5),222-(float)Math.sin(t*Math.PI)*(10-Math.abs(i)),2,2,0xffb4ded4);}
    if(age>=500&&age<6000){float t=age/6000f;box(c,108-t*4,227,25+t*17,1,0xff8abdb8);box(c,115-t*5,230,13+t*20,1,0xffb4d3ca);}c.restore();
  }
  void cracks(Canvas c,float x,float y,float strength,long age){if(strength<=0)return;c.save();c.clipRect(x+4,y+2,x+140,y+38);p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(2);p.setAntiAlias(false);p.setColor(((int)(strength*220)<<24)|0x070e15);
    c.drawLine(x+72,y+2,x+68,y+11,p);c.drawLine(x+68,y+11,x+77,y+19,p);c.drawLine(x+77,y+19,x+72,y+29*strength,p);c.drawLine(x+68,y+11,x+53,y+8,p);c.drawLine(x+53,y+8,x+43,y+16*strength,p);c.drawLine(x+77,y+19,x+92,y+14,p);c.drawLine(x+92,y+14,x+103,y+22*strength,p);p.setStyle(Paint.Style.FILL);c.restore();
    if(age<260){float t=age/260f;for(int i=-2;i<=2;i++)if(i!=0)rawBox(c,x+72+i*(12+t*13),y-3-(float)Math.sin(t*Math.PI)*9,3,2,((int)((1-t)*150)<<24)|0xb5b197);}
  }
  void nightSky(Canvas c,long now,boolean animate,float lighting,float hour){
    if(lighting>=.7f)return;float night=1-lighting/.7f;
    float phase=hour>=20?(hour-20)/10:(hour+4)/10;int moonX=Math.round(32+448*phase),moonY=Math.round(126-(float)Math.sin(Math.PI*phase)*99);
    for(int i=0;i<65;i++){if(!SceneClock.sunVisible(hour)&&starX[i]>=moonX-4&&starX[i]<moonX+26&&starY[i]>=moonY&&starY[i]<moonY+24)continue;float pulse=animate?SkyCycle.twinkle(now,i):.7f;int alpha=(int)((75+180*pulse)*night);int x=starX[i],y=starY[i];
      if(i%7==0){rawBox(c,x-1,y-1,3,3,((int)(alpha*.15f)<<24)|0xabcfff);if(pulse>.82f){rawBox(c,x-2,y,5,1,((int)(alpha*.5f)<<24)|0xc9e4ff);rawBox(c,x,y-2,1,5,((int)(alpha*.5f)<<24)|0xc9e4ff);}}
      rawBox(c,x,y,i%5==0?2:1,1,(alpha<<24)|0xd4e7ff);
    }
    float progress=animate?SkyCycle.comet(now):-1;if(progress<0)return;
    float x=-38+590*progress,y=12+102*progress;float fade=Math.min(1,progress*7)*Math.min(1,(1-progress)*7)*night;
    for(int i=34;i>=0;i--){int alpha=(int)(fade*(1-i/35f)*155);rawBox(c,x-i*2,y-i*.36f,3,i<5?2:1,(alpha<<24)|0xaedbff);}
    rawBox(c,x-1,y-1,4,3,((int)(fade*255)<<24)|0xf2f5df);rawBox(c,x-3,y-3,7,6,((int)(fade*32)<<24)|0xaddfff);
  }
  void box(Canvas c,float x,float y,float w,float h,int col){p.setStyle(Paint.Style.FILL);p.setAntiAlias(false);p.setColor(SceneClock.mix(SceneClock.mix(col,0xff050c1e,.52f),col,light));c.drawRect(x,y,x+w,y+h,p);}
  void clouds(Canvas c,long now,boolean animate,float lighting){light=lighting;float drift=animate?(float)Math.sin(now/110000.0)*13:0;
    cloud(c,52+drift,63);cloud(c,204+drift*.65f,83);cloud(c,426+drift*.8f,90);
  }
  void cloud(Canvas c,float x,float y){box(c,(int)x,y,43,3,0xfff1f0da);box(c,(int)x+10,y-3,24,3,0xfff1f0da);box(c,(int)x+27,y+3,37,2,0xfff1f0da);}
  void flag(Canvas c,int x,int y,int width,int height,long now){for(int dx=0;dx<width;dx++){int wave=Math.round((float)Math.sin(now/240.0-dx*.35)*1.5f);box(c,x+dx,y+wave,1,height-(dx>width*.7f?1:0),dx%5==0?0xff98495d:0xffce6e79);}}
  void details(Canvas c,long now,boolean animate,boolean boats,float lighting){light=lighting;long t=animate?now:0;flag(c,252,45,17,7,t);
    for(int i=0;i<22;i++){int x=i<11?9+i*15:337+(i-11)*15;int y=174+(i*13)%43;float offset=animate?(float)Math.sin(now/1500.0+i)*2:0;if(boats&&y<192&&((x>86&&x<133)||(x>350&&x<389)||(x>444&&x<484)))continue;box(c,x+offset,y,6+i%9,1,0xff9bcdc5);}
  }
  void magic(Canvas c,float x,float y,long now,float lighting,boolean moving,int face,float progress){
    float night=1-lighting;
    // Small translucent pools illuminate the nearby tile without a full-screen effect.
    p.setStyle(Paint.Style.FILL);p.setAntiAlias(true);
    for(int i=3;i>=1;i--){p.setColor(((int)(night*(i==1?27:12))<<24)|0xadddff);c.drawOval(x-13-i*10,y-43-i*7,x+13+i*10,y+14+i*6,p);}
    if(moving){float tail=(float)Math.sin(Math.PI*progress);for(int i=1;i<=4;i++){int alpha=(int)(tail*(130-i*23));rawBox(c,x-face*(i*12+4)-5,y-26+i%2*3,12-i*2,3,(alpha<<24)|0xcceaff);}}
    // A narrow halo and orbiting motes replace the disconnected wings.
    p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(2);p.setAntiAlias(false);p.setColor(0xcce6d69b);c.drawOval(x-10,y-50,x+10,y-46,p);p.setStyle(Paint.Style.FILL);
    for(int i=0;i<3;i++){double a=now/1150.0+i*Math.PI*2/3;rawBox(c,x+(float)Math.cos(a)*23,y-22+(float)Math.sin(a)*12,2,2,0xb7c5e8f2);}
  }
  float bridgeHalf(float t){return 6+14*t;}
  void bridge(Canvas c,float lighting){light=lighting;
    // Perspective planks join the castle platform to the foreground bank.
    for(int y=174;y<=240;y+=3){float t=(y-174)/66f,half=bridgeHalf(t);box(c,253-half,y,half*2,3,y%2==0?0xffaa8051:0xff8a623e);box(c,253-half+2,y,half*2-4,1,0xffc49b65);}
    for(int side=-1;side<=1;side+=2){
      for(int y=174;y<240;y++){float t=(y-174)/66f;float x=253+side*(bridgeHalf(t)+3);int sag=(int)(Math.sin(t*Math.PI)*5);box(c,x,y-14+sag,2,2,0xff584733);box(c,x,y-7+sag,1,1,0xff88734d);}
      for(int i=0;i<=4;i++){float t=i/4f,y=174+t*66,x=253+side*(bridgeHalf(t)+3);box(c,x-1,y-17,3,21,0xff674936);box(c,x-2,y-18,5,3,0xffc49b65);}
    }
    for(int side=-1;side<=1;side+=2)for(int i=0;i<=10;i++){float t=i/10f,y=174+t*66,x=253+side*(bridgeHalf(t)+3);int sag=(int)(Math.sin(t*Math.PI)*5);box(c,x,y-13+sag,1,12-sag,0xffb9a078);}
    bridgeLamps(c,lighting,false);
  }
  void lamp(Canvas c,int x,int base,boolean near){int height=near?30:23;float night=lampStrength;
    p.setStyle(Paint.Style.FILL);p.setAntiAlias(false);
    if(night>0){for(int i=3;i>=1;i--){p.setColor(((int)(night*11)<<24)|0xffca79);c.drawOval(x-i*4,base-height-i*4,x+i*4,base-height+i*4,p);}}
    box(c,x-1,base-height,2,height,0xff4b4239);box(c,x-3,base,6,2,0xff675649);box(c,x-5,base-height-3,10,3,0xff534b3d);box(c,x-3,base-height,6,8,0xff7a6951);
    rawBox(c,x-2,base-height+1,4,5,RealmAmbience.glass(night));box(c,x-4,base-height+8,8,2,0xff534b3d);
  }
  void bridgeLamps(Canvas c,float lighting,boolean near){light=lighting;if(near){lamp(c,225,248,true);lamp(c,281,248,true);}else{lamp(c,234,176,false);lamp(c,272,176,false);}}
  static final String[] HORSE={"..............bb...",".............bbbb..","............bbkbbb.","............bbbbbbb","...bbbbbbbbbbbbb...","..bbbbbbbbbbbb.....",".bbbbbbbbbbbb......",".bbbbbbbbbbb.......","..bb......bb.......","..bb......bb......."};
  static final String[] HARE={"......ww.w...","......ww.w...","......wwww...",".....wwkwww..","..wwwwwwww...",".wwwwwwwww...","wwwwwwww.....",".wwwwwww....."};
  static final String[] WOLF={"..............g..g..",".............ggggg..","............ggkggggg","....gggggggggggggggg","...ggggggggggggggg..","..gggggggggggggg....","gggggggggggggg......",".ggggggggggggg......"};
  void sprite(Canvas c,String[] rows,float x,float y,int scale,int face){for(int r=0;r<rows.length;r++)for(int col=0;col<rows[r].length();col++){char ch=rows[r].charAt(col);if(ch=='.')continue;int color=ch=='b'?0xffa7744e:ch=='g'?0xff8c9ca7:ch=='k'?0xff17202c:0xffe5e4d2;box(c,x+(face>0?col:(rows==HARE?12:rows==HORSE?18:19)-col)*scale,y+r*scale,scale,scale,color);}}
  void wheel(Canvas c,float x,float y,long now){box(c,x-10,y-8,20,16,0xff4e382e);box(c,x-7,y-11,14,22,0xff4e382e);box(c,x-6,y-6,12,12,0xffb9925f);box(c,x-2,y-2,4,4,0xff4e382e);if((now/150)%2==0){box(c,x-1,y-8,2,16,0xff4e382e);box(c,x-8,y-1,16,2,0xff4e382e);}else{for(int j=-6;j<=6;j+=3){box(c,x+j,y+j,3,3,0xff4e382e);box(c,x+j,y-j,3,3,0xff4e382e);}}}
  void story(Canvas c,long elapsed,float lighting){light=lighting;int kind=IdleStory.kind(elapsed);if(kind==0||kind>3)return;long age=IdleStory.age(elapsed);float x=IdleStory.x(elapsed);boolean still=IdleStory.watching(elapsed);int step=still?0:(int)((age/120)%2)*4;
    if(kind==1){
      box(c,x-144,476,90,32,0xff9b7048);box(c,x-149,482,100,5,0xffc49b61);for(int j=0;j<4;j++)box(c,x-138+j*21,478,3,29,0xff674936);
      box(c,x-133,450,58,28,0xffc8bb92);box(c,x-128,442,48,9,0xffe4d5ae);box(c,x-120,438,32,5,0xffe4d5ae);
      for(int j=0;j<4;j++){box(c,x-137+j*19,447,2,30,0xff71523b);box(c,x-138+j*19,446,4,2,0xffaa8354);}box(c,x-140,447,69,2,0xff8a6c49);box(c,x-83,438,2,38,0xff6b503b);box(c,x-92,438,11,2,0xff6b503b);box(c,x-94,440,8,12,0xff8b714b);rawBox(c,x-92,442,4,7,RealmAmbience.glass(lampStrength));
      // Hooded traveller seated at the front with reins.
      box(c,x-76,463,18,20,0xff476953);box(c,x-77,453,17,14,0xff354b42);box(c,x-67,457,8,8,0xffd7ab80);box(c,x-60,477,23,3,0xff2b3036);
      wheel(c,x-128,509,age);wheel(c,x-70,509,age);box(c,x-52,493,61,3,0xff674936);
      sprite(c,HORSE,x-2,466+(step>0?1:0),3,1);box(c,x+6+step,496,6,18-step,0xff674936);box(c,x+29-step,496,6,18-step,0xff674936);box(c,x+42,469,3,20,0xff28303b);box(c,x+39,472,19,3,0xff28303b);
    }else if(kind==2){int bounce=still?0:(int)(Math.abs(Math.sin(age/110.0))*7);int face=still&&((age/450)%2==0)?-1:1;sprite(c,HARE,x,488-bounce,3,face);box(c,x+5,510,15,5,0xffe5e4d2);box(c,x+23+(still?0:step),510-bounce,12,5,0xffe5e4d2);
    }else{int sniff=still?(int)(Math.sin(age/140.0)*2):0;sprite(c,WOLF,x,480+sniff,3,1);box(c,x+12+step,501,6,14,0xff71838f);box(c,x+43-step,501,6,14,0xff71838f);if(still){box(c,x+39,489+sniff,12,12,0xff8c9ca7);box(c,x+48,492+sniff,10,12,0xff8c9ca7);box(c,x+53,500+sniff,11,6,0xff8c9ca7);box(c,x+62,503+sniff,4,3,0xff17202c);}}
  }
}
