package com.eeduspace.report.util;

import com.eeduspace.b2b.report.model.StudentAnswerResultDto;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * 校验
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-07-11 13:16
 **/
public class ValidationUtil {
    public static <T> void  validator( T object){
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> validators = validator.validate(object);
        validators.stream().forEach(constraintViolation->{
            throw new ValidationException(constraintViolation.getMessage());
        });
    }

    public static void main(String[] args) {

        try {
            StudentAnswerResultDto studentAnswerResultDto =new StudentAnswerResultDto();
            ValidationUtil.validator(studentAnswerResultDto);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
