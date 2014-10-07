package com.example.pos_report.graph;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.pos_peport.database.model.SumPromotionShop;
import com.example.pos_report.R;
import com.example.pos_report.database.GetSumPromotionShopDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class SalebyDate_Promotion_PieGraph extends Activity{
	private PieChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.salebydate_promotion_piegraph);

        

        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setPinchZoom(true);
        /*Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setValueTypeface(tf);
        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
*/
        mChart.setHoleRadius(40f);

        mChart.setDescription("");

        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);

        mChart.setDrawHoleEnabled(true);

        // draws the corresponding description value into the slice
        mChart.setDrawXValues(false);
        mChart.setTouchEnabled(true);

        // display percentage values
        mChart.setUsePercentValues(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener

        mChart.animateXY(1500, 1500);

        final GetSumPromotionShopDao gpr = new GetSumPromotionShopDao(this);
		final List<SumPromotionShop> spr = gpr.getSumPromoList();
		ArrayList<String> promotionname = new ArrayList<String>() ;
		ArrayList<String> totaldiscount = new ArrayList<String>() ;
		for (SumPromotionShop sp : spr) totaldiscount.add(Double.toString(sp.getDiscount()));
		for (SumPromotionShop sp : spr) promotionname.add(sp.getPromotionName()+" ("+sp.getDiscount()+")");
		//for (int i = 0; i< promotionname.size();i++){
			//totaldiscount.add(Double.toString(spr2.getDiscount()));
		//}
		
		
		String[] pronameArr = new String[promotionname.size()];
		pronameArr = promotionname.toArray(pronameArr);
        //String[] mParties = new String[] {
               // "Party A", "Party B", "Party C", "Party D", "Party E"
       // };


            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            // ArrayList<Entry> yVals2 = new ArrayList<Entry>();

            // IMPORTANT: In a PieChart, no values (Entry) should have the same
            // xIndex (even if from different DataSets), since no values can be
            // drawn above each other.
            for (int i = 0; i < pronameArr.length; i++) {
            	float val = Float.parseFloat(totaldiscount.get(i));
                yVals1.add(new Entry(val, i));
            }

            // for (int i = mSeekBarX.getProgress() / 2; i <
            // mSeekBarX.getProgress(); i++) {
            // yVals2.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
            // }

            ArrayList<String> xVals = new ArrayList<String>();

            for (int i = 0; i < promotionname.size(); i++)
                xVals.add(pronameArr[i]);

            PieDataSet set1 = new PieDataSet(yVals1, "");
            set1.setSliceSpace(3f);
            set1.setColors(ColorTemplate.createColors(getApplicationContext(),
                    ColorTemplate.PASTEL_COLORS));

            PieData data = new PieData(xVals, set1);
            mChart.setData(data);

            // undo all highlights
            mChart.highlightValues(null);

            // set a text for the chart center
            mChart.setCenterText("Total " + (int) mChart.getYValueSum());
            //mChart.invalidate();
            
            Legend l = mChart.getLegend();
            l.setPosition(LegendPosition.RIGHT_OF_CHART);
            l.setForm(LegendForm.CIRCLE);
            l.setTextSize(10f);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(5f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                if (mChart.isDrawYValuesEnabled())
                    mChart.setDrawYValues(false);
                else
                    mChart.setDrawYValues(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionTogglePercent: {
                if (mChart.isUsePercentValuesEnabled())
                    mChart.setUsePercentValues(false);
                else
                    mChart.setUsePercentValues(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {
                if (mChart.isDrawXValuesEnabled())
                    mChart.setDrawXValues(false);
                else
                    mChart.setDrawXValues(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.animateX: {
                mChart.animateX(1800);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1800);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1800, 1800);
                break;
            }
        }
        return true;
    }

    
    

    public void onValuesSelected(Entry[] values, Highlight[] highs) {

        StringBuffer a = new StringBuffer();

        for (int i = 0; i < values.length; i++)
            a.append("val: " + values[i].getVal() + ", x-ind: " + highs[i].getXIndex()
                    + ", dataset-ind: " + highs[i].getDataSetIndex() + "\n");

        Log.i("PieChart", "Selected: " + a.toString());
    }

    
}
