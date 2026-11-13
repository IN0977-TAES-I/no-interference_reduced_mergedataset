// class name : com.xxmassdeveloper.mpchartexample.ScatterChartActivity
// method name: onProgressChanged(SeekBar, int, boolean)
// side       : left
// region     : lines 168-205 (affected lines: [168, 173, 178, 183, 186, 188, 191, 204, 205])
class ScatterChartActivity {
    void onProgressChanged_SeekBar__int__boolean_() {
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
        
        LineData d = new LineData(sets);
        d.setValueTypeface(tf);
        return d;
    }
    
    private String[] mLabels = new String[] { "Company A", "Company B", "Company C", "Company D", "Company E", "Company F" };
//    private String[] mXVals = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };
    
    private String getLabel(int i) {
        return mLabels[i];
    }
}
    }
}
