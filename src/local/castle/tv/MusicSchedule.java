package local.castle.tv;
public final class MusicSchedule {
  public static int theme(float hour,int mode){
    if(mode>=1&&mode<=3)return mode-1;
    return hour>=6&&hour<17?0:hour>=17&&hour<20?1:2;
  }
}
