package com.eedu.diagnosis.protal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.*;
import com.eedu.diagnosis.inclass.test.api.enums.ConstantEnum;
import com.eedu.diagnosis.inclass.test.api.service.*;
import com.eedu.diagnosis.protal.model.classTest.*;
import com.eedu.diagnosis.protal.service.StudentClassTestService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zz on 2017/10/18.
 */
@Service
public class StudentClassTestServiceImpl implements StudentClassTestService {



    @Autowired
    private DiagnosisInClassRelationService diagnosisInClassRelationService ;//课中测-基础题
    @Autowired
    private DiagnosisInClassTestService diagnosisInClassTestService ;//课中测
    @Autowired
    private DiagnosisStudentAnswerSheetService diagnosisStudentAnswerSheetService ;//课中测答题
    @Autowired
    private DiagnosisStudentAnswerMachineService diagnosisStudentAnswerMachineService ;
    @Autowired
    private AuthUserManagerService authUserManagerService;
    @Autowired
    private DiagnosisStudentAnswerRankingService  diagnosisStudentAnswerRankingService ;
    /**
     *  课中测报告数量
     * @param model
     */
    @Override
    public List<Map<String, Object>> classTestListCount(DiagnosisInClassTestModel model) throws Exception {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        DiagnosisInClassTestDto dto = new DiagnosisInClassTestDto();
        BeanUtils.copyProperties(model,dto);
        dto.setIsRead(ConstantEnum.START.getType());
        PageInfo<DiagnosisInClassTestDto>   pageInfo = diagnosisInClassTestService.selectDiagnosisInClassTestPage(dto);
        if (!CollectionUtils.isEmpty(pageInfo.getList())){
            Map<Integer, List<DiagnosisInClassTestDto>> collect = pageInfo.getList().stream().collect(Collectors.groupingBy(DiagnosisInClassTestDto::getSubjectCode));
            for (Map.Entry<Integer, List<DiagnosisInClassTestDto>> entry : collect.entrySet()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("subject",entry.getKey());
                map.put("count",entry.getValue().size());
                lists.add(map);
            }
        }
        return lists;
    }
    /**
     *  课中测分页列表
     * @param model
     */
    @Override
    public Map<String, Object> classTestList(DiagnosisInClassTestModel model) throws Exception {
             Map<String, Object> map = new HashMap<String, Object>();
            int studentTotalCount = 0;
            DiagnosisInClassTestDto dto = new DiagnosisInClassTestDto();
            dto.setClassCode(model.getClassCode());
            dto.setStatus(model.getStatus());
            dto.setEquipmentType(model.getEquipmentType());
            dto.setPageNum(model.getPageNum());
            dto.setPageSize(model.getPageSize());
            dto.setSubjectCode(model.getSubjectCode());
            PageInfo<DiagnosisInClassTestDto>   pageInfo = diagnosisInClassTestService.selectDiagnosisInClassTestPage(dto);
            map = getMap(pageInfo);
            if (model.getClassCode() != null){
                studentTotalCount = getStudentCount(Integer.parseInt(model.getClassCode())).size();
            }
        map.put("studentTotalCount",studentTotalCount);
        return map;
    }
    /**
     *  课中测code查询基础卷列表
     */
    @Override
    public Map<String, Object> baseQuestionListByClassTestCode(DiagnosisStudentAnswerSheetModel model) throws Exception {
        List<DiagnosisBaseQuestionDto> list = getBaseQuestionList(model);
        if (StringUtils.isNotBlank(model.getSout())){
            list = list.stream().filter(g-> g.getSout().equals(model.getSout())).collect(Collectors.toList());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows",list);
        return map;
    }
    /**
     *  课中测修改阅读状态
     */
    @Override
    public boolean classTestUpdate(DiagnosisInClassTestModel model) throws Exception {
        DiagnosisInClassTestDto dto = new DiagnosisInClassTestDto();
        BeanUtils.copyProperties(model,dto);
        dto.setIsRead(ConstantEnum.END.getType());
        return   diagnosisInClassTestService.updateDiagnosisInClassTest(dto) > 0;
    }
    /** 课中测--提交课中测**/
    @Override
    public boolean studentSubmit(DiagnosisStudentAnswerSheetModel model) throws Exception {

        DiagnosisInClassRelationDto dtos = new DiagnosisInClassRelationDto();
        dtos.setInClassTestCode(model.getInClassTestCode());
        dtos.setBaseCode(model.getBaseCode());
        List<DiagnosisInClassRelationDto> list1 = getInClassRelationList(dtos);

        DiagnosisBaseQuestionDto bean = new DiagnosisBaseQuestionDto();
        BeanUtils.copyProperties(list1.get(0),bean);
        DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();

        BeanUtils.copyProperties(model,dto);
        dto.setRightAnswer(bean.getKnowledges());
        dto.setRightAnswer(bean.getRightAnswer());
        dto.setKnowledges(bean.getKnowledges());
        dto.setStudentAnswerSheetCode(getUUID());
        dto.setIsRight(bean.getRightAnswer().equals(model.getStudentAnswer()) ? ConstantEnum.RIGHT.getType() : ConstantEnum.WRONG.getType());
        return  diagnosisStudentAnswerSheetService.saveDiagnosisStudentAnswerSheet(dto) > 0 ;
    }
    /**
     * 解锁课中测试题
     */
    @Override
    public Map<String, Object> unlockClassTest(DiagnosisStudentAnswerSheetModel model) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        DiagnosisStudentAnswerSheetDto dtos = new DiagnosisStudentAnswerSheetDto();
        dtos.setInClassTestCode(model.getInClassTestCode());
        dtos.setStudentCode(model.getStudentCode());
        List<DiagnosisStudentAnswerSheetDto> studentAnswerSheetDtos = diagnosisStudentAnswerSheetService.selectDiagnosisStudentAnswerSheetList(dtos);
        if (!CollectionUtils.isEmpty(studentAnswerSheetDtos)){
            List<String> collect = studentAnswerSheetDtos.stream().map(DiagnosisStudentAnswerSheetDto::getSout).collect(Collectors.toList());
            map.put("studentAnswer",collect);
        }
        /**获取结束未开始的序号**/
        map.put("soutList",getSize(model));
        List<DiagnosisInClassRelationDto> lists = getUnLockList(model.getInClassTestCode(),ConstantEnum.HAS_ENDED.getType());
        if (!CollectionUtils.isEmpty(lists)){
            map.put("sout",lists.get(0).getSout());
            map.put("question","end");
            return  map;
         }
        /**获取当前开始状态的序号**/
        List<DiagnosisInClassRelationDto> list = getUnLockList(model.getInClassTestCode(),ConstantEnum.START.getType());
            if (CollectionUtils.isEmpty(list)){
                map.put("sout", "-1");
                return map;
            }else if (list.size() == 1){
                map.put("sout", list.get(0).getSout());
                return map;
            }else {
            list.sort((o1, o2) -> Integer.parseInt(o1.getSout())-Integer.parseInt(o2.getSout()));
                map.put("sout", list.get(0).getSout());
                return  map;
            }
        }
    /** 课中测--报告总览**/
    @Override
    public Map<String,Object> reportOverview(DiagnosisStudentAnswerSheetModel model) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DiagnosisStudentAnswerSheetDto> list = getDtoList(model);
        List<StudentRankingModel> studentRanking1 = new ArrayList<StudentRankingModel>();
        map.put("right",0);
        map.put("wrong",0);
        if (!CollectionUtils.isEmpty(list)){
            Map<String, Integer> overview = getOverview(list, model.getStudentCode());
            map.put("right",overview.get("rightCount"));
            map.put("wrong",overview.get("wrongCount"));
        }

