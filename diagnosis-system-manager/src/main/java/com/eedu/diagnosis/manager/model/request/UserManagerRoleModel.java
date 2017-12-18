package com.eedu.diagnosis.manager.model.request;

import com.eedu.auth.beans.AuthRoleBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import lombok.Data;

import java.util.List;

/**
 * Created by liuhongfei on 2017/5/8.
 */
@Data
public class UserManagerRoleModel {

    private AuthUserManagerBean authUserBean;

    private List<AuthRoleBean> authRoleBeans;
}
