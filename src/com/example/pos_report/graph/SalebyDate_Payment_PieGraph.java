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
import android.view.WindowManager;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_report.R;
import com.example.pos_report.SaleByDate;
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.model.SumPaymentShop;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class SalebyDate_Payment_PieGraph extends Activity{
	private PieChart mChart;
	String shopName = SaleByDate.getShopName();
	int month = SaleByDate.getMonth();
	int year = SaleByDate.getYear();
	FlatTextView ShopNameValue,text_tablePayment;
	final ArrayList<String> months = new ArrayList<String>();
	final GlobalPropertyDao gpd = new GlobalPropertyDao(this);
	GlobalProperty format = gpd.getGlobalProperty();
	String currencyformat = format.getCurrencyFormat();
	NumberFormat currencyformatter = new DecimalFormat(currencyformat);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.salebydate_payment_piegraph);
        
        months.add(0,"January");
        months.add(1,"February");
        months.add(2,"March");
        months.add(3,"April");
        months.add(4,"May");
        months.add(5,"June");
        months.add(6,"July");
        months.add(7,"August");
        months.add(8,"September");
        months.add(9,"October");
        months.add(10,"November");
        months.add(11,"December");
        text_tablePayment = (FlatTextView) findViewById(R.id.text_tablePayment);
        ShopNameValue = (FlatTextView) findViewById(R.id.shopNameValue);
        
        text_tablePayment.setText("Sale By Date Payment Chart");
        ShopNameValue.setText(shopName+" ("+ year +" - "+ months.get(month-1) +")");
        mChart = (PieChart) findViewById(R.id.chart1);

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
		
        mChart.setValueTypeface(tf);
        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        
        mChart.setHoleRadius(50f);
        mChart.setDescription("");
        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        // draws the corresponding description value into the slice
        mChart.setDrawXValues(false);
        mChart.setTouchEnabled(true);

        // display percentage values
        mChart.setUsePercentValues(true);
        // mChart.setUnit(" โ�ฌ");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener

        mChart.animateXY(1500, 1500);

		
        final GetSumPaymentShopDao gp = new GetSumPaymentShopDao(this);
		final List<SumPaymentShop> spl = gp.getPaymentlist();
		final SumPaymentShop gsp = gp.getSumPayment();
		
		ArrayList<String> paytype = new ArrayList<String>() ;
		ArrayList<String> totalpay = new ArrayList<String>() ;
		double sumtotalpay = gsp.getTotalPay();
		//double percent = (sp.getTotalPay()* 100) / totalpays;
		
		for (SumPaymentShop sp : spl) totalpay.add(Double.toString(sp.getTotalPay()));
		for (SumPaymentShop sp : spl) paytype.add("("+(currencyformatter.format(sp.getTotalPay()*100 / sumtotalpay))+"%) "+sp.getPayTypeName()+" ("+(currencyformatter.format(sp.getTotalPay()))+")");
		String[] paytypeArr = new String[paytype.size()]; 
		paytypeArr = paytype.toArray(paytypeArr);
		
        //String[] mParties = new String[] {
               // "Party A", "Party B", "Party C", "Party D", "Party E"
       // };


            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            // ArrayList<Entry> yVals2 = new ArrayList<Entry>();

            // IMPORTANT: In a PieChart, no values (Entry) should have the same
            // xIndex (even if from different DataSets), since no values can be
            // drawn above each other.
            for (int i = 0; i < paytype.size(); i++) {
            	float val = Float.parseFloat(totalpay.get(i));
                yVals1.add(new Entry(val, i));
            }

            // for (int i = mSeekBarX.getProgress() / 2; i <
            // mSeekBarX.getProgress(); i++) {
            // yVals2.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
            // }

            ArrayList<String> xVals = new ArrayList<String>();

            for (int i = 0; i < paytype.size(); i++)
                xVals.add(paytypeArr[i]);
            PieDataSet set1 = new PieDataSet(yVals1, "");
            set1.setColors(ColorTemplate.PASTEL_COLORS);

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

    
    

    public void onValuesSelected(Entry[] values, Highlight[] highs) {
        StringBuffer a = new StringBuffer();

        for (int i = 0; i < values.length; i++)
            a.append("val: " + values[i].getVal() + ", x-ind: " + highs[i].getXIndex()
                    + ", dataset-ind: " + highs[i].getDataSetIndex() + "\n");

        Log.i("PieChart", "Selected: " + a.toString());
    }

    
}

