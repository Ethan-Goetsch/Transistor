package utils;

public class Conversions{
    public static String timeDivision(double time){
        int hours = (int)(time/60)/60;
        int minutes= (int) (time/60);
        int seconds= (int) (time%60);
           return hours + " hours " + minutes + " minutes and "+ seconds;
    }
    public static String toKm(double distance){
        double km=distance/1000;
        return km + " km";
    }

}

