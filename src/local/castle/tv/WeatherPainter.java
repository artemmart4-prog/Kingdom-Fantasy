package local.castle.tv;
import android.graphics.*;

/** Low-density pixel weather. No audio, full-screen lightning flashes or particle allocations. */
final class WeatherPainter {
  final Paint p=new Paint();
  void box(Canvas c,float x,float y,float w,float h,int color){p.setStyle(Paint.Style.FILL);p.setAntiAlias(false);p.setColor(color);c.drawRect(x,y,x+w,y+h,p);}
  void cloud(Canvas c,float x,float y,int width,int grey){box(c,x,y,width,5,grey);box(c,x+width*.13f,y-4,width*.60f,6,grey);box(c,x+width*.28f,y-7,width*.29f,4,grey);box(c,x+width*.66f,y+3,width*.32f,4,grey);}
  void sky(Canvas c,long time,int kind,float daylight){if(kind==WeatherKind.CLEAR)return;int grey=SceneClock.mix(0xff304056,kind==WeatherKind.STORM?0xff748894:0xffb4c4c9,daylight);float drift=(float)Math.sin(time/100000.0)*8;
    cloud(c,32+drift,56,88,grey);cloud(c,320+drift*.7f,66,103,grey);
    if(kind==WeatherKind.STORM||kind==WeatherKind.RAIN)cloud(c,167-drift,29,72,grey);
  }
  void precipitation(Canvas c,long time,int kind,float daylight,boolean fog){if(kind==WeatherKind.RAIN||kind==WeatherKind.STORM){int alpha=daylight>.5f?92:72;p.setColor((alpha<<24)|0xb5d5df);p.setStrokeWidth(1);p.setAntiAlias(false);for(int i=0;i<26;i++){float x=(i*79+23)%512,y=(i*53+(time%10000)/29f)%276;c.drawLine(x,y,x-1.5f,y+4,p);}}
    if(kind==WeatherKind.SNOW)for(int i=0;i<15;i++){float x=(i*73+13+(float)Math.sin(time/1800.0+i)*4)%512,y=(i*47+(time%30000)/140f)%285;box(c,x,y,1.5f,1.5f,0x99edf2e6);}
    if(fog&&kind==WeatherKind.FOG){box(c,0,158,512,13,0x177c9da8);box(c,0,205,512,10,0x1b8da8ae);box(c,0,242,512,7,0x137c9da8);}
  }
  void icon(Canvas c,int kind,float x,float y){int white=0xffd9e0d5;switch(kind){case WeatherKind.CLEAR:box(c,x+3,y,6,10,0xffffd481);box(c,x,y+3,12,4,0xffffd481);break;case WeatherKind.FOG:for(int i=0;i<3;i++)box(c,x,y+i*4,14,1,white);break;default:box(c,x,y+3,14,5,white);box(c,x+3,y,7,5,white);if(kind==WeatherKind.RAIN||kind==WeatherKind.STORM){box(c,x+3,y+9,1,4,0xff91b7d3);box(c,x+9,y+9,1,4,0xff91b7d3);}if(kind==WeatherKind.SNOW){box(c,x+2,y+10,2,2,white);box(c,x+9,y+11,2,2,white);}if(kind==WeatherKind.STORM){box(c,x+6,y+7,3,4,0xffebd079);box(c,x+4,y+11,3,3,0xffebd079);}break;}}
}
