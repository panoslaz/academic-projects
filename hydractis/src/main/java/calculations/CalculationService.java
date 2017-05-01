package calculations;


import calculations.model.CalculationValues;
import calculations.model.DiameterValues;

import java.text.DecimalFormat;

/**
 * The class contains all the necessary functions to perform the calculations.
 */
public class CalculationService {

    private static final double[] dList = {0.40, 0.50, 0.60, 0.70, 0.80, 0.90, 1.00, 1.10, 1.20, 1.40, 1.60};

    /**
     * @param qin input value for the tube's inflow
     * @param din input value for diameter
     * @param tin input value for the tube's maximum depth
     * @param roughness input value for the tube's roughness
     * @param angle input value for the tube's angle
     * @return
     */
    public static DiameterValues computeDiameterValues(double qin, double din, double tin, double roughness, double angle) {
        double d;
        if (din == 0) {
            d = tin / 0.9382;
        } else {
            d = computeD(qin, din, roughness, angle);
        }

        // get the right values for idealDiameter, idealPrevious and idealNext from the list
        double idealDiameter = 0;
        double idealPrevious = 0;
        double idealNext = 0;

        for (int i = 1; i < dList.length; i++) {
            if (dList[i] >= d) {
                idealDiameter = dList[i];
                if (i > 0) {
                    idealPrevious = dList[i - 1];
                } else {
                    // if ideal diameter is the first element of the list, set the same for ideal previous.
                    idealPrevious = dList[i];
                }
                if (i < dList.length - 1) {
                    idealNext = dList[i + 1];
                } else {
                    // if ideal diameter is the last element of the list, set the same for ideal next.
                    idealNext = dList[i];
                }
                break;
            }
        }
        // if d is lower than the first element of the list,
        // set the ideal diameter as the first element of the list
        if (d < dList[0]) {
            idealDiameter = dList[0];
            idealNext = dList[1];
        } else if (d > dList[dList.length - 1]) {
            // if d is highest than the last element of the list,
            // set the ideal diameter as the last element of the list
            idealDiameter = dList[dList.length - 1];
            idealPrevious = dList[dList.length - 2];
        }
        DiameterValues values = new DiameterValues();
        values.setIdealDiameter(idealDiameter);
        values.setIdealNext(idealNext);
        values.setIdealPrevious(idealPrevious);
        return values;
    }

    /**
     * @param qin input value for the tube's inflow
     * @param tin input value for the tube's maximum depth
     * @param roughness input value for the tube's roughness
     * @param angle input value for the tube's angle
     * @param dFinal final diameter value
     * @return
     */
    public static CalculationValues getCalculationValues(double qin, double tin, double roughness, double angle, double dFinal) {
        CalculationValues calculationValues = new CalculationValues();
        double idealT;
        if (tin == 0) {
            idealT = computeT(dFinal, roughness, angle, qin);
        } else {
            idealT = tin;
        }
        double b = computeB(dFinal, idealT);
        double surface = computeA(dFinal, b);
        double R = computeR(surface, b, dFinal);

        if (qin == 0) {
            qin = computeQin(R, roughness, angle, surface);
        }
        DecimalFormat df = new DecimalFormat("#0.####");

        calculationValues.setAngle(df.format(angle));
        calculationValues.setRoughness(df.format(roughness));
        calculationValues.setQin(df.format(qin));
        calculationValues.setTin(df.format(idealT));
        calculationValues.setIdealT(df.format(idealT));

        double speed = computeSpeed(qin, surface);
        calculationValues.setSpeed(df.format(speed));
        calculationValues.setSurface(String.format("%.12f", surface));

        double width = computeWidth(dFinal, idealT);
        calculationValues.setWidth(df.format(width));
        calculationValues.setEnergy(df.format(computeEnergy(speed)));
        calculationValues.setFroud(df.format(computeFroud(surface, width, speed)));

        return calculationValues;
    }

    /**
     * @param qin input value for the tube's inflow
     * @param din input value for diameter
     * @param roughness input value for tube's roughness
     * @param angle input value for tube's angle
     * @return
     */
    private static double computeD(double qin, double din, double roughness, double angle) {
        double tin = 0.9382 * din;
        double b = computeB(din, tin);
        double surface = computeA(din, b);
        double r = computeR(surface, b, din);
        double qMax = computeQin(r, roughness, angle, surface);

        if (qin <= qMax) {

            if (din < 0.1) {
                din = 0.1;
            }

            return din;
        } else {

            while (qin > qMax) {
                din = din + 0.01 * din;
                b = computeB(din, tin);
                surface = computeA(din, b);
                r = computeR(surface, b, din);
                qMax = computeQin(r, roughness, angle, surface);
            }
        }

        if (din < 0.1) {
            din = 0.1;
        }
        return din;
    }

