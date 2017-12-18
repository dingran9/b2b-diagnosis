package com.eeduspace.b2b.report.constant;

/**
 * 报告常量池
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-09 13:34
 **/
public class ReportConstant {
    /**
     * 年级对比
     */
    public static final  String GRADE_CONTRAST="GRADE_CONTRAST";
    /**
     * 学校对比
     */
    public static final  String SCHOOL_CONTRAST ="SCHOOL_CONTRAST";
    /**
     * 班级对比
     */
    public static final  String CLASS_CONTRAST ="CLASS_CONTRAST";
    /**
     * 教师对比
     */
    public static final  String TEACHER_CONTRAST ="TEACHER_CONTRAST";
    public static String gradeCode2GradeName(String gradeCode){
        switch (gradeCode){
            case "11":
                return "一年级";
            case "12":
                return "二年级";
            case "13":
                return "三年级";
            case "14":
                return "四年级";
            case "15":
                return "五年级";
            case "16":
                return "六年级";
            case "21":
                return "七年级";
            case "22":
                return "八年级";
            case "23":
                return "九年级";
            case "31":
                return "高一";
            case "32":
                return "高二";
            case "33":
                return "高三";
            default:
                return "未知年级";
        }
    }
}
