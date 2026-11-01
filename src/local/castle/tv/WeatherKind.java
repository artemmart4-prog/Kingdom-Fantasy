package local.castle.tv;
public final class WeatherKind {
  public static final int CLEAR=0,CLOUD=1,RAIN=2,STORM=3,SNOW=4,FOG=5;
  public static final String[] NAMES={"Ясно","Облачно","Дождь","Гроза","Снег","Туман"};
  public static int fromWmo(int code){if(code==0||code==1)return CLEAR;if(code==2||code==3)return CLOUD;if(code==45||code==48)return FOG;if(code>=95&&code<=99)return STORM;if((code>=71&&code<=77)||code==85||code==86)return SNOW;if((code>=51&&code<=67)||(code>=80&&code<=82))return RAIN;return CLEAR;}
  public static float shade(int kind){switch(kind){case CLOUD:return .84f;case RAIN:return .73f;case STORM:return .64f;case SNOW:return .86f;case FOG:return .83f;default:return 1;}}
  public static boolean fresh(long fetched,long now){return fetched>0&&now>=fetched&&now-fetched<=6*60*60*1000L;}
}
