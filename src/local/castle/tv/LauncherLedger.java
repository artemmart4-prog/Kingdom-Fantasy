package local.castle.tv;

import android.content.*;
import org.json.*;
import java.util.*;

/** Only launches initiated by Castle and time spent in Castle. No external viewing history. */
final class LauncherLedger {
  final android.content.SharedPreferences prefs;
  final ArrayList<String> packages=new ArrayList<>(),names=new ArrayList<>();
  long activeSince=-1;
  LauncherLedger(Context c){prefs=c.getSharedPreferences("castle-journal",Context.MODE_PRIVATE);try{JSONArray a=new JSONArray(prefs.getString("recent","[]"));for(int i=0;i<a.length()&&i<6;i++){JSONObject x=a.getJSONObject(i);packages.add(x.getString("package"));names.add(x.getString("name"));}}catch(Exception ignored){packages.clear();names.clear();}}
  void record(String pkg,String name){if(pkg==null||pkg.length()==0)return;int old=packages.indexOf(pkg);if(old>=0){packages.remove(old);names.remove(old);}packages.add(0,pkg);names.add(0,name);while(packages.size()>6){packages.remove(6);names.remove(6);}JSONArray a=new JSONArray();try{for(int i=0;i<packages.size();i++){JSONObject x=new JSONObject();x.put("package",packages.get(i));x.put("name",names.get(i));a.put(x);}}catch(JSONException ignored){}prefs.edit().putString("recent",a.toString()).putLong("launches",launches()+1).apply();}
  long launches(){return prefs.getLong("launches",0);}
  void resume(long now){if(activeSince<0)activeSince=now;}
  void pause(long now){if(activeSince>=0){prefs.edit().putLong("homeMs",prefs.getLong("homeMs",0)+Math.max(0,now-activeSince)).apply();activeSince=-1;}}
  long homeMs(long now){return prefs.getLong("homeMs",0)+(activeSince<0?0:Math.max(0,now-activeSince));}
}
