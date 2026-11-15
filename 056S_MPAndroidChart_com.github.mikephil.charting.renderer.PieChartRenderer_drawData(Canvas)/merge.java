class PieChartRenderer {
    PieChart mChart;
    Bitmap mDrawBitmap;
    Canvas mBitmapCanvas;

    @Override
    public void drawData(Canvas c) {

        if (mDrawBitmap == null
    || (mDrawBitmap.getWidth() != (int) mViewPortHandler.getChartWidth())
    || (mDrawBitmap.getHeight() != (int) mViewPortHandler.getChartHeight())) {
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