        DiagnosisStudentAnswerRankingDto dto = new DiagnosisStudentAnswerRankingDto();
        dto.setInClassTestCode(model.getInClassTestCode());
        List<DiagnosisStudentAnswerRankingDto> studentRanking = diagnosisStudentAnswerRankingService.selectDiagnosisStudentAnswerRankingList(dto);
        if (!CollectionUtils.isEmpty(studentRanking)){
            for(int i=0;i<studentRanking.size();i++){
                StudentRankingModel model1 = new StudentRankingModel();
                if (i < 3 ){
                    model1.setStudentcode(  studentRanking.get(i).getStudentCode());
                    model1.setStudentName(  studentRanking.get(i).getStudentName());
                    model1.setNum(studentRanking.get(i).getRank());
                    studentRanking1.add(model1);
                    continue;
                }
                if (studentRanking.get(i).getStudentCode().equals(model.getStudentCode())){
                    model1.setStudentcode(  studentRanking.get(i).getStudentCode());
                    model1.setStudentName(  studentRanking.get(i).getStudentName());
                    model1.setNum(studentRanking.get(i).getRank());
                    studentRanking1.add(model1);
                }
            }
        }
        map.put("ranking",studentRanking1);


        return map;
    }
    /** 课中测--知识点报告**/
    @Override
    public Map<String,Object> reportByKnowledge(DiagnosisStudentAnswerSheetModel model) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> knowledgeProportion = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> knowledgeReport= new ArrayList<Map<String, Object>>();
        List<DiagnosisStudentAnswerSheetDto> list = getDtoList(model);
        if (!CollectionUtils.isEmpty(list)){
            List<StudentAnswerSheetModel> recombineList = getRecombineList(list);
            if (!CollectionUtils.isEmpty(recombineList)) {
                knowledgeProportion = getKnowledgeCout(recombineList,list);
                knowledgeReport = getKnowledgeReport(recombineList, model.getStudentCode());
            }
        }
        map.put("knowledgeReport",knowledgeReport);
        map.put("knowledgeProportion",knowledgeProportion);
        return map;

    }
    /** 课中测--正确率**/
    @Override
    public  Map<String,Object> reportByAccuracy(DiagnosisStudentAnswerSheetModel model) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<DiagnosisStudentAnswerSheetDto> list =  getDtoList(model);
        List<Map<String, Object>> accuracys = new ArrayList<Map<String, Object>>();
        List<String> sizes = getSize(model);
        if (!CollectionUtils.isEmpty(list)){
            List<Map<String, Object>> accuracy = getAccuracy(list, model.getStudentCode(), null);
            for(int i=0;i<sizes.size();i++){
                int k = 0;
                for (Map<String, Object> maps : accuracy){
                    Integer sout = (Integer) maps.get("sout");
                    if (Integer.parseInt(sizes.get(i)) == (sout)){
                     k++;
                    }
                }
                if (k !=0){
                    for (Map<String, Object> map2 : accuracy){
                        Integer sout = (Integer) map2.get("sout");
                        if ( sout== Integer.parseInt(sizes.get(i))){
                            accuracys.add(map2);
                        }
                    }
                }else {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("sout",Integer.parseInt(sizes.get(i)));
                    map1.put("rightCount",0);
                    map1.put("wrongCount",0);
                    map1.put("flag",-1);
                    accuracys.add(map1);
                }

            }
            map.put("accuracy",accuracys);
            map.put("soutList",sizes);
        }
        return map;
    }
    /** 课中测--报告合并**/
    @Override
    public Map<String, Object> report(DiagnosisStudentAnswerSheetModel model) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = reportOverview(model);
        Map<String, Object> map2 = reportByKnowledge(model);
        Map<String, Object> map3 = reportByAccuracy(model);
        map.put("right",map1.get("right"));
        map.put("wrong",map1.get("wrong"));
        map.put("ranking",map1.get("ranking"));
        map.put("knowledgeReport",map2.get("knowledgeReport"));
        map.put("knowledgeProportion",map2.get("knowledgeProportion"));
        map.put("accuracy",map3.get("accuracy"));
        map.put("soutList",map3.get("soutList"));
        return map;
    }
    /**
     * 测试详情
     */
    @Override
    public Map<String, Object> questionDetails(DiagnosisStudentAnswerSheetModel model) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        List<DiagnosisBaseQuestionMode> baseQuestionModeList = new ArrayList<DiagnosisBaseQuestionMode>();
        List<DiagnosisBaseQuestionDto> list = getBaseQuestionList(model);
        List<DiagnosisStudentAnswerSheetDto> bean = getDtoList(model);
        if (!CollectionUtils.isEmpty(bean)){
            List<Map<String, Object>> accuracy = getAccuracy(bean,model.getStudentCode(),1);
            for (DiagnosisBaseQuestionDto po : list) {
                int k = 0;
                for (Map<String, Object> stringMap : accuracy) {
                    if (Integer.parseInt(po.getSout())==(Integer) stringMap.get("sout")) {
                      k++;
                    }
                }
                if (k != 0) {
                    for (Map<String, Object> stringMap : accuracy) {
                        if (Integer.parseInt(po.getSout()) == (Integer) stringMap.get("sout")) {
                            DiagnosisBaseQuestionMode baseQuestion = new DiagnosisBaseQuestionMode();
                            BeanUtils.copyProperties(po, baseQuestion);
                            baseQuestion.setRightCount((Integer) stringMap.get("rightCount"));
                            baseQuestion.setStudentAnswer((String) stringMap.get("studentAnswer"));
                            baseQuestionModeList.add(baseQuestion);
                        }
                    }
                }else {
                    DiagnosisBaseQuestionMode baseQuestions = new DiagnosisBaseQuestionMode();
                    BeanUtils.copyProperties(po, baseQuestions);
                    baseQuestions.setRightCount(0);
                    baseQuestions.setStudentAnswer(null);
                    baseQuestionModeList.add(baseQuestions);
                }
             }
            map.put("rows",baseQuestionModeList);
        }else {
            map.put("rows",PageHelperUtil.converterList(list,DiagnosisBaseQuestionMode.class));
        }

        return map;
    }
    /**
     *  答题绑定用户添加
     */
    @Override
    public boolean studentSave(DiagnosisStudentAnswerMachineModel model)throws Exception {
        boolean b = false;
        List<DiagnosisStudentAnswerMachineDto> list = studentList(model);
        if (!CollectionUtils.isEmpty(list)){
            DiagnosisStudentAnswerMachineDto diagnosisStudentAnswerMachineDto = list.get(0);
            diagnosisStudentAnswerMachineDto.setMachineCode(model.getMachineCode());
            b = diagnosisStudentAnswerMachineService.updateDiagnosisStudentAnswerMachine(diagnosisStudentAnswerMachineDto) > 0;
        }else {
            DiagnosisStudentAnswerMachineDto dto = new DiagnosisStudentAnswerMachineDto();
            BeanUtils.copyProperties(model,dto);
            dto.setStudentAnswerMachineCode(getUUID());
            b = diagnosisStudentAnswerMachineService.saveDiagnosisStudentAnswerMachine(dto) > 0;
        }
        return b;
    }
    /**
     *  答题绑定用户列表
     */
    @Override
    public List<DiagnosisStudentAnswerMachineDto>  studentList(DiagnosisStudentAnswerMachineModel model) throws Exception {
        DiagnosisStudentAnswerMachineDto dto = new DiagnosisStudentAnswerMachineDto();
        dto.setStudentCode(model.getStudentCode());
        List<DiagnosisStudentAnswerMachineDto> list = diagnosisStudentAnswerMachineService.selectDiagnosisStudentAnswerMachineList(dto);
        return list;
    }

    //单卷所题  学生对错数
    public  Map<String,Integer> getOverview(List<DiagnosisStudentAnswerSheetDto> list, String studentCode){
        Map<String,Integer> map = new HashMap<String,Integer>();
        List<DiagnosisStudentAnswerSheetDto>  studentList  = list.stream().filter(g -> g.getStudentCode().equals(studentCode)).collect(Collectors.toList());
        int wrongCount = studentList.stream().filter(g -> g.getIsRight() == ConstantEnum.WRONG.getType()).collect(Collectors.toList()).size();
        int rightCount = studentList.stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType()).collect(Collectors.toList()).size();
        map.put("rightCount",rightCount);
        map.put("wrongCount",wrongCount);
        return  map;
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
            if (CollectionUtils.isEmpty(collect)){
                flag=-1;
            }
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

    public List<String> getSize(DiagnosisStudentAnswerSheetModel model) throws Exception {
        DiagnosisInClassRelationDto bean = new DiagnosisInClassRelationDto();
        bean.setInClassTestCode(model.getInClassTestCode());
        List<DiagnosisInClassRelationDto> list = getInClassRelationList(bean);
        List<String> collect = list.stream().map(DiagnosisInClassRelationDto::getSout).collect(Collectors.toList());
        collect.sort((o1, o2) -> Integer.parseInt(o1)-Integer.parseInt(o2));
        return  collect;
    }

    //重组list
    public   List<StudentAnswerSheetModel> getRecombineList(List<DiagnosisStudentAnswerSheetDto> beans) throws Exception {
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
        return modelsList;
    }
    public List<Map<String, Object>> getKnowledgeCout(List<StudentAnswerSheetModel> modelsList, List<DiagnosisStudentAnswerSheetDto> list) {
        List<Map<String,Object>>  lists = new ArrayList<>();
        Map<String, List<StudentAnswerSheetModel>> mapCollect = modelsList.stream().collect(Collectors.groupingBy(StudentAnswerSheetModel::getKnowledgeCode));
        for (Map.Entry<String, List<StudentAnswerSheetModel>> entry : mapCollect.entrySet()) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("knowledgeCode",entry.getKey());
            map.put("knowledgeName",entry.getValue().get(0).getKnowledgeName());
            int total = 0;
            for (DiagnosisStudentAnswerSheetDto bean : list){
                List<KnowledgeDateModel> knowledgeDateModels = JSONObject.parseArray(bean.getKnowledges(), KnowledgeDateModel.class);
                total += knowledgeDateModels.size();
            }

            double v = new BigDecimal(new BigDecimal(entry.getValue().size()).divide(new BigDecimal(total), 5, BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            map.put("knowledgeRatio",v);
            lists.add(map);
        }
        return  lists;
    }
    //每个人答对的知识点数
    public List<Map<String, Object>> getKnowledgeReport(List<StudentAnswerSheetModel> modelsList,String studentCode) {
        List<Map<String,Object>>  lists = new ArrayList<>();

        Map<String, List<StudentAnswerSheetModel>> mapCollect = modelsList.stream().collect(Collectors.groupingBy(StudentAnswerSheetModel::getKnowledgeCode));
        for (Map.Entry<String, List<StudentAnswerSheetModel>> entry : mapCollect.entrySet()) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("knowledgeCode",entry.getKey());
            map.put("knowledgeName",entry.getValue().get(0).getKnowledgeName());
            int size1 = entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType() && g.getStudentCode().equals(studentCode)).collect(Collectors.toList()).size();
            int size2 = entry.getValue().stream().filter(g -> g.getStudentCode().equals(studentCode)).collect(Collectors.toList()).size();
            double v = 0;
            if (size2 !=0 ){
                 v = new BigDecimal(size1).divide(new BigDecimal(size2), 5, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
            }
            map.put("rightRatio",v);
            lists.add(map);
        }
        return  lists;
    }



    public List<StudentRankingModel> getStudentRanking(List<DiagnosisStudentAnswerSheetDto> list){
        List<StudentRankingModel>  lists = new ArrayList<StudentRankingModel>();
        Map<String, List<DiagnosisStudentAnswerSheetDto>>  mapCollect= list.stream().collect(Collectors.groupingBy(DiagnosisStudentAnswerSheetDto::getStudentCode));
        for (Map.Entry<String, List<DiagnosisStudentAnswerSheetDto>> entry : mapCollect.entrySet()) {
            StudentRankingModel bean = new StudentRankingModel();
            bean.setStudentName(entry.getValue().get(0).getStudentName());
            bean.setStudentcode(entry.getValue().get(0).getStudentCode());
            bean.setNum(entry.getValue().stream().filter(g -> g.getIsRight() == ConstantEnum.RIGHT.getType()).collect(Collectors.toList()).size());
            lists.add(bean);
        }
        lists.sort((o1, o2) -> o2.getNum()-o1.getNum());
        return  lists;
    }
     /*课中测code获取 答题结果*/
    public   List<DiagnosisStudentAnswerSheetDto> getDtoList(DiagnosisStudentAnswerSheetModel model) throws Exception {
        DiagnosisStudentAnswerSheetDto dto = new DiagnosisStudentAnswerSheetDto();
        BeanUtils.copyProperties(model,dto);
        dto.setStudentCode(null);
        List<DiagnosisStudentAnswerSheetDto> bean = diagnosisStudentAnswerSheetService.selectDiagnosisStudentAnswerSheetList(dto);
        return bean;
    }


    /** classCode获取班级下的学生 **/
    private List<AuthUserBean> getStudentCount(Integer classCode) {
        List<AuthUserBean> userBeanList = authUserManagerService.getMyStudentByClassId(classCode);
        return  userBeanList;
    }

    private  Map<String, Object> getMap(PageInfo  pageInfo){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }

    public static String  getUUID(){
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**获取甬余的试卷 题**/
    private  List<DiagnosisBaseQuestionDto> getBaseQuestionList(DiagnosisStudentAnswerSheetModel  model) throws Exception {
        DiagnosisInClassRelationDto dto = new DiagnosisInClassRelationDto();
        dto.setInClassTestCode(model.getInClassTestCode());
        dto.setPageNum(model.getPageNum());
        dto.setPageSize(model.getPageSize());
        PageInfo<DiagnosisInClassRelationDto> pageInfo = diagnosisInClassRelationService.selectDiagnosisInClassRelationPage(dto);
        List<DiagnosisBaseQuestionDto> list = PageHelperUtil.converterList(pageInfo.getList(), DiagnosisBaseQuestionDto.class);
        return   list;
    }

    private   List<DiagnosisInClassRelationDto> getInClassRelationList(DiagnosisInClassRelationDto  model) throws Exception {
        List<DiagnosisInClassRelationDto> list1 = diagnosisInClassRelationService.selectDiagnosisInClassRelationList(model);
        return   list1;
    }

    private   List<DiagnosisInClassRelationDto> getUnLockList(String  InClassTestCode,Integer flag) throws Exception {
        DiagnosisInClassRelationDto dto1 = new DiagnosisInClassRelationDto();
        dto1.setInClassTestCode(InClassTestCode);
        dto1.setIsEnd(flag);
        List<DiagnosisInClassRelationDto> lists = getInClassRelationList(dto1);
        return   lists;
    }

}
