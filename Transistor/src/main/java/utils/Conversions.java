package calculators;

public class Conversions {

    public static double KPHtoMPS(double speedInKPHFromMPS){
        return speedInKPHFromMPS/3.6;
    }

    public static double MPStoKPH(double speedInMPSFromKPH){
        return speedInMPSFromKPH*3.6;
    }

    public static double MetersToKilometers(double metersToKilometers){ return metersToKilometers/1000; }

    public static double KilometersToMeters(double kilometersToMeters){ return kilometersToMeters*1000; }

//    public static double SecondsToMinutesAndSeconds(double time){
//        return String (time/60) + " minutes and "+ (time%60) + " seconds";
//    }



}
