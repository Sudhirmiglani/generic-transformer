package com.rubix.transformer.controller;

import com.rubix.transformer.builder.PayloadTypeBuilder;
import com.rubix.transformer.builder.TransformAdaptorBuilder;
import com.rubix.transformer.pojo.PayloadType;
import com.rubix.transformer.pojo.TransformAdaptor;
import com.rubix.transformer.processor.PayloadProcessor;
import com.rubix.wms.common.builder.Builder;
import com.rubix.wms.common.controller.AbstractController;
import com.rubix.wms.common.util.ErrorMessageUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sudhir.m on 15/11/16.
 */
@Getter
@RestController
@RequestMapping("/payload-type")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayloadTypeController extends AbstractController<PayloadType, Long> {

    private final PayloadTypeBuilder builder;

    private final PayloadProcessor payloadProcessor;

    private final static Log log = LogFactory.getLog(PayloadTypeController.class);

    @RequestMapping(path = "/load", method = RequestMethod.POST)
    public ResponseEntity<?> loadClasses(@RequestBody @NonNull final List<String> classNames) {
        try {
            payloadProcessor.persistJsonSchema(classNames);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(ErrorMessageUtil.get(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
