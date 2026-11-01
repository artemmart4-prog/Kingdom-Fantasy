package local.castle.tv;
import java.nio.charset.StandardCharsets;import java.security.MessageDigest;
/** Provider rows do not expose a universal service account identifier. */
public final class TvCardPolicy {
 public static String rowKey(String pkg,String intent,boolean history){return pkg+"\n"+(history?"history":"program")+"\n"+intent;}
 public static boolean continuing(long type,long position,long duration){return type==0&&position>0&&(duration<=0||position<duration);}
 public static String fingerprint(String value){try{byte[] bytes=MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));char[] hex="0123456789abcdef".toCharArray(),out=new char[bytes.length*2];for(int i=0;i<bytes.length;i++){out[i*2]=hex[(bytes[i]&255)>>>4];out[i*2+1]=hex[bytes[i]&15];}return new String(out);}catch(Exception e){throw new IllegalStateException(e);}}
}
