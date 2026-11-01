package local.castle.tv;
import android.graphics.*;
final class ShellGameArt {
 final Bitmap cup=Bitmap.createBitmap(48,44,Bitmap.Config.ARGB_8888);final Paint p=new Paint();final RectF rect=new RectF();
 ShellGameArt(){Canvas c=new Canvas(cup);box(c,8,1,29,4,0xffd4bb8e);box(c,5,5,35,31,0xff8b603c);box(c,8,6,29,29,0xffbe9560);box(c,11,7,5,28,0xffe1bd83);box(c,22,7,2,27,0xff9d744c);box(c,31,7,3,27,0xff8c603e);box(c,3,33,39,6,0xff6e5038);box(c,5,34,35,2,0xffc6aa74);box(c,39,9,7,20,0xffbd9a66);box(c,40,13,3,11,0xff584437);}
 void box(Canvas c,float x,float y,float w,float h,int col){p.setColor(col);p.setAntiAlias(false);c.drawRect(x,y,x+w,y+h,p);}
 void draw(Canvas c,float x,float base,float lift){p.setFilterBitmap(false);rect.set(x-72,base-132-lift,x+72,base-lift);c.drawBitmap(cup,null,rect,p);}
 void ball(Canvas c,float x,float base){box(c,x-8,base-15,16,13,0xffe46960);box(c,x-11,base-12,22,7,0xffe46960);box(c,x-4,base-13,5,4,0xffffb4a1);}
}
