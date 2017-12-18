package com.eedu.diagnosis.manager.model.request;

import com.eedu.auth.beans.AuthResourceBean;
import com.eedu.auth.beans.AuthRoleBean;
import lombok.Data;

import java.util.List;

/**
 * Created by liuhongfei on 2017/5/8.
 */
@Data
public class RoleResourceModel {

       private AuthRoleBean authRoleBean;

       private List<AuthResourceBean> authResourceBeans;

}
