package com.rubix.transformer.builder;

import com.rubix.transformer.dao.PayloadTypeDao;
import com.rubix.transformer.model.MPayloadType;
import com.rubix.transformer.pojo.PayloadType;
import com.rubix.transformer.transformer.PayloadTypeTransformer;
import com.rubix.wms.common.builder.AbstractBuilder;
import com.rubix.wms.common.dao.Dao;
import com.rubix.wms.common.search.SpecificationBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sudhir.m on 15/11/16.
 */

@Data
@Component
@EqualsAndHashCode
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayloadTypeBuilder extends AbstractBuilder<MPayloadType, PayloadType, Long> {

    private final PayloadTypeDao dao;

    private final PayloadTypeTransformer transformer;

    private final SpecificationBuilder specificationBuilder;



}
