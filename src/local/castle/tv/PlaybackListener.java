package local.castle.tv;
import android.service.notification.NotificationListenerService;import android.media.session.*;import android.content.*;import android.os.*;import java.util.*;
/** Uses notification access only as Android's gate to media sessions. Does not inspect notification contents. */
public class PlaybackListener extends NotificationListenerService {
 final Handler main=new Handler(Looper.getMainLooper());final HashMap<MediaController,MediaController.Callback> controllers=new HashMap<>();MediaSessionManager manager;
 final MediaSessionManager.OnActiveSessionsChangedListener sessions=new MediaSessionManager.OnActiveSessionsChangedListener(){public void onActiveSessionsChanged(List<MediaController> list){replace(list);}};
 final Runnable tick=new Runnable(){public void run(){for(MediaController c:controllers.keySet())LivePlayback.save(PlaybackListener.this,c);main.postDelayed(this,15000);}};
 @Override public void onListenerConnected(){manager=(MediaSessionManager)getSystemService(MEDIA_SESSION_SERVICE);try{ComponentName n=new ComponentName(this,PlaybackListener.class);manager.addOnActiveSessionsChangedListener(sessions,n,main);replace(manager.getActiveSessions(n));main.removeCallbacks(tick);main.postDelayed(tick,15000);}catch(SecurityException ignored){}}
 void replace(List<MediaController> list){for(MediaController c:controllers.keySet()){LivePlayback.save(this,c);c.unregisterCallback(controllers.get(c));}controllers.clear();if(list==null)return;for(final MediaController c:list){if(!LivePlayback.allowedPackage(c.getPackageName())||controllers.size()>=4)continue;MediaController.Callback callback=new MediaController.Callback(){@Override public void onMetadataChanged(android.media.MediaMetadata m){LivePlayback.save(PlaybackListener.this,c);}@Override public void onPlaybackStateChanged(PlaybackState p){LivePlayback.save(PlaybackListener.this,c);}};controllers.put(c,callback);c.registerCallback(callback,main);LivePlayback.save(this,c);}}
 @Override public void onListenerDisconnected(){cleanup();}
 @Override public void onDestroy(){cleanup();super.onDestroy();}
 void cleanup(){main.removeCallbacks(tick);if(manager!=null)manager.removeOnActiveSessionsChangedListener(sessions);for(MediaController c:controllers.keySet()){LivePlayback.save(this,c);c.unregisterCallback(controllers.get(c));}controllers.clear();}
}
