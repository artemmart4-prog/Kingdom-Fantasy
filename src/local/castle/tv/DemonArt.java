package local.castle.tv;
import android.graphics.*;

/** Tiny cached sprite and six flame frames; no particle allocation during drawing. */
final class DemonArt {
  final Paint p=new Paint();final RectF dst=new RectF();final Bitmap sprite;final Bitmap[] fire=new Bitmap[6];
  final String[] rows={".h..........h...",".hh..c.c.c.hh...","..h..ccccc.h....","...h.ccccc.h....","....ssssss......","....se..es......","....ssssss......",".....ssss.......","...rrddddrr.....","..rrddccddrr....",".rrrddddddrrr...","..rrddddddrr....","...rddddddr.....","....dd..dd......","....dd..dd......","...ddd..ddd....."};
  DemonArt(){sprite=Bitmap.createBitmap(16,16,Bitmap.Config.ARGB_8888);Canvas c=new Canvas(sprite);for(int y=0;y<rows.length;y++)for(int x=0;x<rows[y].length();x++){int color=0;switch(rows[y].charAt(x)){case 'h':color=0xffa44f60;break;case 'c':color=0xffffce72;break;case 's':color=0xff9187ab;break;case 'e':color=0xffff6954;break;case 'r':color=0xff733c77;break;case 'd':color=0xff29223f;break;}if(color!=0){p.setColor(color);c.drawRect(x,y,x+1,y+1,p);}}
    for(int f=0;f<6;f++){fire[f]=Bitmap.createBitmap(72,14,Bitmap.Config.ARGB_8888);Canvas fc=new Canvas(fire[f]);for(int i=0;i<12;i++){int x=i*6,h=3+(i*7+f*3)%9;p.setColor(0x88664076);fc.drawRect(x,16-h,x+6,14,p);p.setColor(0xffb74c61);fc.drawRect(x+1,16-h,x+5,14,p);fc.drawRect(x+2+(i+f)%2,14-h,x+4+(i+f)%2,16-h,p);p.setColor(0xffe17a5e);fc.drawRect(x+2,18-h,x+4,14,p);p.setColor(0xffffc178);fc.drawRect(x+2,20-h,x+3,14,p);}}p.setFilterBitmap(false);
  }
  void flame(Canvas c,float x,float y,long now){p.setAlpha(255);dst.set(x,y-20,x+144,y+7);c.drawBitmap(fire[(int)((now/120)%6)],null,dst,p);}
  void king(Canvas c,float x,float y,int face,float alpha){p.setAlpha(Math.round(255*alpha));dst.set(x-24,y-48,x+24,y);c.save();if(face<0)c.scale(-1,1,x,y);c.drawBitmap(sprite,null,dst,p);c.restore();p.setAlpha(255);}
  void portal(Canvas c,float x,float y,float strength){int a=Math.round(190*Math.max(0,Math.min(1,strength)));p.setColor((a<<24)|0xaf70dc);for(int i=0;i<4;i++){c.drawRect(x-22+i*4,y-52-i*3,x-17+i*4,y+2+i*2,p);c.drawRect(x+17-i*4,y-52-i*3,x+22-i*4,y+2+i*2,p);}p.setColor((a<<24)|0xffc78d);c.drawRect(x-24,y+2,x+24,y+5,p);}
}
