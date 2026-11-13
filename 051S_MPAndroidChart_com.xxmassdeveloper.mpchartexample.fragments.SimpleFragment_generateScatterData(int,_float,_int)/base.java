// class name : com.xxmassdeveloper.mpchartexample.fragments.SimpleFragment
// method name: generateScatterData(int, float, int)
// side       : base
// region     : lines 73-91 (affected lines: [73, 80, 91])
class SimpleFragment {
    void generateScatterData_int__float__int_() {
        
        ScatterShape[] shapes = ScatterChart.getAllPossibleShapes();
        
        for(int i = 0; i < dataSets; i++) {
           
            ArrayList<Entry> entries = new ArrayList<Entry>();
            
            for(int j = 0; j < count; j++) {        
                entries.add(new Entry((float) (Math.random() * range) + range / 4, j));
            }
            
            ScatterDataSet ds = new ScatterDataSet(entries, getLabel(i));
            ds.setScatterShapeSize(12f);
            ds.setScatterShape(shapes[i % shapes.length]);
            ds.setColors(ColorTemplate.COLORFUL_COLORS);
            ds.setScatterShapeSize(9f);
            sets.add(ds);
        }
        
    }
}
