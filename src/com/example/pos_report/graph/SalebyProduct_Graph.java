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

import com.cengalabs.flatui.views.FlatTextView;
import com.example.pos_report.R;
import com.example.pos_report.SaleByProduct;
import com.example.pos_report.database.GetSaleProductShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.model.SaleProductShop;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Highlight;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendForm;
import com.github.mikephil.charting.utils.Legend.LegendPosition;

public class SalebyProduct_Graph extends Activity{
	
	private PieChart mChart;
	private String shopName = SaleByProduct.getShopName();
	private int month = SaleByProduct.getMonth();
	private int year = SaleByProduct.getYear();
	FlatTextView ShopNameValue;
	final ArrayList<String> months = new ArrayList<String>();
	final GlobalPropertyDao gpd = new GlobalPropertyDao(this);
	GlobalProperty format = gpd.getGlobalProperty();
	String currencyformat = format.getCurrencyFormat();
	NumberFormat currencyformatter = new DecimalFormat(currencyformat);
	String currencysymbol = format.getCurrencySymbol();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salebyproduct_piegraph);
        

        ShopNameValue = (FlatTextView)findViewById(R.id.shopNameValue);
        
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
        
        ShopNameValue.setText(shopName+" ("+ year +" - "+ months.get(month-1) +")");
        
        
        mChart = (PieChart) findViewById(R.id.chart1);


		
        final GetSaleProductShopDao gp = new GetSaleProductShopDao(this);
        List<SaleProductShop> sp = gp.getSumProductGraph();
		
		ArrayList<String> productdeptname = new ArrayList<String>() ;
		ArrayList<String> totalprice = new ArrayList<String>() ;
		
		SaleProductShop sum = new SaleProductShop();
		sum = gp.getSumProduct();
		double totalproductprice = sum.getSumSalePrice();
		//double percent = (ss.getSumSalePrice()* 100) / totalprice;
		for (SaleProductShop pmm : sp) productdeptname.add("("+(currencyformatter.format((pmm.getSumSalePrice()* 100)/totalproductprice))+"%) "
		+(pmm.getProductDeptName())+" ("+(currencyformatter.format(pmm.getSumSalePrice()))+" "+currencysymbol+")");
		
		for (SaleProductShop pmm : sp) totalprice.add(Double.toString(pmm.getSumSalePrice()));
		
		String[] deptArr = new String[productdeptname.size()];
		deptArr = productdeptname.toArray(deptArr);
		
            ArrayList<Entry> yVals1 = new ArrayList<Entry>();
            for (int i = 0; i < productdeptname.size(); i++) {
            	float val = Float.parseFloat(totalprice.get(i));
                yVals1.add(new Entry(val, i));
            }

            ArrayList<String> xVals = new ArrayList<String>();

            for (int i = 0; i < productdeptname.size(); i++)
                xVals.add(deptArr[i]);
            PieDataSet set1 = new PieDataSet(yVals1, "");
            set1.setColors(ColorTemplate.PASTEL_COLORS);
            PieData data = new PieData(xVals, set1);
            if(data.getYValCount() == 0){}
            else{
            	//Graph
            	Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
                mChart.setValueTypeface(tf);
                mChart.setCenterTextTypeface(tf);
        		mChart = (PieChart) findViewById(R.id.chart1);
            	mChart.setHoleRadius(50f);
                mChart.setDescription("");
                mChart.setDrawYValues(true);
                mChart.setDrawCenterText(true);
                mChart.setDrawHoleEnabled(true);
                mChart.setDrawXValues(false);
                mChart.setTouchEnabled(false);
                mChart.setUsePercentValues(true);
                mChart.animateXY(1500, 1500);
                mChart.setData(data);
                mChart.highlightValues(null);
                mChart.setCenterText("Total Price " + (int) mChart.getYValueSum());
                Legend l = mChart.getLegend();
                l.setPosition(LegendPosition.RIGHT_OF_CHART);
                l.setForm(LegendForm.CIRCLE);
                l.setTextSize(14f);
                l.setXEntrySpace(7f);
                l.setYEntrySpace(5f);}
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

