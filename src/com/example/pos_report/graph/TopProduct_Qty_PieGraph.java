package com.example.pos_report.graph;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.pos_peport.database.model.TopProductShop;
import com.example.pos_report.R;
import com.example.pos_report.database.GetTopProductShopDao;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class TopProduct_Qty_PieGraph extends Activity implements OnChartValueSelectedListener
{
	private PieChart mChart;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        final GetTopProductShopDao gt = new GetTopProductShopDao(this);
		final List<TopProductShop> tq = gt.getTopQtyProduct();
		ArrayList<String> productname = new ArrayList<String>() ;
		ArrayList<String> saleprice = new ArrayList<String>() ;
		
		for (TopProductShop tps : tq) saleprice.add(Double.toString(tps.getSumSalePrice()));
		for (TopProductShop tps : tq) productname.add(tps.getProductName()+" ("+tps.getSumSalePrice()+")");
		String[] productnameArr = new String[productname.size()];
		productnameArr = productname.toArray(productnameArr);
            
          
        setContentView(R.layout.topproduct_piegraph);
        	
        mChart = (PieChart) findViewById(R.id.chart1);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setValueTypeface(tf);
        mChart.setCenterTextTypeface(tf);
        mChart.setHoleRadius(40f);
        mChart.setDescription("");
        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setDrawXValues(false);
        mChart.setTouchEnabled(true);
        mChart.setUsePercentValues(true);
        mChart.animateXY(1500, 1500);
        
        
        
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            for (int i = 0; i < productname.size(); i++) {
            	float val = Float.parseFloat(saleprice.get(i));
                yVals1.add(new Entry(val, i));
            }

            ArrayList<String> xVals = new ArrayList<String>();

            for (int i = 0; i < productname.size(); i++)
                xVals.add(productnameArr[i]);
            PieDataSet set1 = new PieDataSet(yVals1, "");
            set1.setColors(ColorTemplate.PASTEL_COLORS);

            PieData data = new PieData(xVals, set1);
            
            mChart.setData(data);
            mChart.highlightValues(null);
            mChart.setCenterText("Total Price " + (int) mChart.getYValueSum());
            
            Legend l = mChart.getLegend();
            l.setPosition(LegendPosition.RIGHT_OF_CHART_CENTER);
            l.setForm(LegendForm.CIRCLE);
            l.setTextSize(14f);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(5f);
            l.setTypeface(tf);
            
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

    
    

    @Override
    public void onValueSelected(Entry e, int dataSetIndex) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }


    
}

