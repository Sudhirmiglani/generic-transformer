package com.rubix.transformer.builder;

import com.rubix.transformer.dao.TransformDao;
import com.rubix.transformer.model.MTransformAdaptor;
import com.rubix.transformer.pojo.TransformAdaptor;
import com.rubix.transformer.transformer.TransformAdaptorTransformer;
import com.rubix.wms.common.builder.AbstractBuilder;
import com.rubix.wms.common.search.SpecificationBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by sudhir.m on 22/10/16.
 */
@Data
@Component
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransformAdaptorBuilder extends AbstractBuilder<MTransformAdaptor, TransformAdaptor, Long> {

    private final TransformDao dao;

    private final TransformAdaptorTransformer transformer;

    private final SpecificationBuilder specificationBuilder;

}
