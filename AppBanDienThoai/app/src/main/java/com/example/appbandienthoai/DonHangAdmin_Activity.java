package com.example.appbandienthoai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DonHangAdmin_Activity extends AppCompatActivity {

    private Database database;
    private ListView listView;
    private DonHang_Adapter donHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_admin);

        // Khởi tạo các thành phần
        listView = findViewById(R.id.listViewChiTiet);
        database = new Database(this, "banhang.db", null, 1);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Order order = donHangAdapter.getItem(position);

            if (order != null) {
                // Hiển thị Toast với ID đơn hàng
                Toast.makeText(DonHangAdmin_Activity.this, "ID đơn hàng: " + order.getId(), Toast.LENGTH_SHORT).show();

                // Gửi thông tin đơn hàng qua Intent
                Intent intent = new Intent(DonHangAdmin_Activity.this, ChiTietDonHangAdmin_Activity.class);
                intent.putExtra("donHangId", String.valueOf(order.getId())); // Đảm bảo rằng ID là chuỗi
                startActivity(intent);
            }
        });
        ImageButton btntrangchu=findViewById(R.id.btntrangchu);
        btntrangchu.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(), TrangChuAdmin_Activity.class);
            startActivity(a);
        });
        ImageButton btncanhan=findViewById(R.id.btncanhan);
        btncanhan.setOnClickListener(view -> {
            //kiểm tra trạng thái đăng nhập của ng dùng
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            Intent intent;
            if (!isLoggedIn) {
                // Chưa đăng nhập, chuyển đến trang login
                intent = new Intent(getApplicationContext(), Login_Activity.class);
            } else {
                // Đã đăng nhập, chuyển đến trang 2
                intent = new Intent(getApplicationContext(), TrangCaNhanAdmin_Activity.class);
            }
            startActivity(intent);
        });
        ImageButton btndonhang=findViewById(R.id.btndonhang);
        btndonhang.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(), DonHangAdmin_Activity.class);
            startActivity(a);
        });
        ImageButton btnsanpham = findViewById(R.id.btnsanpham);
        btnsanpham.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(), SanPhamAdmin_Activity.class);
            startActivity(a);
        });
        ImageButton btnnhomsp = findViewById(R.id.btnnhomsp);
        btnnhomsp.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(),NhomSanPhamAdmin_Activity.class);
            startActivity(a);
        });
        ImageButton btntaikhoan = findViewById(R.id.btntaikhoan);
        btntaikhoan.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(), TaiKhoan_admin_Activity.class);
            startActivity(a);
        });

        // Tạo bảng nếu chưa tồn tại
        createTableIfNotExists();
        loadDonHang(); // Gọi phương thức loadDonHang với tenDN
    }

    private void createTableIfNotExists() {
        // Tạo bảng đơn hàng nếu chưa tồn tại
        database.QueryData("CREATE TABLE IF NOT EXISTS Dathang (" +
                "id_dathang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenkh TEXT, " +
                "diachi TEXT, " +
                "sdt TEXT, " +
                "tongthanhtoan REAL, " +
                "ngaydathang DATETIME DEFAULT CURRENT_TIMESTAMP);");
    }

    private void loadDonHang() {

        // Lấy danh sách đơn hàng từ cơ sở dữ liệu
        List<Order> orders = database.getAllDonHang();
        if (orders.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy đơn hàng nào!", Toast.LENGTH_SHORT).show();
        } else {
            // Sử dụng DonHangAdapter để hiển thị danh sách đơn hàng
            donHangAdapter = new DonHang_Adapter(this, orders);
            listView.setAdapter(donHangAdapter); // Gán adapter cho ListView
        }
    }
}