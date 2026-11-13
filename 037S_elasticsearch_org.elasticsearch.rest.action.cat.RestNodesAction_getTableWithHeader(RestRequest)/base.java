// class name : org.elasticsearch.rest.action.cat.RestNodesAction
// method name: getTableWithHeader(RestRequest)
// side       : base
// region     : lines 153-159 (affected lines: [153, 154, 156, 157, 158, 159])
class RestNodesAction {
    void getTableWithHeader_RestRequest_() {
        table.addCell("search.fetch_total", "alias:sfto,searchFetchTotal;default:false;text-align:right;desc:total fetch ops");
        table.addCell("search.open_contexts", "alias:so,searchOpenContexts;default:false;text-align:right;desc:open search contexts");
        table.addCell("search.query_current", "alias:sqc,searchQueryCurrent;default:false;text-align:right;desc:current query phase ops");
        table.addCell("search.query_time", "alias:sqti,searchQueryTime;default:false;text-align:right;desc:time spent in query phase");
        table.addCell("search.query_total", "alias:sqto,searchQueryTotal;default:false;text-align:right;desc:total query phase ops");
        table.addCell("search.scroll_current", "alias:scc,searchScrollCurrent;default:false;text-align:right;desc:open scroll contexts");
        table.addCell("search.scroll_time", "alias:scti,searchScrollTime;default:false;text-align:right;desc:time scroll contexts held open");
    }
}
