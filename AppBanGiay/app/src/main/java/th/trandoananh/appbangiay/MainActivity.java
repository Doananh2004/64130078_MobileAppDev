package th.trandoananh.appbangiay;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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