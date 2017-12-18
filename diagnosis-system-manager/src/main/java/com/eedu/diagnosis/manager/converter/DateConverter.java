package com.eedu.diagnosis.manager.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dqy on 2017/3/21.
 */
public class DateConverter implements Converter<String,Date>{
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String[] DATE_FORMATS = {"yyyy-MM-dd HH:mm:ss" };
    @Override
    public Date convert(String s) {
        if(StringUtils.isEmpty(s)) return null;

        Date date = null;
        try {
            date = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] args) {
        DateConverter dc = new DateConverter();
        Date convert = dc.convert("2017-03-22");
        System.out.println(convert);
    }
}
