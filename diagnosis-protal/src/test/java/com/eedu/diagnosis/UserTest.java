package com.eedu.diagnosis;

import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.protal.model.request.ResourceBaseModel;
import com.eedu.diagnosis.protal.model.user.UserRequestModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.service.UserService;
import com.eeduspace.uuims.api.response.login.LoginResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/spring-*.xml")
public class UserTest {

	@Autowired
	private UserService userServiceImpl;

	@Test
	public void t1() throws Exception{
			ResourceBaseModel model = new ResourceBaseModel();
			model.setGradeCode("33");
			model.setSubjectCode("5");
			List<String> list = new ArrayList<String>();
			list.add("54320002");
			model.setKnowledges(list);
//			List<KnowVideoModel> getknowledgequestion = userServiceImpl.register(userModel);
//			sys("------t1------="+JSONObject.toJSONString(getknowledgequestion));
		
	}
	
	@Test
	public void t2() throws Exception{
			ResourceBaseModel model = new ResourceBaseModel();
			model.setGradeCode("33");
			model.setSubjectCode("5");
			model.setBooktypeCode("002770a34b04454fa6626e28ba67fa4c");
			model.setKnowledgeCode("54320002");
			model.setPageNum("1");
			model.setPageSize("10");
			model.setType("402881c5529ab64101529b4b3ea2002a");
//			QuestionListModel getknowledgequestion = userManagementControllerImpl.getknowledgequestion(model);
//			sys("------t2------="+JSONObject.toJSONString(getknowledgequestion));
		
	}
//	@Test
//	public void t3() throws Exception{
//			DiagnosisFavoriteModele model = new DiagnosisFavoriteModele();
//			model.setGradeCode("33");
//			model.setSubjectCode("5");
//			model.setBaseProduction(null);
//			model.setBookTypeCode(null);
//			model.setBookTypeVersionCode(null);
//			model.setKnowledgeCode(null);
//			model.setPlatform(null);
//			model.setQuestionAnalyze(null);
//			model.setQuestionCode(null);
//			model.setQuestionScore(null);
//			model.setQuestionStem(null);
//			model.setStageCode(null);
//			model.setType(null);
//			model.setUnitCode(null);
//			model.setUserCode(null);
//			model.setVideoUrl(null);
//			String service = userServiceImpl.saveOrUpdateDiagnosisFavoriteService(model);
//			sys("------t3------"+service);
//		
//	}
    
    @Test
	public void test3() throws Exception{
		BaseResponse baseResponse = new BaseResponse("ahah");
		UserRequestModel userModel = new UserRequestModel();
		userModel.setName("zhuchaowei");
		userModel.setPassword("e10adc3949ba59abbe56e057f20f883e");
		userModel.setEquipmentType("Web");
		LoginResponse login = userServiceImpl.login(userModel, false, baseResponse);
		System.out.println("afafafadf"+JSONObject.toJSONString(login));
	}


}
