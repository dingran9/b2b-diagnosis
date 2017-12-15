package com.eedu.auth.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuhongfei  on 2017/5/27.
 */
@Data
public class AuthUserGroupRecordBean extends BaseModel implements Serializable {


    private Integer id;

    private Integer userType;  //用户类型1、学生，2、教师，3、校长

    private Integer userId;    //用户编号

    private Integer groupType; //组织类型1,学校，2,年级，3,班级

    private Integer groupId;   //组织编号

    private Date createDate;   //创建时间

    private Date updateDate;   //修改时间

    private Integer userOperating; //用户操作类型(0.新增，1.修改，2.删除)

}
