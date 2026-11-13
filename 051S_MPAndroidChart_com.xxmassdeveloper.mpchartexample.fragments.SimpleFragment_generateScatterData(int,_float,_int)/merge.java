// class name : com.xxmassdeveloper.mpchartexample.fragments.SimpleFragment
// method name: generateScatterData(int, float, int)
// side       : merge
// region     : lines 73-91 (affected lines: [73, 80, 91])
class SimpleFragment {
    void generateScatterData_int__float__int_() {
        ScatterChart.ScatterShape[] shapes = ScatterChart.ScatterShape.getAllDefaultShapes();
        
        for(int i = 0; i < dataSets; i++) {
           
            ArrayList<Entry> entries = new ArrayList<Entry>();
            
            for(int j = 0; j < count; j++) {        
                entries.add(new Entry(j, (float) (Math.random() * range) + range / 4));
            }
            
            ScatterDataSet ds = new ScatterDataSet(entries, getLabel(i));
            ds.setScatterShapeSize(12f);
            ds.setScatterShape(shapes[i % shapes.length]);
            ds.setColors(ColorTemplate.COLORFUL_COLORS);
            ds.setScatterShapeSize(9f);
            sets.add(ds);
        }
        
        ScatterData d = new ScatterData(sets);
    }
}
