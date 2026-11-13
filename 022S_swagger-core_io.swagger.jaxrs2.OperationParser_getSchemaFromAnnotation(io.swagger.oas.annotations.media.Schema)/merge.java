// class name : io.swagger.jaxrs2.OperationParser
// method name: getSchemaFromAnnotation(io.swagger.oas.annotations.media.Schema)
// side       : merge
// region     : lines 187-244 (affected lines: [187, 188, 234, 235, 236, 237, 238, 239, 240, 241, 244])
class OperationParser {
    void getSchemaFromAnnotation_io_swagger_oas_annotations_media_Schema_() {
        if (StringUtils.isNotBlank(schema.defaultValue())) {
            schemaObject.setDefault(schema.defaultValue());
            isEmpty = false;
        }
        if (StringUtils.isNotBlank(schema.example())) {
            schemaObject.setExample(schema.example());
            isEmpty = false;
        }
        if (StringUtils.isNotBlank(schema.format())) {
            schemaObject.setFormat(schema.format());
            isEmpty = false;
        }
        if (StringUtils.isNotBlank(schema.example())) {
            schemaObject.setExample(schema.example());
            isEmpty = false;
        }
        if (StringUtils.isNotBlank(schema.pattern())) {
            schemaObject.setPattern(schema.pattern());
            isEmpty = false;
        }
        if (schema.readOnly()) {
            schemaObject.setReadOnly(schema.readOnly());
            isEmpty = false;
        }
        if (schema.deprecated()) {
            schemaObject.setDeprecated(schema.deprecated());
            isEmpty = false;
        }
        if (schema.exclusiveMaximum()) {
            schemaObject.setExclusiveMaximum(schema.exclusiveMaximum());
            isEmpty = false;
        }
        if (schema.exclusiveMinimum()) {
            schemaObject.setExclusiveMinimum(schema.exclusiveMinimum());
            isEmpty = false;
        }
        if (schema.maxLength() > 0) {
            if (schema.maxProperties() > 0) {
                schemaObject.setMaxProperties(schema.maxProperties());
                isEmpty = false;
            }
        }
        if (schema.minProperties() > 0) {
            schemaObject.setMinProperties(schema.minProperties());
            isEmpty = false;
        }

        if (NumberUtils.isNumber(schema.maximum())) {
            String filteredMaximum = schema.maximum().replaceAll(COMMA, StringUtils.EMPTY);
            schemaObject.setMaximum(new BigDecimal(filteredMaximum));
        }

        if (NumberUtils.isNumber(schema.minimum())) {
            String filteredMinimum = schema.minimum().replaceAll(COMMA, StringUtils.EMPTY);
            schemaObject.setMinimum(new BigDecimal(filteredMinimum));
        }

        ReaderUtils.getStringListFromStringArray(schema.allowableValues()).ifPresent(schemaObject::setEnum);
    }
}
