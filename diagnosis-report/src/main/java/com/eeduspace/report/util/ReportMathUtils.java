package com.eeduspace.report.util;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 报告计算工具类
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-12 9:55
 **/
public class ReportMathUtils {
    /**
     * 计算标准差
     * @param doubles 分数集合
     * @param avg 平均分
     * @return
     */
    public static Double getStandardDeviation(List<Double> doubles, Double avg) {
        List<Double> sumDouble= Lists.newArrayList();
        //计算平均分差值 平方
        doubles.stream().forEach(d -> {
            BigDecimal subtractScore = new BigDecimal(d).subtract(new BigDecimal(avg));
            Double pow = subtractScore.pow(2).doubleValue();
            sumDouble.add(pow);
        });

        Double powSumScore = sumDouble.stream().collect(Collectors.summingDouble(powScore -> powScore));
        BigDecimal divideScore = new BigDecimal(powSumScore).divide(new BigDecimal(sumDouble.size()),10,RoundingMode.HALF_UP);
        return new BigDecimal(Math.sqrt(divideScore.doubleValue())).setScale(10, RoundingMode.HALF_UP).doubleValue();
    }
    /**
     *四舍五入
     * @param d 数值
     * @param numberOfBits  保留位数
     * @return
     */
    public static Double rounding(Double d,int numberOfBits){
        BigDecimal b=new BigDecimal(d).setScale(numberOfBits,RoundingMode.HALF_UP);
        return b.doubleValue();
    }



    /**
     * 获取平均标准分
     * @param gradeAveScore 平均分
     * @param gradeDeviationScore 标准差
     * @param baseDoubles 基础得分集合
     * @return
     */
    public static Double getAveStandard(Double gradeAveScore, Double gradeDeviationScore, List<Double> baseDoubles) {
        return baseDoubles.stream().map(d -> {
            BigDecimal c = getSingleStandardBigDecimal(gradeAveScore, gradeDeviationScore, d);
            return c.doubleValue();
        }).collect(Collectors.averagingDouble(score -> score));
    }



    /**
     * 计算单个的标准分
     * @param gradeAveScore  平均分
     * @param gradeDeviationScore 标准差
     * @param getScore 当前得分
     * @return
     */
    private static BigDecimal getSingleStandardBigDecimal(Double gradeAveScore, Double gradeDeviationScore, Double getScore) {
        BigDecimal a=new BigDecimal(getScore).subtract(new BigDecimal(gradeAveScore));
        BigDecimal b=new BigDecimal(gradeDeviationScore);
        if(b.equals(new BigDecimal(0))){
            return new BigDecimal(0);
        }
        return a.divide(b, 10, RoundingMode.HALF_UP);
    }
}
