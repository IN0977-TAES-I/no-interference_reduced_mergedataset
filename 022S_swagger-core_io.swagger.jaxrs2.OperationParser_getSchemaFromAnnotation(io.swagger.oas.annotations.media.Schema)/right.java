// class name : io.swagger.jaxrs2.OperationParser
// method name: getSchemaFromAnnotation(io.swagger.oas.annotations.media.Schema)
// side       : right
// region     : lines 187-244 (affected lines: [187, 188, 234, 235, 236, 237, 238, 239, 240, 241, 244])
class OperationParser {
    void getSchemaFromAnnotation_io_swagger_oas_annotations_media_Schema_() {
        }

        ReaderUtils.getStringListFromStringArray(schema.allowableValues()).ifPresent(schemaObject::setEnum);
        getExternalDocumentation(schema.externalDocs()).ifPresent(schemaObject::setExternalDocs);

        if (isEmpty) {
            return Optional.empty();
        }
        return Optional.of(schemaObject);
    }

    public static Optional<Set<Tag>> getTags(String[] tags) {
        if (tags == null) {
            return Optional.empty();
        }
        Set<Tag> tagsList = new LinkedHashSet<>();
        boolean isEmpty = true;
        for (String tag : tags) {
            Tag tagObject = new Tag();
            if (StringUtils.isNotBlank(tag)) {
                isEmpty = false;
            }
            tagObject.setDescription(tag);
            tagObject.setName(tag);
            tagsList.add(tagObject);
        }
        if (isEmpty) {
            return Optional.empty();
        }
        return Optional.of(tagsList);
    }

    public static Optional<List<Server>> getServers(io.swagger.oas.annotations.servers.Server[] servers) {
        if (servers == null) {
            return Optional.empty();
        }
        List<Server> serverObjects = new ArrayList<>();
        for (io.swagger.oas.annotations.servers.Server server : servers) {
            getServer(server).ifPresent(serverObjects::add);
        }
        if (serverObjects.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(serverObjects);
    }

    public static Optional<Server> getServer(io.swagger.oas.annotations.servers.Server server) {
        if (server == null) {
            return Optional.empty();
        }

        Server serverObject = new Server();
        boolean isEmpty = true;
        if (StringUtils.isNotBlank(server.url())) {
            serverObject.setUrl(server.url());
            isEmpty = false;
        }
        if (StringUtils.isNotBlank(server.description())) {
    }
}
