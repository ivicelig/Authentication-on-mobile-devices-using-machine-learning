package com.project.test.authenticator;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.project.test.authenticator.database.Settings;
import com.project.test.authenticator.database.SettingsController;
import com.raizlabs.android.dbflow.config.FlowManager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FlowManager.init(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //Setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Setting tabLayout bar
        TabLayout tablayout = (TabLayout) findViewById(R.id.tab_layout);
        tablayout.addTab(tablayout.newTab().setText("Home"));
        tablayout.addTab(tablayout.newTab().setText("Summary"));
        tablayout.addTab(tablayout.newTab().setText("Settings"));

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final com.project.test.authenticator.adapters.PagerAdapter adapter = new com.project.test.authenticator.adapters.PagerAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        SettingsController sc = new SettingsController();
        sc.saveToTable(4,4,"ivicelig@gmail.com",2.0);
        ActionBar actionBar = getSupportActionBar();


    }


}
