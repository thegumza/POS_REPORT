package com.example.pos_report.graph;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.example.pos_peport.database.model.SaleProductShop;
import com.example.pos_report.R;
import com.example.pos_report.database.GetSaleProductShopDao;

import lecho.lib.hellocharts.model.ArcValue;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.util.Utils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.PieChartView;
import lecho.lib.hellocharts.view.PieChartView.PieChartOnValueTouchListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SalebyProduct_Hello_Graph extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	/**
	 * A fragment containing a pie chart.
	 */
	public static class PlaceholderFragment extends Fragment {

		private PieChartView chart;
		private PieChartData data;

		private boolean hasLabels = true;
		private boolean hasLabelsOutside = true;
		private boolean hasCenterCircle = true;
		private boolean hasCenterText1 = false;
		private boolean hasCenterText2 = false;
		private boolean isExploaded = false;
		private boolean hasArcSeparated = false;
		private boolean hasLabelForSelected = false;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			View rootView = inflater.inflate(R.layout.fragment_pie_chart, container, false);

			chart = (PieChartView) rootView.findViewById(R.id.chart);
			chart.setOnValueTouchListener(new ValueTouchListener());

			generateData();

			return rootView;
		}


		private void reset() {
			chart.setCircleFillRatio(1.0f);
			hasLabels = false;
			hasLabelsOutside = false;
			hasCenterCircle = false;
			hasCenterText1 = false;
			hasCenterText2 = false;
			isExploaded = false;
			hasArcSeparated = false;
			hasLabelForSelected = false;
		}

		private void generateData() {
			
			final GetSaleProductShopDao gp = new GetSaleProductShopDao(getActivity());
	        List<SaleProductShop> sp = gp.getSumProductGraph();
			
			ArrayList<String> productdeptname = new ArrayList<String>() ;
			ArrayList<String> totalprice = new ArrayList<String>() ;
			for (SaleProductShop pmm : sp) productdeptname.add((pmm.getProductDeptName())+" ("+pmm.getSumSalePrice()+")");
			
			for (SaleProductShop pmm : sp) totalprice.add(Double.toString(pmm.getSumSalePrice()));
			
			String[] deptArr = new String[productdeptname.size()];
			deptArr = productdeptname.toArray(deptArr);
			//Character[] cs = productdeptname.toArray(new Character[productdeptname.size()]);
			
			
			int numValues = totalprice.size();

			List<ArcValue> values = new ArrayList<ArcValue>();
			for (int i = 0; i < numValues; ++i) {
				final int COLOR_VIOLET = Color.parseColor("#AA66CC");
				final int COLOR_RED = Color.parseColor("#FF4444");
				final int COLOR_PURPLE = Color.parseColor("#33B5E5");
				final int COLOR_BLUE = Color.parseColor("#5677fc");
				final int COLOR_TEAL = Color.parseColor("#009688");
				final int COLOR_GREEN = Color.parseColor("#8bc34a");
				final int COLOR_ORANGE = Color.parseColor("#ff5722");
				final int COLOR_YELLOW = Color.parseColor("#ffea00");
				final int COLOR_BROWN = Color.parseColor("#795548");
				final int COLOR_GRAY = Color.parseColor("#9e9e9e");
				final int[] COLORS = new int[] { COLOR_VIOLET, COLOR_RED, COLOR_PURPLE, COLOR_BLUE,COLOR_TEAL,COLOR_GREEN,COLOR_ORANGE,COLOR_YELLOW,COLOR_BROWN,COLOR_GRAY };
				
				ArcValue arcValue = new ArcValue((float) Float.parseFloat(totalprice.get(i)), COLORS[i]);
				
				values.add(arcValue);
				
			}

			data = new PieChartData(values);
			data.setHasLabels(hasLabels);
			data.setHasLabelsOnlyForSelected(hasLabelForSelected);
			data.setHasLabelsOutside(hasLabelsOutside);
			data.setHasCenterCircle(hasCenterCircle);

			if (hasCenterText1) {
				data.setCenterText1("Hello!");

				// Get roboto-italic font.
				Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
				data.setCenterText1Typeface(tf);

				// Get font size from dimens.xml and convert it to sp(library uses sp values).
				data.setCenterText1FontSize(Utils.px2sp(getResources().getDisplayMetrics().scaledDensity,
						(int) getResources().getDimension(R.dimen.pie_chart_text1_size)));
			}

			if (hasCenterText2) {
				data.setCenterText2("Charts (Roboto Italic)");

				Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");

				data.setCenterText2Typeface(tf);
				data.setCenterText2FontSize(Utils.px2sp(getResources().getDisplayMetrics().scaledDensity,
						(int) getResources().getDimension(R.dimen.pie_chart_text2_size)));
			}

			chart.setPieChartData(data);
		}

		private void explodeChart() {
			isExploaded = !isExploaded;
			generateData();

		}

		private void separateSingleArc() {
			hasArcSeparated = !hasArcSeparated;
			if (hasArcSeparated) {
				isExploaded = false;
			}
			generateData();
		}

		private void toggleLabelsOutside() {
			// has labels have to be true:P
			hasLabelsOutside = !hasLabelsOutside;
			if (hasLabelsOutside) {
				hasLabels = true;
				hasLabelForSelected = false;
				chart.setValueSelectionEnabled(hasLabelForSelected);
			}

			if (hasLabelsOutside) {
				chart.setCircleFillRatio(0.7f);
			} else {
				chart.setCircleFillRatio(1.0f);
			}

			generateData();

		}

		private void toggleLabels() {
			hasLabels = !hasLabels;

			if (hasLabels) {
				hasLabelForSelected = false;
				chart.setValueSelectionEnabled(hasLabelForSelected);

				if (hasLabelsOutside) {
					chart.setCircleFillRatio(0.7f);
				} else {
					chart.setCircleFillRatio(1.0f);
				}
			}

			generateData();
		}

		private void toggleLabelForSelected() {
			hasLabelForSelected = !hasLabelForSelected;

			chart.setValueSelectionEnabled(hasLabelForSelected);

			if (hasLabelForSelected) {
				hasLabels = false;
				hasLabelsOutside = false;

				if (hasLabelsOutside) {
					chart.setCircleFillRatio(0.7f);
				} else {
					chart.setCircleFillRatio(1.0f);
				}
			}

			generateData();
		}

		/**
		 * To animate values you have to change targets values and then call {@link Chart#startDataAnimation()}
		 * method(don't confuse with View.animate()).
		 */
		private void prepareDataAnimation() {
			for (ArcValue value : data.getValues()) {
				value.setTarget((float) Math.random() * 30 + 15);
			}
		}

		private class ValueTouchListener implements PieChartOnValueTouchListener {

			@Override
			public void onValueTouched(int selectedArc, ArcValue value) {
				Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onNothingTouched() {
				// TODO Auto-generated method stub

			}

		}
	}
}

