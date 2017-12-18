package com.eedu.diagnosis.protal.model.user;
/**
 * Author: dingran
 * Date: 2016/4/21
 * Description:
 */
public class UserInfoResponse {
	private String openId;
	private String phone;
	private String accessKey;
	private String secretKey;
	private String status;
	private String createDate;
	private String productType;
	private String productName;
	private boolean isBandQQ;
	private boolean isBandWX;
	private boolean isBandSina;
	private boolean isBandEmail;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public boolean isBandQQ() {
		return isBandQQ;
	}
	public void setBandQQ(boolean isBandQQ) {
		this.isBandQQ = isBandQQ;
	}
	public boolean isBandWX() {
		return isBandWX;
	}
	public void setBandWX(boolean isBandWX) {
		this.isBandWX = isBandWX;
	}
	public boolean isBandSina() {
		return isBandSina;
	}
	public void setBandSina(boolean isBandSina) {
		this.isBandSina = isBandSina;
	}
	public boolean isBandEmail() {
		return isBandEmail;
	}
	public void setBandEmail(boolean isBandEmail) {
		this.isBandEmail = isBandEmail;
	}

}
