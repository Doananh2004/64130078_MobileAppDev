package com.example.appbandienthoai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TimKiemSanPham_Activity extends AppCompatActivity {
    private GridView grv;
    private ArrayList<SanPham> productList; // Change to ArrayList
    private SanPham_TimKiem_Adapter productAdapter;
    private DatabaseHelper dbHelper;
    String tendn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_san_pham);
        EditText timkiem=findViewById(R.id.timkiem);
        timkiem.requestFocus();
        // Initialize the GridView and DatabaseHelper
        grv = findViewById(R.id.grv);
        dbHelper = new DatabaseHelper(this);
        productList = new ArrayList<>();
        // Initialize and set the adapter with the product list
        productAdapter = new SanPham_TimKiem_Adapter(this, productList, false);
        grv.setAdapter(productAdapter);
        ImageButton btntimkiem=findViewById(R.id.btntimkiem);
        ImageButton btntrangchu=findViewById(R.id.btntrangchu);
        ImageButton btncard=findViewById(R.id.btncart);
        ImageButton btndonhang=findViewById(R.id.btndonhang);
        ImageButton btncanhan=findViewById(R.id.btncanhan);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        tendn = sharedPreferences.getString("tendn", null);

        // Nếu SharedPreferences không có, thử lấy từ Intent
        if (tendn == null) {
            tendn = getIntent().getStringExtra("tendn");
        }

        TextView textTendn = findViewById(R.id.tendn);
        // Kiểm tra giá trị tendn
        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            // Nếu không có tên đăng nhập, chuyển đến trang login
            Intent intent = new Intent(TimKiemSanPham_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish(); // Kết thúc activity nếu chưa đăng nhập
            return;
        }
        btncard.setOnClickListener(view -> {
            //kiểm tra trạng thái đăng nhập của ng dùng
            SharedPreferences sharedPreferences13 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences13.getBoolean("isLoggedIn", false);

            Intent intent;
            if (!isLoggedIn) {
                // Chưa đăng nhập, chuyển đến trang login
                intent = new Intent(getApplicationContext(), Login_Activity.class);
            } else {
                // Đã đăng nhập, chuyển đến trang 2
                intent = new Intent(getApplicationContext(), GioHang_Activity.class);
            }
            startActivity(intent);
        });
        btntrangchu.setOnClickListener(view -> {
            // Đã đăng nhập, chuyển đến trang đơn hàng
            Intent intent = new Intent(getApplicationContext(), TrangChuNguoiDung_Activity.class);
            intent.putExtra("tendn", tendn); // Truyền tendn qua intent
            startActivity(intent);
        });
        btndonhang.setOnClickListener(view -> {
            // Kiểm tra trạng thái đăng nhập của người dùng
            SharedPreferences sharedPreferences12 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences12.getBoolean("isLoggedIn", false);

            Intent intent;
            if (!isLoggedIn) {
                // Chưa đăng nhập, chuyển đến trang login
                intent = new Intent(getApplicationContext(), Login_Activity.class);
            } else {
                // Đã đăng nhập, chuyển đến trang đơn hàng
                intent = new Intent(getApplicationContext(), DonHangUser_Activity.class);
                intent.putExtra("tendn", tendn); // Truyền tendn qua intent
            }
            startActivity(intent);
        });

        btncanhan.setOnClickListener(view -> {
            //kiểm tra trạng thái đăng nhập của ng dùng
            SharedPreferences sharedPreferences1 = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences1.getBoolean("isLoggedIn", false);

            Intent intent;
            if (!isLoggedIn) {
                // Chưa đăng nhập, chuyển đến trang login
                intent = new Intent(getApplicationContext(), Login_Activity.class);
            } else {
                // Đã đăng nhập, chuyển đến trang 2
                intent = new Intent(getApplicationContext(), TrangCaNhanNguoiDung_Activity.class);
            }
            startActivity(intent);
        });

        btntimkiem.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(),TimKiemSanPham_Activity.class);
            startActivity(a);
        });

        timkiem.setOnClickListener(v -> {
            String query = timkiem.getText().toString().trim();
            if (!query.isEmpty()) {
                // Gọi phương thức tìm kiếm trong DatabaseHelper
                productList.clear(); // Xóa danh sách trước khi thêm kết quả mới
                ArrayList<SanPham> foundProducts = dbHelper.searchSanPhamByName(query);
                if (foundProducts.isEmpty()) {
                    Toast.makeText(TimKiemSanPham_Activity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    productList.addAll(foundProducts);
                }
                productAdapter.notifyDataSetChanged(); // Cập nhật adapter
            } else {
                Toast.makeText(TimKiemSanPham_Activity.this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }
}