package com.example.pos_report;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


import com.example.flatui_library.FButton;
import com.example.flatuilibrary.FlatButton;
import com.example.flatuilibrary.FlatTextView;
import com.example.pos_peport.database.model.GlobalProperty;
import com.example.pos_peport.database.model.ProductGroup;
import com.example.pos_peport.database.model.ProductModel;
import com.example.pos_peport.database.model.ProductModel.ProductNameModel;
import com.example.pos_peport.database.model.SaleProductShop;
import com.example.pos_peport.database.model.ShopProperty;
import com.example.pos_peport.database.model.SumPaymentShop;
import com.example.pos_peport.database.model.SumProductShop;
import com.example.pos_peport.database.model.SumPromotionShop;
import com.example.pos_peport.database.model.SumTransactionShop;
import com.example.pos_peport.database.model.TopProductShop;
import com.example.pos_report.SaleByDate.PaymentlistAdapter;
import com.example.pos_report.SaleByDate.PromotionlistAdapter;
import com.example.pos_report.SaleByDate.SaleListAdapter;
import com.example.pos_report.database.GetSaleProductShopDao;
import com.example.pos_report.database.GetSumPaymentShopDao;
import com.example.pos_report.database.GetSumProductShopDao;
import com.example.pos_report.database.GetSumPromotionShopDao;
import com.example.pos_report.database.GetSumTransactionShopDao;
import com.example.pos_report.database.GetTopProductShopDao;
import com.example.pos_report.database.GlobalPropertyDao;
import com.example.pos_report.database.ProductGroupDao;
import com.example.pos_report.database.ReportDatabase;
import com.example.pos_report.database.ShopPropertyDao;
import com.example.pos_report.graph.SalebyProduct_Graph;
import com.example.pos_report.graph.TopProduct_Qty_PieGraph;
import com.example.pos_report.graph.TopProduct_Saleprice_PieGraph;

