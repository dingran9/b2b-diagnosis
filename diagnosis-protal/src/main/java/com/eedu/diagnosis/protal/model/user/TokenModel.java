package com.eedu.diagnosis.protal.model.user;

import com.eedu.diagnosis.protal.model.response.AuthUserVo;

/**
 * Author: dingran
 * Date: 2015/10/30
 * Description:
 */
public class TokenModel {
	private String code;
    private String token;
    private String refreshToken;
    private String openId;
    private String expires;
    private String scope;
    private String sessionId;
    private String equipmentType;//来源设备
    private String type;
    
    private AuthUserVo authUserVo;
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public AuthUserVo getAuthUserVo() {
		return authUserVo;
	}

	public void setAuthUserVo(AuthUserVo authUserVo) {
		this.authUserVo = authUserVo;
	}

}
