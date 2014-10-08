package com.example.pos_report;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.flatuilibrary.FlatButton;
import com.example.flatuilibrary.FlatTextView;
import com.example.pos_peport.database.model.GlobalProperty;
import com.example.pos_peport.database.model.LastSaleDate;
import com.example.pos_peport.database.model.ProductGroup;
import com.example.pos_peport.database.model.ShopProperty;
import com.example.pos_peport.database.model.SumPaymentShop;
import com.example.pos_peport.database.model.SumPromotionShop;
import com.example.pos_peport.database.model.SumTransactionShop;
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.example.pos_report.database.GetSumPromotionShopDao;
import com.example.pos_report.database.GetSumTransactionShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.ReportDatabase;
import com.example.pos_report.database.ShopPropertyDao;
import com.example.pos_report.graph.SalebyDate_Graph;
import com.example.pos_report.graph.SalebyDate_Payment_PieGraph;
import com.example.pos_report.graph.SalebyDate_Promotion_PieGraph;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


public class SaleByDate extends Fragment implements OnRefreshListener {
	
	private static final String ARG_SECTION_NUMBER = "section_number";
	private Spinner shopSelect,monthSelect, yearSelect;
	private ReportDatabase Database;
	private FlatButton showReport;
	private FlatButton showChart_sale,showChart_payment,showChart_promotion;
	private int ShopID = 3 ,month,year;
	private static String Date;
	private static int TotalBill,TotalCust;
	private static double TotalVat,TotalRetail,TotalDis,TotalSale;
	private FlatTextView text_sum_totalBill,text_sum_discount,text_sum_salePrice;
	private FlatTextView text_sum_payment_amount,text_sum_payment_percent;
	private FlatTextView text_sum_promo_amount,text_sum_promo_percent;
	public static String URL;
	private ListView listSale,listPayment,listPromotion;
	private SwipeRefreshLayout swipeLayout;
	private ProgressDialog pdia;
	private String lastsaledate;
	
	 public static SaleByDate newInstance(int sectionNumber) {
		 SaleByDate fragment = new SaleByDate();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			return fragment;
	 }

	 public SaleByDate() {
	 
	 }
	 
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	  View rootView = inflater.inflate(R.layout.salebydate_main, container,
	    false);
	  final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
	   String path_ip = sharedPreferences.getString("path_ip", "27.254.23.18");
	   String path_visual = sharedPreferences.getString("path_visual", "mpos6");
	   URL = "http://"+path_ip+"/"+path_visual+"/ws_dashboard.asmx?WSDL";
	  	new ShopDataLoader(getActivity(), "123").execute(URL);
		new AllProductDataLoader(getActivity(), "123").execute(URL);
		
		
		swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(R.color.candy_primary, 
                R.color.fbutton_color_amethyst, 
                R.color.fbutton_color_turquoise, 
                R.color.HEADGROUP_COLOR);
        
	  	text_sum_totalBill = (FlatTextView)rootView.findViewById(R.id.text_sum_totalBill);
		text_sum_discount = (FlatTextView)rootView.findViewById(R.id.text_sum_discount);
		text_sum_salePrice = (FlatTextView)rootView.findViewById(R.id.text_sum_salePrice);
		text_sum_payment_amount = (FlatTextView)rootView.findViewById(R.id.text_sum_payment_amount);
		text_sum_payment_percent = (FlatTextView)rootView.findViewById(R.id.text_sum_payment_percent);
		text_sum_promo_amount = (FlatTextView)rootView.findViewById(R.id.text_sum_promo_amount);
		text_sum_promo_percent = (FlatTextView)rootView.findViewById(R.id.text_sum_promo_percent);
		
		listSale = (ListView)rootView.findViewById(R.id.listSale);
		listPayment = (ListView)rootView.findViewById(R.id.listPayment);
		listPromotion = (ListView)rootView.findViewById(R.id.listPromotion);
		
