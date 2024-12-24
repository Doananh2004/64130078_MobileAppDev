package th.trandoananh.appbangiay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DonMua extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_mua);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Set up the ViewPager with the sections adapter
        viewPager.setAdapter(new SectionsPagerAdapter(this));

        // Set up the TabLayout with the ViewPager
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Chờ xác nhận");
                            break;
                        case 1:
                            tab.setText("Chờ giao hàng");
                            break;
                        case 2:
                            tab.setText("Đã giao hàng");
                            break;
                    }
                }).attach();
    }

    private static class SectionsPagerAdapter extends FragmentStateAdapter {

        public SectionsPagerAdapter(@NonNull AppCompatActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new ChoGiaoHangFragment();
                case 2:
                    return new DaGiaoFragment();
                default:
                    return new ChoXacNhanFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3; // số lượng tab
        }
    }
}