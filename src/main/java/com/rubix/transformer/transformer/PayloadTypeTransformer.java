package com.rubix.transformer.transformer;

import com.rubix.transformer.model.MPayloadType;
import com.rubix.transformer.model.MTransformAdaptor;
import com.rubix.transformer.pojo.PayloadType;
import com.rubix.transformer.pojo.TransformAdaptor;
import com.rubix.wms.common.transformer.ModelMapperTransformer;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sudhir.m on 15/11/16.
 */

@Component
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayloadTypeTransformer extends ModelMapperTransformer<MPayloadType, PayloadType> {

    private final ModelMapper modelMapper;

    @Override
    public Class<? extends MPayloadType> getModelClass() {
        return MPayloadType.class;
    }

    @Override
    public Class<? extends PayloadType> getPojoClass() {
        return PayloadType.class;
    }
}