import android.app.Activity;
import android.app.Fragment;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SaleByProduct extends Fragment implements OnRefreshListener{
	ExpandableListView elv;
	private static final String ARG_SECTION_NUMBER = "section_number";
	public static String URL;
	String ProductGroupCode="";
	int ShopID,month,year,mode;
	int sumAmount;
	ListView listProduct,list_TopProduct;
	FlatButton showReportBtn,showReportBtn2,showChart_product,showChart_TopProduct;
	FlatTextView text_sum_qty,text_sum_price,text_sum_percent;
	Double sumQty,sumTotalPrice,sumPercent,sumProduct,sumSalePrice;
	Spinner shopSelect,monthSelect, yearSelect,topProduct_productSelect, topProduct_modeSelect;
	ReportDatabase Database;
	SwipeRefreshLayout swipeLayout;
	 public static SaleByProduct newInstance(int sectionNumber) {
		 SaleByProduct fragment = new SaleByProduct();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		// loginFragment fragment = new loginFragment();
	 // return fragment;
	 }

	 public SaleByProduct() {
		 
	 }
	 
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	  View rootView = inflater.inflate(R.layout.salebyproduct_main, container,
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
        
	  text_sum_qty = (FlatTextView)rootView.findViewById(R.id.text_sum_qty);
	  text_sum_price = (FlatTextView)rootView.findViewById(R.id.text_sum_price);
	  text_sum_percent = (FlatTextView)rootView.findViewById(R.id.text_sum_percent);
	  
	  //listProduct = (ListView)rootView.findViewById(R.id.listProduct);
	  elv=(ExpandableListView)rootView.findViewById(R.id.elv);
	  list_TopProduct = (ListView)rootView.findViewById(R.id.list_TopProduct);
	  
	  	shopSelect = (Spinner)rootView.findViewById(R.id.shopspinner);
		monthSelect = (Spinner)rootView.findViewById(R.id.monthspinner);
		yearSelect = (Spinner)rootView.findViewById(R.id.yearspinner);
		topProduct_productSelect = (Spinner) rootView.findViewById(R.id.topProduct_productSelect);
		topProduct_modeSelect = (Spinner) rootView.findViewById(R.id.topProduct_modeSelect);
		
		

			Database = new ReportDatabase(getActivity());
			
		elv.setOnTouchListener(new ListView.OnTouchListener() {
			   
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				   v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		list_TopProduct.setOnTouchListener(new ListView.OnTouchListener() {
			   
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				   v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});
		final ShopPropertyDao sp = new ShopPropertyDao(getActivity());
		final ProductGroupDao pg = new ProductGroupDao(getActivity());
		final List<ShopProperty> Shoplist = sp.getShopList();
		
		List<ProductGroup> Productgroup = new ArrayList<ProductGroup>();
		
		ProductGroup item;
		 item = new ProductGroup();
		 item.setProductGroupCode("");
		 item.setProductGroupName("All Product");
		 //Productgroup.add(0,item);
		 
		 Productgroup = pg.getProductGroup();
		 Productgroup.add(0,item);
		final GetSumProductShopDao gss = new GetSumProductShopDao(getActivity());
		shopSelect.setAdapter(new ShopSpinner(Shoplist));
		
		topProduct_productSelect.setAdapter(new GroupSpinner(Productgroup));
		
		String[] years = new String[]{"2012", "2013", "2014"};
		 final String[] months = new String[]{"January", "February", "March","April","May","June","July","August","September","October","November","December"};
		 String[] modeselect = new String[]{"Qty", "Saleprice"};
		 monthSelect.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1, months));
		yearSelect.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1, years));
		topProduct_modeSelect.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1, modeselect));
		onDataChange();
		
		shopSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				ShopProperty  Shoplist = (ShopProperty) parent.getItemAtPosition(position);
				ShopID = Shoplist.getShopID();
				onDataChange();
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
				onDataChange();
				onTopDataChange();
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
				onDataChange();
				onTopDataChange();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		topProduct_productSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				ProductGroup Grouplist = (ProductGroup) parent.getItemAtPosition(position);
				ProductGroupCode = Grouplist.getProductGroupCode();
				onTopDataChange();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		topProduct_modeSelect.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mode = Integer.valueOf(position);
				onTopDataChange();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		showReportBtn = (FlatButton)rootView.findViewById(R.id.BtnshowProductReport);
		
		showReportBtn.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	onDataChange();
		    	
				
		    }
		});
		showReportBtn2 = (FlatButton)rootView.findViewById(R.id.showReportBtn2);
		
		showReportBtn2.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	onTopDataChange();
				
				
		    }
		});
		showChart_product = (FlatButton)rootView.findViewById(R.id.showChart_product);
		showChart_product.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	
		    	Intent intentMain = new Intent(getActivity() , 
						 SalebyProduct_Graph.class);
		    	
		    	startActivity(intentMain);
		    	
		    	
		    }
		});
		showChart_TopProduct = (FlatButton)rootView.findViewById(R.id.showChart_TopProduct);
		showChart_TopProduct.setOnClickListener(new View.OnClickListener() {
		    @Override
			public void onClick(View v) {
		    	if(mode ==0){
		    	Intent intentMain = new Intent(getActivity() , 
						 TopProduct_Qty_PieGraph.class);
		    	
		    	startActivity(intentMain);}
		    	else if (mode == 1)
		    	{
		    	Intent intentMain = new Intent(getActivity() , 
							 TopProduct_Saleprice_PieGraph.class);
			    	
			    startActivity(intentMain);
		    	}
		    	
		    }
		});
		
		return rootView;
	
}
	 /*@Override
	public void onStart() {
			super.onStart();
			
			new ShopDataLoader(getActivity(), "123").execute(URL);
			new AllProductDataLoader(getActivity(), "123").execute(URL);
			final ShopPropertyDao sp = new ShopPropertyDao(getActivity());
			final List<ShopProperty> Shoplist = sp.getShopList();
			shopSelect.setAdapter(new ShopSpinner(Shoplist));
		}*/
	 
	 @Override
	 public void onAttach(Activity activity) {
	  super.onAttach(activity);
	  super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
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
				convertView = inflater.inflate(R.layout.spinner_item, parent,false);
				TextView textView = (TextView)convertView.findViewById(R.id.textView1);
				ShopProperty sp = Shoplist.get(position);
				textView.setText(sp.getShopName());
				return convertView;
			}}
	 public class GroupSpinner extends BaseAdapter {
			
			List<ProductGroup> Productgroup;
			public GroupSpinner(List<ProductGroup> pg) {
				Productgroup = pg;
			}
			@Override
			public int getCount() {
				return Productgroup.size();
			}

			@Override
			public Object getItem(int position) {
				// 
				return Productgroup.get(position);
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
				TextView textView = (TextView)convertView.findViewById(R.id.textView1);
				ProductGroup pg = Productgroup.get(position);
				textView.setText(pg.getProductGroupName());
				return convertView;
			}
}
public void onDataChange(){
	 
	//final GetSumProductShopDao gp = new GetSumProductShopDao(getActivity());
	final GetSaleProductShopDao gs = new GetSaleProductShopDao(getActivity());
	//Send Parameter to Web Service
	new GetSumProductShop(getActivity(),ShopID , month, year, "123").execute(URL);
	new GetSaleProductShop(getActivity(), ShopID, month, year, ProductGroupCode, "123").execute(URL);
	//Set ListViewAdapter Salebyproduct
	List<ProductModel> Salebyproduct = gs.getSaleProduct();
	//listProduct.setAdapter(new SalebyProductListAdapter(Salebyproduct));
	elv.setAdapter(new ProductExpandableListAdapter(Salebyproduct));
     int count = elv.getCount();
     for(int i = 0;i < count; i++ )
     elv.expandGroup(i);
     
    elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
        }
    });
}

