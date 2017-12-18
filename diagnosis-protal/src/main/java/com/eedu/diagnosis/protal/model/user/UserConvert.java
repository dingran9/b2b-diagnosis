package com.eedu.diagnosis.protal.model.user;

import org.springframework.stereotype.Component;



/**
 * Author: zz
 * Description:  实体转换类
 */
@Component
public class UserConvert {


    public UserEnum.EquipmentType converterSourceEquipmentType(String type) {
        if(org.apache.commons.lang3.StringUtils.isBlank(type)){
            return null;
        }
        switch (type) {
            case "Test":
                return UserEnum.EquipmentType.Test;
            case "Android":
                return UserEnum.EquipmentType.Android;
            case "Ios":
                return UserEnum.EquipmentType.Ios;
            case "Tv":
                return UserEnum.EquipmentType.Tv;
            default:
                return UserEnum.EquipmentType.Web;
        }
    }
    public int converterUserSexType(Integer type) {
        if(type==null){
            return UserEnum.Sex.UnKnow.getValue();
        }
        switch (type) {
            case 0:
                return UserEnum.Sex.Man.getValue();
            case 1:
                return UserEnum.Sex.Woman.getValue();
            case 2:
                return UserEnum.Sex.UnKnow.getValue();
            default:
                return UserEnum.Sex.UnKnow.getValue();
        }
    }
}