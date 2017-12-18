package com.eeduspace.report.service.impl.open;

import com.eeduspace.b2b.report.model.*;
import com.eeduspace.b2b.report.model.report.*;
import com.eeduspace.b2b.report.service.GeneralReportOpenService;
import com.eeduspace.report.model.GradeTotalModel;
import com.eeduspace.report.model.ResultsChangModel;
import com.eeduspace.report.service.ReportService;
import com.eeduspace.report.service.ReportstatisticsService;
import com.eeduspace.report.util.NaturalOrderComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 全科报告对外接口实现
 *
 * @author liuhongfei
 * @e-email: liuhongfei@e-eduspace.com
 * @create 2017-04-24
 **/
@Slf4j
@Service("generalReportOpenServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class GeneralReportOpenServiceImpl implements GeneralReportOpenService {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportstatisticsService reportstatisticsService;

    /**
     * 全年级总分成绩分布情况
     *
     * @param gradeCode       学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public AllGradeScoreDto getGradeTotalScoreReport(String gradeCode, String releaseExamCode) throws Exception {

        log.info("getGradeTotalScoreReport    parameter     gradeCode :" +gradeCode+" ,  releaseExamCode : "+releaseExamCode);
        AllGradeScoreDto dot = new AllGradeScoreDto();
        //查询数据
        List<GradeTotalModel> list = reportService.getTotalScoreByGrade(releaseExamCode);

        if (list.size() == 0) return dot;
        //处理数据  //返回结果
        dot = dataprocessing(list);

        return dot;
    }

    /**
     * 成绩分布统计处理逻辑
     * @param list  学生成绩列表
     * @return
     */
    public AllGradeScoreDto dataprocessing(List<GradeTotalModel> list) {


        AllGradeScoreDto dot = new AllGradeScoreDto();

        double total = list.get(0).getAllscore();

        DoubleSummaryStatistics dss = list.stream().collect(Collectors.summarizingDouble(GradeTotalModel::getTotalscore));
        //学生总人数
        long totalcount = dss.getCount();
        //最高分
        double max = dss.getMax();
        //最低分
        double min = dss.getMin();
        //平均分
        double ave = new BigDecimal(dss.getAverage()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        //取出每个学生的分数合集
        List<Double> totals = list.stream().map(GradeTotalModel::getTotalscore).collect(Collectors.toList());
        //标准差
        double deviation = getStandardDevition(totals, ave);
        //人数 >=90%  <=100%
        int firstcount = list.stream().filter(g -> g.getTotalscore() >= total * 0.9 && g.getTotalscore() <= total).collect(Collectors.toList()).size();
        //人数  >=80% <90%
        int secondcount = list.stream().filter(g -> g.getTotalscore() >= total * 0.8 && g.getTotalscore() < total * 0.9).collect(Collectors.toList()).size();
        //人数  >=70%  <80%
        int thirdcount = list.stream().filter(g -> g.getTotalscore() >= total * 0.7 && g.getTotalscore() < total * 0.8).collect(Collectors.toList()).size();
        //人数  >=60 <70%
        int fourthcount = list.stream().filter(g -> g.getTotalscore() >= total * 0.6 && g.getTotalscore() < total * 0.7).collect(Collectors.toList()).size();
        //人数  <60%
        int fifthcount = list.stream().filter(g -> g.getTotalscore() < total * 0.6).collect(Collectors.toList()).size();
        //占比 >=90%  <=100%
        double firstrange = new BigDecimal(new BigDecimal(firstcount).divide(new BigDecimal(totalcount),4,BigDecimal.ROUND_HALF_UP).doubleValue()  * 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
        //占比  >=80% <90%
        double secondrange = new BigDecimal(new BigDecimal(secondcount).divide(new BigDecimal(totalcount),4,BigDecimal.ROUND_HALF_UP).doubleValue()   * 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
        //占比  >=70%  <80%
        double thirdrange = new BigDecimal(new BigDecimal(thirdcount).divide(new BigDecimal(totalcount),4,BigDecimal.ROUND_HALF_UP).doubleValue()  * 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
        //占比   >=60 <70%
        double fourthrange = new BigDecimal(new BigDecimal(fourthcount).divide(new BigDecimal(totalcount),4,BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(2, RoundingMode.HALF_UP).doubleValue();
        //占比   <60%
        double fifthrange = new BigDecimal(new BigDecimal(fifthcount).divide(new BigDecimal(totalcount),4,BigDecimal.ROUND_HALF_UP).doubleValue() * 100).setScale(2, RoundingMode.HALF_UP).doubleValue();

        dot.setTotalcount(totalcount);
        dot.setAve(ave);
        dot.setMax(max);
        dot.setMin(min);
        dot.setFirstcount(firstcount);
        dot.setFirstrange(firstrange);
        dot.setSecondcount(secondcount);
        dot.setSecondrange(secondrange);
        dot.setThirdcount(thirdcount);
        dot.setThirdrange(thirdrange);
        dot.setFourthcount(fourthcount);
        dot.setFourthrange(fourthrange);
        dot.setFifthcount(fifthcount);
        dot.setFifthrange(fifthrange);
        dot.setDeviation(deviation);
        dot.setTatalscore(total);
        return dot;
    }

    /**
     * 各班学生成绩分布情况概览
     *
     * @param gradeCode       学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public List<AllGradeScoreDto> getGradeTotalScoreByClassReport(String gradeCode, String releaseExamCode) throws Exception {

        log.info("getGradeTotalScoreByClassReport    parameter     gradeCode :" +gradeCode+" ,  releaseExamCode : "+releaseExamCode);
        //返回数据
        List<AllGradeScoreDto> dtos = new ArrayList<AllGradeScoreDto>();

        List<GradeTotalModel> result = reportService.getTotalScoreByGrade(releaseExamCode);
        if (result.size() == 0) return dtos;

        Map<String, List<GradeTotalModel>> gradeclass = result.stream().collect(Collectors.groupingBy(GradeTotalModel::getClass_name));

        List<String> classlist = new ArrayList<String>(gradeclass.keySet());

        classlist.stream().sorted((o1, o2) -> NaturalOrderComparator.compare(o1,o2));

        String[] class_names = classlist.toArray(new String[]{});

        for (String class_name : class_names) {

            List<GradeTotalModel> list = gradeclass.get(class_name);

            if (null != list && list.size() > 0) {

                AllGradeScoreDto dto = dataprocessing(list);

                dto.setClassname(class_name);

                dtos.add(dto);
            }

        }

        return dtos;
    }


    /**
     * 全学科班级平均成绩，排名统计
     *
     * @param gradeCode       学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public AvgScoreDto getSubjectAvgScoreByClass(String gradeCode, String releaseExamCode) throws Exception {

        log.info("getSubjectAvgScoreByClass    parameter     gradeCode :" +gradeCode+" ,  releaseExamCode : "+releaseExamCode);

        AvgScoreDto avgScoreDto = new AvgScoreDto();

        List<GradeTotalModel> list = reportService.getAvgScoreByClass(releaseExamCode);

        if (list.size() == 0) return avgScoreDto;

        Map<String, List<GradeTotalModel>> collect = list.stream().collect(Collectors.groupingBy(GradeTotalModel::getSubject_code));

        List<String> subjectlist = new ArrayList<String>(collect.keySet());

        subjectlist.stream().sorted((o1, o2) -> NaturalOrderComparator.compare(o1,o2));
        //本次发布的考试涉及的科目
        String[] subjects = subjectlist.toArray(new String[]{});

        //用于班级总分排名
        Map<String, Double> classTotal = new HashMap();

        List<ClassAvgScoreModel> classAvgScores = new ArrayList<ClassAvgScoreModel>();

        //按班级分组
        Map<String, List<GradeTotalModel>> class_collect = list.stream().collect(Collectors.groupingBy(GradeTotalModel::getClass_name));

        List<String> classlist = new ArrayList<String>(class_collect.keySet());
        //班级名称排序
        classlist.stream().sorted((o1, o2) -> NaturalOrderComparator.compare(o1,o2));

        //本次发布的考试涉及的科目
        String[] classnames = classlist.toArray(new String[]{});
        //先将单班级下所有学科数据处理
        for(String class_name : classnames) {
            //班级下所有学科的平均分
            List<GradeTotalModel> classgrades = class_collect.get(class_name);

            ClassAvgScoreModel classAvgScore = new ClassAvgScoreModel();

            List<AvgScoreModel> avgScores = new ArrayList<AvgScoreModel>();
            //单班级下学科分组
            Map<String, List<GradeTotalModel>> class_subject_collect = classgrades.stream().collect(Collectors.groupingBy(GradeTotalModel::getSubject_code));

            //每个班级九大学科所有分数的和
            double classTotalvag = 0;

            for (String subjectcode : subjects) {

            if(null != class_subject_collect.get(subjectcode) && class_subject_collect.get(subjectcode).size() > 0){

                List<GradeTotalModel> class_subject_avgs = class_subject_collect.get(subjectcode);

                for (GradeTotalModel g : class_subject_avgs) {
                    //取出每个学科下经过考试的班级
//                List<GradeTotalModel> result = list.stream().filter(g -> subjectcode.equals(g.getSubject_code())).collect(Collectors.toList());
                    AvgScoreModel avgsocre = new AvgScoreModel();

                    avgsocre.setSubjectcode(subjectcode);

                    if (g.getSubject_code().equals(subjectcode)) {

                        avgsocre.setAvgscore(String.valueOf(g.getAvgscore()));

                        classTotalvag = classTotalvag + g.getAvgscore();
                    }
                    avgScores.add(avgsocre);
                }

            }else{
                AvgScoreModel avgsocre = new AvgScoreModel();
                avgsocre.setSubjectcode(subjectcode);
                avgsocre.setAvgscore(" * ");
                avgsocre.setRanking(" / ");
                avgScores.add(avgsocre);
            }

            }
            //保留两位小数
            classTotalvag = new BigDecimal(classTotalvag).setScale(2, RoundingMode.HALF_UP).doubleValue();

            classTotal.put(class_name, classTotalvag);

            classAvgScore.setTotalscore(String.valueOf(classTotalvag));

            classAvgScore.setClassname(class_name);

            classAvgScore.setAvgScoreModels(avgScores);

            classAvgScores.add(classAvgScore);
        }

        //获取学科下班级平均分总和的排名
        Map<String, Integer> classTotalpm = getpm(classTotal);
        //将排名写入数据
        for (ClassAvgScoreModel classAvgScoreModel : classAvgScores) {

            if (null != classTotalpm.get(classAvgScoreModel.getClassname()) && classTotalpm.get(classAvgScoreModel.getClassname()) > 0) {

                classAvgScoreModel.setClassranking(classTotalpm.get(classAvgScoreModel.getClassname()) + "");

            }

        }

        Map<String, String> gradeavgscore = new HashMap<String, String>();
        //计算单科下所有班级总分的平均分
        for (String subjectcode : subjects) {
            double subjectavgscore = 0;
            //单科下所有班级的学生数量
            int usercount = 0;
            //单科下所有班级成绩的总和
            double classTotalscore = 0;
            for (GradeTotalModel g : list) {
                if (g.getSubject_code().equals(subjectcode)) {
                    classTotalscore = classTotalscore + g.getSumscore();
                    usercount = usercount + g.getUsercount();
                }
                //单科下所有班级的平均分
                if (classTotalscore > 0)
                    subjectavgscore = new BigDecimal(classTotalscore).divide(new BigDecimal(usercount),2,BigDecimal.ROUND_HALF_UP).doubleValue();
                gradeavgscore.put(subjectcode, subjectavgscore + "");
            }
        }
        //循环学科  得到单科下所有班级的平均分 然后计算排名，单科下年级平均得分
        for (String subjectcode : subjects) {
            Map<String, Double> subjectclass = new HashMap();

            for (ClassAvgScoreModel classAvgScoreModel : classAvgScores) {
                String classname = classAvgScoreModel.getClassname();
                if (null != classAvgScoreModel.getAvgScoreModels() && classAvgScoreModel.getAvgScoreModels().size() > 0) {
                    for (AvgScoreModel avgScoreModel : classAvgScoreModel.getAvgScoreModels()) {
                        if (subjectcode.equals(avgScoreModel.getSubjectcode()) && !" * ".equals(avgScoreModel.getAvgscore())) {
                            subjectclass.put(classname, Double.valueOf(avgScoreModel.getAvgscore()));
                        }
                    }
                }

            }

            Map<String, Integer> result = getpm(subjectclass);
            //为单科下所有的班级添加排名
            for (ClassAvgScoreModel classAvgScoreModel : classAvgScores) {
                String classname = classAvgScoreModel.getClassname();
                Integer pm = result.get(classname);
                if (null != classAvgScoreModel.getAvgScoreModels() && classAvgScoreModel.getAvgScoreModels().size() > 0 && null != pm && pm > 0) {
                    for (AvgScoreModel avgScoreModel : classAvgScoreModel.getAvgScoreModels()) {
                        if (subjectcode.equals(avgScoreModel.getSubjectcode()) && !" * ".equals(avgScoreModel.getAvgscore())) {
                            avgScoreModel.setRanking(pm + "");
                        }
                    }
                }

            }

        }


        avgScoreDto.setClassAvgScoreModels(classAvgScores);
        avgScoreDto.setGradeavgscore(gradeavgscore);
        return avgScoreDto;
    }


    /**
     * 全年级，各学科优秀，及格人数占比
     *
     * @param gradeCode       学年code
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public List<PassingDot> getPassingResults(String gradeCode, String releaseExamCode) throws Exception {

        log.info("getPassingResults    parameter     gradeCode :" +gradeCode+" ,  releaseExamCode : "+releaseExamCode);

        List<PassingDot> passdots = new ArrayList<PassingDot>();
        //参加考试人数
        Integer count = reportService.getTotalPeople(releaseExamCode);

        if (count == 0) return passdots;
        //获取合格，优秀人数
        List<GradeTotalModel> gradeTotalModels = reportService.getPassingByGrade(releaseExamCode);

        BigDecimal all_num = new BigDecimal(count);
        //赋值
        if (null != gradeTotalModels && gradeTotalModels.size() > 0) {
            for (GradeTotalModel gradeTotalModel : gradeTotalModels) {
                PassingDot dot = new PassingDot();
                BigDecimal excellent_rate = new BigDecimal(gradeTotalModel.getExcellent_count());
                BigDecimal passing_rate = new BigDecimal(gradeTotalModel.getPassing_count());
                dot.setExcellent_rate(excellent_rate.divide(all_num,4,BigDecimal.ROUND_HALF_UP).doubleValue() * 100 + "%");
                dot.setPassing_rate(passing_rate.divide(all_num,4,BigDecimal.ROUND_HALF_UP).doubleValue() * 100 + "%");
                dot.setSubject_code(gradeTotalModel.getSubject_code());
                passdots.add(dot);
            }
        }

        return passdots;
    }


    /**
     * 学生总分成绩变化（高二，高三 分 文。理 科）
     * @param gradeCode       学科code
     * @param artType         文理类型 0 无类型  1文  2理
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public List<ResultsVarietyDto> getChangePerformance(String gradeCode,String artType, String releaseExamCode) throws Exception {

        log.info("getChangePerformance    parameter     gradeCode :" +gradeCode+" ,  artType : "+artType+",  releaseExamCode : "+releaseExamCode);

        List<ResultsVarietyDto> resultsVarietyDtos = new ArrayList<ResultsVarietyDto>();

        List<ResultsChangModel> resultsChangModels = reportstatisticsService.getResultsChang(artType, releaseExamCode);

        if (resultsChangModels.size() == 0) return resultsVarietyDtos;

        //按发布code分组
        Map<Integer, List<ResultsChangModel>> releaseResultsMap = resultsChangModels.stream().collect(Collectors.groupingBy(ResultsChangModel::getCode));

        Integer[] codes = releaseResultsMap.keySet().toArray(new Integer[]{});
        //发布顺序排序
        Arrays.sort(codes);
        //按班级分组
        Map<String, List<ResultsChangModel>> classResultsMap = resultsChangModels.stream().collect(Collectors.groupingBy(ResultsChangModel::getClass_name));

//        String[] class_names = classResultsMap.keySet().toArray(new String[]{});

        List<String> classlist = new ArrayList<String >(classResultsMap.keySet());

        classlist.stream().sorted((o1, o2) -> NaturalOrderComparator.compare(o1,o2));

        String[] class_names = classlist.toArray(new String[]{});

//        Arrays.sort(class_names);

        for (int i=0 ;i<codes.length;i++) {

            //单次考试下整个年级所有学科的学生成绩列表
            List<ResultsChangModel> resultsChangList = releaseResultsMap.get(codes[i]);

            if(null != resultsChangList && resultsChangList.size() > 0) {

                ResultsVarietyDto dto = new ResultsVarietyDto();
                //第几次发布
                dto.setRelease_times(i + 1);

                DoubleSummaryStatistics dss = resultsChangList.stream().collect(Collectors.summarizingDouble(ResultsChangModel::getScore));
                //全科下所有班级学生的成绩和
                double sum_score = dss.getSum();
                //全科下所有班级学生的人数
                int grade_num = resultsChangList.stream().collect(Collectors.groupingBy(ResultsChangModel::getUser_code)).keySet().size();

                //全科下所有班级学生的平均分
                double avg_score = new BigDecimal(sum_score).divide(new BigDecimal(grade_num), 4, BigDecimal.ROUND_HALF_UP).doubleValue();
                //将全科下所有班级学生成绩取出
                List<Double> scorelist = resultsChangList.stream().map(e -> e.getScore()).collect(Collectors.toList());
                //获取标准差
                double deviation = getStandardDevition(scorelist, avg_score);

                //按班分组
                Map<String, List<ResultsChangModel>> codeClasslist = resultsChangList.stream().collect(Collectors.groupingBy(ResultsChangModel::getClass_name));

                List<ClassDateAvgModel> classDateAvgModels = new ArrayList<ClassDateAvgModel>();

                for (String class_name : class_names) {

                    ClassDateAvgModel cdm = new ClassDateAvgModel();

                    cdm.setClass_name(class_name);

                    if (null != codeClasslist.get(class_name) && codeClasslist.get(class_name).size() > 0) {

                        List<ResultsChangModel> classresults = codeClasslist.get(class_name);
                        //每个班级下学生的人数
                        int stu_num = classresults.stream().collect(Collectors.groupingBy(ResultsChangModel::getUser_code)).keySet().size();

                        for (ResultsChangModel r : classresults) {

                            if (deviation == 0) r.setStandardpoints(0);
                            else//为每个学生计算标准分数
                                r.setStandardpoints(new BigDecimal(r.getScore() - avg_score).divide(new BigDecimal(deviation), 4, BigDecimal.ROUND_HALF_UP).doubleValue());
                        }
                        DoubleSummaryStatistics classdss = classresults.stream().collect(Collectors.summarizingDouble(ResultsChangModel::getStandardpoints));

//                    cdm.setClass_avg(new BigDecimal(classdss.getAverage()).setScale(4, RoundingMode.HALF_UP).doubleValue()+"");

                        cdm.setClass_avg(new BigDecimal(classdss.getSum()).divide(new BigDecimal(stu_num), 4, BigDecimal.ROUND_HALF_UP).doubleValue() + "");
                    } else {

                        cdm.setClass_avg(" * ");

                    }
                    classDateAvgModels.add(cdm);
                }
                dto.setClassDateAvgModels(classDateAvgModels);

                resultsVarietyDtos.add(dto);
            }
        }


        return resultsVarietyDtos;

    }







    /**
     * 各学科教学成绩贡献率
     *
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    public TeachingratioDto getSubjectContributionrate(String releaseExamCode) throws Exception {

        log.info("getGradeTotalScoreReport    parameter   releaseExamCode : "+releaseExamCode);

        TeachingratioDto teachingratioDto = new TeachingratioDto();

        List<ResultsChangModel> teachingachievements = reportstatisticsService.getTeachingachievement("",releaseExamCode);

        if (teachingachievements.size() == 0) return teachingratioDto;

        teachingratioDto = Teachprocess(teachingachievements);

        return teachingratioDto;
    }


    /**
     * 各学科教师教学成绩
     *
     * @param releaseExamCode 发布考试记录code
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Map<String,String>> getTeachchanges(String releaseExamCode)throws  Exception{

        log.info("getGradeTotalScoreReport    parameter    releaseExamCode : "+releaseExamCode);

        Map<String, Map<String,String>> map = new HashMap<>();

        List<ResultsChangModel> teachingachievements = reportstatisticsService.getTeachingachievement("all",releaseExamCode);

        if(teachingachievements.size() == 0) return map;
        //按发布code分组
        Map<Integer,List<ResultsChangModel>> releaseResultsMap = teachingachievements.stream().collect(Collectors.groupingBy(ResultsChangModel::getCode));

        Integer[] codes = releaseResultsMap.keySet().toArray(new Integer[]{});
        //排序，用于标记第几次测试
        Arrays.sort(codes);
        //按学科分组
        Map<String,List<ResultsChangModel>> subjectResultsMap = teachingachievements.stream().collect(Collectors.groupingBy(ResultsChangModel::getSubject_code));

        String[] subject_codes = subjectResultsMap.keySet().toArray(new String[]{});
        //学科排序
        Arrays.sort(subject_codes);

        for(int i=0; i<codes.length; i++){

            String times = String.valueOf(i + 1);

            List<ResultsChangModel> coderesult = releaseResultsMap.get(codes[i]);

            TeachingratioDto teachingratioDto  =  Teachprocess(coderesult);

            Map<String,String> gradeavgmap = teachingratioDto.getGradeavgscore();

            Map<String ,String> resultmap = new HashMap<String ,String>();

            for(String subject_code : subject_codes){

                if(null != gradeavgmap.get(subject_code) && !"".equals(gradeavgmap.get(subject_code)))
                    resultmap.put(subject_code, gradeavgmap.get(subject_code));
                else
                    resultmap.put(subject_code, "0");

            }
            map.put(times,resultmap);
        }


        return map;

    }


    /**
     * 贡献率处理逻辑
     * @param teachingachievements  学生成绩列表
     * @return
     */
    private TeachingratioDto Teachprocess(List<ResultsChangModel> teachingachievements){

        TeachingratioDto teachingratioDto = new TeachingratioDto();
        //本次发布考试的全科的试卷总分  例如高考总分 750
        double total_score = teachingachievements.get(0).getTotal_score();
        //按学科分组
        Map<String,List<ResultsChangModel>> subjectResultsMap = teachingachievements.stream().collect(Collectors.groupingBy(ResultsChangModel::getSubject_code));

        String[] subject_codes = subjectResultsMap.keySet().toArray(new String[]{});
        //学科排序
        Arrays.sort(subject_codes);
        //按班级分组
        Map<String,List<ResultsChangModel>> classResultsMap = teachingachievements.stream().collect(Collectors.groupingBy(ResultsChangModel::getClass_name));

        List<String> classlist = new ArrayList<String >(classResultsMap.keySet());

        classlist.stream().sorted((o1, o2) -> NaturalOrderComparator.compare(o1,o2));

        String[] class_names = classlist.toArray(new String[]{});

//        String[] class_names = classResultsMap.keySet().toArray(new String[]{});
//
//        Arrays.sort(class_names);

        List<ClassachievementDto> classachievementDtos =new ArrayList<ClassachievementDto>();

        for(String class_name : class_names){

            ClassachievementDto classdot = new ClassachievementDto();

            classdot.setClass_name(class_name);

            List<ResultsChangModel> classresults = classResultsMap.get(class_name);

            DoubleSummaryStatistics classdss = classresults.stream().collect(Collectors.summarizingDouble(ResultsChangModel::getScore));
            //单班级全学科下学生的总成绩
            double classscoresum = classdss.getSum();
            //单班级全学科下学生的人数
            int stu_num = classresults.stream().collect(Collectors.groupingBy(ResultsChangModel::getUser_code)).keySet().size();
            //单班级全学科下学生的总成绩的平均分
            double classavg = new BigDecimal(classscoresum).divide(new BigDecimal(stu_num),4,BigDecimal.ROUND_HALF_UP).doubleValue();

            //单班级单科目分组
            Map<String,List<ResultsChangModel>> classsubjectResultsMap = classresults.stream().collect(Collectors.groupingBy(ResultsChangModel::getSubject_code));

            List<SubjectachievementModel> subjectachievementModels =new ArrayList<SubjectachievementModel>();

            for(String subject_code : subject_codes){

                SubjectachievementModel subjectmodel = new SubjectachievementModel();

                subjectmodel.setSubject_code(subject_code);

                if(null != classsubjectResultsMap.get(subject_code) && classsubjectResultsMap.get(subject_code).size() > 0){
                    //单班级单科下试卷的总分 例如  数学 150
                    double paper_socre = classsubjectResultsMap.get(subject_code).get(0).getPaper_score();

                    DoubleSummaryStatistics classsubjectdss = classsubjectResultsMap.get(subject_code).stream().collect(Collectors.summarizingDouble(ResultsChangModel::getScore));
                    //单班级单科下学生的成绩的平均分
                    double classsubjectavg = classsubjectdss.getAverage();
                    //班级单学科平均分与班级平均分和相除
                    double classavgscore = new BigDecimal(classsubjectavg).divide(new BigDecimal(classavg),4,BigDecimal.ROUND_HALF_UP).doubleValue();
                    //学生学科总分与试卷总分相除
                    double paperavgscore =  new BigDecimal(paper_socre).divide(new BigDecimal(total_score),4,BigDecimal.ROUND_HALF_UP).doubleValue();

                    subjectmodel.setRatio(new BigDecimal(classavgscore).divide(new BigDecimal(paperavgscore),2,BigDecimal.ROUND_HALF_UP).doubleValue()+"");
                }else{
                    subjectmodel.setRatio(" / ");
                }

                subjectachievementModels.add(subjectmodel);
            }

            classdot.setSubjectachievementModels(subjectachievementModels);

            classachievementDtos.add(classdot);

        }

        teachingratioDto.setClassachievementDtos(classachievementDtos);

        Map<String,String> gradeavgscore = new HashMap<String,String>();
        //求比率的合集平均值
        for(String subject_code : subject_codes){

            int classcount = 0;

            double sumavg = 0;

            for(ClassachievementDto classDto : classachievementDtos){

                if(null != classDto.getSubjectachievementModels() && classDto.getSubjectachievementModels().size() > 0){

                    for(SubjectachievementModel subjectmodel : classDto.getSubjectachievementModels()){

                        if(subject_code.equals(subjectmodel.getSubject_code()) && !subjectmodel.getRatio().equals(" / ")) {

                            sumavg = sumavg + Double.valueOf(subjectmodel.getRatio());

                            classcount = classcount + 1;
                        }
                    }
                }
            }


            if(classcount > 0)gradeavgscore.put(subject_code,new BigDecimal(sumavg).divide(new BigDecimal(classcount),2,BigDecimal.ROUND_HALF_UP).doubleValue()+"");
            else gradeavgscore.put(subject_code," 0 ");

        }

        teachingratioDto.setGradeavgscore(gradeavgscore);

        return teachingratioDto;

    }





    //获取两个数组 不同的数据
    public static <T> List<T> compare(List<T> t1, List<T> t2) {
        List<T> list = new ArrayList<T>();
        for (T t : t2) {
            if (!t1.contains(t)) {
                list.add(t);
            }
        }
        return list;
    }


    //排名
    public static Map<String, Integer> getpm(Map<String, Double> map) {

        Map<String, Integer> result = new HashMap<String, Integer>();

        TreeSet<Double> set = new TreeSet<Double>(new Comparator<Double>() {

            @Override
            public int compare(Double o1, Double o2) {

                if (o1 == null || o2 == null) {
                    return 0;
                }
                if (o1 > o2) {
                    return -1;
                } else if (o1 < o2) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        for (Double d : map.values()) {
            set.add(d);
        }

        for (String key : map.keySet()) {
            double value = map.get(key);
            int i = 0;
            for (double d : set) {
                i++;
                if (value == d) {
                    result.put(key, i);
                    break;
                }

            }

        }


        return result;
    }


    //获取标准差
    private double getStandardDevition(List<Double> scores, double ave) {
        int num = scores.size();
        double sum = 0;
        for (double score : scores) {
            sum += ((score - ave) * (score - ave));
        }
        return new BigDecimal(Math.sqrt(sum / num)).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }



}
