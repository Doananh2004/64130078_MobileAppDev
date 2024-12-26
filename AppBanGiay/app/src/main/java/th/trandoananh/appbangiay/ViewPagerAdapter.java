package th.trandoananh.appbangiay;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
    // Danh sách chứa các fragment
    private final List<Fragment> fragmentList;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        fragmentList = new ArrayList<>();
        fragmentList.add(new ChoXacNhanFragment()); // Các fragment sẽ được khởi tạo tại thời điểm cần thiết
        fragmentList.add(new ChoGiaoHangFragment());
        fragmentList.add(new DaGiaoFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
