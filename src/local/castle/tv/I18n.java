package local.castle.tv;
import android.content.*;import android.graphics.*;import android.text.*;import java.io.*;import java.util.*;
/** Offline UI translations; programme titles and account data are never sent to a translator. */
final class I18n {
 static int language;static final String[] NAMES={"Русский","English","Deutsch","Українська","Türkçe","简体中文","Français","日本語","اردو"};
 static final Map<String,String[]> rows=new HashMap<>();static final LinkedHashMap<String,String> cache=new LinkedHashMap<>();static final LinkedHashMap<String,StaticLayout> rtl=new LinkedHashMap<>();
 static void init(Context c,int lang){if(rows.isEmpty())try(BufferedReader r=new BufferedReader(new InputStreamReader(c.getAssets().open("ui-languages.tsv"),"UTF-8"))){String line;while((line=r.readLine())!=null){String[] p=line.split("\\|",-1);if(p.length==9)rows.put(p[0].toLowerCase(Locale.ROOT),p);}}catch(Exception ignored){}set(lang);}
 static void set(int lang){language=Math.max(0,Math.min(8,lang));cache.clear();rtl.clear();}
 static Typeface face(Typeface font){return language==5||language==7||language==8?Typeface.DEFAULT:font;}
 static String tr(String text){if(language==0||text==null||text.length()==0)return text;String hit=cache.get(text);if(hit!=null)return hit;String[] row=rows.get(text.toLowerCase(Locale.ROOT));String out=row==null?text:row[language];if(row!=null&&text.equals(text.toUpperCase(Locale.ROOT)))out=out.toUpperCase(Locale.ROOT);if(cache.size()>400)cache.clear();cache.put(text,out);return out;}
 static boolean hasArabic(String s){for(int i=0;i<s.length();i++)if(s.charAt(i)>=0x600&&s.charAt(i)<=0x6ff)return true;return false;}
 static void draw(Canvas c,String s,float x,float y,Paint p){if(language!=8||!hasArabic(s)){c.drawText(s,x,y,p);return;}String key=s+"/"+p.getTextSize()+"/"+p.getColor();StaticLayout layout=rtl.get(key);if(layout==null){TextPaint t=new TextPaint(p);int width=Math.max(1,(int)Math.ceil(t.measureText(s))+4);layout=StaticLayout.Builder.obtain(s,0,s.length(),t,width).setTextDirection(TextDirectionHeuristics.FIRSTSTRONG_RTL).setAlignment(Layout.Alignment.ALIGN_NORMAL).setIncludePad(false).build();if(rtl.size()>160)rtl.clear();rtl.put(key,layout);}c.save();c.translate(x,y-layout.getLineBaseline(0));layout.draw(c);c.restore();}
}
