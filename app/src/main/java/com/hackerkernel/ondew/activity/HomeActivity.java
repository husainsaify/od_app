package com.hackerkernel.ondew.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.hackerkernel.ondew.R;
import com.hackerkernel.ondew.adapter.FragmentViewPagerAdapter;
import com.hackerkernel.ondew.fragment.ChatFragment;
import com.hackerkernel.ondew.fragment.FeesFragment;
import com.hackerkernel.ondew.fragment.NotificationFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tab) TabLayout mTabLayout;
    @Bind(R.id.viewpager) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(R.string.app_name);

        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NotificationFragment(),"NOTIFICATION");
        adapter.addFragment(new FeesFragment(),"FEES");
        adapter.addFragment(new ChatFragment(),"Chat");
        viewPager.setAdapter(adapter);
    }
}
