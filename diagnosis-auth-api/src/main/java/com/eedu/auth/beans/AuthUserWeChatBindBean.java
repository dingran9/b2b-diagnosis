package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/4/7
 * Time: 10:50
 * Describe: 用户绑定微信
 */
public class AuthUserWeChatBindBean implements Serializable {

	private Integer userWechatBindId;

	private String openId;

	private Integer userId;
	
	private Integer type;

	private String nickName;

	private String sex;

	private String province;

	private String city;

    private String country;

	/**用户头像 **/
	private String headImgUrl;
	/** 用户特权信息，json 数组**/
	private String privilege;
	/**只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段 **/
	private String unionId;
	
	private Date   createDate;
	
	private Date   updateDate;
	
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Integer getUserWechatBindId() {
		return userWechatBindId;
	}

	public void setUserWechatBindId(Integer userWechatBindId) {
		this.userWechatBindId = userWechatBindId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}
