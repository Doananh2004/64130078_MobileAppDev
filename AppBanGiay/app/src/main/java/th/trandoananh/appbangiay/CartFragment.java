package th.trandoananh.appbangiay;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment implements CartAdapter.CartUpdateListener{

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<Product> cartProductList;
    private TextView emptyCartTextView;
    private TextView totalQuantityTextView;
    private TextView totalPriceTextView;
    private LinearLayout cartSummaryLayout;
    private Button checkoutButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCart);
        emptyCartTextView = view.findViewById(R.id.emptyCartTextView);
        totalQuantityTextView = view.findViewById(R.id.totalQuantityTextView);
        totalPriceTextView = view.findViewById(R.id.totalPriceTextView);
        cartSummaryLayout = view.findViewById(R.id.cartSummaryLayout);
        checkoutButton = view.findViewById(R.id.checkoutButton);

        cartProductList = CartManager.getInstance().getCartProductList();

        if (cartProductList.isEmpty()) {
            emptyCartTextView.setVisibility(View.VISIBLE);
            cartSummaryLayout.setVisibility(View.GONE);
        } else {
            emptyCartTextView.setVisibility(View.GONE);
            cartSummaryLayout.setVisibility(View.VISIBLE);
            cartAdapter = new CartAdapter(getContext(), cartProductList, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(cartAdapter);

            updateCartSummary();
        }

        checkoutButton.setOnClickListener(v -> navigateToPaymentFragment());

        return view;
    }

    private void navigateToPaymentFragment() {
        PaymentFragment paymentFragment = new PaymentFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, paymentFragment)
                .addToBackStack(null)
                .commit();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCartUpdated() {
        cartProductList = CartManager.getInstance().getCartProductList();
        if (cartProductList.isEmpty()) {
            emptyCartTextView.setVisibility(View.VISIBLE);
            cartSummaryLayout.setVisibility(View.GONE);
        } else {
            emptyCartTextView.setVisibility(View.GONE);
            cartSummaryLayout.setVisibility(View.VISIBLE);
            cartAdapter.notifyDataSetChanged(); // Cập nhật lại RecyclerView
            updateCartSummary();
        }

        // Cập nhật badge trên MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateCartBadge();
        }
    }

    @SuppressLint("SetTextI18n")
    private void updateCartSummary() {
        int totalQuantity = 0;
        int totalPrice = 0;

        for (Product product : cartProductList) {
            totalQuantity += product.getQuantity();
            totalPrice += product.getQuantity() * Integer.parseInt(product.getPrice().replaceAll("\\D", ""));
        }

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        totalQuantityTextView.setText("Tổng số lượng: " + totalQuantity);
        totalPriceTextView.setText("Tổng tiền: " + numberFormat.format(totalPrice) + " đ");
    }
}