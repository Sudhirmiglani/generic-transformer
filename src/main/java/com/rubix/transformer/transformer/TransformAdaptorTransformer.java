package com.rubix.transformer.transformer;

import com.rubix.transformer.model.MTransformAdaptor;
import com.rubix.transformer.pojo.TransformAdaptor;
import com.rubix.wms.common.transformer.ModelMapperTransformer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by sudhir.m on 11/11/16.
 */

@Component
@Getter
@Setter
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransformAdaptorTransformer extends ModelMapperTransformer<MTransformAdaptor, TransformAdaptor> {

    private final ModelMapper modelMapper;

    @PostConstruct
    private void onCreate() {
        PropertyMap<MTransformAdaptor, TransformAdaptor> map = new PropertyMap<MTransformAdaptor, TransformAdaptor>() {
            protected void configure() {
                skip(source.getSourcePayloadType(), destination.getSourcePayloadType());
            }
        };
        modelMapper.addMappings(map);
    }

    @Override
    public Class<? extends MTransformAdaptor> getModelClass() {
        return MTransformAdaptor.class;
    }

    @Override
    public Class<? extends TransformAdaptor> getPojoClass() {
        return TransformAdaptor.class;
    }
}
