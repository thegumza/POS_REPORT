package com.example.pos_report;

import java.util.List;

import progress.menu.item.ProgressMenuItemHelper;

import com.example.flatuilibrary.FlatTextView;
import com.example.pos_report.database.model.ShopProperty;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class MainActivity extends ActionBarActivity implements
	NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	private ProgressMenuItemHelper progressHelper;
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
	public void onNavigationDrawerItemSelected(final int position) {
		// update the main content by replacing fragments
		final FragmentManager fragmentManager = getFragmentManager();
		/*fragmentManager
				.beginTransaction()
				.replace(R.id.container,PlaceholderFragment.newInstance(position + 1)).commit();*/
			
		if (position == 0) {
			fragmentManager.beginTransaction()
			.replace(R.id.container, MainDashBoard.newInstance(position + 1))
			.commit();
			
			  } else if (position == 1) {
				  
				  fragmentManager.beginTransaction()
					.replace(R.id.container, SaleByDate.newInstance(position + 1))
					.commit();
				  
			  }
			  else if (position == 2) {
				  
				  fragmentManager.beginTransaction()
					.replace(R.id.container, SaleByProduct.newInstance(position + 1))
					.commit();
				  
			  }
			  else if (position == 3) {
				  AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
		            builder1.setMessage("Do you want to exit?");
		            builder1.setCancelable(true);
		            builder1.setPositiveButton("Yes",
		                    new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                    finish();
		                }
		            });
		            builder1.setNegativeButton("Cancel",
		                    new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    dialog.cancel();
		                }
		            });

		            AlertDialog alert11 = builder1.create();
		            alert11.show();
				  
				  
			  }
			  else {
				  
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
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_logout);
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}
	
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
	
	
}