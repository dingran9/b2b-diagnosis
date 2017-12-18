package com.eedu.diagnosis;

import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.protal.model.request.ResourceBaseModel;
import com.eedu.diagnosis.protal.service.StudentTutoringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/spring-*.xml")
public class firstTest {

	@Autowired
	private StudentTutoringService studentTutoringServiceImpl;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	 public void sys(String aa){
	    	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~=");
	    	System.out.println(aa);
	    }
	
	
	@Test
	public void t1() throws Exception{
//			ResourceBaseModel model = new ResourceBaseModel();
//			model.setGradeCode("33");
//			model.setSubjectCode("5");
//			List<String> list = new ArrayList<String>();
//			list.add("54320002");
//			model.setKnowledges(list);
		//	List<KnowVideoModel> getknowledgequestion = studentTutoringServiceImpl.getVideoByKnowledge(model);
		//	sys("------t1------="+JSONObject.toJSONString(getknowledgequestion));
		redisClientTemplate.set("aaaaaa","adfadfasdf");
		System.out.println(redisClientTemplate.get("aaaaaa"));
		
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
//			QuestionListModel getknowledgequestion = studentTutoringServiceImpl.getknowledgequestion(model);
//			sys("------t2------="+JSONObject.toJSONString(getknowledgequestion));
		
	}

	@Test
	public void Test3(){


	}
    
    
}
