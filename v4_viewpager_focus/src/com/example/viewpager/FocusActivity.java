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
import android.widget.TextView;

import com.example.android.bugfix.R;

public class FocusActivity extends Activity implements OnClickListener {
	private ViewPager mViewPager;
    private  TextView mText;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setAdapter(new PagerAdapter() {
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
			
			@Override
			public float getPageWidth(int position) {
//			    return super.getPageWidth(position);
			    return 0.5f;
			}
			@Override
			public int getCount() {
				return 5;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View v = View.inflate(getApplicationContext(), R.layout.viewpager_content, null);
                container.addView(v);
				return v;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView((View) object);
			}
		});
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
        if (view.getId() == R.id.button1) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        } else {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
	}

}