class PieChartRenderer {
    PieChart mChart;
    Bitmap mDrawBitmap;
    Canvas mBitmapCanvas;

    @Override
    public void drawData(Canvas c) {

        if (mDrawBitmap == null
                || ((int) mViewPortHandler.getChartHeight() != mDrawBitmap.getHeight())) {
            mDrawBitmap = Bitmap.createBitmap((int) mViewPortHandler.getChartWidth(),
                    (int) mViewPortHandler.getChartHeight(), Bitmap.Config.ARGB_8888);
            mBitmapCanvas = new Canvas(mDrawBitmap);
        }


        mDrawBitmap.eraseColor(Color.TRANSPARENT);

        PieData pieData = mChart.getData();

        for (PieDataSet set : pieData.getDataSets()) {

            if (set.isVisible())
                drawDataSet(c, set);
        }
    }
}
