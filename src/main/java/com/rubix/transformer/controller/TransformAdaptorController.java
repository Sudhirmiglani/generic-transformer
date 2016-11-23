package com.rubix.transformer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubix.transformer.builder.TransformAdaptorBuilder;
import com.rubix.transformer.component.TransformerComponent;
import com.rubix.transformer.pojo.TransformAdaptor;
import com.rubix.wms.common.controller.AbstractController;
import com.rubix.wms.common.util.ErrorMessageUtil;
import com.rubix.wms.common.validation.ErrorMessages;
import com.rubix.wms.common.validation.ErrorMessagesFactory;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Created by sudhir.m on 11/11/16.
 */

@RestController
@Getter
@RequestMapping("/transform-adaptor")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransformAdaptorController extends AbstractController<TransformAdaptor, Long> {

    private final TransformAdaptorBuilder builder;

    private final TransformerComponent transformerComponent;

    private final ErrorMessagesFactory errorMessagesFactory;

    private final static Log log = LogFactory.getLog(TransformAdaptorController.class);

    @Override
    @RequestMapping(method = {RequestMethod.POST})
    public ResponseEntity<?> save(@RequestBody @NonNull final TransformAdaptor transformAdaptor) {
        final ErrorMessages errorMessage = errorMessagesFactory.createErrorMessage(transformAdaptor);

        if (errorMessage.hasErrors()) {
            final String message = errorMessage.getFormattedError();
            log.error(message);
            return new ResponseEntity<>(ErrorMessageUtil.get(HttpStatus.BAD_REQUEST, message), HttpStatus.BAD_REQUEST);
        }
        return super.save(transformAdaptor);
    }

    @RequestMapping(path = "/transform/{transformId}", method = {RequestMethod.POST})
    public ResponseEntity<?> transform(@PathVariable("transformId") final Long id, @RequestBody @NonNull final String json) {

        try {
            final JSONObject jsonObject = new JSONObject(json);
            final JSONObject transformedJson = transformerComponent.transform(jsonObject, id);
            return new ResponseEntity<>(transformedJson.toString(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(ErrorMessageUtil.get(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