    /**
     * ideal depth calculation
     *
     * @param dFinal final diameter value
     * @param roughness input value for tube's roughness
     * @param angle input value for tube's angle
     * @param qin input value for the tube's inflow
     * @return
     */
    private static double computeT(double dFinal, double roughness, double angle, double qin) {

        double tInit = 0.9382 * (dFinal / 2);
        double b = computeB(dFinal, tInit);
        double surface = computeA(dFinal, b);
        double r = computeR(surface, b, dFinal);
        double qCalc = computeQin(r, roughness, angle, surface);
        double tCalc = tInit;
        if (qCalc >= (qin - ((0.01 / 100) * dFinal)) && qCalc <= (((0.01 / 100) * dFinal) + qin)) {
            return tInit;
        } else {
            int step = 1;

            while (!(qCalc >= (qin - ((0.01 / 100) * dFinal)) && qCalc <= (((0.01 / 100) * dFinal) + qin))) {
                if (qCalc >= (qin + ((0.01 / 100) * dFinal)) && step != 1) {
                    tCalc = tCalc - tInit / step;
                } else if (step != 1) {
                    tCalc = tCalc + tInit / step;
                }

                b = computeB(dFinal, tCalc);
                surface = computeA(dFinal, b);
                r = computeR(surface, b, dFinal);
                qCalc = computeQin(r, roughness, angle, surface);
                step = step * 2;
            }

        }
        return tCalc;
    }


    /**
     * b calculation
     *
     * @param dFinal final diameter value
     * @param idealT ideal maximum depth
     * @return b = 2*acos*((r-idealT)/r)
     */
    private static double computeB(double dFinal, double idealT) {
        double radius = dFinal / 2;
        return 2 * Math.acos(((radius - idealT) / radius));
    }


    /**
     * surface calculation
     *
     * @param dFinal final diameter value
     * @param b parameter b = 2*acos*((r-idealT)/r)
     * @return A = (r^2)/2*(b-sinb) water's surface in the tube calculation
     */
    private static double computeA(double dFinal, double b) {
        return (Math.pow(dFinal / 2, 2) / 2) * (b - Math.sin(b));
    }


    /**
     *
     *
     * @param R Hydraulic radius
     * @param roughness input value for tube's roughness
     * @param angle input value for tube's angle
     * @param surface water surface in the tube
     * @return Q = 1/roughness*R^(2/3)8surface*angle^(1/2) tube's inflow calculation
     */
    private static double computeQin(double R, double roughness, double angle, double surface) {

        return (1 / roughness) * Math.pow(R, (double) 2 / 3) * surface * Math.pow(angle, (double) 1 / 2);
    }


    /**
     * computeSpeed calculation
     *
     * @param qin input value for the tube's inflow
     * @param surface water surface in the tube
     * @return speed = qin/surface water's speed in the tube calculation
     */
    private static double computeSpeed(double qin, double surface) {
        return qin / surface;
    }


    /**
     * computeWidth calculation
     *
     * @param dFinal final diameter value
     * @param t tube's maximum depth
     * @return width = 2*sqrt(t*(dFinal-t) water's width in the tube calculation
     */
    private static double computeWidth(double dFinal, double t) {
        return 2 * Math.sqrt(t * (dFinal - t));
    }


    /**
     * computeEnergy calculation
     *
     * @param speed water's speed in the tube
     * @return Energy = speed^2/2*9.81 water's energy in the tube calculation
     */
    private static double computeEnergy(double speed) {
        return Math.pow(speed, 2) / (2 * 9.81);
    }

    /**
     * computeFroud calculation
     *
     * @param surface water surface in the tube
     * @param width water width in the tube
     * @param speed water's speed in the tube
     * @return Froud= speed/sqrt(9.81*(surface/width)) Froud number calculation
     */
    private static double computeFroud(double surface, double width, double speed) {
        return speed / (Math.sqrt(9.81 * surface / width));
    }

    /**
     * computeR(Hydraulic radius) calculation
     *
     * @param surface water surface in the tube
     * @param b
     * @param dFinal final diameter value
     * @return Hydraulic radius = surface/((dFinal/2)*b)
     */
    private static double computeR(double surface, double b, double dFinal) {
        return surface / ((dFinal / 2) * b);
    }
}
