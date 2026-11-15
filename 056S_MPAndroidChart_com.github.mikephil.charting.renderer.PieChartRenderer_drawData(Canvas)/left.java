class PieChartRenderer {
    PieChart mChart;
    Bitmap mDrawBitmap;
    Canvas mBitmapCanvas;

    @Override
    public void drawData(Canvas c) {

        if (mDrawBitmap == null
    || mDrawBitmap.getWidth() != mViewPortHandler.getChartWidth()
    || mDrawBitmap.getHeight() != mViewPortHandler.getChartHeight()) {
            mDrawBitmap = Bitmap.createBitmap((int) mViewPortHandler.getChartWidth(),
                    (int) mViewPortHandler.getChartHeight(), Bitmap.Config.ARGB_4444);
            mBitmapCanvas = new Canvas(mDrawBitmap);
        }

        mDrawBitmap.eraseColor(Color.TRANSPARENT);

        PieData pieData = mChart.getData();

        for (PieDataSet set : pieData.getDataSets()) {

            if (set.isVisible())
                drawDataSet(c, set);
        }

        c.drawBitmap(mDrawBitmap, 0, 0, mRenderPaint);
    }
}
