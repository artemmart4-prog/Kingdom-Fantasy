package local.castle.tv;

import android.graphics.*;

/** Cached 512x288 stall, cached cards, finite entry and low-frequency idle gestures. */
final class MerchantScene {
  final Paint p=new Paint();
  int season=SeasonCycle.SUMMER,weatherKind=WeatherKind.CLEAR,cachedSeason=-1,cachedWeather=-1;long weatherTime;boolean fog;
  final RectF cardBounds=new RectF();
  Bitmap background;int cachedLight=-1,cachedLamp=-1;float lampStrength=0;Typeface type=Typeface.MONOSPACE;
  final String[] titles={"ПРОДОЛЖИТЬ","МОИ СЕРВИСЫ","ПОДБОРКА","ЛЕТОПИСЬ"};
  void box(Canvas c,float x,float y,float w,float h,int color){p.setColor(color);p.setStyle(Paint.Style.FILL);p.setAntiAlias(false);c.drawRect(x,y,x+w,y+h,p);}
  int shade(int color,float day){return SceneClock.mix(SceneClock.mix(color,0xff10182b,.68f),color,day);}
  void b(Canvas c,float x,float y,float w,float h,int color,float day){box(c,x,y,w,h,shade(color,day));}
  void txt(Canvas c,String s,float x,float y,float size,int color){s=I18n.tr(s);p.setTypeface(I18n.face(type));p.setTextSize(size);p.setColor(color);p.setAntiAlias(true);I18n.draw(c,s,x,y,p);}
  void fit(Canvas c,String s,float x,float y,float size,float width,int color){s=I18n.tr(s);p.setTypeface(type);p.setTextSize(size);int count=p.breakText(s,true,width,null);if(count<s.length())s=s.substring(0,Math.max(0,count-1))+"…";middle(c,s,x,y,size,color);}
  void middle(Canvas c,String s,float x,float y,float size,int color){s=I18n.tr(s);p.setTypeface(type);p.setTextSize(size);txt(c,s,x-p.measureText(s)/2,y,size,color);}
  void jar(Canvas c,int x,int y,int color,float d){b(c,x+2,y,10,3,0xffbca276,d);b(c,x,y+3,14,19,color,d);b(c,x+2,y+7,3,10,0xffcdbf8a,d);b(c,x+1,y+22,12,2,0xff3a3133,d);}
  void barrel(Canvas c,int x,int y,float d){b(c,x+3,y,29,3,0xffac8150,d);b(c,x,y+3,35,35,0xff795233,d);b(c,x+3,y+38,29,3,0xff573d2e,d);for(int k=7;k<30;k+=7)b(c,x+k,y+4,2,33,0xffa77543,d);b(c,x,y+10,35,4,0xff4b4b4d,d);b(c,x,y+29,35,4,0xff4b4b4d,d);}
  void lamp(Canvas c,float d){boolean on=lampStrength>0;if(on){box(c,373,84,34,71,(Math.round(11*lampStrength)<<24)|0xffcb78);box(c,379,93,22,56,(Math.round(18*lampStrength)<<24)|0xffcb78);box(c,384,109,12,27,(Math.round(34*lampStrength)<<24)|0xffcb78);}b(c,389,94,2,13,0xff42312b,d);b(c,384,105,12,3,0xffa1804e,d);box(c,384,108,2,17,0xff403831);box(c,394,108,2,17,0xff403831);box(c,386,109,8,14,RealmAmbience.glass(lampStrength));box(c,389,112,2,8,SceneClock.mix(0xff35474b,0xffffefbd,lampStrength));b(c,383,124,14,3,0xff765136,d);}
  void ensure(float daylight,Typeface face){int key=Math.round(daylight*20);int lampKey=Math.round(lampStrength*20);if(background!=null&&key==cachedLight&&cachedLamp==lampKey&&cachedSeason==season&&cachedWeather==weatherKind&&type==face)return;cachedSeason=season;cachedWeather=weatherKind;cachedLamp=lampKey;type=face;cachedLight=key;if(background==null)background=Bitmap.createBitmap(512,288,Bitmap.Config.RGB_565);Canvas c=new Canvas(background);float d=key/20f;
    for(int y=0;y<288;y+=16)b(c,0,y,512,16,y<128?0xff8eb8c0:y<196?SeasonCycle.vegetation(0xff697f69,season):SeasonCycle.vegetation(0xff53644a,season),d);
    for(int i=0;i<3;i++){int x=i*193-21;for(int y=0;y<60;y+=4)b(c,x+40-y,100+y,y*2+16,4,i%2==0?0xff799098:0xff6b838e,d);}
    // Framing trees and cobbled approach stay outside the counter.
    for(int x:new int[]{28,465}){b(c,x+5,110,7,94,0xff69513c,d);for(int y=95;y<167;y+=12)b(c,x-22+(y%24==0?5:0),y,60-(y-95)/4,14,SeasonCycle.vegetation(0xff496a50,season),d);}
    for(int y=192;y<288;y+=7){int w=280+(y-192)*2;int x=256-w/2;b(c,x,y,w,7,y%14==0?0xff918a71:0xff827c65,d);for(int a=x+(y%14==0?0:9);a<x+w;a+=23)b(c,a,y,2,6,0xff625e52,d);}
    b(c,97,91,320,136,0xff4d3830,d);b(c,110,96,294,100,0xff6d4d38,d);
    for(int x=115;x<402;x+=20)b(c,x,99,1,99,0xff543a2e,d);
    for(int y:new int[]{130,165}){b(c,115,y,284,5,0xffa47846,d);b(c,115,y+5,284,3,0xff3f302b,d);}
    for(int i=0;i<5;i++)jar(c,126+i*19,105, i%2==0?0xff75947b:0xffad7854,d);
    for(int i=0;i<4;i++){b(c,320+i*13,109-i%2*5,9,20+i%2*5,i%2==0?0xffa35b50:0xff7d956d,d);b(c,321+i*13,115,7,2,0xffd1ba7b,d);}
    for(int i=0;i<4;i++)jar(c,128+i*19,140,i%2==0?0xff84959b:0xffb79665,d);
    b(c,320,143,58,17,0xffbdaf83,d);for(int i=0;i<4;i++)b(c,325+i*12,145,2,13,0xff785b41,d);
    b(c,96,75,8,153,0xff795334,d);b(c,99,76,2,151,0xffb08750,d);b(c,407,75,8,153,0xff795334,d);b(c,409,76,2,151,0xffb08750,d);
    // A heavy cloth awning, scallops and hanging wooden sign.
    for(int i=0;i<15;i++){int x=83+i*23;b(c,x,60,23,26,i%2==0?0xff995456:0xffded0a1,d);b(c,x,86,23,7,i%2==0?0xff79444b:0xffb8a77d,d);b(c,x+3,93,17,4,i%2==0?0xff79444b:0xffb8a77d,d);}
    b(c,82,57,346,5,0xff523a2e,d);b(c,171,12,3,21,0xff66503b,d);b(c,337,12,3,21,0xff66503b,d);b(c,158,27,196,30,0xff4a332b,d);b(c,161,30,190,24,0xffaa7e49,d);b(c,164,33,184,18,0xff745039,d);middle(c,"ЛАВКА КУПЦА",256,46,8,shade(0xffffdda0,d));
    // Seated, broad merchant: waistcoat, striped sleeves, cheeks, cap, moustache.
    b(c,215,134,84,51,0xff5a433e,d);b(c,207,145,100,33,0xff9d7657,d);b(c,217,137,79,47,0xff84505b,d);b(c,220,142,73,42,0xffa86666,d);b(c,247,137,17,47,0xffd0b58a,d);b(c,250,142,11,42,0xffead7a7,d);
    for(int y=149;y<181;y+=11)b(c,255,y,3,3,0xff826237,d);
    b(c,231,103,50,32,0xffbc8059,d);b(c,226,110,8,17,0xffd39b6b,d);b(c,279,110,8,17,0xffb67c54,d);b(c,234,101,43,30,0xffe0ad7b,d);b(c,237,106,37,23,0xffefc18b,d);b(c,231,120,49,13,0xffdda171,d);b(c,238,132,35,5,0xffb87f59,d);
    b(c,240,110,7,2,0xff674b3b,d);b(c,265,110,7,2,0xff674b3b,d);b(c,243,113,3,3,0xff2e2b2b,d);b(c,266,113,3,3,0xff2e2b2b,d);b(c,253,115,7,6,0xffc68b60,d);b(c,249,119,15,4,0xff5f4537,d);b(c,240,121,13,5,0xff76513b,d);b(c,262,121,13,5,0xff76513b,d);b(c,248,128,17,2,0xff82523d,d);
    b(c,231,94,49,9,0xff476958,d);b(c,237,86,37,9,0xff5d8263,d);b(c,247,82,22,6,0xff5d8263,d);b(c,229,100,54,4,0xffa59766,d);b(c,269,83,3,17,0xffbca77d,d);b(c,271,76,5,10,0xffdbbc80,d);b(c,275,71,3,7,0xffeee0ad,d);
    // Inventory, leather sacks, stout barrels, counter legs and carved fascia.
    barrel(c,65,199,d);barrel(c,420,194,d);b(c,78,191,19,7,0xffb49668,d);b(c,83,187,8,5,0xff79603f,d);
    b(c,112,224,13,36,0xff64412e,d);b(c,386,224,13,36,0xff64412e,d);b(c,111,252,17,5,0xff987043,d);b(c,384,252,17,5,0xff987043,d);
    b(c,106,181,300,8,0xffd0a167,d);b(c,108,189,296,10,0xffa67b4e,d);b(c,115,199,282,33,0xff765036,d);b(c,119,203,274,24,0xff8f623f,d);
    for(int i=0;i<7;i++){int x=128+i*40;b(c,x,207,20,2,0xffb88951,d);b(c,x,209,2,11,0xffb88951,d);b(c,x+18,209,2,11,0xffb88951,d);b(c,x,220,20,2,0xffb88951,d);}
    for(int x=114;x<402;x+=21){b(c,x,192,3,3,0xffe4ba73,d);b(c,x,184,13,1,0xff9e7448,d);}
    lamp(c,d);b(c,430,173,18,15,0xffb6a27b,d);b(c,434,169,10,4,0xff594533,d);
  }
  void arm(Canvas c,float x,float y,float w,float d){b(c,x,y,w,9,0xffcbb58b,d);for(int i=0;i<w-7;i+=8)b(c,x+i,y+1,2,7,0xff927756,d);b(c,x+w-7,y-2,13,12,0xffe3af7d,d);b(c,x+w+1,y,7,7,0xffefc18b,d);}
  /** Sheltered window: weather remains outside, never across the merchant's cards. */
  void outdoor(Canvas c,float d){
    b(c,285,98,31,47,0xff493729,d);b(c,287,100,27,43,0xffba935e,d);
    c.save();c.clipRect(289,102,312,141);
    b(c,289,102,23,39,weatherKind==WeatherKind.STORM?0xff718592:0xff8dbbc8,d);
    b(c,289,128,23,13,SeasonCycle.vegetation(0xff72915b,season),d);
    if(weatherKind==WeatherKind.CLEAR){b(c,302,106,5,5,lampStrength>.7f?0xffdce6da:0xffffdf91,d);}
    else{float drift=(float)Math.sin(weatherTime/25000.0)*2;b(c,289+drift,110,14,3,0xffb2c4cb,d);b(c,300-drift,115,16,3,0xff9fb4bd,d);}
    b(c,294,124,2,15,0xff715940,d);b(c,291,121,8,6,SeasonCycle.vegetation(0xff49764e,season),d);
    if(season==SeasonCycle.SPRING){b(c,292,122,2,2,0xffe8b8cf,d);b(c,297,125,2,2,0xffe8b8cf,d);}
    if(weatherKind==WeatherKind.RAIN||weatherKind==WeatherKind.STORM)for(int i=0;i<7;i++){float y=102+(i*11+weatherTime/80f)%40;b(c,290+(i*7)%22,y,1,3,0xffb7d8e6,d);}
    if(weatherKind==WeatherKind.SNOW)for(int i=0;i<6;i++){float y=102+(i*13+weatherTime/190f)%40;b(c,290+(i*7)%22,y,1,1,0xfff0f4ed,d);}
    if(fog&&weatherKind==WeatherKind.FOG){box(c,289,124,23,5,0x338ca6b4);box(c,289,132,23,3,0x228ca6b4);}
    c.restore();b(c,299,102,1,39,0xff7b5d3f,d);b(c,289,119,23,1,0xff7b5d3f,d);b(c,285,142,31,3,0xffd0aa72,d);
  }
  void bentArm(Canvas c,float shoulderX,float handX,float handY,float d){p.setColor(shade(0xffcbb58b,d));p.setStrokeWidth(8);p.setAntiAlias(false);c.drawLine(shoulderX,157,handX+4,handY+4,p);p.setStrokeWidth(1);b(c,handX,handY,10,10,0xffe3af7d,d);b(c,handX+2,handY+1,6,6,0xffefc18b,d);}
  void mug(Canvas c,float x,float y,float d){b(c,x,y,11,14,0xffab7944,d);b(c,x+2,y+2,3,10,0xffdab572,d);b(c,x,y,11,3,0xfff1dfb2,d);b(c,x+11,y+3,4,8,0xffa78153,d);b(c,x+11,y+5,2,4,0xff65503b,d);}
  void cardIcon(Canvas c,int n,float x,float y,boolean selected){int ink=0xff684c40,accent=selected?0xff955654:0xff8a6651;switch(n){case 0:box(c,x,y+4,18,14,ink);box(c,x+2,y+6,14,10,0xffb6af8c);for(int a=0;a<7;a++)box(c,x+7+a,y+7+a*.55f,1,8-a,accent);break;case 1:box(c,x,y+3,18,17,ink);for(int i=0;i<3;i++){box(c,x+3+i*5,y+6,3,3,0xffdbc99d);box(c,x+3+i*5,y+12,3,3,0xffdbc99d);}break;case 2:box(c,x+7,y,4,20,accent);box(c,x,y+8,18,4,accent);box(c,x+3,y+3,12,14,accent);box(c,x+7,y+7,4,6,0xffe8d6ac);break;default:box(c,x,y+17,20,2,ink);for(int i=0;i<4;i++)box(c,x+2+i*5,y+13-i*3,3,4+i*3,accent);break;}}
  final Bitmap[] cards=new Bitmap[8];String cardRecent=null,cardRecommendation=null;Bitmap cardPoster,cardPickPoster;Typeface cardFace;
  void ensureCards(Typeface face,String recent,Bitmap poster,String recommendation,Bitmap pickPoster){if(cardFace==face&&recent.equals(cardRecent)&&recommendation.equals(cardRecommendation)&&poster==cardPoster&&pickPoster==cardPickPoster)return;cardFace=face;cardRecent=recent;cardRecommendation=recommendation;cardPoster=poster;cardPickPoster=pickPoster;for(int n=0;n<8;n++){if(cards[n]==null)cards[n]=Bitmap.createBitmap(116,162,Bitmap.Config.ARGB_8888);cards[n].eraseColor(Color.TRANSPARENT);Canvas c=new Canvas(cards[n]);c.scale(2,2);int i=n/2;boolean focus=n%2==1;float x=1,y=1;box(c,x+2,y+3,54,76,0xff483b32);box(c,x-1,y-1,56,77,focus?0xffffd68c:0xff6c4c36);box(c,x,y,54,74,focus?0xfff4dfad:0xffdec99b);box(c,x+3,y+3,48,68,0xff9a7b50);box(c,x+4,y+4,46,66,focus?0xfff0dfb7:0xffd8c397);box(c,x+7,y+7,3,3,0xff936e49);box(c,x+44,y+7,3,3,0xff936e49);Bitmap art=i==0?poster:i==2?pickPoster:null;if(art!=null){p.setFilterBitmap(true);c.drawBitmap(art,null,new RectF(x+7,y+10,x+47,y+33),p);p.setFilterBitmap(false);}else cardIcon(c,i,x+18,y+13,focus);middle(c,titles[i],x+27,y+43,4f,0xff594034);String top=i==0?(recent.length()==0?"Пока пусто":"Android TV"):new String[]{"","Твои","Android TV","История"}[i];String bottom=i==0?recent:i==2?(recommendation.length()==0?"Нет карточек":recommendation):new String[]{"","приложения","","замка"}[i];fit(c,top,x+27,y+52,4f,42,0xff725343);fit(c,bottom,x+27,y+59,4f,42,0xff725343);middle(c,new String[]{"I","II","III","IV"}[i],x+27,y+68,4.5f,0xff8b6343);}}
  void draw(Canvas c,long age,float daylight,Typeface face,int selected,String recent,Bitmap poster,String recommendation,Bitmap pickPoster,long refreshing){ensure(daylight,face);ensureCards(face,recent,poster,recommendation,pickPoster);float camera=refreshing>=0?MerchantMotion.refreshCamera(refreshing):MerchantMotion.camera(age),z=1+camera*.68f;c.save();c.translate(256,144);c.scale(z,z);c.translate(-256,-144-30*camera);p.setFilterBitmap(false);c.drawBitmap(background,0,0,p);outdoor(c,daylight);
    long idle=Math.max(0,age-MerchantMotion.READY);int gesture=refreshing<0&&MerchantMotion.ready(age)?MerchantIdle.kind(idle):0;float lift=MerchantIdle.lift(idle);
    if(MerchantMotion.ready(age)&&MerchantIdle.blink(idle)){b(c,243,113,3,3,0xffefc18b,daylight);b(c,266,113,3,3,0xffefc18b,daylight);b(c,242,115,5,1,0xff674b3b,daylight);b(c,265,115,5,1,0xff674b3b,daylight);}
    float reach=refreshing>=0?.7f:MerchantMotion.hand(age);if(gesture==3){b(c,185,140,14,25,0xff6d4d38,daylight);b(c,195,140,1,25,0xff543a2e,daylight);jar(c,185-Math.round(lift*13),140-Math.round(lift*3),0xffb79665,daylight);bentArm(c,218,194-lift*14,155-lift*4,daylight);}else arm(c,205+reach*15,162+reach*6,24+reach*4,daylight);
    if(gesture==1){bentArm(c,292,278-lift*13,163-lift*42,daylight);b(c,262,121,13,5,0xffdda171,daylight);b(c,262,121-lift*2,13,5,0xff76513b,daylight);}else if(gesture==2){float x=289-35*lift,y=164-46*lift;bentArm(c,292,x+9,y+8,daylight);mug(c,x,y,daylight);}else{c.save();c.scale(-1,1,256,0);arm(c,205+reach*10,162+reach*5,24,daylight);c.restore();mug(c,289,164,daylight);}
    if(age>=1200){float deckY=162+MerchantMotion.unit(age,1200,350)*11;float shake=refreshing>=1300&&refreshing<2100?(float)Math.sin(refreshing/50.0)*5:0;c.save();c.translate(shake,0);box(c,250,deckY+3,19,26,0xff443b36);box(c,249,deckY,19,26,0xffebd7ac);box(c,251,deckY+2,15,22,0xff895c58);box(c,253,deckY+4,11,18,0xffc39c77);box(c,257,deckY+10,3,6,0xff895c58);c.restore();}
    for(int i=0;i<4;i++){float t=refreshing>=0?MerchantMotion.refreshCard(refreshing,i):MerchantMotion.card(age,i);if(t<=0)continue;float x=249+(135+i*63-249)*t,y=174+(167-174)*t;boolean focus=refreshing<0&&MerchantMotion.ready(age)&&selected==i;if(focus)y-=3;p.setFilterBitmap(false);float size=.34f+.66f*t;cardBounds.set(x-1,y-1,x-1+58*size,y-1+81*size);c.drawBitmap(cards[i*2+(focus?1:0)],null,cardBounds,p);}
    c.restore();
  }
}
