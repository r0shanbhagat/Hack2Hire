package com.hack.easyhomeloan.utilities;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalDigitsInputFilter implements InputFilter {

    private Pattern mPattern;
    private int digitsBeforeZero;

    public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
        this.digitsBeforeZero = digitsBeforeZero;
        mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches()) {
            if (dest.toString().contains(".")) {
                if (dest.toString().substring(dest.toString().indexOf(".")).length() > 2) {
                    return "";
                }
                return null;
            } else if (!Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}").matcher(dest).matches()) {
                if (!dest.toString().contains(".")) {
                    if (source.toString().equalsIgnoreCase(".")) {
                        return null;
                    }
                }
                return "";
            } else {
                return null;
            }
        }

        return null;
    }
}