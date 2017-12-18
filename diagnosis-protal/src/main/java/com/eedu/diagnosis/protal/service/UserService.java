package com.eedu.diagnosis.protal.service;

import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.diagnosis.common.enumration.EventSourceEnum;
import com.eedu.diagnosis.protal.model.request.DiagnosisFavoriteModele;
import com.eedu.diagnosis.protal.model.request.DiagnosisFeedbackModel;
import com.eedu.diagnosis.protal.model.response.DiagnosisFavoriteVo;
import com.eedu.diagnosis.protal.model.user.UserRequestModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eeduspace.uuims.api.response.login.LoginResponse;
import com.github.pagehelper.PageInfo;

import java.io.IOException;
import java.util.List;







public interface UserService  {


    /**
     * 用户注册
     * @param userModel
     * @return
     * @throws IOException
     * @throws Exception 
     */
	public AuthUserBean register(UserRequestModel userModel) throws IOException, Exception;

    /**
     * 刷新令牌
     * @param userModel
     * @return
     * @throws IOException
     */
	public BaseResponse refreshToken(UserRequestModel userModel) throws IOException;

    /**
     * 用户登录
     * @param userModel
     * @param isScan
     * @return
     * @throws IOException
     * @throws Exception 
     */
	public LoginResponse login(UserRequestModel userModel, boolean isScan, BaseResponse baseResponse) throws IOException, Exception;
	
	public String saveOrUpdateDiagnosisFavoriteService(DiagnosisFavoriteModele model) throws Exception;
	
	public PageInfo<DiagnosisFavoriteVo> getDiagnosisFavoritePaper(DiagnosisFavoriteModele model) throws Exception;
	
	public String sendSms(String phone, String smsType) throws Exception;

	public BaseResponse chackOldPwd(UserRequestModel userRequestModel, BaseResponse baseResponse)throws Exception;

	public BaseResponse editPhone(UserRequestModel userRequestModel, BaseResponse baseResponse) throws Exception;

	public void saveDiagnosisFeedback(DiagnosisFeedbackModel model)throws Exception;

	public List<Integer> initStudentSubject(String schoolCode, String gradeCode);

	/**
	 * 阳光金点专属-登录
	 * @param userModel
	 * @param baseResponse
	 * @return
	 * @throws Exception
	 * @author dingran
	 */
	public BaseResponse ygjdLogin(UserRequestModel userModel,BaseResponse baseResponse) throws  Exception;

	public List<AuthGroupBean> getGroupByParent(AuthGroupBean groupBean) throws Exception;

	public List<AuthGroupBean> getGroupByCondition(AuthGroupBean grade)throws Exception ;

	public List<AuthUserBean> getUserBean(AuthUserBean userBean) throws Exception;

	public boolean updateStuInfo(AuthUserBean condition)throws Exception ;

	public void sendToSms(UserRequestModel model, EventSourceEnum editPhoneSms);

	/**
	 * 异步保存学生的登录时间
	 * @param bean
	 * @throws Exception
	 */
	public void saveStudentLoginTime(AuthUserBean bean) throws Exception;
	/**
	 * 获取学生个人信息
	 *
	 * @param bean
	 * @return
	 */
	public AuthUserBean getStudentInfo(AuthUserBean bean) throws Exception;

}
