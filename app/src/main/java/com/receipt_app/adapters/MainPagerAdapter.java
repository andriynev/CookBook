package com.receipt_app.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.receipt_app.models.Category;
import com.receipt_app.ui.fragments.SoupsFragment;
import com.receipt_app.ui.fragments.SaladsFragment;
import com.receipt_app.ui.fragments.DrinksFragment;
import com.receipt_app.ui.fragments.DesertsFragment;
import com.receipt_app.ui.fragments.SecondCoursesFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private static final int TAB_COUNT = 5;
    private String[] tabTitles = Category.allCategories;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = SoupsFragment.newInstance(tabTitles[position]);
                break;
            case 1:
                frag = SecondCoursesFragment.newInstance(tabTitles[position]);
                break;
            case 2:
                frag = SaladsFragment.newInstance(tabTitles[position]);
                break;
            case 3:
                frag = DesertsFragment.newInstance(tabTitles[position]);
                break;
            case 4:
                frag = DrinksFragment.newInstance(tabTitles[position]);
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
