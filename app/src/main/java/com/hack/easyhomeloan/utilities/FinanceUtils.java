package com.hack.easyhomeloan.utilities;

/**
 * Created by roshan on 20/5/16.
 */


public final class FinanceUtils {
    private static final double FINANCIAL_PRECISION = 0.00000001; //1.0e-08
    private static final double FINANCIAL_MAX_ITERATIONS = 128;
    public static String TAG = "FinanceUtils";

    private FinanceUtils() {
    }

    /**
     * Calculate EMI
     *
     * @param tenure
     * @param roi
     * @param loanAmount
     * @param isAdvanceEMI
     * @return
     */

    public static double calculateEMI(int tenure, double roi, double loanAmount, boolean isAdvanceEMI) {
        double result = 0.0;
        result = Finance.pmt((roi / 1200), tenure, loanAmount, 0, isAdvanceEMI) * -1;
        //  String val = Utility.getFormattedStringDouble(String.valueOf(result));
        return result;
    }

    /**
     * Calculate ROI
     *
     * @param tenure
     * @param emiVal
     * @param loanAmount
     * @param futureVal
     * @param isAdvEmi
     * @return
     */

    public static double calculateROI(double tenure, double emiVal, double loanAmount, double futureVal, boolean isAdvEmi) {
        double rate = 0.1;
        double emi;
        double futureValue = 0.0;
        int isAdvanceEmi = (isAdvEmi ? 1 : 0);
        if (Math.abs(rate) < FINANCIAL_PRECISION) {
            emi = loanAmount * (1 + tenure * rate) + emiVal * (1 + rate * isAdvanceEmi) * tenure + futureVal;
        } else {
            futureValue = Math.exp(tenure * Math.log(1 + rate));
            emi = loanAmount * futureValue + emiVal * (1 / rate + isAdvanceEmi) * (futureValue - 1) + futureVal;
        }

        double y0 = loanAmount + emiVal * tenure + futureVal;
        double y1 = loanAmount * futureValue + emiVal * (1 / rate + isAdvanceEmi) * (futureValue - 1) + futureVal;

        // find root by secant method
        int i = 0;
        double x0 = 0.0;
        double x1 = rate;
        while ((Math.abs(y0 - y1) > FINANCIAL_PRECISION) && (i < FINANCIAL_MAX_ITERATIONS)) {
            rate = (y1 * x0 - y0 * x1) / (y1 - y0);
            x0 = x1;
            x1 = rate;

            if (Math.abs(rate) < FINANCIAL_PRECISION) {
                emi = loanAmount * (1 + tenure * rate) + emiVal * (1 + rate * isAdvanceEmi) * tenure + futureVal;
            } else {
                futureValue = Math.exp(tenure * Math.log(1 + rate));
                emi = loanAmount * futureValue + emiVal * (1 / rate + isAdvanceEmi) * (futureValue - 1) + futureVal;
            }

            y0 = y1;
            y1 = emi;
            i++;
        }
        return rate;
    }


    /**
     * Calculate Tenure
     *
     * @param roi
     * @param emi
     * @param loanAmount
     * @param futureValue
     * @param isAdvanceEmi
     * @return
     */

    public static double calculateTenure(double roi, double emi, double loanAmount, double futureValue, boolean isAdvanceEmi) {
        double retval = 0;
        if (roi == 0) {
            retval = -1 * (futureValue + loanAmount) / emi;
        } else {
            double r1 = roi + 1;
            double ryr = (isAdvanceEmi ? r1 : 1) * emi / roi;
            double a1 = ((ryr - futureValue) < 0)
                    ? Math.log(futureValue - ryr)
                    : Math.log(ryr - futureValue);
            double a2 = ((ryr - futureValue) < 0)
                    ? Math.log(-loanAmount - ryr)
                    : Math.log(loanAmount + ryr);
            double a3 = Math.log(r1);
            retval = (a1 - a2) / a3;
        }
        return retval;
    }

    /**
     * Calculate Intrest
     *
     * @param roi
     * @param count
     * @param tenure
     * @param loanAmount
     * @param futureValue
     * @param isAdvanceEmi
     * @return
     */

    public static double calculateIntrest(double roi, int count, double tenure, double loanAmount, double futureValue, int isAdvanceEmi) {

        double ipmt = fv(roi, count - 1, pmt(roi, tenure, loanAmount, futureValue, isAdvanceEmi), loanAmount, isAdvanceEmi) * roi;
        if (isAdvanceEmi == 1) ipmt /= (1 + roi);
        return ipmt;
    }


    /**
     * Calculate Principle
     *
     * @param roi
     * @param count
     * @param tenure
     * @param loanAmount
     * @param futureValue
     * @param isAdvanceEmi
     * @return
     */

    public static double calculatePrincipal(double roi, int count, double tenure, double loanAmount, double futureValue, int isAdvanceEmi) {
        return pmt(roi, tenure, loanAmount, futureValue, isAdvanceEmi) - calculateIntrest(roi, count, tenure, loanAmount, futureValue, isAdvanceEmi);
    }

    static public double pmt(double r, double nper, double pv, double fv, int type) {
        return (-r * (pv * Math.pow(1 + r, nper) + fv) / ((1 + r * type) * (Math.pow(1 + r, nper) - 1)));
    }

