package com.eedu.diagnosis.exam.test;

import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.exam.persist.po.DiagnosisMarkQuestionRecordPo;
import com.eedu.diagnosis.exam.persist.po.DiagnosisStudentAnswerRecordPo;
import com.eedu.diagnosis.exam.service.DiagnosisMarkQuestionRecordService;
import com.eedu.diagnosis.exam.service.DiagnosisStudentAnswerRecordService;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by dqy on 2017/3/14.
 */
@ContextConfiguration("classpath*:META-INF/spring/spring-*.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AnswerRecordTest {
    @Autowired
    private DiagnosisStudentAnswerRecordService diagnosisStudentAnswerRecordService;
    @Autowired
    private DiagnosisMarkQuestionRecordService diagnosisMarkQuestionRecordService;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
//    @Autowired
//    private MqProducerClientTemplate mqProducerClientTemplate;
    @Test
    public  void testMarkQuestion(){
        List<DiagnosisMarkQuestionRecordPo> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            DiagnosisMarkQuestionRecordPo diagnosisMarkQuestionRecordPo =new DiagnosisMarkQuestionRecordPo();
            diagnosisMarkQuestionRecordPo.setAnswerRecordCode("123"+i);
            list.add(diagnosisMarkQuestionRecordPo);
        }
        
        try {
            diagnosisMarkQuestionRecordService.saveList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test333(){
        System.out.println(redisClientTemplate.get("dong29"));
    }
    @Test
    public void add(){
        DiagnosisStudentAnswerRecordPo ar = null;
        List<DiagnosisStudentAnswerRecordPo> list = new ArrayList<>();
        for (int i = 5;i < 9;i++){

            ar = new DiagnosisStudentAnswerRecordPo();
            ar.setCode("F1A2BEE8EA32F77633EA04ACD1669A6"+i);
            ar.setStudentCode(i);
            ar.setQuestionCode("ACDEA31669A6D2F33EA081F7764A2BE"+i);
            ar.setQuestionScore(new BigDecimal("6.00"));
            ar.setCreateTime(new Date());
            ar.setUpdateTime(new Date());
            list.add(ar);
        }
        try {
            diagnosisStudentAnswerRecordService.saveList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void listByIds(){
        Object[] list = {"33EA0A2BEE8EA3ACD1669A6D2F1F7764","69A6D2F1F776433EA0A2BEE8EA3ACD16"};
        List<Object> strings = Arrays.asList(list);
//        List<Object> strings = new ArrayList<>();
        System.out.println(strings.size());
        List<DiagnosisStudentAnswerRecordPo> diagnosisStudentAnswerRecordPos =
                null;
        try {
            diagnosisStudentAnswerRecordPos = diagnosisStudentAnswerRecordService.listByIds(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(diagnosisStudentAnswerRecordPos.size());
    }
    @Test
    public void list(){
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("code","F1A2BEE8EA32F77633EA04ACD1669A6D");
        PageHelper.startPage(1, 2);
        List<DiagnosisStudentAnswerRecordPo> answerRecordPos = null;
        try {
            answerRecordPos = diagnosisStudentAnswerRecordService.findByCondition(queryMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        List<DiagnosisStudentAnswerRecordPo> answerRecordPos = diagnosisStudentAnswerRecordService.getAll();
//        redisClientTemplate.set("dong===========","1111111");
        System.out.println(answerRecordPos.size());
//        final Message message;
//        try {
//            message = new Message("diagnosisExamServiceTopic",// topic
//                    "goodsService",// tag (5)
//                    "addGoods",//key
//                    "aaaaaaa".getBytes(RemotingHelper.DEFAULT_CHARSET)// body (6)
//            );
//            mqProducerClientTemplate.getDefaultProducer().send(message, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    try {
//                        System.out.printf("%-10d OK %s %s %s %s %s %n", 1, sendResult.getMsgId(),sendResult.getQueueOffset(),
//                                sendResult.getMessageQueue(),sendResult.getOffsetMsgId(),
//                                new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET));
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//                @Override
//                public void onException(Throwable e) {
//                    System.out.printf("%-10d Exception %s %n", 1, e);
//                    e.printStackTrace();
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    @Test
//    public void remote(){
//        UserDto ud = new UserDto();
//        ud.setUserName("1111111朱2ba");
//        testBaseOpenService.save(ud);
//    }
    @Test
    public void delete(){
//        DiagnosisStudentAnswerRecordPo ds = new DiagnosisStudentAnswerRecordPo();
//  ds.setCode("A2BEE8EA32F1F77633EA04ACD1669A6D");
//     diagnosisStudentAnswerRecordService.delete("F1A2BEE8EA32F77633EA04ACD1669A6D");
        Object[] list = {"33EA0A2BEE8EA3ACD1669A6D2F1F7764","69A6D2F1F776433EA0A2BEE8EA3ACD16"};
        List<Object> strings = Arrays.asList(list);
        try {
            diagnosisStudentAnswerRecordService.delete(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void update(){
        DiagnosisStudentAnswerRecordPo ds = new DiagnosisStudentAnswerRecordPo();
        ds.setCode("33EA0A2BEE8EA3ACD1669A6D2F1F7764");
        ds.setUpdateTime(new Date());
        try {
            diagnosisStudentAnswerRecordService.update(ds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
