// class name : com.xxmassdeveloper.mpchartexample.LineChartActivity2
// method name: onCreate(Bundle)
// side       : left
// region     : lines 117-126 (affected lines: [117, 126])
class LineChartActivity2 {
    void onCreate_Bundle_() {
        ll1.setTextSize(10f);
        ll1.setTypeface(tf);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        ll2.setTypeface(tf);

    }
}
