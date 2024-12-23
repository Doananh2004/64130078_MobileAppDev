package th.trandoananh.appbangiay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PaymentFragment extends Fragment {

    private RadioGroup radioGroupPaymentMethods;
    private Button btnConfirmPayment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        radioGroupPaymentMethods = view.findViewById(R.id.radioGroupPaymentMethods);
        btnConfirmPayment = view.findViewById(R.id.btnConfirmPayment);

        btnConfirmPayment.setOnClickListener(v -> handlePaymentConfirmation());

        return view;
    }

    private void handlePaymentConfirmation() {
        int selectedPaymentMethodId = radioGroupPaymentMethods.getCheckedRadioButtonId();

        // Lưu trữ danh sách sản phẩm đã thanh toán
        List<Product> purchasedProducts = new ArrayList<>(CartManager.getInstance().getCartProductList());
        CartManager.getInstance().clearCart();  // Xóa sản phẩm trong giỏ hàng
        CartManager.getInstance().addPurchasedProducts(purchasedProducts);

        // Hiển thị thông báo thanh toán thành công
        Toast.makeText(getContext(), "Đã thanh toán thành công", Toast.LENGTH_SHORT).show();

        // Cập nhật badge trên MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateCartBadge();
        }

        // Chuyển đến màn hình ChoXacNhanFragment
        ChoXacNhanFragment choXacNhanFragment = new ChoXacNhanFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, choXacNhanFragment)
                .addToBackStack(null)  // Thêm vào stack để có thể quay lại khi cần
                .commit();
    }
}