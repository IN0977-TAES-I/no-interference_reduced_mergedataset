class MultiMatchQueryBuilder {
    Object text;
    List<String> fields;
    ObjectFloatHashMap<String> fieldsBoosts;
    MultiMatchQueryBuilder.Type type;
    MatchQueryBuilder.Operator operator;
    String analyzer;
    Float boost;
    Integer slop;
    Fuzziness fuzziness;
    Integer prefixLength;
    Integer maxExpansions;
    String minimumShouldMatch;
    String fuzzyRewrite;
    Boolean useDisMax;
    Float tieBreaker;
    Boolean lenient;
    Float cutoffFrequency;
    MatchQueryBuilder.ZeroTermsQuery zeroTermsQuery;
    String queryName;

    @Override
    public void doXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject(MultiMatchQueryParser.NAME);

        builder.field("query", text);
        builder.startArray("fields");
        for (String field : fields) {
            final int keySlot;
            if (fieldsBoosts != null && ((keySlot = fieldsBoosts.indexOf(field)) >= 0)) {
                field += "^" + fieldsBoosts.indexGet(keySlot);
            }
            builder.value(field);
        }
        builder.endArray();

        if (type != null) {
            builder.field("type", type.toString().toLowerCase(Locale.ENGLISH));
        }
        if (operator != null) {
            builder.field("operator", operator.toString());
        }
        if (analyzer != null) {
            builder.field("analyzer", analyzer);
        }
        if (boost != null) {
            builder.field("boost", boost);
        }
        if (slop != null) {
            builder.field("slop", slop);
        }
        if (fuzziness != null) {
            fuzziness.toXContent(builder, params);
        }
        if (prefixLength != null) {
            builder.field("prefix_length", prefixLength);
        }
        if (maxExpansions != null) {
            builder.field("max_expansions", maxExpansions);
        }
        if (minimumShouldMatch != null) {
            builder.field("minimum_should_match", minimumShouldMatch);
        }
        if (fuzzyRewrite != null) {
            builder.field("fuzzy_rewrite", fuzzyRewrite);
        }

        if (useDisMax != null) {
            builder.field("use_dis_max", useDisMax);
        }

        if (tieBreaker != null) {
            builder.field("tie_breaker", tieBreaker);
        }

        if (lenient != null) {
            builder.field("lenient", lenient);
        }

        if (cutoffFrequency != null) {
            builder.field("cutoff_frequency", cutoffFrequency);
        }

        if (zeroTermsQuery != null) {
            builder.field("zero_terms_query", zeroTermsQuery.toString());
        }

        if (queryName != null) {
            builder.field("_name", queryName);
        }

        builder.endObject();
    }
}
