// class name : com.xxmassdeveloper.mpchartexample.ScatterChartActivity
// method name: onCreate(Bundle)
// side       : merge
// region     : lines 57-86 (affected lines: [57, 63, 77, 80, 86])
class ScatterChartActivity {
    void onCreate_Bundle_() {
            }
            
            BarDataSet ds = new BarDataSet(entries, getLabel(i));
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }
        
        BarData d = new BarData(sets);
        d.setValueTypeface(tf);
        return d;
    }
    
    protected ScatterData generateScatterData(int dataSets, float range, int count) {
        
        ArrayList<IScatterDataSet> sets = new ArrayList<IScatterDataSet>();
        
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
    }
}
