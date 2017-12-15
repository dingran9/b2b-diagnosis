package com.eedu.auth.test;

import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.service.AuthUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/17
 * Time: 11:07
 * Describe:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class AuthUserTest {

	@Autowired
	private AuthUserService authUserService;

	@Test
	public void testMd5(){
		System.out.println();
	}


	@Test
	public void test1(){

		AuthUserBean userBean = new AuthUserBean();

		userBean.setUserId(233);
		userBean.setSchoolName("通州第一高中");
		userBean.setSchoolId(619);
		try {
			authUserService.updateUserInfo(userBean);
		}catch (Exception e){

		}
	}

//	@Test
//	public void Test2(){
//		AuthUserBean condition = new AuthUserBean();
//		condition.setUserId(285);
//		condition.setUserType(1);
//		condition.setSchoolId(754);
//		condition.setGradeId(755);
//		condition.setClassId(758);
//		condition.setUserName("刘鸿飞");
//		try {
//			authUserService.reBindStudentGroup(condition);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}

	@Test
	public void Test3(){
		AuthUserBean condition = new AuthUserBean();
		condition.setUserId(285);
		condition.setUserType(1);
		try {
			authUserService.delStudentGroup(condition);
		}catch (Exception e){
			e.printStackTrace();
		}
	}


//	@Test
//	public void Test4(){
//		AuthUserBean condition = new AuthUserBean();
//		condition.setUserId(285);
//		condition.setUserLoginDate(new Date());
//		try {
//			authUserService.saveStudentLoginTime(condition);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}

//
//	@Test
//	public void Test5(){
//		List<AuthUserBean> conditions = new ArrayList<>();
//		AuthUserBean condition = new AuthUserBean();
//		condition.setUserId(285);
//		AuthUserBean condition1 = new AuthUserBean();
//		condition1.setUserId(293);
//		conditions.add(condition);
//		conditions.add(condition1);
//		try {
//			int count = authUserService.delStudentGroupBatch(conditions);
//			System.out.println(count);
//		}catch (Exception e){
//			e.printStackTrace();
//		}
//	}


}
