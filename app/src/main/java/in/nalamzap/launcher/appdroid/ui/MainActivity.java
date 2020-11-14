package in.nalamzap.launcher.appdroid.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import in.nalamzap.launcher.R;
import in.nalamzap.launcher.appdroid.ui.main.SectionsPagerAdapter;
import in.nalamzap.launcher.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind =  ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.wall.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.wall,null));

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),getLifecycle(),getApplicationContext());
        bind.viewPagerMain.setAdapter(sectionsPagerAdapter);
        bind.viewPagerMain.setCurrentItem(1);

        bind.viewPagerMain.setClipToPadding(false);
        bind.viewPagerMain.setOffscreenPageLimit(1);
        float nextItemVisiblePx = getResources().getDimension(R.dimen.viewpager_next_item_visible);
        float currentItemHorizontalMarginPx = getResources().getDimension(R.dimen.viewpager_current_item_horizontal_margin);
        final float pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx;
        ViewPager2.PageTransformer pageTransformer = new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setTranslationX(-pageTranslationX * position);
                page.setScaleY((float) (1-(0.1 * Math.abs(position))));
            }
        };
        bind.viewPagerMain.setPageTransformer(pageTransformer);

    }

    @Override
    public void onBackPressed() {
        bind.viewPagerMain.setCurrentItem(1);
    }
}