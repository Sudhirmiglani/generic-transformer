package com.rubix.transformer.validation;

import com.rubix.transformer.pojo.TransformAdaptor;
import com.rubix.wms.common.validation.Validator;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;

/**
 * Created by sudhir.m on 16/11/16.
 */
public class TransformAdaptorValidator implements Validator {

    private void validateEmptyString(Errors errors, String str, String errorCode) {
        if (StringUtils.isBlank(str)) {
            errors.reject(errorCode);
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TransformAdaptor.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        TransformAdaptor transformAdaptor = (TransformAdaptor) target;


    }
}