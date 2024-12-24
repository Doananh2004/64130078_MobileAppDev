package th.trandoananh.appbangiay;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private final CartFragment cartFragment = new CartFragment();
    private final HomeFragment homeFragment = new HomeFragment();
    private final UserFragment userFragment = new UserFragment();
    private final ChoXacNhanFragment choXacNhanFragment = new ChoXacNhanFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;
                } else if(itemId == R.id.user){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, userFragment).commit();
                    return true;
                } else if(itemId == R.id.cart){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, cartFragment).commit();
                    updateCartBadge();
                    return true;
                }else return false;
            }
        });
    }

    public void updateCartBadge() {
        int cartItemCount = CartManager.getInstance().getCartProductList().size();
        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.cart);
        badge.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.purple_500));
        badge.setVisible(cartItemCount > 0);
        badge.setNumber(cartItemCount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartBadge();
    }
}