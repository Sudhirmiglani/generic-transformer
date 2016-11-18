package com.rubix.transformer.component;

import com.rubix.transformer.dao.TransformDao;
import com.rubix.transformer.model.MTransformAdaptor;
import com.rubix.transformer.pojo.Field;
import com.rubix.transformer.pojo.TransformAdaptor;
import com.rubix.transformer.pojo.Transformation;
import com.rubix.transformer.transformer.TransformAdaptorTransformer;
import com.rubix.wms.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sudhir.m on 11/11/16.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransformerImpl implements Transformer {

    private final TransformDao transformDao;

    private final TransformAdaptorTransformer transformAdaptorTransformer;

    @Override
    public void transform(JSONObject jsonObject, Long adaptorId) throws NotFoundException {
        System.out.println("here");

        final MTransformAdaptor mTransformAdaptor = transformDao.findOne(adaptorId);
        if (mTransformAdaptor == null) {
            throw new NotFoundException(String.format("Adaptor with id %d Not Found", adaptorId));
        }
        final TransformAdaptor transformAdaptor = transformAdaptorTransformer.toPojo(mTransformAdaptor);
        final List<Transformation> transformations = transformAdaptor.getTransformations();

        transformations.forEach((transformation) -> {
            final Field source = transformation.getSource();
            final Field destination = transformation.getDestination();

            System.out.println(source);
            System.out.println(destination);

        });


    }

    private Object createJsonObject(Field field) {

        int depthOfNode = field.getName().split("\\.").length;
        if (depthOfNode == 0) {
            return getJsonObjectByType(field.getType());
        }
        while (depthOfNode > 1) {
            //

        }


        return null;
    }

    private Object getJsonObjectByType(String type) {

        //switch (type)
        return null;

    }
}
