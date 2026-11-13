// class name : org.elasticsearch.rest.action.cat.RestShardsAction
// method name: getTableWithHeader(RestRequest)
// side       : left
// region     : lines 112-113 (affected lines: [112, 113])
class RestShardsAction {
    void getTableWithHeader_RestRequest_() {
        table.addCell("query_cache.memory_size", "alias:fcm,queryCacheMemory;default:false;text-align:right;desc:used query cache");
        table.addCell("query_cache.evictions", "alias:fce,queryCacheEvictions;default:false;text-align:right;desc:query cache evictions");
    }
}
