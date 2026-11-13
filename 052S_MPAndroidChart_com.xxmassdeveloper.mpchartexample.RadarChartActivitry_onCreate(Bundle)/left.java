// class name : com.xxmassdeveloper.mpchartexample.RadarChartActivitry
// method name: onCreate(Bundle)
// side       : left
// region     : lines 59-72 (affected lines: [59, 60, 61, 62, 72])
class RadarChartActivitry {
    void onCreate_Bundle_() {

        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);

        mSeekBarX.setProgress(45);
        mSeekBarY.setProgress(100);

        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);
    }
}
