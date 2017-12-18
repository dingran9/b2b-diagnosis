package com.eeduspace.report.model;

import com.eeduspace.b2b.report.model.question.KnowledgeModel;
import com.eeduspace.b2b.report.model.question.Questions;
import com.eeduspace.b2b.report.model.report.WrongKnowledgeRankModel;
import lombok.Data;

import java.util.List;

/**
 * <p>描述  答题结果处理后 实体</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 19:35
 * @param    
 * @return   
**/
@Data
public class DealWithAnswerResultModel {
    /**
     * 处理后试题集合 用于获取报告
     */
    List<Questions> questionses;
    /**
     * 错题题号统计
     */
    List<String> wrongQuestionSn;
    /**
     * 错误知识点排行统计
     */
    List<WrongKnowledgeRankModel> wrongKnowledgeRank;

    /**
     * 知识点信息
     */
    List<KnowledgeModel> knowledgeModelList;

}
