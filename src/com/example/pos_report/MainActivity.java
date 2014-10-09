package com.example.pos_report;

import java.util.List;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_peport.database.model.ShopProperty;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class MainActivity extends Activity implements
	NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		
	}
	
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,PlaceholderFragment.newInstance(position + 1)).commit();
			
		if (position == 0) {
			fragmentManager.beginTransaction()
			.replace(R.id.container, SaleByDate.newInstance(position + 1))
			.commit();
			
			  } else if (position == 1) {
				  
				  fragmentManager.beginTransaction()
					.replace(R.id.container, SaleByProduct.newInstance(position + 1))
					.commit();
				  //Intent intentMain = new Intent(MainActivity.this,salebydategraph.class);
				  //MainActivity.this.startActivity(intentMain);
				  
			  } else {
				  
				  fragmentManager.beginTransaction()
				  .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
				  .commit();
				  
			  }

	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
	
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			  Intent intentMain = new Intent(MainActivity.this,Setting.class);
			  MainActivity.this.startActivity(intentMain);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		private static final String ARG_SECTION_NUMBER = "section_number";

		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}}
	/*public class KeySpinner extends BaseAdapter {
		
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
			LayoutInflater inflater = getLayoutInflater();
			convertView = inflater.inflate(R.layout.spinner_item, parent,false);
			FlatTextView textView = (FlatTextView)convertView.findViewById(R.id.textView1);
			ShopProperty sp = Shoplist.get(position);
			textView.setText(sp.getShopName());
			return convertView;
		}}*/
}