package com.violet.schelude.UI;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.violet.schelude.UI.Fragments.*;

public class SchedulePagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 7; // Количество дней недели

    public SchedulePagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public Fragment getItem(int position) {
        // Возвращаем соответствующий фрагмент для каждого дня недели
        switch (position) {
            case 0:
                return new MondayFragment();
            case 1:
                return new TuesdayFragment();
            case 2:
                return new WednesdayFragment();
            case 3:
                return new ThursdayFragment();
            case 4:
                return new FridayFragment();
            case 5:
                return new SaturdayFragment();
            case 6:
                return new SundayFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
