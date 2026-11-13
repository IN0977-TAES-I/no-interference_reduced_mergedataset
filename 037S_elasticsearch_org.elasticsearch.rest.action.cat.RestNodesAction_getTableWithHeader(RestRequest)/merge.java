// class name : org.elasticsearch.rest.action.cat.RestNodesAction
// method name: getTableWithHeader(RestRequest)
// side       : merge
// region     : lines 153-159 (affected lines: [153, 154, 156, 157, 158, 159])
class RestNodesAction {
    void getTableWithHeader_RestRequest_() {
        table.addCell("search.query_total", "alias:sqto,searchQueryTotal;default:false;text-align:right;desc:total query phase ops");
        table.addCell("search.scroll_current", "alias:scc,searchScrollCurrent;default:false;text-align:right;desc:open scroll contexts");
        table.addCell("search.scroll_time", "alias:scti,searchScrollTime;default:false;text-align:right;desc:time scroll contexts held open");
        table.addCell("search.scroll_total", "alias:scto,searchScrollTotal;default:false;text-align:right;desc:completed scroll contexts");

        table.addCell("segments.count", "alias:sc,segmentsCount;default:false;text-align:right;desc:number of segments");
        table.addCell("segments.memory", "alias:sm,segmentsMemory;default:false;text-align:right;desc:memory used by segments");
    }
}
