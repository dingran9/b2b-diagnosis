
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisBaseQuestionDto;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerRankingDto;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerSheetDto;
import com.eedu.diagnosis.inclass.test.api.enums.ConstantEnum;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisBaseQuestionService;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisStudentAnswerRankingService;
import com.eedu.diagnosis.inclass.test.api.service.DiagnosisStudentAnswerSheetService;
import com.eedu.diagnosis.inclass.test.persist.model.StudentRankingModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.CollectionUtils;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/10/14.
 */
@ContextConfiguration("classpath*:spring-comm-conf.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DiagnosisStudentAnswerSheetTest {

    @Autowired
    DiagnosisStudentAnswerSheetService service;
    @Autowired
    DiagnosisBaseQuestionService diagnosisBaseQuestionService;
    @Autowired
    DiagnosisStudentAnswerRankingService diagnosisStudentAnswerRankingService;



    @Test
    public void test0() throws Exception {
        String aa = "[{\"inClassTestCode\":\"1\",\"rank\":1,\"studentAnswerRankingCode\":\"1\",\"studentCode\":\"1\",\"studentName\":\"1\"},{\"inClassTestCode\":\"2\",\"rank\":2,\"studentAnswerRankingCode\":\"2\",\"studentCode\":\"2\",\"studentName\":\"2\"}]" ;
        List<DiagnosisStudentAnswerRankingDto> dto = JSONObject.parseArray(aa, DiagnosisStudentAnswerRankingDto.class);
        System.out.println("-------------mapCollect---------------------------------" + JSONObject.toJSONString(dto));

        int i = diagnosisStudentAnswerRankingService.batchSaveDiagnosisStudentAnswerRanking(dto);
        System.out.println("------------------------------------------=" + i);
    }
    @Test
    public void test00() throws Exception {
        List<DiagnosisStudentAnswerRankingDto> list = diagnosisStudentAnswerRankingService.selectDiagnosisStudentAnswerRankingList(new DiagnosisStudentAnswerRankingDto());
        System.out.println("-------------mapCollect---------------------------------" + JSONObject.toJSONString(list));

    }










    @Test
    public void test1() throws Exception {
        String aa = "[{\"baseCode\":\"1\",\"createTime\":1508143084000,\"inClassTestCode\":\"1\",\"isRight\":1,\"knowledges\":\"1\",\"rightAnswer\":\"1\",\"sout\":\"1\",\"studentAnswer\":\"1\",\"studentAnswerSheetCode\":\"q\",\"studentCode\":\"1\",\"updateTime\":1508143086000}]\n";
        List<DiagnosisStudentAnswerSheetDto> dto = JSONObject.parseArray(aa, DiagnosisStudentAnswerSheetDto.class);
        int i = service.saveDiagnosisStudentAnswerSheet(dto.get(0));
        System.out.println("------------------------------------------=" + i);
    }

    @Test
    public void test2() throws Exception {
        DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
        List<DiagnosisStudentAnswerSheetDto> list = service.selectDiagnosisStudentAnswerSheetList(dto);

//
//        List<DiagnosisStudentAnswerSheetDto>  isRightList  = list.stream().filter(g -> g.getIsRight() == 1).collect(Collectors.toList());
//        Map<String, List<DiagnosisStudentAnswerSheetDto>>  mapCollect= list.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerSheetDto::getStudentCode));
//        System.out.println("-------------mapCollect---------------------------------"+JSONObject.toJSONString(mapCollect));

        List<Map<String, Integer>> lists = new ArrayList<>();
        Map<String, List<DiagnosisStudentAnswerSheetDto>> mapCollect = list.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerSheetDto::getSout));

        System.out.println("-------------mapCollect---------------------------------" + JSONObject.toJSONString(mapCollect));
        String aa = "aa";
        for (Map.Entry<String, List<DiagnosisStudentAnswerSheetDto>> entry : mapCollect.entrySet()) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            List<DiagnosisStudentAnswerSheetDto> isWrongList = list.stream().filter(g -> g.getIsRight() == 0).collect(Collectors.toList());
            int wrongCount = entry.getValue().stream().filter(g -> g.getIsRight() == 0).collect(Collectors.toList()).size();
            int rightCount = entry.getValue().stream().filter(g -> g.getIsRight() == 1).collect(Collectors.toList()).size();
            int flag = entry.getValue().stream().filter(g -> g.getIsRight() == 1 && g.getStudentCode().equals(aa)).collect(Collectors.toList()).size();
            map.put("sout", Integer.parseInt(entry.getKey()));
            map.put("rightCount", rightCount);
            map.put("wrongCount", wrongCount);
            map.put("flag", flag);
            lists.add(map);
        }
        System.out.println("-------------dgfsdf---------------------------------" + JSONObject.toJSONString(lists));
    }

    @Test
    public void test3() throws Exception {
        DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
        dto.setInClassTestCode("1");
        List<DiagnosisStudentAnswerSheetDto> list = service.selectDiagnosisStudentAnswerSheetList(dto);
        List<StudentRankingModel> lists = new ArrayList<StudentRankingModel>();
        List<DiagnosisStudentAnswerSheetDto> isRightList = list.stream().filter(g -> g.getIsRight() == 1).collect(Collectors.toList());
        Map<String, List<DiagnosisStudentAnswerSheetDto>> mapCollect = list.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerSheetDto::getStudentCode));
        for (Map.Entry<String, List<DiagnosisStudentAnswerSheetDto>> entry : mapCollect.entrySet()) {
            StudentRankingModel bean = new StudentRankingModel();
            bean.setStudentName(entry.getKey());
            bean.setNum(entry.getValue().stream().filter(g -> g.getIsRight() == 1).collect(Collectors.toList()).size());
            lists.add(bean);
        }
        lists.sort((o1, o2) -> o2.getNum() - o1.getNum());
        System.out.println("-------------dgfsdf---------------------------------" + JSONObject.toJSONString(lists));
    }

    @Test
    public void test4() throws Exception {
        DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
        dto.setInClassTestCode("1");
        List<DiagnosisStudentAnswerSheetDto> list = service.selectDiagnosisStudentAnswerSheetList(dto);

//
//        List<DiagnosisStudentAnswerSheetDto>  isRightList  = list.stream().filter(g -> g.getIsRight() == 1).collect(Collectors.toList());
//        Map<String, List<DiagnosisStudentAnswerSheetDto>>  mapCollect= list.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerSheetDto::getStudentCode));
//        System.out.println("-------------mapCollect---------------------------------"+JSONObject.toJSONString(mapCollect));


        String studentCode = "aa";
        Map<String, Integer> map = new HashMap<String, Integer>();

        List<DiagnosisStudentAnswerSheetDto> studentList = list.stream().filter(g -> g.getStudentCode().equals(studentCode)).collect(Collectors.toList());

        int wrongCount = studentList.stream().filter(g -> g.getIsRight() == 0).collect(Collectors.toList()).size();
        int rightCount = studentList.stream().filter(g -> g.getIsRight() == 1).collect(Collectors.toList()).size();
        map.put("right", rightCount);
        map.put("wrong", wrongCount);


        System.out.println("-------------dgfsdf---------------------------------" + JSONObject.toJSONString(map));
    }

    @Test
    public void test5() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        List<DiagnosisBaseQuestionMode> baseQuestionModeList = new ArrayList<DiagnosisBaseQuestionMode>();
        List<DiagnosisBaseQuestionDto> list = diagnosisBaseQuestionService.selectDiagnosisBaseQuestionByInClassTest("1", null, null).getList();
        DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
        dto.setInClassTestCode("1");
        List<DiagnosisStudentAnswerSheetDto> bean = service.selectDiagnosisStudentAnswerSheetList(dto);
        List<Map<String, Object>> accuracy = getAccuracy(bean, "aa", 1);
        for (DiagnosisBaseQuestionDto po : list) {
            for (Map<String, Object> stringMap : accuracy) {
                if (Integer.parseInt(po.getSout()) == (Integer) stringMap.get("sout")) {
                    DiagnosisBaseQuestionMode baseQuestion = new DiagnosisBaseQuestionMode();
                    BeanUtils.copyProperties(po, baseQuestion);
                    baseQuestion.setRightCount((Integer) stringMap.get("rightCount"));
                    baseQuestion.setStudentAnswer((String) stringMap.get("studentAnswer"));
                    baseQuestionModeList.add(baseQuestion);
                }
            }
        }

        map.put("result", baseQuestionModeList);
        System.out.println("-------------dgfsdf---------------------------------" + JSONObject.toJSONString(baseQuestionModeList));

    }

    @Test
    public void test6() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        List<DiagnosisBaseQuestionMode> baseQuestionModeList = new ArrayList<DiagnosisBaseQuestionMode>();
        List<DiagnosisBaseQuestionDto> list = diagnosisBaseQuestionService.selectDiagnosisBaseQuestionByInClassTest("1", null, null).getList();
        DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
        dto.setInClassTestCode("1");
        List<DiagnosisStudentAnswerSheetDto> beans = service.selectDiagnosisStudentAnswerSheetList(dto);

        List<StudentAnswerSheetModel> modelsList = new ArrayList<StudentAnswerSheetModel>();
        for (DiagnosisStudentAnswerSheetDto bean : beans) {
            List<KnowledgeDateModel> modelList = JSONObject.parseArray(bean.getKnowledges(), KnowledgeDateModel.class);
            if (!CollectionUtils.isEmpty(modelList)) {
                for (KnowledgeDateModel knowledgeModel : modelList) {
                    StudentAnswerSheetModel studentAnswerSheetModel = new StudentAnswerSheetModel();
                    BeanUtils.copyProperties(bean, studentAnswerSheetModel);
                    studentAnswerSheetModel.setKnowledgeCode(knowledgeModel.getId());
                    studentAnswerSheetModel.setKnowledgeName(knowledgeModel.getName());
                    modelsList.add(studentAnswerSheetModel);
                }
            }
        }
        if (!CollectionUtils.isEmpty(modelsList)) {
            List<Map<String, Object>> knowledgeRanking = getKnowledgeCout(modelsList);
            List<Map<String, Object>> knowledgeReport = getKnowledgeReport(modelsList,"aa");

            System.out.println("-------------knowledgeRanking---------------------------------" + JSONObject.toJSONString(knowledgeRanking));
            System.out.println("-------------knowledgeReport---------------------------------" + JSONObject.toJSONString(knowledgeReport));


        }else {
            System.out.println("-------------dgfsdf---------------------------------" + JSONObject.toJSONString("aa"));
        }








    }

    public List<Map<String, Object>> getKnowledgeCout(List<StudentAnswerSheetModel> modelsList) {
        List<Map<String,Object>>  lists = new ArrayList<>();
        Map<String, List<StudentAnswerSheetModel>> mapCollect = modelsList.stream().collect(Collectors.groupingBy(StudentAnswerSheetModel::getKnowledgeCode));
        System.out.println("-------------dgfsdf---------------------------------" + JSONObject.toJSONString(mapCollect));

       for (Map.Entry<String, List<StudentAnswerSheetModel>> entry : mapCollect.entrySet()) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("knowledgeCode",entry.getKey());
            map.put("knowledgeName",entry.getValue().get(0).getKnowledgeName());
            List<String> collect = entry.getValue().stream().map(StudentAnswerSheetModel::getSout).collect(Collectors.toList());
            map.put("soutCount",collect.stream().distinct().collect(Collectors.toList()).size());
            lists.add(map);
        }
        return  lists;
    }


    public List<Map<String, Object>> getKnowledgeReport(List<StudentAnswerSheetModel> modelsList,String studentCode) {
        List<Map<String,Object>>  lists = new ArrayList<>();

        Map<String, List<StudentAnswerSheetModel>> mapCollect = modelsList.stream().collect(Collectors.groupingBy(StudentAnswerSheetModel::getKnowledgeCode));
        for (Map.Entry<String, List<StudentAnswerSheetModel>> entry : mapCollect.entrySet()) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("knowledgeCode",entry.getKey());
            map.put("knowledgeName",entry.getValue().get(0).getKnowledgeName());
            map.put("rightCount", entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType() && g.getStudentCode().equals(studentCode)).collect(Collectors.toList()).size());
            lists.add(map);
        }
        return  lists;
    }







    public  List<Map<String,Object>> getAccuracy(List<DiagnosisStudentAnswerSheetDto> list, String studentCode,Integer additional){
        List<Map<String,Object>>  lists = new ArrayList<>();
        Map<String, List<DiagnosisStudentAnswerSheetDto>> mapCollect = list.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerSheetDto::getSout));
        for (Map.Entry<String, List<DiagnosisStudentAnswerSheetDto>> entry : mapCollect.entrySet()) {
            Map<String,Object> map = new HashMap<String,Object>();
            int wrongCount = entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.WRONG.getType()).collect(Collectors.toList()).size();
            int rightCount = entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType()).collect(Collectors.toList()).size();
            int flag = entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType() && g.getStudentCode().equals(studentCode)).collect(Collectors.toList()).size();
            List<DiagnosisStudentAnswerSheetDto> collect = entry.getValue().stream().filter(g -> g.getStudentCode().equals(studentCode)).collect(Collectors.toList());

            map.put("sout",Integer.parseInt(entry.getKey()));
            map.put("rightCount",rightCount);
            map.put("wrongCount",wrongCount);
            map.put("flag",flag);
            if (additional!=null && additional==1){
                collect.stream().forEach(aa->{
                    if (aa.getSout().equals(entry.getKey())){
                        map.put("studentAnswer",aa.getStudentAnswer());
                    }
                });
            }
            lists.add(map);
        }
        return  lists;
    }


}


