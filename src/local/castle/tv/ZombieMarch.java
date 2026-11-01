package local.castle.tv;
/** One ground-anchored jump at a time. Rapid requests replace only the pending destination. */
public final class ZombieMarch {
 public static final int HOLD=0,HIDE=1,JUMP=2,RISE=3,HIDE_MS=200,JUMP_MS=440,RISE_MS=300;
 public int phase=HOLD;public long started;public float x,y,fromX,fromY,toX,toY,wantX,wantY,hideOne=1,hideTwo=1;
 public void place(float px,float py){x=fromX=toX=wantX=px;y=fromY=toY=wantY=py;phase=HOLD;hideOne=hideTwo=1;}
 public void summon(long now,float px,float py){place(px,py);phase=RISE;started=now;}
 boolean pending(){return wantX!=toX||wantY!=toY;}
 public void request(long now,float px,float py,boolean animate){if(!animate){place(px,py);return;}advance(now);wantX=px;wantY=py;if((phase==HOLD||phase==RISE)&&(px!=x||py!=y)){hideOne=helper(now,1);hideTwo=helper(now,2);phase=HIDE;started=now;}}
 int duration(){return phase==HIDE?HIDE_MS:phase==JUMP?JUMP_MS:RISE_MS;}
 void jump(long at){fromX=x;fromY=y;toX=wantX;toY=wantY;started=at;phase=JUMP;}
 public void advance(long now){for(int guard=0;guard<8&&phase!=HOLD&&now-started>=duration();guard++){long at=started+duration();if(phase==HIDE){if(wantX==x&&wantY==y){phase=RISE;started=at;}else jump(at);}else if(phase==JUMP){x=toX;y=toY;if(pending())jump(at);else{phase=RISE;started=at;}}else phase=HOLD;}if(phase==JUMP){float t=JumpMotion.progress(now-started,JUMP_MS);x=fromX+(toX-fromX)*JumpMotion.ease(t);y=fromY+(toY-fromY)*JumpMotion.ease(t)-JumpMotion.lift(t);} }
 public float helper(long now,int id){if(phase==HOLD)return 1;if(phase==JUMP)return 0;long age=now-started;if(phase==HIDE)return (id==1?hideOne:hideTwo)*(1-JumpMotion.ease(JumpMotion.progress(age-(id-1)*35,165)));return JumpMotion.ease(JumpMotion.progress(age-(id-1)*70,230));}
 public boolean moving(){return phase!=HOLD;}
}
