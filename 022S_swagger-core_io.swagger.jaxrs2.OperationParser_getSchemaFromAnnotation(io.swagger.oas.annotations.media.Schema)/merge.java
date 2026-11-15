class OperationParser {
    static String COMMA;

    public static Optional<Schema> getSchemaFromAnnotation(io.swagger.oas.annotations.media.Schema schema) {
        if (schema == null) {
            return Optional.empty();
        }
        Schema schemaObject = new Schema();
        boolean isEmpty = true;
        if (StringUtils.isNotBlank(schema.description())) {
            schemaObject.setDescription(schema.description());
            isEmpty = false;
        }
        if (StringUtils.isNotBlank(schema.ref())) {
            schemaObject.set$ref(schema.ref());
            isEmpty = false;
        }
        if (StringUtils.isNotBlank(schema.type())) {
            schemaObject.setType(schema.type());
            isEmpty = false;
        }
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
        getExternalDocumentation(schema.externalDocs()).ifPresent(schemaObject::setExternalDocs);

        if (isEmpty) {
            return Optional.empty();
        }
        return Optional.of(schemaObject);
    }
}
