package com.hackerkernel.ondew.activity;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hackerkernel.ondew.R;
import com.hackerkernel.ondew.adapter.FragmentViewPagerAdapter;
import com.hackerkernel.ondew.fragment.ChatFragment;
import com.hackerkernel.ondew.fragment.FeesFragment;
import com.hackerkernel.ondew.fragment.NotificationFragment;
import com.hackerkernel.ondew.infrastructure.BaseAuthActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseAuthActivity {
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tab) TabLayout mTabLayout;
    @Bind(R.id.viewpager) ViewPager mViewPager;
    @Bind(R.id.navigationView) NavigationView mNavigationView;
    @Bind(R.id.drawerLayout) DrawerLayout mDrawerLayout;

    private DrawerLayout drawerlayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

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
        initNavigationDrawer();
    }

    private void initNavigationDrawer() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.home:
                        Toast.makeText(getApplicationContext(),"HOME",Toast.LENGTH_LONG).show();
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(),"TRASH",Toast.LENGTH_LONG).show();
                        drawerlayout.closeDrawers();
                        break;
                    case R.id.logout:
                        Toast.makeText(getApplicationContext(),"Logout",Toast.LENGTH_LONG).show();
                        drawerlayout.closeDrawers();
                        break;
                }
                return true;

            }
        });
        View header = mNavigationView.getHeaderView(0);
        TextView email = (TextView) header.findViewById(R.id.tv_email);
        email.setText("murtaza.agz@gmail.com");

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close);
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }



    private void setupViewPager(ViewPager viewPager) {
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NotificationFragment(),"NOTIFICATION");
        adapter.addFragment(new FeesFragment(),"FEES");
        adapter.addFragment(new ChatFragment(),"Chat");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
