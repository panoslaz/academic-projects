package calculations;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.text.DecimalFormat;


/**
 * Created by User on 14/3/2017.
 */
@Path("/calculator")
public class Calculator {

    private static final double[] dList = {0.40, 0.50, 0.60, 0.70, 0.80, 0.90, 1.00, 1.10, 1.20, 1.40, 1.60};

    @GET
    @Path("/diameter")
    @Produces("application/json")
    public DiameterValues diameter(@QueryParam("din") double din) {
        double ideald = 0;
        double idealPrevious = 0;
        double idealNext = 0;

        for (int i = 1; i < dList.length; i++) {
            if (dList[i] >= din) {
                ideald = dList[i];
                idealPrevious = dList[i - 1];
                idealNext = dList[i + 1];
                break;
            }
        }
        if(din<dList[0] && ideald == 0) {
            ideald=dList[0];
        } else if( ideald == 0) {
            ideald = dList[dList.length - 1];
        }
        DiameterValues values = new DiameterValues();
        values.setIdeald(ideald);
        values.setIdealNext(idealNext);
        values.setIdealPrevious(idealPrevious);

        return values;
    }


    @GET
    @Path("/allValues")
    @Produces("application/json")
    public CalculationValues calculations(
            @QueryParam("din") double din,
            @QueryParam("roughness") double roughness,
            @QueryParam("angle") double angle

    ) {

        CalculationValues calculationValues = new CalculationValues();
        double ideald = getIdeal(din);
        double idealt = computeT(ideald);
        double t = tmax(din);
        double b = computeB(din, t);
        double surface = computeA(din, b);
        double R = R(surface, b, din);
        double qin = qin(R, roughness, angle, surface);
        DecimalFormat df = new DecimalFormat("#0.####");

        calculationValues.setAngle(df.format(angle));
        calculationValues.setRoughness(df.format(roughness));
        calculationValues.setT(df.format(t));
        calculationValues.setQin(df.format(qin));
        calculationValues.setIdealt(df.format(idealt));

        double speed = speed(din, b);
        calculationValues.setSpeed(df.format(speed));
        calculationValues.setSurface(String.format("%.12f", surface));

        double width = width(ideald, idealt);
        calculationValues.setWidth(df.format(width));
        calculationValues.setEnergy(df.format(energy(speed)));
        calculationValues.setFroud(df.format(froud(surface, width, speed)));

        return calculationValues;
    }


    //ideal, previous and next diameter calculation
    public static double getIdeal(double din) {
        double ideald = 0;

        for (int i = 1; i < dList.length; i++) {
            if (dList[i] >= din) {
                ideald = dList[i];
                break;
            }
        }
        if(din<dList[0] && ideald == 0) {
            ideald=dList[0];
        } else if( ideald == 0) {
            ideald = dList[dList.length - 1];
        }
        return ideald;
    }


    //ideal depth calculation
    public static double computeT(double ideald) {
        return 0.9382 * ideald;

    }


    public static double tmax(double din) {
        return 0.9382 * din;
    }


    //b calculation
    public static double computeB(double din, double t) {
        double radius = din / 2;
        double v = ((radius - t) / radius);
        double f = Math.acos(Math.toRadians(v));
        return 2* f;
    }


    //surface calculation
    public static double computeA(double din, double b){
        double radius = din / 2;
        return (Math.pow(radius, 2)/2)*(b-Math.sin(b));
    }


    //Q=Qin calculation
    public static double qin(double R, double roughness, double angle, double surface) {

        return (1 / roughness) * Math.pow(R, 2 / 3) * surface * Math.pow(angle, 1 / 2);
    }


    //speed calculation
    public static double speed(double surface, double qin) {
        return qin / surface;
    }


    //width calculation
    public static double width(double din, double t) {
        double c = t * (din - t);
        return 2 * Math.sqrt(c);
    }


    //energy calculation
    public static double energy(double v) {
        return Math.pow(v, 2) / (2 * 9.81);
    }


    //froud calculation
    public static double froud(double a, double B, double v) {
        double th = a / B;
        return v / (Math.sqrt(9.81 * th));
    }


    //R(υδραυλική ακτίνα) calculation
    public static double R(double surface, double b, double din) {
        double R = surface / ((din/2)*b);
        return R;
    }

}






