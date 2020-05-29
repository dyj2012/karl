package com.karl.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.MessageFormat;

/**
 * BigDecimal转化工具类
 *
 * @author 杜永军
 * @date 2018/08/22
 */
public class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    public static BigDecimal convertDigDecimal(Object obj) {
        if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj);
        } else {
            return new BigDecimal(String.valueOf(obj));
        }
    }

    /**
     * 将金额格式化成字符
     *
     * @param amount    金额
     * @param precision 保留小数位
     * @param thousands 是否显示千分位
     * @param symbol    货币符号
     * @return
     */
    public static String bigDecimalToString(BigDecimal amount, int precision, boolean thousands, String symbol) {
        StringBuilder precisionStr = new StringBuilder("0");
        if (precision > 0) {
            precisionStr.append(".");
            for (int i = 0; i < precision; i++) {
                precisionStr.append("0");
            }
        }
        if (thousands) {
            precisionStr.insert(0, "###,##");
        }
        DecimalFormat decimalFormat = new DecimalFormat(precisionStr.toString());
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return symbol == null ? decimalFormat.format(amount)
                : MessageFormat.format("{0}{1}", symbol, decimalFormat.format(amount));
    }
}
