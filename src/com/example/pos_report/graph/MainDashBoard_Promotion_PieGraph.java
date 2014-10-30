package com.example.pos_report.graph;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_report.MainActivity;
import com.example.pos_report.MainDashBoard;
import com.example.pos_report.R;
import com.example.pos_report.SaleByDate;
import com.example.pos_report.database.GetSumPromotionShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.model.SumPromotionShop;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class MainDashBoard_Promotion_PieGraph extends Activity{
	private PieChart mChart;
	String shopName = MainDashBoard.getShopName();
	String lastSaleDate = MainDashBoard.getLastsaledate();
	FlatTextView ShopNameValue,text_tablePromotion;
	final GlobalPropertyDao gpd = new GlobalPropertyDao(this);
	GlobalProperty format = gpd.getGlobalProperty();
	String currencyformat = format.getCurrencyFormat();
	NumberFormat currencyformatter = new DecimalFormat(currencyformat);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salebydate_promotion_piegraph);
        ShopNameValue = (FlatTextView) findViewById(R.id.shopNameValue);
        text_tablePromotion = (FlatTextView) findViewById(R.id.text_tablePromotion);
        text_tablePromotion.setText("Promotion Chart");
        ShopNameValue.setText(shopName+" ("+ lastSaleDate +")");
        
        mChart = (PieChart) findViewById(R.id.chart1);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		
        mChart.setValueTypeface(tf);
        mChart.setCenterTextTypeface(tf);
        mChart.setHoleRadius(50f);
        mChart.setDescription("");
        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setDrawXValues(false);
        mChart.setTouchEnabled(true);
        mChart.setUsePercentValues(true);
        mChart.animateXY(1500, 1500);

        final GetSumPromotionShopDao gpr = new GetSumPromotionShopDao(this);
		final List<SumPromotionShop> spr = gpr.getSumPromoList();
		ArrayList<String> promotionname = new ArrayList<String>() ;
		ArrayList<String> totaldiscount = new ArrayList<String>() ;
		final SumPromotionShop gr = gpr.getSumPromotion();
		double totaldis = gr.getDiscount();
		
		for (SumPromotionShop sp : spr) totaldiscount.add(Double.toString(sp.getDiscount()));
		for (SumPromotionShop sp : spr) promotionname.add("("+(currencyformatter.format((sp.getDiscount()*100)/totaldis))+"%) "+sp.getPromotionName()+" ("+(currencyformatter.format(sp.getDiscount()))+")");
		
		
		String[] pronameArr = new String[promotionname.size()];
		pronameArr = promotionname.toArray(pronameArr);

            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            for (int i = 0; i < pronameArr.length; i++) {
            	float val = Float.parseFloat(totaldiscount.get(i));
                yVals1.add(new Entry(val, i));
            }


            ArrayList<String> xVals = new ArrayList<String>();

            for (int i = 0; i < promotionname.size(); i++)
                xVals.add(pronameArr[i]);

            PieDataSet set1 = new PieDataSet(yVals1, "");
            set1.setColors(ColorTemplate.PASTEL_COLORS);
            mChart.highlightValues(null);
            PieData data = new PieData(xVals, set1);
            mChart.setData(data);

            mChart.setCenterText("Total " + (int) mChart.getYValueSum());
            Legend l = mChart.getLegend();
            l.setPosition(LegendPosition.RIGHT_OF_CHART);
            l.setForm(LegendForm.CIRCLE);
            l.setTextSize(14f);
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
