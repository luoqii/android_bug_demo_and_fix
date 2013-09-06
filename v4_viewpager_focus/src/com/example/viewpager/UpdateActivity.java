package com.example.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bugfix.R;

public class UpdateActivity extends Activity implements OnClickListener {

	private ViewPager mViewPager;
    private  TextView mText;
	private PagerAdapter mAdapter;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mAdapter = new BugFixAdapter(mViewPager) {
			
			@Override
			public int getCount() {
				return 5;
			}
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ViewGroup group = (ViewGroup) View.inflate(getApplicationContext(), R.layout.viewpager_content, null);
                
				// update view;
				((Button)group.findViewById(R.id.button1)).setText("new data: " + Math.random());
				
				container.addView(group);
				return group;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView((View) object);
			}
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}
		};
		mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                mText.setText(position + "/" + mViewPager.getAdapter().getCount());
            }
        });
        mText = (TextView)findViewById(R.id.editText);
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		KeyEvent event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT);
		if (view.getId() == R.id.button1) {
		} else {
			event = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT);
		}

		mViewPager.executeKeyEvent(event);
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mAdapter.notifyDataSetChanged();
			}
		});
	}

	public abstract class BugFixAdapter extends PagerAdapter {
		private ViewPager mPager;
		private int mExistedPageCount;

		public BugFixAdapter(ViewPager pager) {
			mPager = pager;
		}
		
		@Override
		public int getItemPosition(Object object) {
			if (mExistedPageCount > 0) {
				mExistedPageCount--;
				// to cheat viewpager
				// let pager destroy ourself and re-create to update data.
				return POSITION_NONE;
			}
			return super.getItemPosition(object);
		}
		
		@Override
		public void notifyDataSetChanged() {
			mExistedPageCount = mPager.getChildCount();
			super.notifyDataSetChanged();
		}
	}
}
