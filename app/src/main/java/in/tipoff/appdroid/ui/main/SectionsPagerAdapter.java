package in.tipoff.appdroid.ui.main;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Map;

import in.tipoff.appdroid.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Context mContext) {
        super(fragmentManager, lifecycle);

        this.mContext = mContext;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Bundle bundle = new Bundle();
        //bundle.putStringArrayList("data",map.get(position));
        if(position == 0)
            return PlaceholderFragment.newInstance(position + 1);
        else if(position == 1)
            return AppListFragment.newInstance();
        else
            return PlaceholderFragment2.newInstance();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}