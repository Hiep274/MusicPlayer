package com.huce.imuisc.view;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.huce.imuisc.R;
import com.huce.imuisc.adapter.MainViewPagerAdapter;
import com.huce.imuisc.fragment.FragmentHome;
import com.huce.imuisc.fragment.FragmentPlay;
import com.huce.imuisc.fragment.FragmentSearch;

public class MainActivity extends AppCompatActivity {
    public static ViewPager viewPager;
    public static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.myTabLayout);
        viewPager = findViewById(R.id.myViewPager);
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        }, 3500);
        init();
    }

    private void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new FragmentSearch(), "");
        mainViewPagerAdapter.addFragment(new FragmentHome(), "");
        mainViewPagerAdapter.addFragment(new FragmentPlay(), "");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.icontimkiem);
        tabLayout.getTabAt(1).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(2).setIcon(R.drawable.iconlogo);
        Intent intent = getIntent();
        String name = intent.getStringExtra("check");
        if (name != null) {
            viewPager.setCurrentItem(2);
        }else {
            viewPager.setCurrentItem(1);
        }
    }

}