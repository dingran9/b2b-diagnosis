package com.eedu.diagnosis.common.utils;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dqy on 2017/3/21.
 */
public class PageHelperUtil {

    public static <T> PageInfo<T> pageInfoConverter(PageInfo source, Class<T> targetClazz) throws IllegalAccessException, InstantiationException {
        List<T> dtoList = converterList(source.getList(), targetClazz);

        PageInfo<T> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(source,pageInfo);
        pageInfo.setList(dtoList);
        return pageInfo;
    }

    public static <T> List<T> converterList(List source,Class<T> targetClazz) throws IllegalAccessException, InstantiationException {
        List<T> result = new ArrayList();
        T t;
        for (Object dto:source) {
            t = targetClazz.newInstance();
            BeanUtils.copyProperties(dto,t);
            result.add(t);
        }
        return result;
    }
}
