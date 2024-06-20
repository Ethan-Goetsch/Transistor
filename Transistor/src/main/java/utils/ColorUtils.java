package utils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ColorUtils
{
    public static Color intToColor(int num)
    {
        List<Color> colors = new  ArrayList<Color>();

        colors.add(convertToColor(0xFFFFB300)); //Vivid Yellow
        colors.add(convertToColor(0xFF803E75)); //Strong Purple
        colors.add(convertToColor(0xFFFF6800)); //Vivid Orange
        colors.add(convertToColor(0xFFA6BDD7)); //Very Light Blue
        colors.add(convertToColor(0xFFC10020)); //Vivid Red
        colors.add(convertToColor(0xFFCEA262)); //Grayish Yellow
        colors.add(convertToColor(0xFF817066)); //Medium Gray

        //The following will not be good for people with defective color vision
        colors.add(convertToColor(0xFF007D34)); //Vivid Green
        colors.add(convertToColor(0xFFF6768E)); //Strong Purplish Pink
        colors.add(convertToColor(0xFF00538A)); //Strong Blue
        colors.add(convertToColor(0xFFFF7A5C)); //Strong Yellowish Pink
        colors.add(convertToColor(0xFF53377A)); //Strong Violet
        colors.add(convertToColor(0xFFFF8E00)); //Vivid Orange Yellow
        colors.add(convertToColor(0xFFB32851)); //Strong Purplish Red
        colors.add(convertToColor(0xFFF4C800)); //Vivid Greenish Yellow
        colors.add(convertToColor(0xFF7F180D)); //Strong Reddish Brown
        colors.add(convertToColor(0xFF93AA00)); //Vivid Yellowish Green
        colors.add(convertToColor(0xFF593315)); //Deep Yellowish Brown
        colors.add(convertToColor(0xFFF13A13)); //Vivid Reddish Orange
        colors.add(convertToColor(0xFF232C16)); //Dark Olive Green

        int index = num % colors.size();
        
        return colors.get(index);

    }

    public static Color convertToColor(long unsignedInt)
    {
        // Mask the lower 24 bits to get the RGB values
        int rgb = (int) (unsignedInt & 0xFFFFFF);

        // Extract individual color components
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Create and return the Color object
        return new Color(red, green, blue);
    }
}