		shopSelect = (Spinner)rootView.findViewById(R.id.shopspinner);
		monthSelect = (Spinner)rootView.findViewById(R.id.monthspinner);
		yearSelect = (Spinner)rootView.findViewById(R.id.yearspinner);
		//FlatTextView txtmonth = (FlatTextView)rootView.findViewById(R.id.txtmonth);
		 String[] years = new String[]{"2012", "2013", "2014"};
		 final String[] months = new String[]{"January", "February", "March","April","May","June","July","August","September","October","November","December"};
		 monthSelect.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1, months));
		 yearSelect.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1, years));
		
		onChangeData();
		new GetLastSaleDateShop(getActivity(),ShopID, "123",new GetLastSaleDateShop.GetLastSaleDate() {
			
			@Override
			public void onSuccess(String result) {
				lastsaledate = result;
				int currentmonth = Integer.parseInt(lastsaledate.substring(5, 7));
				String currentyear = lastsaledate.substring(0,4);
				monthSelect.setSelection(currentmonth-1);
				ArrayAdapter<String> yearadapter = (ArrayAdapter<String>) yearSelect.getAdapter();
				int position = yearadapter.getPosition(currentyear);
				yearSelect.setSelection(position);
				pdia.dismiss();
				
			}

			@Override
			public void onLoad() {
				// TODO Auto-generated method stub
				pdia = new ProgressDialog(getActivity());
		        pdia.setMessage("Loading...");
		        pdia.show();
			}
		}).execute(URL);
		
		Database = new ReportDatabase(getActivity());
		final ShopPropertyDao sp = new ShopPropertyDao(getActivity());
		final List<ShopProperty> Shoplist = sp.getShopList();
		shopSelect.setAdapter(new KeySpinner(Shoplist));
		listSale.setOnTouchListener(new ListView.OnTouchListener() {
			   
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				   v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
			});
		
		listSale.setOnItemClickListener(new OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
				   SumTransactionShop  Saledate = (SumTransactionShop) parent.getItemAtPosition(position);
				   	Date = Saledate.getSaleDate();
				   	TotalBill = Saledate.getTotalBill();
				   	TotalCust = Saledate.getTotalCust();
				   	TotalVat = Saledate.getTransVAT();
				   	TotalRetail = Saledate.getRetailPrice();
				   	TotalDis = Saledate.getDiscount();
				   	TotalSale = Saledate.getSalePrice();
				   	Intent IntentMain = new Intent(getActivity(), SaleByDateDetail.class);
				   	
	              startActivity(IntentMain);
			   } 
			});
		
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
		
		 
		shopSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				ShopProperty  Shoplist = (ShopProperty) parent.getItemAtPosition(position);
				ShopID = Shoplist.getShopID();
				onChangeData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		monthSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				month = Integer.valueOf(position+1);
				onChangeData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		
		yearSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				year = Integer.parseInt(parent.getItemAtPosition(position).toString());
				onChangeData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		showReport = (FlatButton)rootView.findViewById(R.id.btnShowSaleReport);
		
		showReport.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	onChangeData();
		    }
		});
		
		showChart_sale = (FlatButton)rootView.findViewById(R.id.showChart_sale);
		
		showChart_sale.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	Intent intentMain = new Intent(getActivity() , 
						 SalebyDate_Graph.class);
		    	
		    	startActivity(intentMain);
		    	
		    }
		});
		
		showChart_payment = (FlatButton)rootView.findViewById(R.id.showChart_payment);
		
		showChart_payment.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	Intent intentMain = new Intent(getActivity() , 
						 SalebyDate_Payment_PieGraph.class);
		    	
		    	startActivity(intentMain);
		    	
		    }
		});
		
		showChart_promotion = (FlatButton)rootView.findViewById(R.id.showChart_promotion);
		
		showChart_promotion.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	Intent intentMain = new Intent(getActivity() , 
						 SalebyDate_Promotion_PieGraph.class);
		    	
		    	startActivity(intentMain);
		    	
		    }
		});
		
	return rootView;
	
}
	
	public static String getDate(){
		return Date;
	}
	
	 public static int getTotalBill() {
		return TotalBill;
	}

	public static int getTotalCust() {
		return TotalCust;
	}

	public static double getTotalVat() {
		return TotalVat;
	}

	public static double getTotalRetail() {
		return TotalRetail;
	}

	public static double getTotalDis() {
		return TotalDis;
	}

	public static double getTotalSale() {
		return TotalSale;
	}


	@Override
	 public void onAttach(Activity activity) {
	  super.onAttach(activity);
	  super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	 }
	 
		public void onChangeData(){
			
			final GetSumTransactionShopDao gt = new GetSumTransactionShopDao(getActivity());
			final GetSumPaymentShopDao gp = new GetSumPaymentShopDao(getActivity());
			final GetSumPromotionShopDao gpr = new GetSumPromotionShopDao(getActivity());
			//Send Parameter to Web Service
	    	new GetSumTransactionShop(getActivity(),ShopID , month, year, "123").execute(URL);
	    	new GetSumPaymentShop(getActivity(),ShopID , month, year, "123").execute(URL);
	    	new GetSumPromotionShop(getActivity(),ShopID , month, year, "123").execute(URL);
	    	// ย้ายไปไว้ใน Shopselect
			//Set ListViewAdapter Salebydate
	    	List<SumTransactionShop> Salebydate = gt.getSaleDate();
			listSale.setAdapter(new SaleListAdapter(Salebydate));
			
			
			//Set ListViewAdapter Payment
			List<SumPaymentShop> Paymentlist = gp.getPaymentlist();
			listPayment.setAdapter(new PaymentlistAdapter(Paymentlist));
			
			
			//Set ListViewAdapter Promotion 
			List<SumPromotionShop> Promotionlist = new ArrayList<SumPromotionShop>();
			Promotionlist = gpr.getSumPromoList();
			
			listPromotion.setAdapter(new PromotionlistAdapter(Promotionlist));
	    	
			
		}
	 
	 public void onRefresh() {
	        new Handler().postDelayed(new Runnable() {
	            @Override public void run() {
	            	onChangeData();
	                swipeLayout.setRefreshing(false);
	            }
	        }, 3000);
	    }
	 public class KeySpinner extends BaseAdapter {
			
			List<ShopProperty> Shoplist;
			public KeySpinner(List<ShopProperty> sl) {
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
				convertView = inflater.inflate(R.layout.spinner_item, parent,false);
				FlatTextView textView = (FlatTextView)convertView.findViewById(R.id.textView1);
				ShopProperty sp = Shoplist.get(position);
				textView.setText(sp.getShopName());
				return convertView;
			}}
	 public class TypeSpinner extends BaseAdapter {
			
			List<ProductGroup> Productgrouplist;
			public TypeSpinner(List<ProductGroup> pg) {
				Productgrouplist = pg;
			}
			@Override
			public int getCount() {
				// 
				return Productgrouplist.size();
			}

			@Override
			public Object getItem(int position) {
				// 
				return Productgrouplist.get(position);
			}

			@Override
			public long getItemId(int position) {
				// 
				return position;
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = getActivity().getLayoutInflater();
				convertView = inflater.inflate(R.layout.spinner_product, parent,false);
				FlatTextView TextView = (FlatTextView)convertView.findViewById(R.id.textView1);
				ProductGroup gp = Productgrouplist.get(position);
				TextView.setText(gp.getProductGroupName());
				return convertView;
			}}
	 
	//ListViewAdapter
			public class SaleListAdapter extends BaseAdapter{
				
				List<SumTransactionShop> Salebydate;
				public SaleListAdapter(List<SumTransactionShop> sd) {
					Salebydate = sd;
				}
				@Override
				public int getCount() {
					return Salebydate.size();
				}

				@Override
				public Object getItem(int position) {
					return Salebydate.get(position);
				}

				@Override
				public long getItemId(int position) {
					return position;
				}
				
				private class ViewHolder {
					FlatTextView dateValue;
					FlatTextView totalBill;
					FlatTextView discountValue;
					FlatTextView priceValue; 
				}
				
				@Override
				public View getView(int position, View convertView, ViewGroup parent) {
					ViewHolder  holder;
					LayoutInflater inflater = getActivity().getLayoutInflater();
					
						if(convertView == null){
							convertView = inflater.inflate(R.layout.salebydate_column, parent,false);
						holder = new ViewHolder();
						holder.dateValue=(FlatTextView)convertView.findViewById(R.id.dateValue);
						holder.totalBill=(FlatTextView)convertView.findViewById(R.id.totalBill);
						holder.discountValue=(FlatTextView)convertView.findViewById(R.id.discountValue);
						holder.priceValue=(FlatTextView)convertView.findViewById(R.id.priceValue);
						convertView.setTag(holder);
						}else{
							holder=(ViewHolder)convertView.getTag();
						}
						SumTransactionShop st = Salebydate.get(position);
						final GetSumTransactionShopDao gt = new GetSumTransactionShopDao(getActivity());
						final SumTransactionShop Sumsale = gt.getSumSaleShop();
						final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
						GlobalProperty format = gpd.getglobalproperty();
						String formatnumber = format.getCurrencyFormat();
						NumberFormat formatter = new DecimalFormat(formatnumber);
						holder.dateValue.setText(st.getSaleDate());
						holder.totalBill.setText(Integer.toString(st.getTotalBill()));
						holder.discountValue.setText(formatter.format(st.getDiscount()));
						holder.priceValue.setText(formatter.format((st.getSalePrice())));
						text_sum_totalBill.setText(Integer.toString(Sumsale.getTotalBill()));
						text_sum_discount.setText(formatter.format(Sumsale.getDiscount()));
						text_sum_salePrice.setText(formatter.format(Sumsale.getSalePrice()));
						
					return convertView;
						}
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
					final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
					GlobalProperty format = gpd.getglobalproperty();
					String formatnumber = format.getCurrencyFormat();
					NumberFormat formatter = new DecimalFormat(formatnumber);
					SumPaymentShop sp = Paymentlist.get(position);
					holder.typePaymentValue.setText(sp.getPayTypeName());
					holder.amountPaymentValue.setText(formatter.format(sp.getTotalPay()));
					double totalpay = gsp.getTotalPay();
					double percent = (sp.getTotalPay()* 100) / totalpay;
					holder.percentPaymentValue.setText(formatter.format(percent));
					text_sum_payment_amount.setText(formatter.format(gsp.getTotalPay()));
					
					
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
					final GetSumPromotionShopDao gp = new GetSumPromotionShopDao(getActivity());
					final SumPromotionShop gpr = gp.getSumPromotion();
					final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
					GlobalProperty format = gpd.getglobalproperty();
					String formatnumber = format.getCurrencyFormat();
					NumberFormat formatter = new DecimalFormat(formatnumber);
					SumPromotionShop spr = Promotiontlist.get(position);
					holder.typePromotionValue.setText(spr.getPromotionName());
					holder.amountPromotionValue.setText(formatter.format(spr.getDiscount()));
					double totaldis = gpr.getDiscount();
					double percent = (spr.getDiscount()* 100) / totaldis;
					holder.percentPromotionValue.setText(formatter.format(percent));
					text_sum_promo_amount.setText(formatter.format(gpr.getDiscount()));
					
				return convertView;
					}
	 
			}
			
		

}
