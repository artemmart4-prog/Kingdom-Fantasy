package local.castle.tv;
import android.app.Dialog;import android.content.Context;import android.graphics.*;import android.view.*;
/** Small offline remote keyboard. Codes never leave the device. */
final class RoyalCodeDialog extends Dialog {
 interface Apply {boolean code(String value);}
 final Apply apply;final Typeface font;String value="",error="";int row,col;final String[] letters={"qwertyuiop","asdfghjkl-","zxcvbnm_01"},actions={"←","Очистить","Применить","Отмена"};
 RoyalCodeDialog(Context context,Typeface font,Apply apply){super(context);this.font=font;this.apply=apply;requestWindowFeature(Window.FEATURE_NO_TITLE);setContentView(new Keys(context));getWindow().setBackgroundDrawableResource(android.R.color.transparent);getWindow().setLayout(-1,-1);}
 void finishCode(){if(apply.code(value)){dismiss();}else{error="Неизвестный код";getWindow().getDecorView().invalidate();}}
 class Keys extends View {final Paint p=new Paint();Keys(Context c){super(c);setFocusable(true);setFocusableInTouchMode(true);requestFocus();}
 void box(Canvas c,float x,float y,float w,float h,int color){p.setColor(color);p.setAntiAlias(false);c.drawRect(x,y,x+w,y+h,p);}
 void text(Canvas c,String s,float x,float y,int size,int color){p.setTypeface(I18n.face(font));p.setTextSize(size);p.setColor(color);p.setAntiAlias(true);I18n.draw(c,I18n.tr(s),x,y,p);}
 void activate(){if(row<3){if(value.length()<32)value+=letters[row].charAt(col);}else if(col==0){if(!value.isEmpty())value=value.substring(0,value.length()-1);}else if(col==1)value="";else if(col==2){finishCode();return;}else dismiss();invalidate();}
 @Override protected void onDraw(Canvas c){c.save();c.scale(getWidth()/1024f,getHeight()/576f);box(c,0,0,1024,576,0xe0172130);box(c,60,65,904,458,0xffc1a16d);box(c,65,70,894,448,0xff30384a);text(c,"Ввести чит-код",90,116,23,0xffffd48c);box(c,88,142,848,58,0xffe2cda3);text(c,value+"│",104,181,23,0xff3a302a);text(c,error,90,229,13,0xffffb0a0);for(int r=0;r<4;r++){int count=r==3?4:10;float width=848f/count;for(int k=0;k<count;k++){float x=88+k*width,y=250+r*54;boolean focus=r==row&&k==col;box(c,x,y,width-5,45,focus?0xffe6c17e:0xff807458);box(c,x+2,y+2,width-9,41,focus?0xff775a3e:0xff26384c);text(c,r==3?actions[k]:""+letters[r].charAt(k),x+13,y+29,r==3?13:20,0xffffdba1);}}text(c,"Стрелки · OK буква · Enter применить · Назад отмена",89,494,11,0xffd8d1bb);c.restore();}
 @Override public boolean onKeyDown(int key,KeyEvent e){if(key==4){dismiss();return true;}if(key==19)row=Math.floorMod(row-1,4);else if(key==20)row=(row+1)%4;else if(key==21)col=Math.floorMod(col-1,row==3?4:10);else if(key==22)col=(col+1)%(row==3?4:10);else if(key==23){if(e.getRepeatCount()==0)activate();}else if(key==66){if(e.getRepeatCount()==0)finishCode();}else if(key==67){if(!value.isEmpty())value=value.substring(0,value.length()-1);}else{int u=e.getUnicodeChar();if(u>0&&!Character.isISOControl(u)&&value.length()<32)value+=new String(Character.toChars(u));}col=Math.min(col,row==3?3:9);invalidate();return true;}
 @Override public boolean onTouchEvent(MotionEvent e){if(e.getAction()==1){float x=e.getX()/getWidth()*1024,y=e.getY()/getHeight()*576;int r=(int)((y-250)/54);if(x>=88&&x<936&&y>=250&&r<4){row=r;col=Math.min(row==3?3:9,(int)((x-88)/(848f/(row==3?4:10))));activate();}}return true;}
 }
 @Override public void show(){super.show();getWindow().setLayout(-1,-1);getWindow().getDecorView().setSystemUiVisibility(5894);}
}
