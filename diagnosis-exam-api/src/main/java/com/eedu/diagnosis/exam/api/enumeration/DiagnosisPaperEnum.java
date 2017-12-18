package com.eedu.diagnosis.exam.api.enumeration;

/**
 * Created by dqy on 2017/3/17.
 */
public class DiagnosisPaperEnum {
    /**
     * 是否需要判卷
     */
    public enum NeedmarkEnum{
        NOTNEED("不需要",0),
        NEED("需要",1);
        private String desc;
        private Integer value;
        NeedmarkEnum(String desc,Integer value){
            this.value = value;
            this.desc = desc;
        }



        @Override
        public String toString() {
            return  desc;
        }

        public String getDesc() {
            return desc;
        }
        public Integer getValue() {
            return value;
        }
    }
}
