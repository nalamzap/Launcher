package in.nalamzap.launcher.appdroid.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.widget.ImageView;

import in.nalamzap.launcher.R;
import in.nalamzap.launcher.appdroid.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView wall = findViewById(R.id.wall);
        wall.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.wall,null));

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),getLifecycle(),getApplicationContext());
        viewPager = findViewById(R.id.view_pager_main);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(1);

        viewPager.setClipToPadding(false);
        viewPager.setOffscreenPageLimit(1);
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
        viewPager.setPageTransformer(pageTransformer);

    }

    @Override
    public void onBackPressed() {
        viewPager.setCurrentItem(1);
    }
}