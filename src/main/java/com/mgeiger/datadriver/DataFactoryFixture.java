/*
 * Returns formatted string data
 */
package com.mgeiger.datadriver;

import java.text.DecimalFormat;

/**
 * @class DataFactoryFixture
 */
public class DataFactoryFixture {

    private static DataFactoryFixture INSTANCE;
    private String hyphenatedNdc;
    private static String price;

    public static DataFactoryFixture getInstance() {
        INSTANCE = (INSTANCE == null) ? new DataFactoryFixture() : INSTANCE;

        return INSTANCE;
    }

    public void setHyphenateNdc(String ndc) {
        hyphenatedNdc = ndc;
    }

    public String getHyphenateNdc() {
        return hyphenatedNdc;
    }

    public String storeHyphenatedNdc() {
        String ndc = this.hyphenatedNdc;
        String ndcString = ndc.substring(0, 5) + "-"
                + ndc.substring(5, 9) + "-" + ndc.substring(9);

        return ndcString;
    }
    
    public void setPrice(String formatPrice) {
        price = formatPrice;
    }
    
    public static String getPrice() {
        return price;
    }

    public static String priceWithDecimal(Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.00");
        return formatter.format(price);
    }

    public static String priceWithoutDecimal(Double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(price);
    }

    public static String fetchFloatToMoneyFormat() {
        String toShow = DataFactoryFixture.getPrice();
        if (toShow.indexOf(".") > 0) {
            return priceWithDecimal(Double.parseDouble(toShow));
        } else {
            return priceWithoutDecimal(Double.parseDouble(toShow));
        }
    }
}
