package com.eeduspace.b2b.report.enumerate;

/**
 * Created by zhuchaowei on 2017/3/17.
 */
public enum ReportCategoryEnum {
    /**
     * 教师报告
     */
    TEACHER_REPORT("教师报告","teacher_report"),
    /**
     * 校长报告
     */
    PRINCIPAL_REPORT("教师报告","principal_report"),
    /**
     * 所有类别 报告   教师报告与校长报告
     */
    ALL_REPORT("所欲类别报告（包含教师与校长）","all_reprot")
    ;

    ReportCategoryEnum(String desc,String code) {
    }
}
