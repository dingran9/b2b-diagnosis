package com.eedu.auth.beans;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zyb on 2017/5/10.
 */
@Data
public class AuthRoleConditionBean extends BaseModel implements Serializable {

    private Integer id;

    private Integer roleId;

    private String roleName;

    private Integer roleProductId;

    private Integer roleSort;

    private String roleDesc;


}
