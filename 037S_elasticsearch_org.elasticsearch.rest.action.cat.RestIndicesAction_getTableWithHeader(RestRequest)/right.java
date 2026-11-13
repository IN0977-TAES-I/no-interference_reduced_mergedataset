// class name : org.elasticsearch.rest.action.cat.RestIndicesAction
// method name: getTableWithHeader(RestRequest)
// side       : right
// region     : lines 138-153 (affected lines: [138, 141, 144, 147, 150, 153])
class RestIndicesAction {
    void getTableWithHeader_RestRequest_() {
        table.addCell("merges.total_docs", "alias:mtd,mergesTotalDocs;default:false;text-align:right;desc:docs merged");
        table.addCell("merges.total_size", "alias:mts,mergesTotalSize;default:false;text-align:right;desc:size merged");
        table.addCell("merges.total_time", "alias:mtt,mergesTotalTime;default:false;text-align:right;desc:time spent in merges");

        table.addCell("percolate.current", "alias:pc,percolateCurrent;default:false;text-align:right;desc:number of current percolations");
        table.addCell("percolate.memory_size", "alias:pm,percolateMemory;default:false;text-align:right;desc:memory used by percolations");
        table.addCell("percolate.queries", "alias:pq,percolateQueries;default:false;text-align:right;desc:number of registered percolation queries");
        table.addCell("percolate.time", "alias:pti,percolateTime;default:false;text-align:right;desc:time spent percolating");
        table.addCell("percolate.total", "alias:pto,percolateTotal;default:false;text-align:right;desc:total percolations");

        table.addCell("refresh.total", "alias:rto,refreshTotal;default:false;text-align:right;desc:total refreshes");
        table.addCell("refresh.time", "alias:rti,refreshTime;default:false;text-align:right;desc:time spent in refreshes");

        table.addCell("search.fetch_current", "alias:sfc,searchFetchCurrent;default:false;text-align:right;desc:current fetch phase ops");
        table.addCell("search.fetch_time", "alias:sfti,searchFetchTime;default:false;text-align:right;desc:time spent in fetch phase");
        table.addCell("search.fetch_total", "alias:sfto,searchFetchTotal;default:false;text-align:right;desc:total fetch ops");
    }
}
