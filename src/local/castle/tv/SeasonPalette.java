package local.castle.tv;
/** Only vegetation changes hue; water, masonry, fire and UI retain their palettes. */
public final class SeasonPalette {
 public static boolean grass(int c){switch(c){case 0xff192d38:case 0xff1d2e36:case 0xff223b40:case 0xff1c3037:case 0xff15242f:case 0xff45635b:case 0xff29433f:return true;default:return false;}}
 public static boolean foliage(int c){switch(c){case 0xff3c674a:case 0xff497b4f:case 0xff578a51:case 0xff80a85f:case 0xff325e45:case 0xff496d47:case 0xff3c754c:case 0xff558b52:case 0xff365c44:case 0xff88ac62:case 0xff365e45:case 0xff90a46a:return true;default:return false;}}
}
