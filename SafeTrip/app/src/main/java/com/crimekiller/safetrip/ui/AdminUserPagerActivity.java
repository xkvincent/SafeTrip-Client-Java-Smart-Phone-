package com.crimekiller.safetrip.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.crimekiller.safetrip.model.User;
import com.crimekiller.safetrip.R;

import java.util.ArrayList;


/**
 * Created by Wenlu on 3/27/16.
 */
public class AdminUserPagerActivity extends FragmentActivity {
    ViewPager mViewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        final ArrayList<User> users = User.getUser();

        FragmentManager fm = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return users.size();
            }

            @Override
            public Fragment getItem(int pos) {
                String userName = users.get(pos).getName();
                return AdminUserPagerFragment.newInstance(userName);
            }
        });

        String name= (String) getIntent().getSerializableExtra(AdminUserPagerFragment.USER_NAME);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}
