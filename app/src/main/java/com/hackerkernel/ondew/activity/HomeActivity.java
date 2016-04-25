package com.hackerkernel.ondew.activity;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import com.hackerkernel.ondew.infrastructure.BaseActivity;
import com.hackerkernel.ondew.infrastructure.BaseAuthActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends BaseAuthActivity {
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.tab) TabLayout mTabLayout;
    @Bind(R.id.viewpager) ViewPager mViewPager;
    private DrawerLayout drawerlayout;

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
        initnavigationdrawer();
    }

    private void initnavigationdrawer() {
        NavigationView navigationview = (NavigationView) findViewById(R.id.navigationView);
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
                    case R.id.setting:
                        finish();
                }
                return true;

            }
        });
        View header = navigationview.getHeaderView(0);
        TextView email = (TextView) findViewById(R.id.tv_email);
        email.setText("murtaza.agz@gmai.com");
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerlayout,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        }; 

    }



    private void setupViewPager(ViewPager viewPager) {
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new NotificationFragment(),"NOTIFICATION");
        adapter.addFragment(new FeesFragment(),"FEES");
        adapter.addFragment(new ChatFragment(),"Chat");
        viewPager.setAdapter(adapter);
    }
   
}
