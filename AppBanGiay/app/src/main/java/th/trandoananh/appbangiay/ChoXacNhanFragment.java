package th.trandoananh.appbangiay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ChoXacNhanFragment extends Fragment {
    private RecyclerView recyclerViewDonMua;
    private ProductAdapter productAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cho_xac_nhan, container, false);

        recyclerViewDonMua = view.findViewById(R.id.recyclerViewDonMua);

        // Lấy danh sách các sản phẩm đã thanh toán
        List<Product> purchasedProductList = CartManager.getInstance().getPurchasedProductList();

        // Cài đặt adapter cho RecyclerView
        productAdapter = new ProductAdapter(getContext(), purchasedProductList);
        recyclerViewDonMua.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewDonMua.setAdapter(productAdapter);

        return view;
    }
}