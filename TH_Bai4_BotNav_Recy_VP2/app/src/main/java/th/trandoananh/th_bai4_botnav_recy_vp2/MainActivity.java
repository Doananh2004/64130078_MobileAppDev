package th.trandoananh.th_bai4_botnav_recy_vp2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        // Thiết lập điều hướng Fragment
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_cn1) {
                replaceFragment(new FragmentCN1());
                return true;
            } else if (itemId == R.id.nav_cn2) {
                replaceFragment(new FragmentCN2());
                return true;
            } else if (itemId == R.id.nav_cn3) {
                replaceFragment(new FragmentCN3());
                return true;
            } else if (itemId == R.id.nav_cn4) {
                replaceFragment(new FragmentCN4());
                return true;
            } else {
                return false;
            }
        });

        bottomNavigation.setSelectedItemId(R.id.nav_cn1);
        bottomNavigation.setSelectedItemId(R.id.nav_cn2);
        bottomNavigation.setSelectedItemId(R.id.nav_cn3);
        bottomNavigation.setSelectedItemId(R.id.nav_cn4);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}