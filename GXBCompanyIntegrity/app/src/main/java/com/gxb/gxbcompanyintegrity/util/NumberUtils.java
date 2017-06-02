/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gxb.gxbcompanyintegrity.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数字格式化
 *
 * @author venshine
 */
public class NumberUtils {

    private static final int nNumberOfDecimals = 2;

    /**
     * 保留一位小数
     *
     * @param number
     * @return
     */
    public static String formatOneDecimal(double number) {
        DecimalFormat oneDec = new DecimalFormat("##0.0");
        return oneDec.format(number);
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static String formatTwoDecimal(double number, RoundingMode roundingMode) {
        DecimalFormat twoDec = new DecimalFormat("##0.00");
        twoDec.setRoundingMode(roundingMode);
        return twoDec.format(number);
    }


    /**
     * 格式 1,000.00
     * @param number
     * @return
     */
    public static String formatTwoDecimal(double number, int nNumberOfDecimals, RoundingMode roundingMode) {
        DecimalFormat twoDec = new DecimalFormat();
        twoDec.setMaximumFractionDigits(nNumberOfDecimals);
        twoDec.setMinimumFractionDigits(nNumberOfDecimals);
        twoDec.setMinimumIntegerDigits(1);
        twoDec.setRoundingMode(roundingMode);
        return twoDec.format(number);
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static String formatTwoDecimal(double number) {
        DecimalFormat twoDec = new DecimalFormat("##0.00");
        return twoDec.format(number);
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static String formatTwoDecimal(String number, RoundingMode roundingMode) {
        double fNumber = parseDouble(number);
        return formatTwoDecimal(fNumber,roundingMode);
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static String formatTwoDecimal(String number) {
        double fNumber = parseDouble(number);
        return formatTwoDecimal(fNumber);
    }

    /**
     * 保留两位小数百分比
     *
     * @param number
     * @return
     */
    public static String formatTwoDecimalPercent(double number) {
        return formatTwoDecimal(number) + "%";
    }

    /**
     * 四舍五入
     *
     * @param number
     * @param scale  scale of the result returned.
     * @return
     */
    public static double roundingNumber(String number, int scale) {
        return roundingNumber(parseDouble(number), scale, RoundingMode.HALF_UP);
    }

    /**
     * 四舍五入
     *
     * @param number
     * @param scale  scale of the result returned.
     * @return
     */
    public static double roundingNumber(double number, int scale) {
        return roundingNumber(number, scale, RoundingMode.HALF_UP);
    }

    /**
     * 四舍五入
     *
     * @param number
     * @param scale        scale of the result returned.
     * @param roundingMode rounding mode to be used to round the result.
     * @return
     */
    public static double roundingNumber(double number, int scale, RoundingMode roundingMode) {
        BigDecimal b = new BigDecimal(number);
        return b.setScale(scale, roundingMode).doubleValue();
    }

    public static double parseDouble(String sNumber) {
        try {
            if (!TextUtils.isEmpty(sNumber)) {
                sNumber = sNumber.replace(",", "");
                return Double.parseDouble(sNumber);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static float parseFloat(String sNumber) {
        try {
            if (!TextUtils.isEmpty(sNumber)) {
                sNumber = sNumber.replace(",", "");
                return Float.parseFloat(sNumber);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getDecimals(String sInput, int nNumberOfDecimals) {
        double fInput = parseDouble(sInput);
        return getDecimals(fInput, nNumberOfDecimals);
    }

    public static String getDecimals(double fInput, int nNumberOfDecimals) {
        NumberFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(nNumberOfDecimals);
        df.setMinimumFractionDigits(nNumberOfDecimals);
        df.setMinimumIntegerDigits(1);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(fInput);
    }

    public static int parseInt(String sNumber, int nDefault) {
        try {
            if (!TextUtils.isEmpty(sNumber))
                return Integer.parseInt(sNumber);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return nDefault;
    }

    public static boolean compareDouble(double d1, double d2, int newScale) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);
        bd1 = bd1.setScale(newScale, BigDecimal.ROUND_HALF_UP);
        bd2 = bd2.setScale(newScale, BigDecimal.ROUND_HALF_UP);
        return bd1.compareTo(bd2) == 0;
    }

    public static String getFloatIncome(String prodYearIncome, String dispYearIncome) {
        double fProdYearIncome = parseDouble(prodYearIncome);
        double fDispYearIncome = parseDouble(dispYearIncome);
        double fResult = fProdYearIncome - fDispYearIncome;
        if (!compareDouble(fResult, 0, 3)) {
            return "+" + formatTwoDecimalPercent(fResult);
        }
        return "";
    }

    public static String formatAmount(String sInput) {
        return formatAmount(sInput, nNumberOfDecimals);
    }

    public static String formatAmount(String sInput, int nNumberOfDecimals) {
        double fInput = parseDouble(sInput);
        return formatAmount(fInput, nNumberOfDecimals);
    }

    public static String formatAmount(double amount) {
        return formatAmount(amount, nNumberOfDecimals);
    }

    public static String formatAmount(double amount, int nNumberOfDecimals) {
        NumberFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(nNumberOfDecimals);
        df.setMinimumFractionDigits(nNumberOfDecimals);
        df.setMinimumIntegerDigits(1);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(amount);
    }

    /**
     * 格式化金额(以万为单位)
     * @param sInput
     * @return
     */
    public static String formatWanYuanAmount(String sInput) {
        return formatWanYuanAmount(parseDouble(sInput));
    }

    /**
     * 格式化金额(以万为单位)
     * @param amount
     * @return
     */
    public static String formatWanYuanAmount(double amount) {
        int nNumberOfDecimals=0;
        double targetAmount;
        if (amount >= 10000) {
            if(amount%10000>0&&amount%1000>0){
                nNumberOfDecimals=2;
            }else if(amount%10000>0){
                nNumberOfDecimals=1;
            }
            targetAmount = amount / 10000;
            return NumberUtils.formatAmount(targetAmount, nNumberOfDecimals) + "万元";
        }
        return NumberUtils.formatAmount(amount, nNumberOfDecimals) + "元";
    }

}
