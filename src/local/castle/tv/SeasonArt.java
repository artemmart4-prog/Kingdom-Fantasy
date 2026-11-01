package local.castle.tv;
import android.graphics.*;

/** Small fixed-count seasonal details. Static snow is baked into the existing scene cache. */
final class SeasonArt {
 final Paint p=new Paint();
 void box(Canvas c,float x,float y,float w,float h,int col,float light){p.setAntiAlias(false);p.setColor(SceneClock.mix(SceneClock.mix(col,0xff102238,.67f),col,light));c.drawRect(x,y,x+w,y+h,p);}
 void snow(Canvas c,float light){
  for(int x=221;x<286;x+=12)box(c,x-1,79,9,2,0xffeaf2f2,light);
  for(int x=194;x<222;x+=9)box(c,x,99,7,2,0xffeaf2f2,light);
  for(int x=285;x<315;x+=9)box(c,x,92,7,2,0xffeaf2f2,light);
  for(int x=238;x<270;x+=9)box(c,x,51,7,2,0xffeaf2f2,light);
  box(c,221,86,65,2,0xffedf5f1,light);box(c,184,164,142,2,0xffdbeaf0,light);
  for(int y=177;y<240;y+=9){float half=6+14*(y-174)/66f;box(c,253-half+1,y,half*2-2,1,0xffd3e2e9,light);}
 }
 void leaves(Canvas c,long now,float light){
  for(int i=0;i<8;i++){float t=Math.floorMod(now+i*1297,10500)/10500f;float x=(i<4?65:466)+(i%4-1.5f)*9+(float)Math.sin(t*12+i)*8+t*12,y=(i<4?220:227)+t*63;int col=i%3==0?0xffdc9d45:i%3==1?0xffc56b3c:0xffe2bd64;box(c,x,y,2+(i%2),1+(int)(t*8+i)%2,col,light);}
 }
 void fox(Canvas c,long age,float light){
  float x=SeasonCycle.foxX(age);boolean sniff=SeasonCycle.foxSniff(age);int step=sniff?0:(int)((age/130)%2)*4;float y=492+(sniff?(float)Math.sin(age/170.0)*1.3f:(float)Math.sin(age/180.0));
  // Low snowy body, sweeping brush, pointed ears and a small dark nose.
  box(c,x-28,y-2,28,10,0xffc2d4de,light);box(c,x-36,y-7,16,9,0xffe7eff0,light);box(c,x-40,y-8,9,6,0xfff3f5ef,light);
  box(c,x,y-12,42,17,0xffedf2ef,light);box(c,x+4,y-17,30,8,0xfff5f5ed,light);box(c,x+7,y+2,28,5,0xffc4d4db,light);
  box(c,x+4+step,y+4,5,13-step,0xffdce5e6,light);box(c,x+29-step,y+4,5,13-step,0xffe7edeb,light);
  float head=sniff?10:0;box(c,x+32,y-23+head,18,18,0xfff0f4ef,light);box(c,x+34,y-30+head,5,9,0xffd4e1e5,light);box(c,x+44,y-29+head,5,8,0xffd4e1e5,light);box(c,x+35,y-28+head,2,5,0xff8499a8,light);
  box(c,x+45,y-14+head,13,7,0xffe7eeed,light);box(c,x+54,y-13+head,5,4,0xff283b4b,light);box(c,x+45,y-19+head,2,2,0xff263c4d,light);
  if(sniff){box(c,x+55,y-3+head,1,2,0xffa5bdca,light);box(c,x+60,y-5+head,1,1,0xffa5bdca,light);}
 }
}
