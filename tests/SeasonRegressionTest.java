import local.castle.tv.*;
public class SeasonRegressionTest {
 static void c(boolean ok,String why){if(!ok)throw new AssertionError(why);}
 public static void main(String[] args){
  int[] expected={4,4,1,1,1,2,2,2,3,3,3,4};
  for(int m=1;m<=12;m++){c(SeasonCycle.resolve(0,m)==expected[m-1],"calendar month "+m);for(int s=1;s<=4;s++)c(SeasonCycle.resolve(s,m)==s,"manual choice ignores calendar");}
  c(SeasonCycle.resolve(0,13)==4&&SeasonCycle.resolve(0,0)==4,"normalization at year boundary");
  c(!SeasonCycle.insects(4)&&SeasonCycle.insects(1)&&SeasonCycle.insects(2),"no winter insects, day or night");
  c(SeasonCycle.vegetation(0xff427364,2)==0xff427364,"summer palette preserved exactly");
  c(!SeasonPalette.grass(0xff28384c)&&!SeasonPalette.grass(0xff34465a),"water and castle excluded from vegetation tint");
  c(SeasonCycle.foxX(0)<0&&SeasonCycle.foxX(12000)>1024,"fox enters and exits screen");
  c(Math.abs(SeasonCycle.foxX(4199)-SeasonCycle.foxX(4200))<1&&Math.abs(SeasonCycle.foxX(7799)-SeasonCycle.foxX(7800))<1,"fox path remains continuous");
  c(SeasonCycle.foxSniff(4200)&&!SeasonCycle.foxSniff(7800),"sniffing pause boundaries");
  for(long t=0;t<IdleStory.CYCLE;t+=20)if(IdleStory.kind(t)==3)c(IdleStory.age(t)<12000,"fox replaces one exclusive story slot");
  System.out.println("PASS: seasonal boundaries, manual overrides, no winter insects, summer preservation, water exclusion and exclusive fox path");
 }
}
