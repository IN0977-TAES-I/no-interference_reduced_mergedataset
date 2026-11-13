// class name : org.elasticsearch.index.query.support.NestedInnerQueryParseSupport
// method name: setPathLevel()
// side       : right
// region     : lines 193-200 (affected lines: [193, 195, 197, 199, 200])
class NestedInnerQueryParseSupport {
    void setPathLevel__() {
        }
        childFilter = nestedObjectMapper.nestedTypeFilter();
        parentObjectMapper = parseContext.nestedScope().nextLevel(nestedObjectMapper);
    }

    private void resetPathLevel() {
        parseContext.nestedScope().previousLevel();
    }
    }
}
