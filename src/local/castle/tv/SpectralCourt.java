package local.castle.tv;
import android.graphics.*;
/** Cached pixel paladin, three small minions and bounded drip animation. */
final class SpectralCourt {
 final Paint p=new Paint();final Bitmap paladin;final Bitmap[][] zombies=new Bitmap[3][2];final Bitmap[] blood=new Bitmap[12];final Bitmap[] mist=new Bitmap[8];final RectF dest=new RectF();
 void box(Canvas c,float x,float y,float w,float h,int color){p.setColor(color);p.setAntiAlias(false);c.drawRect(x,y,x+w,y+h,p);}
 SpectralCourt(){paladin=Bitmap.createBitmap(96,120,Bitmap.Config.ARGB_8888);Canvas c=new Canvas(paladin);
 // Torn translucent mantle behind a human silhouette.
 box(c,27,40,43,29,0x665b83b7);box(c,22,48,50,35,0x775b83b7);box(c,18,65,58,22,0x555b83b7);box(c,21,86,11,15,0x445b83b7);box(c,31,87,8,19,0x335b83b7);box(c,59,84,10,18,0x445b83b7);box(c,68,78,7,15,0x335b83b7);
 // Crested closed helmet, narrow glowing eyes, face guard.
 box(c,36,14,24,6,0x8879b4cf);box(c,32,20,32,23,0xcc6898b2);box(c,36,17,24,23,0xcc96c4d6);box(c,32,21,4,15,0xe0c1e8eb);box(c,36,23,24,4,0xffd0f2f4);box(c,37,28,22,6,0xff233e58);box(c,39,29,6,2,0xffb6ffff);box(c,51,29,6,2,0xffb6ffff);box(c,46,27,4,15,0xff9bd0df);box(c,38,35,6,5,0xbb537991);box(c,52,35,6,5,0xbb537991);box(c,41,42,15,4,0xdd385b76);box(c,46,7,4,13,0xbbdaf7f2);box(c,44,9,8,6,0xff93f4df);
 // Layered pauldrons and breastplate with a luminous oath rune.
 box(c,24,43,17,10,0xbb4b7898);box(c,20,47,21,6,0xdd82b7cc);box(c,24,43,13,3,0xe0d1eeee);box(c,58,43,17,10,0xbb4b7898);box(c,58,47,21,6,0xdd82b7cc);box(c,62,43,13,3,0xe0d1eeee);box(c,34,47,28,24,0xcc547f9e);box(c,36,47,24,4,0xe0b2dde2);box(c,39,54,18,14,0xbb305775);box(c,46,51,4,16,0xff9be5e6);box(c,42,55,12,3,0xffc4faf2);box(c,35,69,27,4,0xddb0c9c6);box(c,46,69,5,5,0xff91f4e4);
 // Arms, shield and a single sword held by the commander.
 box(c,24,53,10,20,0xcc416989);box(c,26,54,5,13,0xcc8dbfd1);box(c,65,53,8,18,0xcc416989);box(c,67,55,4,12,0xcc8dbfd1);box(c,69,66,9,7,0xff8ab8ce);box(c,77,17,3,48,0x9996e6ee);box(c,78,13,2,48,0xe0ddffff);box(c,75,61,9,3,0xffc4eee6);box(c,78,64,3,11,0xff527c99);box(c,76,74,6,3,0xff91e6e9);
 box(c,13,59,17,24,0xaa9acbd4);box(c,15,61,13,20,0xcc315674);box(c,18,79,7,6,0xaa9acbd4);box(c,20,63,3,15,0xff9ae7df);box(c,17,67,9,3,0xff9ae7df);
 // Split armour and dissolving legs keep the figure humanoid.
 box(c,34,74,12,12,0xaa6f9dbb);box(c,50,74,12,12,0xaa6f9dbb);box(c,38,84,7,12,0x9977b3d0);box(c,52,84,7,12,0x9977b3d0);box(c,39,95,5,10,0x6677c6df);box(c,52,94,5,13,0x6677c6df);box(c,36,105,6,4,0x4477c6df);box(c,54,107,5,4,0x4477c6df);
 for(int variant=0;variant<3;variant++)for(int pose=0;pose<2;pose++){Bitmap b=Bitmap.createBitmap(18,25,Bitmap.Config.ARGB_8888);Canvas z=new Canvas(b);int skin=variant==1?0xff7d9d73:variant==2?0xff789f8e:0xff91a777,cloth=variant==1?0xff6c4e69:variant==2?0xff556b7f:0xff78634e;box(z,6,2,7,7,skin);box(z,5,1,7,3,0xff3b5046);box(z,7,4,2,1,0xffd6ecc4);box(z,11,4,2,1,0xffd6ecc4);box(z,9,7,3,1,0xff405448);box(z,6,9,7,8,cloth);box(z,5,9,3,4,skin);box(z,12,10,3,3,skin);box(z,2,pose==0?12:7,5,2,skin);box(z,14,pose==0?12:7,4,2,skin);box(z,6,16,3,6,0xff3e4d48);box(z,11,16,3,pose==0?6:4,0xff3e4d48);box(z,5,22,4,2,skin);box(z,11,pose==0?22:20,5,2,skin);box(z,7,10,2,4,0xffa0ab8d);zombies[variant][pose]=b;}
 for(int f=0;f<12;f++){Bitmap b=Bitmap.createBitmap(80,64,Bitmap.Config.ARGB_8888);Canvas d=new Canvas(b);for(int i=0;i<3;i++){int age=(f+i*4)%12;if(age<2||age>10)continue;int x=12+i*27,y=51+(age-2)*12/9;box(d,x,y,2,2,0xdd963b48);box(d,x,y,1,1,0xffc55a61);}blood[f]=b;}
 for(int f=0;f<8;f++){Bitmap b=Bitmap.createBitmap(96,24,Bitmap.Config.ARGB_8888);Canvas m=new Canvas(b);for(int i=0;i<4;i++){int x=6+i*21+(f+i)%4,y=9+(i*3+f)%6;box(m,x,y,22,5,0x168bcbd9);box(m,x+4,y-3,13,3,0x1b9ed6df);box(m,x+2,y+5,17,2,0x0ea2dfe5);}mist[f]=b;}
 p.setFilterBitmap(false);p.setAlpha(255);
 }
 void commander(Canvas c,float x,float y,long now,long summoned){float bob=(float)Math.sin(now/900.0)*2;float arrival=Math.max(0,Math.min(1,summoned/700f));p.setAlpha(Math.round(225*arrival));p.setFilterBitmap(false);c.drawBitmap(paladin,x-48,y-112+bob,p);p.setAlpha(255);int a=(int)((45+20*Math.sin(now/600.0))*arrival);box(c,x-27,y-4,54,2,(a<<24)|0x87e9e8);box(c,x-20,y+1,40,1,(a<<24)|0x87e9e8);if(summoned<1100){int radius=20+(int)(summoned/35);for(int i=0;i<8;i++){double angle=i*Math.PI/4+summoned/500.0;box(c,x+(float)Math.cos(angle)*radius,y-35+(float)Math.sin(angle)*radius*.4f,3,3,0x888bdcdd);}} }
 void zombie(Canvas c,int id,float x,float y,boolean airborne,int face,float appearance){p.setAlpha(Math.round(255*Math.max(0,Math.min(1,appearance))));float size=id==0?2f:1.6f;c.save();c.translate(Math.round(x),Math.round(y));if(face<0)c.scale(-1,1);dest.set(-9*size,-25*size,9*size,0);p.setFilterBitmap(false);c.drawBitmap(zombies[id][airborne?1:0],null,dest,p);c.restore();p.setAlpha(255);}
 void mist(Canvas c,float x,float y,long now,float fade){if(fade<=0)return;p.setAlpha(Math.round(255*fade));p.setFilterBitmap(false);c.drawBitmap(mist[(int)((now/140)%8)],x-48,y-12,p);p.setAlpha(255);}
 void drips(Canvas c,float x,float y,long now){p.setAlpha(255);p.setFilterBitmap(false);dest.set(x-8,y-10,x+152,y+118);c.drawBitmap(blood[(int)((now/140)%12)],null,dest,p);}
}
