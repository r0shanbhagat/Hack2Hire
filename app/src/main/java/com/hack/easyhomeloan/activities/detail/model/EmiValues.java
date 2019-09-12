package com.hack.easyhomeloan.activities.detail.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Gautam Sharma on 03-02-2018.
 */

public class EmiValues implements Parcelable {

    public static final Creator<EmiValues> CREATOR = new Creator<EmiValues>() {
        @Override
        public EmiValues createFromParcel(Parcel source) {
            return new EmiValues(source);
        }

        @Override
        public EmiValues[] newArray(int size) {
            return new EmiValues[size];
        }
    };
    private List<String> rowMonth = null;
    private List<String> rowEMI = null;
    private List<String> rowPrincipal = null;
    private List<String> rowIntrest = null;
    private List<String> rowOutStanding = null;
    private double loanAmount;
    private double roi;
    private int tenure;
    private double emi;
    private boolean isAdvancedEmi;

    public EmiValues() {
    }

    private EmiValues(Parcel in) {
        this.rowMonth = in.createStringArrayList();
        this.rowEMI = in.createStringArrayList();
        this.rowPrincipal = in.createStringArrayList();
        this.rowIntrest = in.createStringArrayList();
        this.rowOutStanding = in.createStringArrayList();
        this.loanAmount = in.readDouble();
        this.roi = in.readDouble();
        this.tenure = in.readInt();
        this.emi = in.readDouble();
        this.isAdvancedEmi = in.readByte() != 0;
    }

    public boolean isAdvancedEmi() {
        return isAdvancedEmi;
    }

    public void setAdvancedEmi(boolean advancedEmi) {
        isAdvancedEmi = advancedEmi;
    }

    public double getEmi() {
        return emi;
    }

    public void setEmi(double emi) {
        this.emi = emi;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public List<String> getRowMonth() {
        return rowMonth;
    }

    public void setRowMonth(List<String> rowMonth) {
        this.rowMonth = rowMonth;
    }

    public List<String> getRowEMI() {
        return rowEMI;
    }

    public void setRowEMI(List<String> rowEMI) {
        this.rowEMI = rowEMI;
    }

    public List<String> getRowPrincipal() {
        return rowPrincipal;
    }

    public void setRowPrincipal(List<String> rowPrincipal) {
        this.rowPrincipal = rowPrincipal;
    }

    public List<String> getRowIntrest() {
        return rowIntrest;
    }

    public void setRowIntrest(List<String> rowIntrest) {
        this.rowIntrest = rowIntrest;
    }

    public List<String> getRowOutStanding() {
        return rowOutStanding;
    }

    public void setRowOutStanding(List<String> rowOutStanding) {
        this.rowOutStanding = rowOutStanding;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.rowMonth);
        dest.writeStringList(this.rowEMI);
        dest.writeStringList(this.rowPrincipal);
        dest.writeStringList(this.rowIntrest);
        dest.writeStringList(this.rowOutStanding);
        dest.writeDouble(this.loanAmount);
        dest.writeDouble(this.roi);
        dest.writeInt(this.tenure);
        dest.writeDouble(this.emi);
        dest.writeByte(this.isAdvancedEmi ? (byte) 1 : (byte) 0);
    }
}
