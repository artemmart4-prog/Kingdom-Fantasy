package local.castle.tv;

/** Northern-hemisphere calendar seasons; manual selection never changes device time. */
public final class SeasonCycle {
 public static final int SPRING=1,SUMMER=2,AUTUMN=3,WINTER=4;
 public static final String[] NAMES={"Авто по календарю","Весна","Лето","Осень","Зима"};
 public static int resolve(int mode,int month){if(mode>=1&&mode<=4)return mode;month=Math.floorMod(month-1,12)+1;return month>=3&&month<=5?SPRING:month>=6&&month<=8?SUMMER:month>=9&&month<=11?AUTUMN:WINTER;}
 public static boolean insects(int season){return season!=WINTER;}
 public static int birdMonth(int season,int mode,int actual){return mode==0?actual:season==SPRING?4:season==SUMMER?7:season==AUTUMN?10:1;}
 public static int vegetation(int color,int season){
  if(season==SUMMER)return color;
  int r=(color>>>16)&255,g=(color>>>8)&255,b=color&255;
  float v=(r*.22f+g*.65f+b*.13f)/255f;
  if(season==WINTER)return rgb(151+Math.round(v*102),174+Math.round(v*79),185+Math.round(v*68));
  if(season==AUTUMN)return rgb(116+Math.round(v*122),62+Math.round(v*111),35+Math.round(v*44));
  return rgb(Math.round(r*.88f+17),Math.min(255,Math.round(g*1.12f+12)),Math.round(b*.83f+9));
 }
 static int rgb(int r,int g,int b){return 0xff000000|(Math.min(255,r)<<16)|(Math.min(255,g)<<8)|Math.min(255,b);}
 /** Fox follows the existing exclusive wolf slot, with a paused sniff in the middle. */
 public static float foxX(long age){age=Math.max(0,Math.min(12000,age));return age<4200?-95+575*age/4200f:age<7800?480+(float)Math.sin((age-4200)*Math.PI*2/3600.0)*12:480+650*(age-7800)/4200f;}
 public static boolean foxSniff(long age){return age>=4200&&age<7800;}
}
