package com.eedu.diagnosis.protal.model;

import com.eedu.diagnosis.common.model.paperEntity.BaseProductionModel;
import com.eedu.diagnosis.common.model.paperEntity.SimpleTree;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 判题信息与答题信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-09-20 11:30
 **/
@Data
public class AnswerAndMarkQuestionModel implements Serializable{
    private String answerRecordCode;
    private String questionCode;
    private String complexQuestionCode;
    private String answerResult;
    private Integer markQuestionResult;
    private List<SimpleTree> tree=new ArrayList<SimpleTree>(0);
    private List<BaseProductionModel> basetree=new ArrayList<BaseProductionModel>(0);
    private Double score;
    private Double surfaceScore;
}
