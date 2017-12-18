package com.eedu.diagnosis.protal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.paper.api.dto.DiagnosisPaperDto;
import com.eedu.diagnosis.paper.api.model.ResourceBaseDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.paper.api.openService.ResourceOpenService;
import com.eedu.diagnosis.protal.model.request.ResourceBaseModel;
import com.eedu.diagnosis.protal.model.response.VideoInfoModel;
import com.eedu.diagnosis.protal.model.response.VideoInfoVo;
import com.eedu.diagnosis.protal.model.response.question.QuestionListModel;
import com.eedu.diagnosis.protal.model.response.question.QuestionModel;
import com.eedu.diagnosis.protal.service.StudentTutoringService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dqy on 2017/3/21.
 */
@Service
public class StudentTutoringServiceImpl implements StudentTutoringService {

	@Autowired
	private ResourceOpenService resourceOpenServiceImpl;
	@Autowired
	private BasePaperOpenService basePaperOpenService;
	 /**
     *  根据知识点获取视频
     */
	@Override
	public VideoInfoVo getVideoByKnowledge(ResourceBaseModel model)throws Exception {
		VideoInfoVo videoInfoVo = new VideoInfoVo();
		List<VideoInfoModel> videoList = new ArrayList<>();
		DiagnosisPaperDto dpDto = new DiagnosisPaperDto();
		dpDto.setCode(model.getDiagnosisPaperCode());
		List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dpDto);

		if (!CollectionUtils.isEmpty(diagnosisPaperList)) {
			String unitCode = diagnosisPaperList.get(0).getUnitCode();
			ResourceBaseDto rbDto = new ResourceBaseDto();
			rbDto.setUnitCode(unitCode);
			rbDto.setKnowledgeCode(model.getKnowledgeCode());
			//根据示例化节点code和知识点code 获取视频
			String responseJson = resourceOpenServiceImpl.getVideo(rbDto);
			if (responseJson!=null && !"".equals(responseJson)) {
				videoList = JSONArray.parseArray(responseJson,VideoInfoModel.class);
			}
		}
		videoInfoVo.setVideoInfo(videoList);
		return videoInfoVo;
	}

	/**
	 * 已过时
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public QuestionListModel getknowledgequestion(ResourceBaseModel model)throws Exception {
		ResourceBaseDto rbDto = new ResourceBaseDto();
		BeanUtils.copyProperties(model,rbDto);
		String responseJson = resourceOpenServiceImpl.getQuestions(rbDto);
		if (responseJson!=null && !"".equals(responseJson)) {

			return  JSONObject.parseObject(responseJson, QuestionListModel.class);
		}
		return null;
	 }

	/**
	 * 已过时
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	public QuestionListModel getQuestionByDiagnosisPaper(ResourceBaseModel model)  throws Exception {
		String responseJson = resourceOpenServiceImpl.getQuestionByDiagnosisPaper(model.getDiagnosisPaperCode());
		if (responseJson!=null && !"".equals(responseJson)) {
			QuestionListModel parseObject = JSONObject.parseObject(responseJson, QuestionListModel.class);
			List<QuestionModel> lists = parseObject.getQuestionInfo();
            List<QuestionModel> list = new ArrayList<QuestionModel>();
            for (QuestionModel questionModel : lists) {
                if (!StringUtils.isEmpty(questionModel.getKnowledges()) && questionModel.getKnowledges().indexOf(model.getKnowledgeName()) != -1 ) {
                     list.add(questionModel);
                }
            }
            Integer pageNum = model.getPageNum() == null || model.getPageNum().equals("") ? 1 : Integer.parseInt(model.getPageNum());
			Integer pageSize = model.getPageSize() == null || model.getPageSize().equals("") ? 5 : Integer.parseInt(model.getPageSize());
			if (list != null && list.size() != 0 ) {
                Integer toIndex = (pageNum * pageSize <= list.size()) ? pageNum * pageSize  : list.size();
                List<QuestionModel> subList = list.subList((pageNum - 1) * pageSize, toIndex);
                parseObject.setQuestionInfo(subList);
                return  parseObject;
		     }
	     }
		return null;
     }

	@Override
	public QuestionListModel getknowledgequestionV2(ResourceBaseModel model) throws Exception{
		QuestionListModel questionListModel = new QuestionListModel();
		DiagnosisPaperDto dpDto = new DiagnosisPaperDto();
		dpDto.setCode(model.getDiagnosisPaperCode());
		List<DiagnosisPaperDto> diagnosisPaperList = basePaperOpenService.getDiagnosisPaperList(dpDto);

		if (!CollectionUtils.isEmpty(diagnosisPaperList)) {
			String unitCode = diagnosisPaperList.get(0).getUnitCode();
			ResourceBaseDto rbDto = new ResourceBaseDto();
			rbDto.setKnowledgeCode(model.getKnowledgeCode());
			rbDto.setUnitCode(unitCode);
			String questions = resourceOpenServiceImpl.getQuestionsV2(rbDto);
			if(questions == null || "".equals(questions) || "\"\"".equals(questions) || "[]".equals(questions)){
				questionListModel.setQuestionInfo(new ArrayList<>());
				return questionListModel ;
			}
			List<QuestionModel> questionListModels = JSONArray.parseArray(questions, QuestionModel.class);
			//只取单选题
			List<QuestionModel> questionModels = questionListModels.stream().filter(questionModel -> questionModel.getType().equals("1")).collect(Collectors.toList());
			if(!CollectionUtils.isEmpty(questionModels) && questionModels.size() > 5){
				List<QuestionModel> result;
				Set<QuestionModel> set = new HashSet<>();
				while(true){
					int b=(int)(Math.random()*questionModels.size());
					set.add(questionModels.get(b));
					if (set.size()==5) {
						result = new ArrayList<>(set);
						break;
					}
				}
				questionListModel.setQuestionInfo(result);
			}else {
				questionListModel.setQuestionInfo(questionModels);
			}
			return  questionListModel;
		}
		return null ;
	}

	@Override
	public QuestionListModel getQuestionsByVideo(ResourceBaseModel model) throws Exception {
		QuestionListModel questionListModel = new QuestionListModel();
		ResourceBaseDto rbDto = new ResourceBaseDto();
		rbDto.setVideoId(model.getVideoId());
		rbDto.setSubjectCode(model.getSubjectCode());
		String questions = resourceOpenServiceImpl.getQuestionsV3(rbDto);
		if(questions == null){
			questionListModel.setQuestionInfo(new ArrayList<>());
			return questionListModel ;
		}
		List<QuestionModel> questionListModels = JSONArray.parseArray(questions, QuestionModel.class);
		//只取单选题
		List<QuestionModel> questionModels = questionListModels.stream().filter(questionModel -> questionModel.getType().equals("1")).collect(Collectors.toList());
		if(!CollectionUtils.isEmpty(questionModels) && questionModels.size() > 5){
			List<QuestionModel> result;
			Set<QuestionModel> set = new HashSet<>();
			while(true){
				int b=(int)(Math.random()*questionModels.size());
				set.add(questionModels.get(b));
				if (set.size()==5) {
					result = new ArrayList<>(set);
					break;
				}
			}
			questionListModel.setQuestionInfo(result);
		}else {
			questionListModel.setQuestionInfo(questionModels);
		}
		return  questionListModel;
	}

}
	
