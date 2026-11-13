// class name : com.xxmassdeveloper.mpchartexample.ScatterChartActivity
// method name: onProgressChanged(SeekBar, int, boolean)
// side       : base
// region     : lines 168-205 (affected lines: [168, 173, 178, 183, 186, 188, 191, 204, 205])
class ScatterChartActivity {
    void onProgressChanged_SeekBar__int__boolean_() {
    protected LineData getComplexity() {
        
        ArrayList<ILineDataSet> sets = new ArrayList<ILineDataSet>();
        
        LineDataSet ds1 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "n.txt"), "O(n)");
        LineDataSet ds2 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "nlogn.txt"), "O(nlogn)");
        LineDataSet ds3 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "square.txt"), "O(n\u00B2)");
        LineDataSet ds4 = new LineDataSet(FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "three.txt"), "O(n\u00B3)");
        
        ds1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ds3.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        ds4.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);
        
        ds1.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        ds2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[1]);
        ds3.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        ds4.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[3]);
        
        ds1.setLineWidth(2.5f);
        ds1.setCircleRadius(3f);
        ds2.setLineWidth(2.5f);
        ds2.setCircleRadius(3f);
        ds3.setLineWidth(2.5f);
        ds3.setCircleRadius(3f);
        ds4.setLineWidth(2.5f);
        ds4.setCircleRadius(3f);
        
        
        // load DataSets from textfiles in assets folders
        sets.add(ds1);        
        sets.add(ds2);
        sets.add(ds3);
        sets.add(ds4);
        
        LineData d = new LineData(ChartData.generateXVals(0, ds1.getEntryCount()), sets);
        d.setValueTypeface(tf);
        return d;
    }
}