    static public double fv(double r, double nper, double pmt, double pv, int type) {
        return ((pv * Math.pow(1 + r, nper) + pmt * (1 + r * type) * (Math.pow(1 + r, nper) - 1) / r));
    }

    /**
     * @param basicPrimium
     * @param totalTax
     * @param ncb
     * @param discount
     * @return
     */
    static public double getTotalPremium(double basicPrimium, double totalTax, double ncb, double discount) {
        double totalPremium = 0;
        try {

            totalPremium = Math.round((basicPrimium + basicPrimium * (totalTax) / 100));
        } catch (Exception ex) {

        }

        return totalPremium;
    }

    /**
     * @param ncb
     * @param idvValue
     * @param totalTaxex
     * @return
     */
    static public double calculateNCb(double ncb, double idvValue, double totalTaxex, double tariffRate, double discount) {
        double totalncb = 0;
        try {

            double od = Math.round((idvValue * tariffRate) / 100);
            double odAfterDiscount = Math.round(od - discount);
            double ncbPer = (ncb) / 100.0;
            //totalncb = (odAfterDiscount*ncbPer) + ((odAfterDiscount*ncbPer)*(totalTaxex)/100);
            totalncb = (odAfterDiscount * ncbPer);

            //totalncb = Math.round((ncb * (basicPremium + basicPremium * (totalTaxex) / 100)) / 100);
        } catch (Exception ex) {

        }
        return totalncb;
    }

    /**
     * @param baseiPremium
     * @param totalTaxex
     * @return
     */
    static public double calculateTaxes(double baseiPremium, double totalTaxex) {
        double totalTaxesAmount = 0;
        try {
            totalTaxesAmount = Math.round(baseiPremium * (totalTaxex) / 100);
        } catch (Exception ex) {

        }

        return totalTaxesAmount;
    }

    /**
     * @param od
     * @param tp
     * @param pa
     * @return
     */
    static public double calculateBsicPremium(Double od, Double tp, Double pa) {
        double basicPremium = 0;
        try {
            basicPremium = Math.round(od + tp + pa);
        } catch (Exception ex) {

        }

        return basicPremium;
    }

    static public double calculateOD(Double idv, Double tariff, Double zeroDepMultiplier, double ncb, double discount) {
        try {
            if (null == zeroDepMultiplier)
                zeroDepMultiplier = Double.valueOf(0);
            double od = Math.round((idv * tariff) / 100);
            double odAfterDiscount = Math.round(od - discount);
            double ncbPer = (100.0 - ncb) / 100.0;
            double finalODWithNcb = odAfterDiscount * ncbPer;
            double zeroDep = (idv * zeroDepMultiplier) / 100;
            return (double) Math.round(finalODWithNcb + zeroDep);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    static public double calculateZeroDepAmt(Double zeroDepthMul, Double idv) {
        try {
            return (idv * zeroDepthMul) / 100;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * @param idv
     * @param updateIdv
     * @param idv_per
     * @return
     */
    public static boolean calculateIdvChangesValue(double idv, double updateIdv, double idv_per) {
        double newIdv = 0;
        boolean isSuccess = false;
        try {

            newIdv = ((idv * idv_per) / 100);
            double diff = idv - updateIdv;
            if (diff < 0) {
                diff = diff * (-1);
            }
            isSuccess = diff <= newIdv;
        } catch (Exception ex) {
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * @param premium
     * @param discount
     * @param disc_per
     * @return
     */
    public static boolean calculateDiscountChangesValue(double premium, double discount, double disc_per) {
        double newDiscount = 0;
        boolean isSuccess = false;
        newDiscount = ((premium * disc_per) / 100);
        if (discount <= newDiscount) {
            isSuccess = true;
        }
        return isSuccess;
    }

    /**
     * loanAmount change
     *
     * @param loanAmount
     * @param exShoowRoomPrice
     * @param roi
     * @param tenure
     */
    private double calculateEmiLoanAmountChange(Double loanAmount, Double exShoowRoomPrice, Double roi, int tenure, boolean isAdvanceEmi) {

        Double newDownPayment = exShoowRoomPrice - loanAmount;
        // Double newEmi = Utils.getEMI(roi, tenure, loanAmount,product.getAdvancedEmi());
        return calculateEMI(tenure, roi, loanAmount, isAdvanceEmi);

    }

    /**
     * DownPAyment Change
     *
     * @param downPayment
     * @param exShoowRoomPrice
     * @param roi
     * @param tenure
     */
    private double calculateEmiDownPaymentChange(double downPayment, double exShoowRoomPrice, double roi, int tenure, boolean isAdvanceEmi) {
        Double newLoanAmount = exShoowRoomPrice - downPayment;
        return calculateEMI(tenure, roi, newLoanAmount, isAdvanceEmi);
    }

    /**
     * Tenure Change
     *
     * @param loanAmount
     * @param roi
     * @param tenure
     */
    private double calculateEmiTenureChange(Double loanAmount, Double roi, int tenure, boolean isAdvanceEMi) {
        return calculateEMI(tenure, roi, loanAmount, isAdvanceEMi);

    }

}
