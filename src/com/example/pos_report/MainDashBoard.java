package com.example.pos_report;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import progress.menu.item.ProgressMenuItemHelper;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.flatuilibrary.FlatButton;
import com.example.flatuilibrary.FlatTextView;
import com.example.flatuilibrary.FlatUI;
import com.example.pos_report.GetSumTransactionShop.GetSumTransacTion;
import com.example.pos_report.SaleByDate.PaymentlistAdapter;
import com.example.pos_report.SaleByDate.PromotionlistAdapter;
import com.example.pos_report.SaleByDate.SaleListAdapter;
import com.example.pos_report.SaleByDate.ShopSpinner;
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.example.pos_report.database.GetSumPromotionShopDao;
import com.example.pos_report.database.GetSumTransactionShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.ReportDatabase;
import com.example.pos_report.database.ShopPropertyDao;
import com.example.pos_report.database.model.GlobalProperty;
import com.example.pos_report.database.model.ProductGroup;
import com.example.pos_report.database.model.ShopProperty;
import com.example.pos_report.database.model.SumPaymentShop;
import com.example.pos_report.database.model.SumPromotionShop;
import com.example.pos_report.database.model.SumTransactionShop;
import com.example.pos_report.graph.MainDashBoard_Payment_PieGraph;
import com.example.pos_report.graph.MainDashBoard_Promotion_PieGraph;
import com.example.pos_report.graph.SalebyDate_Graph;
import com.example.pos_report.graph.SalebyDate_Payment_PieGraph;
import com.example.pos_report.graph.SalebyDate_Promotion_PieGraph;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class MainDashBoard extends Fragment  {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	private Spinner shopSelect;
	private int ShopID;
	private static int currentmonth,currentyear;
	private FlatTextView text_sum_payment_amount,text_sum_payment_percent;
	private FlatTextView text_sum_promo_amount,text_sum_promo_percent;
	private Button showChart_payment,showChart_promotion;
	public static String URL;
	private ListView listPayment,listPromotion;
	private ProgressDialog  pdia;
	private static String lastsaledate;
	private String saledate;
	private static String shopName;
	private FlatTextView ShopNameValue,saledatevalue,totalbillvalue,totalcustvalue,totalvatvalue,totalretailvalue,totaldisvalue,totalsalevalue;
	private int totalbill,totalcust;
	private double totalvat,totalretail,totaldis,totalsale;
	private ProgressMenuItemHelper progressHelper;
	private static int CurrentShopID;
	private final int APP_THEME = R.array.deep;
	
	final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
	GlobalProperty format = gpd.getGlobalProperty();
	String formatnumber = format.getCurrencyFormat();
	String formatqty = format.getQtyFormat();
	NumberFormat formatter = new DecimalFormat(formatnumber);
	NumberFormat qtyformatter = new DecimalFormat(formatqty);
	
	 public static MainDashBoard newInstance(int sectionNumber) {
		 MainDashBoard fragment = new MainDashBoard();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
	 }

	 public MainDashBoard() {
	 
	 }
	 
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
		setHasOptionsMenu(true);
	  View rootView = inflater.inflate(R.layout.main_dashboard, container,
	    false);
	  final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
	  //Database = new ReportDatabase(getActivity());
	   String path_ip = sharedPreferences.getString("path_ip", "");
	   String path_visual = sharedPreferences.getString("path_visual", "");
	   URL = "http://"+path_ip+"/"+path_visual+"/ws_dashboard.asmx?WSDL";
       FlatUI.initDefaultValues(getActivity());
       FlatUI.setDefaultTheme(APP_THEME);
	   pdia = new ProgressDialog(getActivity());
	   pdia.setCancelable(true);
	   pdia.setIndeterminate(true);
		text_sum_payment_amount = (FlatTextView)rootView.findViewById(R.id.text_sum_payment_amount);
		text_sum_payment_percent = (FlatTextView)rootView.findViewById(R.id.text_sum_payment_percent);
		text_sum_promo_amount = (FlatTextView)rootView.findViewById(R.id.text_sum_promo_amount);
		text_sum_promo_percent = (FlatTextView)rootView.findViewById(R.id.text_sum_promo_percent);
		
		saledatevalue = (FlatTextView) rootView
				.findViewById(R.id.SaleDateValue);
		totalbillvalue = (FlatTextView) rootView
				.findViewById(R.id.TotalBillValue);
		totalcustvalue = (FlatTextView) rootView
				.findViewById(R.id.ToTalCustValue);
		totalvatvalue = (FlatTextView) rootView
				.findViewById(R.id.TotalVatValue);
		totalretailvalue = (FlatTextView) rootView
				.findViewById(R.id.TotalRetailValue);
		totaldisvalue = (FlatTextView) rootView
				.findViewById(R.id.TotalDisValue);
		totalsalevalue = (FlatTextView) rootView
				.findViewById(R.id.TotalSaleValue);
		listPayment = (ListView)rootView.findViewById(R.id.listPayment);
		listPromotion = (ListView)rootView.findViewById(R.id.listPromotion);
		
		shopSelect = (Spinner)rootView.findViewById(R.id.shopspinner);
		 
		ShopPropertyDao sp = new ShopPropertyDao(getActivity());

		final List<ShopProperty> Shoplist = sp.getShopList();
		shopSelect.setAdapter(new ShopSpinner(Shoplist));
		ArrayList<Integer> saledate = new ArrayList<Integer>() ;
		for (ShopProperty st : Shoplist) saledate.add(st.getShopID());
		CurrentShopID = saledate.get(0);
		 
		shopSelect.getItemAtPosition(0);
		shopSelect.setSelection(0);
		
		new GetLastSaleDateShop(getActivity(),CurrentShopID, "123",new GetLastSaleDateShop.GetLastSaleDate() {
			
			@Override
			public void onSuccess(String result) {
				lastsaledate = result;
				currentmonth = Integer.parseInt(lastsaledate.substring(5, 7));
				currentyear = Integer.parseInt(lastsaledate.substring(0,4));
				pdia.dismiss();
			}

			@Override
			public void onLoad() {
				// TODO Auto-generated method stub
				
		        pdia.setMessage("Loading...");
		        pdia.show();
			}
		}).execute(URL);
		
		
		
		listPayment.setOnTouchListener(new ListView.OnTouchListener() {
			   
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				   v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		listPromotion.setOnTouchListener(new ListView.OnTouchListener() {
			   
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				   v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		showChart_payment = (FlatButton)rootView.findViewById(R.id.showChart_payment);
		
		showChart_payment.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	Intent intentMain = new Intent(getActivity() , 
						 MainDashBoard_Payment_PieGraph.class);
		    	
		    	startActivity(intentMain);
		    	
		    }
		});
		showChart_promotion = (FlatButton)rootView.findViewById(R.id.showChart_promotion);
		
		showChart_promotion.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	Intent intentMain = new Intent(getActivity() , 
						 MainDashBoard_Promotion_PieGraph.class);
		    	
		    	startActivity(intentMain);
		    	
		    }
		});
		shopSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				ShopProperty  Shoplist = (ShopProperty) parent.getItemAtPosition(position);
				ShopID = Shoplist.getShopID();
				new GetLastSaleDateShop(getActivity(),ShopID, "123",new GetLastSaleDateShop.GetLastSaleDate() {
					
					@Override
					public void onSuccess(String result) {
						lastsaledate = result;
						currentmonth = Integer.parseInt(lastsaledate.substring(5, 7));
						currentyear = Integer.parseInt(lastsaledate.substring(0,4));
						onChangeData();
						progressHelper.stopProgress();
						
					}

					@Override
					public void onLoad() {
						// TODO Auto-generated method stub
						
						progressHelper.startProgress();
					}
				}).execute(URL);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		
		
	return rootView;
	
}
	

	public static String getLastsaledate() {
		return lastsaledate;
	}

	@Override
	 public void onAttach(Activity activity) {
	  super.onAttach(activity);
	  super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	 }
	 
		public void onChangeData(){
			
			
	    	new GetSumTransactionShop(getActivity(),ShopID , currentmonth, currentyear, "123",new GetSumTransacTion() {
				//not sure
				@Override
				public void onSuccess(String result) {
					Gson gson = new Gson();
					try {
						Type collectionType = new TypeToken<Collection<SumTransactionShop>>(){}.getType();
						
						List<SumTransactionShop> gs = (List<SumTransactionShop>) gson.fromJson(result, collectionType);
						
						// insert GetSumTransactionShop data into database
						GetSumTransactionShopDao gt = new GetSumTransactionShopDao(getActivity());
						gt.insertSumTransactionShopData(gs);
						//Set ListViewAdapter Salebydate
						
				    	SumTransactionShop LastSaleDate = gt.getLastSaleDate();
				    	saledate = LastSaleDate.getSaleDate();
				    	totalbill = LastSaleDate.getTotalBill();
						totalcust = LastSaleDate.getTotalCust();
						totalvat = LastSaleDate.getTransVAT();
						totalretail = LastSaleDate.getRetailPrice();
						totaldis = LastSaleDate.getDiscount();
						totalsale = LastSaleDate.getSalePrice();
						
						saledatevalue.setText(""+ saledate);
						totalbillvalue.setText("" + totalbill);
						totalcustvalue.setText("" + totalcust);
						totalvatvalue.setText("" + formatter.format((totalvat)));
						totalretailvalue.setText("" + formatter.format((totalretail)));
						totaldisvalue.setText("" + formatter.format((totaldis)));
						totalsalevalue.setText("" + formatter.format((totalsale)));
						
						progressHelper.stopProgress();
						
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					}
					
				}
				
				@Override
				public void onLoad() {
					// TODO Auto-generated method stub
					progressHelper.startProgress();
					
				}
			}).execute(URL);
	    	new GetSumPaymentShop(getActivity(),ShopID , currentmonth, currentyear, "123",new GetSumPaymentShop.GetSumPayment() {
				
				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					try {
						Type collectionType = new TypeToken<Collection<SumPaymentShop>>(){}.getType();
						@SuppressWarnings("unchecked")
						List<SumPaymentShop> gs = (List<SumPaymentShop>) gson.fromJson(result, collectionType);
						
						// insert GetSumTransactionShop data into database
						GetSumPaymentShopDao gp = new GetSumPaymentShopDao(getActivity());
						gp.insertPaymentShopData(gs);
						//Set ListViewAdapter Payment
						List<SumPaymentShop> Paymentlist = gp.getPaymentlist();
						listPayment.setAdapter(new PaymentlistAdapter(Paymentlist));
						
						progressHelper.stopProgress();
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					}
					
					
				}
				
				@Override
				public void onLoad() {
					// TODO Auto-generated method stub
					progressHelper.startProgress();
					
				}
				
			}).execute(URL);
	    	new GetSumPromotionShop(getActivity(),ShopID , currentmonth, currentyear, "123",new GetSumPromotionShop.GetSumPromotion() {
				
				@Override
				public void onSuccess(String result) {
					// TODO Auto-generated method stub
					Gson gson = new Gson();
					try {
						Type collectionType = new TypeToken<Collection<SumPromotionShop>>(){}.getType();
						List<SumPromotionShop> spr = (List<SumPromotionShop>) gson.fromJson(result, collectionType);
						
						// insert GetSumTransactionShop data into database
						GetSumPromotionShopDao gpr = new GetSumPromotionShopDao(getActivity());
						gpr.insertPromoShopData(spr);
						
						//Set ListViewAdapter Promotion 
						List<SumPromotionShop> Promotionlist = gpr.getSumPromoList();
						listPromotion.setAdapter(new PromotionlistAdapter(Promotionlist));
						
						progressHelper.stopProgress();
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					}
					
					
				}
				
				@Override
				public void onLoad() {
					// TODO Auto-generated method stub
					progressHelper.startProgress();
				}
			}).execute(URL);
	    	
			
		}
	 
	 public class ShopSpinner extends BaseAdapter {
			
			List<ShopProperty> Shoplist;
			public ShopSpinner(List<ShopProperty> sl) {
				Shoplist = sl;
			}
			@Override
			public int getCount() {
				// 
				return Shoplist.size();
			}

			@Override
			public Object getItem(int position) {
				// 
				return Shoplist.get(position);
			}

			@Override
			public long getItemId(int position) {
				// 
				return position;
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = getActivity().getLayoutInflater();
				convertView = inflater.inflate(R.layout.spinner_item_main, parent,false);
				FlatTextView textView = (FlatTextView)convertView.findViewById(R.id.textView1);
				ShopProperty sp = Shoplist.get(position);
				shopName = sp.getShopName();
				textView.setText(sp.getShopName());
				return convertView;
			}}
	 
	 public static String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		MainDashBoard.shopName = shopName;
	}

	 public class PaymentlistAdapter extends BaseAdapter{
			
			List<SumPaymentShop> Paymentlist;
			
			public PaymentlistAdapter(List<SumPaymentShop> pl) {
				Paymentlist = pl;
			}
			@Override
			public int getCount() {
				return Paymentlist.size();
			}

			@Override
			public Object getItem(int position) {
				return Paymentlist.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}
			
			private class ViewHolder {
				FlatTextView typePaymentValue;
				FlatTextView amountPaymentValue;
				FlatTextView percentPaymentValue;
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder  holder;
				LayoutInflater inflater = getActivity().getLayoutInflater();
				
					if(convertView == null){
						convertView = inflater.inflate(R.layout.salebydate_payment_column, parent,false);
					holder = new ViewHolder();
					holder.typePaymentValue=(FlatTextView)convertView.findViewById(R.id.typePaymentValue);
					holder.amountPaymentValue=(FlatTextView)convertView.findViewById(R.id.amountPaymentValue);
					holder.percentPaymentValue=(FlatTextView)convertView.findViewById(R.id.percentPaymentValue);
					
					convertView.setTag(holder);
					}else{
						holder=(ViewHolder)convertView.getTag();
					}
					final GetSumPaymentShopDao gp = new GetSumPaymentShopDao(getActivity());
					final SumPaymentShop gsp = gp.getSumPayment();
					
					//Number Format
					final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
					GlobalProperty format = gpd.getGlobalProperty();
					String currencyformat = format.getCurrencyFormat();
					NumberFormat currencyformatter = new DecimalFormat(currencyformat);
					
					SumPaymentShop sp = Paymentlist.get(position);
					holder.typePaymentValue.setText(sp.getPayTypeName());
					holder.amountPaymentValue.setText(currencyformatter.format(sp.getTotalPay()));
					double totalpay = gsp.getTotalPay();
					double percent = (sp.getTotalPay()* 100) / totalpay;
					holder.percentPaymentValue.setText(currencyformatter.format(percent));
					text_sum_payment_amount.setText(currencyformatter.format(gsp.getTotalPay()));
					text_sum_payment_percent.setText("100%");
					
				return convertView;
					}}
		
			public class PromotionlistAdapter extends BaseAdapter{
			
			List<SumPromotionShop> Promotiontlist;
			
			public PromotionlistAdapter(List<SumPromotionShop> prl) {
				Promotiontlist = prl;
			}
			@Override
			public int getCount() {
				return Promotiontlist.size();
			}

			@Override
			public Object getItem(int position) {
				return Promotiontlist.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}
			
			private class ViewHolder {
				FlatTextView typePromotionValue;
				FlatTextView amountPromotionValue;
				FlatTextView percentPromotionValue;
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder  holder;
				LayoutInflater inflater = getActivity().getLayoutInflater();
				
					if(convertView == null){
						convertView = inflater.inflate(R.layout.salebydate_promotion_column, parent,false);
					holder = new ViewHolder();
					holder.typePromotionValue=(FlatTextView)convertView.findViewById(R.id.typePromotionValue);
					holder.amountPromotionValue=(FlatTextView)convertView.findViewById(R.id.amountPromotionValue);
					holder.percentPromotionValue=(FlatTextView)convertView.findViewById(R.id.percentPromotionValue);
					convertView.setTag(holder);
					}else{
						holder=(ViewHolder)convertView.getTag();
					}
					//Number Format
					final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
					GlobalProperty format = gpd.getGlobalProperty();
					String currencyformat = format.getCurrencyFormat();
					NumberFormat currencyformatter = new DecimalFormat(currencyformat);
					
					final GetSumPromotionShopDao gp = new GetSumPromotionShopDao(getActivity());
					final SumPromotionShop gpr = gp.getSumPromotion();
					SumPromotionShop spr = Promotiontlist.get(position);
					holder.typePromotionValue.setText(spr.getPromotionName());
					holder.amountPromotionValue.setText(currencyformatter.format(spr.getDiscount()));
					double totaldis = gpr.getDiscount();
					double percent = (spr.getDiscount()* 100) / totaldis;
					holder.percentPromotionValue.setText(currencyformatter.format(percent));
					text_sum_promo_amount.setText(currencyformatter.format(gpr.getDiscount()));
					text_sum_promo_percent.setText("100%");
				return convertView;
					}
	 
			}
			
			@Override
			public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
				super.onCreateOptionsMenu(menu, inflater);
		        inflater.inflate(R.menu.refresh_menu, menu);
		        progressHelper = new ProgressMenuItemHelper(menu, R.id.action_refresh, 1);
		        
		    }

		    @Override
		    public boolean onOptionsItemSelected(MenuItem item) {
		        switch (item.getItemId()) {
		            case R.id.action_refresh:
		            	onChangeData();
		                return true;
		            default:
		                return super.onOptionsItemSelected(item);
		        }
		    }
			

}