public void onTopDataChange(){
	 
	final GetTopProductShopDao gt = new GetTopProductShopDao(getActivity());
	//Send Parameter to Web Service
	new GetTopProductShop(getActivity(), ShopID , month, year, ProductGroupCode,0, 10, "123").execute(URL);
	
	if(mode == 0){
	//Set ListViewAdapter TopqtyProduct
	List<TopProductShop> Topqtyproduct = gt.getTopQtyProduct();
	list_TopProduct.setAdapter(new TopQtyProductListAdapter(Topqtyproduct));
	}
	else if(mode ==1){
		List<TopProductShop> Topsaleproduct = gt.getTopSaleProduct();
		list_TopProduct.setAdapter(new TopSaleProductListAdapter(Topsaleproduct));
	}
}
public void onRefresh() {
    new Handler().postDelayed(new Runnable() {
        @Override public void run() {
        	onDataChange();
        	onTopDataChange();
            swipeLayout.setRefreshing(false);
        }
    }, 3000);
}
		//ListViewAdapter
		public class ProductListAdapter extends BaseAdapter{
			
			List<SumProductShop> Salebyproduct;
			public ProductListAdapter(List<SumProductShop> sp) {
				Salebyproduct = sp;
			}
			@Override
			public int getCount() {
				return Salebyproduct.size();
			}

			@Override
			public Object getItem(int position) {
				return Salebyproduct.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}
			
			private class ViewHolder {
				FlatTextView itemValue;
				FlatTextView qtyValue;
				FlatTextView priceValue;
				//TextView percentValue; 
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder  holder;
				LayoutInflater inflater = getActivity().getLayoutInflater();
				
					if(convertView == null){
						convertView = inflater.inflate(R.layout.salebyproduct_column, parent,false);
					holder = new ViewHolder();
					holder.itemValue=(FlatTextView)convertView.findViewById(R.id.itemValue);
					holder.qtyValue=(FlatTextView)convertView.findViewById(R.id.qtyValue);
					holder.priceValue=(FlatTextView)convertView.findViewById(R.id.priceValue);
					//holder.percentValue=(TextView)convertView.findViewById(R.id.percentValue);
					convertView.setTag(holder);
					}else{
						holder=(ViewHolder)convertView.getTag();
					}
					SumProductShop st = Salebyproduct.get(position);
					final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
					GlobalProperty format = gpd.getglobalproperty();
					String formatnumber = format.getCurrencyFormat();
					NumberFormat formatter = new DecimalFormat(formatnumber);
					
					holder.itemValue.setText(st.getProductName());
					holder.qtyValue.setText(Integer.toString(st.getQty()));
					holder.priceValue.setText(formatter.format(st.getSalePrice()));
					//GetSaleProductShopDao gsp = new GetSaleProductShopDao(getActivity());
					/*SaleProductShop sum = new SaleProductShop();
					sum = gsp.getSumProduct();
					double totalprice = sum.getSumSalePrice();
					double percent = (st.getSalePrice()* 100) / totalprice;*/
					//holder.percentValue.setText(formatter.format((st.)));
					
				return convertView;
					}
		}
		//ListViewAdapter
				public class SalebyProductListAdapter extends BaseAdapter{
					
					List<SumProductShop> saleproduct;
					public SalebyProductListAdapter(List<SumProductShop> sp) {
						saleproduct = sp;
					}
					@Override
					public int getCount() {
						return saleproduct.size();
					}

					@Override
					public Object getItem(int position) {
						return saleproduct.get(position);
					}

					@Override
					public long getItemId(int position) {
						return position;
					}
					
					private class ViewHolder {
						FlatTextView itemValue;
						FlatTextView qtyValue;
						FlatTextView priceValue;
						//TextView percentValue; 
					}
					
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						ViewHolder  holder;
						LayoutInflater inflater = getActivity().getLayoutInflater();
						
							if(convertView == null){
								convertView = inflater.inflate(R.layout.salebyproduct_column, parent,false);
							holder = new ViewHolder();
							holder.itemValue=(FlatTextView)convertView.findViewById(R.id.itemValue);
							holder.qtyValue=(FlatTextView)convertView.findViewById(R.id.qtyValue);
							holder.priceValue=(FlatTextView)convertView.findViewById(R.id.priceValue);
							
							convertView.setTag(holder);
							}else{
								holder=(ViewHolder)convertView.getTag();
							}
							SumProductShop st = saleproduct.get(position);
							
							final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
							GlobalProperty format = gpd.getglobalproperty();
							String formatnumber = format.getCurrencyFormat();
							NumberFormat formatter = new DecimalFormat(formatnumber);
							
							holder.itemValue.setText(st.getProductName());
							holder.qtyValue.setText(Integer.toString(st.getQty()));
							holder.priceValue.setText(formatter.format(st.getSalePrice()));
							//holder.percentValue.setText(formatter.format((st.getSalePrice())));
							//text_sum_totalBill.setText(formatter.format(Sumsale.getTotalBill()));
							//text_sum_discount.setText(formatter.format(Sumsale.getDiscount()));
							//text_sum_salePrice.setText(formatter.format(Sumsale.getSalePrice()));
						
						return convertView;
							}
				}
				//ListViewAdapter
				public class TopQtyProductListAdapter extends BaseAdapter{
					
					List<TopProductShop> topqtyproduct;
					public TopQtyProductListAdapter(List<TopProductShop> tq) {
						topqtyproduct = tq;
					}
					@Override
					public int getCount() {
						return topqtyproduct.size();
					}

					@Override
					public Object getItem(int position) {
						return topqtyproduct.get(position);
					}

					@Override
					public long getItemId(int position) {
						return position;
					}
					
					private class ViewHolder {
						FlatTextView number_QtyValue;
						FlatTextView item_QtyValue;
						FlatTextView qty_QtyValue;
						FlatTextView salePrice_QtyValue;
					}
					
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						ViewHolder  holder;
						LayoutInflater inflater = getActivity().getLayoutInflater();
						
							if(convertView == null){
								convertView = inflater.inflate(R.layout.salebyproduct_top_qty_column, parent,false);
							holder = new ViewHolder();
							holder.number_QtyValue=(FlatTextView)convertView.findViewById(R.id.number_QtyValue);
							holder.item_QtyValue=(FlatTextView)convertView.findViewById(R.id.item_QtyValue);
							holder.qty_QtyValue=(FlatTextView)convertView.findViewById(R.id.qty_QtyValue);
							holder.salePrice_QtyValue=(FlatTextView)convertView.findViewById(R.id.salePrice_QtyValue);
							
							convertView.setTag(holder);
							}else{
								holder=(ViewHolder)convertView.getTag();
							}
							TopProductShop ts = topqtyproduct.get(position);
							final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
							
							GlobalProperty format = gpd.getglobalproperty();
							String formatnumber = format.getCurrencyFormat();
							NumberFormat formatter = new DecimalFormat(formatnumber);
							holder.number_QtyValue.setText(""+(position+1));
							holder.item_QtyValue.setText(ts.getProductName());
							holder.qty_QtyValue.setText(Integer.toString(ts.getQty()));
							holder.salePrice_QtyValue.setText(formatter.format(ts.getSumSalePrice()));
							//holder.percentValue.setText(formatter.format((st.getSalePrice())));
							//text_sum_totalBill.setText(formatter.format(Sumsale.getTotalBill()));
							//text_sum_discount.setText(formatter.format(Sumsale.getDiscount()));
							//text_sum_salePrice.setText(formatter.format(Sumsale.getSalePrice()));
						
						return convertView;
							}
				}
				//ListViewAdapter
				public class TopSaleProductListAdapter extends BaseAdapter{
					
					List<TopProductShop> topsaleproduct;
					public TopSaleProductListAdapter(List<TopProductShop> ts) {
						topsaleproduct = ts;
					}
					@Override
					public int getCount() {
						return topsaleproduct.size();
					}

					@Override
					public Object getItem(int position) {
						return topsaleproduct.get(position);
					}

					@Override
					public long getItemId(int position) {
						return position;
					}
					
					private class ViewHolder {
						FlatTextView number_PriceValue;
						FlatTextView item_PriceValue;
						FlatTextView qty_PriceValue;
						FlatTextView salePrice_PriceValue;
					}
					
					@Override
					public View getView(int position, View convertView, ViewGroup parent) {
						ViewHolder  holder;
						LayoutInflater inflater = getActivity().getLayoutInflater();
						
							if(convertView == null){
								convertView = inflater.inflate(R.layout.salebyproduct_top_price_column, parent,false);
							holder = new ViewHolder();
							holder.number_PriceValue=(FlatTextView)convertView.findViewById(R.id.number_PriceValue);
							holder.item_PriceValue=(FlatTextView)convertView.findViewById(R.id.item_PriceValue);
							holder.qty_PriceValue=(FlatTextView)convertView.findViewById(R.id.qty_PriceValue);
							holder.salePrice_PriceValue=(FlatTextView)convertView.findViewById(R.id.salePrice_PriceValue);
							
							convertView.setTag(holder);
							}else{
								holder=(ViewHolder)convertView.getTag();
							}
							TopProductShop ts = topsaleproduct.get(position);
							final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
							
							GlobalProperty format = gpd.getglobalproperty();
							String formatnumber = format.getCurrencyFormat();
							NumberFormat formatter = new DecimalFormat(formatnumber);
							holder.number_PriceValue.setText(""+(position+1));
							holder.item_PriceValue.setText(ts.getProductName());
							holder.qty_PriceValue.setText(Integer.toString(ts.getQty()));
							holder.salePrice_PriceValue.setText(formatter.format(ts.getSumSalePrice()));
							//holder.percentValue.setText(formatter.format((st.getSalePrice())));
							//text_sum_totalBill.setText(formatter.format(Sumsale.getTotalBill()));
							//text_sum_discount.setText(formatter.format(Sumsale.getDiscount()));
							//text_sum_salePrice.setText(formatter.format(Sumsale.getSalePrice()));
						
						return convertView;
							}
				}
				public class ProductExpandableListAdapter extends BaseExpandableListAdapter {
					  List<ProductModel> product;
					  ProductExpandableListAdapter(List<ProductModel> ps) {
						  product = ps;
					  }

					  @Override
					  public int getGroupCount() {
						  
					    return product != null ? product.size() : 0;
					  }

					  @Override
					  public Object getGroup(int groupPosition) {
					    
						  return product.get(groupPosition);
					  }

					  @Override
					  public long getGroupId(int groupPosition) {
						  return 0;
					  }
					  private class ViewHolder {
						  FlatTextView productGroupName;
						  FlatTextView productDeptName;
						  FlatTextView itemValue;
						  FlatTextView qtyValue;
						  FlatTextView priceValue;
						  FlatTextView percentValue;
						}
					  @Override
					  public View getGroupView(int groupPosition, boolean isExpanded,
					                           View convertView, ViewGroup parent) {
						  ViewHolder  holder;
						  LayoutInflater inflater = getActivity().getLayoutInflater();
					    if (convertView == null) {
					    	convertView = inflater.inflate(R.layout.salebyproduct_group_column, parent,false);
							holder = new ViewHolder();
							holder.productGroupName=(FlatTextView)convertView.findViewById(R.id.ProductGroupName);
							holder.productDeptName=(FlatTextView)convertView.findViewById(R.id.ProductDeptName);
							
					    convertView.setTag(holder);
						}else{
							holder=(ViewHolder)convertView.getTag();
						}
					    
					    ProductModel ss = product.get(groupPosition);
					    holder.productGroupName.setText(ss.getProductGroupName()+" : ");
					    holder.productDeptName.setText(ss.getProductDeptName());
					    
					    
					    return(convertView);
					  }

					
					@Override
					public int getChildrenCount(int groupPosition) {
						return product.get(groupPosition).productNameModel != null ? product.get(groupPosition).productNameModel.size() : 0;
					}

					@Override
					public Object getChild(int groupPosition, int childPosition) {
						return product.get(groupPosition).productNameModel.get(childPosition);
					}
					
					@Override
					public long getChildId(int groupPosition, int childPosition) {
						return childPosition;
					}

					@Override
					public boolean hasStableIds() {
						return(true);
					}
					@Override
					public View getChildView(int groupPosition,int childPosition, boolean isLastChild,
							View convertView, ViewGroup parent) {
						ViewHolder  holder;
						LayoutInflater inflater = getActivity().getLayoutInflater();
						
						if(convertView == null){
							
						convertView = inflater.inflate(R.layout.salebyproduct_column, parent,false);
						
						holder = new ViewHolder();
						
						holder.itemValue=(FlatTextView)convertView.findViewById(R.id.itemValue);
						holder.qtyValue=(FlatTextView)convertView.findViewById(R.id.qtyValue);
						holder.priceValue=(FlatTextView)convertView.findViewById(R.id.priceValue);
						holder.percentValue=(FlatTextView)convertView.findViewById(R.id.percentValue);
						//holder.sumDeptAmountValue=(FlatTextView)convertView.findViewById(R.id.sumDeptAmountValue);
						//holder.SumDeptPriceValue=(FlatTextView)convertView.findViewById(R.id.SumDeptPriceValue);
						convertView.setTag(holder);
						}else{
							holder=(ViewHolder)convertView.getTag();
						}
						GetSaleProductShopDao gsp = new GetSaleProductShopDao(getActivity());
						ProductNameModel ss = product.get(groupPosition).productNameModel.get(childPosition);
						final GlobalPropertyDao gpd = new GlobalPropertyDao(getActivity());
						//final GetSaleProductShopDao gss = new GetSaleProductShopDao(getActivity());
						GlobalProperty format = gpd.getglobalproperty();
						String formatnumber = format.getCurrencyFormat();
						NumberFormat formatter = new DecimalFormat(formatnumber);
						holder.itemValue.setText(""+ss.getProductName());
						holder.qtyValue.setText(""+ss.getSumAmount());
						holder.priceValue.setText(""+Double.toString(ss.getSumSalePrice()));
						
						SaleProductShop sum = new SaleProductShop();
						sum = gsp.getSumProduct();
						double totalprice = sum.getSumSalePrice();
						double percent = (ss.getSumSalePrice()* 100) / totalprice;
						//holder.sumDeptAmountValue.setText(""+ss.getSumAmount());
						//holder.SumDeptPriceValue.setText(""+ss.getSumSalePrice());
						holder.percentValue.setText(formatter.format((percent)));
						
						//Summary ProductGroup
						
						SaleProductShop s = gsp.getSumProduct();
						sumAmount = s.getSumAmount();
						sumSalePrice = s.getSumSalePrice();
						text_sum_qty.setText(""+sumAmount);
						text_sum_price.setText(""+sumSalePrice);
						//text_sum_percent.setText(formatter.format(Sumsale.getSalePrice()));
	
					return convertView;
						
					}

					@Override
					public boolean isChildSelectable(int groupPosition,
							int childPosition) {
						return true;
					}
					}

}