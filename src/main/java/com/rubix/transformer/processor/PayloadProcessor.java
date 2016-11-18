package com.rubix.transformer.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.types.ArraySchema;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.rubix.transformer.builder.PayloadTypeBuilder;
import com.rubix.transformer.dummy.Dummy;
import com.rubix.transformer.pojo.Field;
import com.rubix.transformer.pojo.PayloadType;
import com.rubix.transformer.runner.ApplicationRunner;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sudhir.m on 15/11/16.
 */

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PayloadProcessor {

    private final static Log log = LogFactory.getLog(PayloadProcessor.class);

    private final PayloadTypeBuilder payloadTypeBuilder;

    public void persistJsonSchema(Class[] classes) {

        for (Class className : classes) {
            loadSchema(className);
        }
    }

    private void loadSchema(Class clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
            mapper.acceptJsonFormatVisitor(clazz, visitor);
            JsonSchema schema = visitor.finalSchema();

            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema));

            System.out.println("---------------------");
            List<Field> fields = new ArrayList<>();
            List<String> ancestors = new ArrayList<>();
            processJsonNodes(schema, fields, ancestors, 0);

            System.out.println("---------------------");
            for (Field field : fields) {
                System.out.println(field);
            }

            PayloadType payloadType = PayloadType.builder()
                    .fields(fields)
                    .name(clazz.getName())
                    .version(1L)
                    .build();

            payloadTypeBuilder.save(payloadType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private boolean isPrimitiveSchema(final JsonSchema schema) {
        return schema.isBooleanSchema() || schema.isIntegerSchema() || schema.isStringSchema() || schema.isNullSchema() || schema.isNumberSchema() || schema.isAnySchema();
    }

    private boolean isArraySchema(final JsonSchema schema) {
        return schema.isArraySchema();
    }

    private boolean isObjectSchema(final JsonSchema schema) {
        return schema.isObjectSchema();
    }

    private boolean isMapSchema(final JsonSchema schema) {
        return schema != null && isObjectSchema(schema) && schema.asObjectSchema().getAdditionalProperties() != null;
    }

    private void processJsonNodes(final JsonSchema schema, final List<Field> fields, final List<String> fieldQualifiers,
                                  final int length) {

        if (schema == null) {
            return;
        }

        if (isObjectSchema(schema)) {
            ObjectSchema objectSchema = schema.asObjectSchema();
            Map<String, JsonSchema> stringJsonSchemaMap = objectSchema.getProperties();

            for (Map.Entry<String, JsonSchema> map : stringJsonSchemaMap.entrySet()) {

                final String key = map.getKey();
                final JsonSchema value = map.getValue();
                if (isPrimitiveSchema(value)) {
                    fields.add(createField(key, value.getType().value(), fieldQualifiers, length));
                } else if (isMapSchema(value)) {
                    final ObjectSchema.SchemaAdditionalProperties additionalProperties =
                            (ObjectSchema.SchemaAdditionalProperties) value.asObjectSchema().getAdditionalProperties();
                    final JsonSchema jsonSchema = additionalProperties.getJsonSchema();

                    if (isPrimitiveSchema(jsonSchema)) {
                        fields.add(createField(null, jsonSchema.getType().value(), fieldQualifiers, length));
                    } else {
                        processJsonNodes(jsonSchema, fields, fieldQualifiers, length);
                    }
                } else {
                    fieldQualifiers.add(length, key);
                    processJsonNodes(value, fields, fieldQualifiers, length + 1);
                }
            }
        } else if (isArraySchema(schema)) {
            final ArraySchema arraySchema = schema.asArraySchema();
            fields.add(length, createField(null, arraySchema.getType().value(), fieldQualifiers, length));

            if (arraySchema.getItems() != null) {
                final JsonSchema jsonSchema = arraySchema.getItems().asSingleItems().getSchema();

                if (!isPrimitiveSchema(jsonSchema)) {
                    processJsonNodes(jsonSchema, fields, fieldQualifiers, length);
                }
            }

        }
    }

    private Field createField(String key, String value, List<String> fieldQualifiers, int length) {

        final List<String> fieldQualifiersLocal = new ArrayList<>(fieldQualifiers.subList(0, length));

        if (StringUtils.isNotBlank(key)) {
            fieldQualifiersLocal.add(key);
        }
        final String fieldFullName = String.join(".", fieldQualifiersLocal);
        return Field.builder()
                .isInternal(true)
                .name(fieldFullName)
                .type(value)
                .build();
    }
}
