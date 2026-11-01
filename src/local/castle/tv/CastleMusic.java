package local.castle.tv;
import android.content.*;
import android.media.*;
import android.os.*;

/** Foreground-only, bounded-memory procedural score. */
final class CastleMusic {
  final AudioManager manager; final AudioFocusRequest request;
  volatile Session session; boolean active=false; int theme=0; float level=.12f;
  final AudioManager.OnAudioFocusChangeListener focus=new AudioManager.OnAudioFocusChangeListener(){public void onAudioFocusChange(int change){Session s=session;if(s!=null)s.muted=change!=AudioManager.AUDIOFOCUS_GAIN;}};
  CastleMusic(Context c){manager=(AudioManager)c.getSystemService(Context.AUDIO_SERVICE);request=new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()).setWillPauseWhenDucked(true).setOnAudioFocusChangeListener(focus,new Handler(Looper.getMainLooper())).build();}
  void release(){Session s=session;session=null;if(s!=null){s.running=false;AudioTrack track=s.track;if(track!=null)try{track.pause();track.flush();}catch(Exception ignored){}}manager.abandonAudioFocusRequest(request);}
  void pause(){active=false;release();}
  void update(boolean foreground,boolean on,int volume,float hour,int mode){active=foreground;theme=MusicSchedule.theme(hour,mode);level=volume/100f;
    if(!active||!on||volume<=0){release();return;}Session s=session;if(s!=null){s.theme=theme;s.level=level;return;}
    if(manager.isMusicActive())return;if(manager.requestAudioFocus(request)!=AudioManager.AUDIOFOCUS_REQUEST_GRANTED)return;
    s=new Session();s.theme=theme;s.level=level;session=s;new Thread(s,"Castle score").start();
  }
  final class Session implements Runnable {
    volatile boolean running=true,muted=false;volatile int theme;volatile float level;volatile AudioTrack track;
    public void run(){android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
      AudioTrack local=null;
      try{int size=Math.max(32768,AudioTrack.getMinBufferSize(LivingScore.RATE,AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT));
        local=new AudioTrack.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()).setAudioFormat(new AudioFormat.Builder().setSampleRate(LivingScore.RATE).setChannelMask(AudioFormat.CHANNEL_OUT_MONO).setEncoding(AudioFormat.ENCODING_PCM_16BIT).build()).setBufferSizeInBytes(size).setTransferMode(AudioTrack.MODE_STREAM).build();
        track=local;if(!running)return;
        LivingScore score=new LivingScore(System.nanoTime());short[] buffer=new short[2048];
        // Prime the full bounded buffer before starting, avoiding a startup underrun.
        int primed=0;while(running&&primed<size/2){score.fill(buffer,theme,muted?0:level);int count=Math.min(buffer.length,size/2-primed);int n=local.write(buffer,0,count,AudioTrack.WRITE_NON_BLOCKING);if(n<=0)break;primed+=n;}
        if(!running)return;local.play();
        int lastTheme=theme;while(running){if(lastTheme!=theme){score=new LivingScore(System.nanoTime());lastTheme=theme;}if(muted)java.util.Arrays.fill(buffer,(short)0);else score.fill(buffer,theme,level);int offset=0;while(running&&offset<buffer.length){int n=local.write(buffer,offset,buffer.length-offset,AudioTrack.WRITE_BLOCKING);if(n<0&&running)throw new IllegalStateException("Audio write "+n);if(n<=0)break;offset+=n;}}
      }catch(Exception e){android.util.Log.w("CastleMusic","Score stopped",e);}finally{track=null;if(local!=null)local.release();new Handler(Looper.getMainLooper()).post(new Runnable(){public void run(){if(session==Session.this){session=null;manager.abandonAudioFocusRequest(request);}}});}
    }
  }
}
