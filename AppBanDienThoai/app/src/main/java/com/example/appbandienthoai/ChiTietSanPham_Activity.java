package com.example.appbandienthoai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChiTietSanPham_Activity extends AppCompatActivity {

    String masp, tendn;
    Button btndathang, btnaddcart;
    private ChiTietSanPham chiTietSanPham;
    private GioHangManager gioHangManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        // Khởi tạo các thành phần giao diện
        btndathang = findViewById(R.id.btndathang);
        btnaddcart = findViewById(R.id.btnaddcart);

        TextView tensp = findViewById(R.id.tensp);
        ImageView imgsp = findViewById(R.id.imgsp);
        TextView dongia = findViewById(R.id.dongia);
        TextView mota = findViewById(R.id.mota);

        TextView soluongkho = findViewById(R.id.soluongkho);
        gioHangManager = GioHangManager.getInstance(); // Sử dụng singleton
        TextView textTendn = findViewById(R.id.tendn); // TextView hiển thị tên đăng nhập

        // Lấy tendn từ SharedPreferences
        SharedPreferences sharedPre = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String tendn = sharedPre.getString("tendn", null);

        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(ChiTietSanPham_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish(); // Kết thúc activity nếu chưa đăng nhập
            return;
        }

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();


        // Nhận chi tiết sản phẩm nếu có
        chiTietSanPham = intent.getParcelableExtra("chitietsanpham");

        // Nếu không có chi tiết sản phẩm, bạn có thể xử lý mã sản phẩm theo cách của riêng bạn
        if (chiTietSanPham != null) {
            masp = chiTietSanPham.getMasp(); // Lấy mã sản phẩm từ chi tiết
            tensp.setText(chiTietSanPham.getTensp());
            dongia.setText(chiTietSanPham.getDongia() != null ? String.valueOf(chiTietSanPham.getDongia()) : "Không có dữ liệu");
            mota.setText(chiTietSanPham.getMota() != null ? chiTietSanPham.getMota() : "Không có dữ liệu");
            soluongkho.setText(String.valueOf(chiTietSanPham.getSoluongkho()));
            byte[] anhByteArray = chiTietSanPham.getAnh();
            if (anhByteArray != null && anhByteArray.length > 0) {
                Bitmap imganhbs = BitmapFactory.decodeByteArray(anhByteArray, 0, anhByteArray.length);
                imgsp.setImageBitmap(imganhbs);
            } else {
                imgsp.setImageResource(R.drawable.vest); // Ảnh mặc định
            }
        } else {
            tensp.setText("Không có dữ liệu");
        }


        // Kiểm tra trạng thái đăng nhập và thêm sản phẩm vào giỏ hàng
        btnaddcart.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            if (!isLoggedIn) {
                Intent loginIntent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(loginIntent);
            } else {
                gioHangManager.addItem(chiTietSanPham); // Gọi phương thức addItem
                Toast.makeText(ChiTietSanPham_Activity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
        // Kiểm tra trạng thái đăng nhập và thêm sản phẩm vào giỏ hàng
        btndathang.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            if (!isLoggedIn) {
                Intent loginIntent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(loginIntent);
            } else {
                gioHangManager.addItem(chiTietSanPham); // Gọi phương thức addItem
                Intent intent1=new Intent(ChiTietSanPham_Activity.this,GioHang_Activity.class);
                startActivity(intent1);
            }
        });
        // Các nút điều hướng
        setupNavigationButtons();
    }

    private void setupNavigationButtons() {
        ImageButton btntrangchu = findViewById(R.id.btntrangchu);
        ImageButton btntimkiem = findViewById(R.id.btntimkiem);
        ImageButton btndonhang = findViewById(R.id.btndonhang);
        ImageButton btngiohang = findViewById(R.id.btncart);
        ImageButton btncanhan = findViewById(R.id.btncanhan);

        btntrangchu.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TrangChuNguoiDung_Activity.class);
            startActivity(intent);
        });
        btntimkiem.setOnClickListener(view -> {
            Intent a=new Intent(ChiTietSanPham_Activity.this,TimKiemSanPham_Activity.class);
            startActivity(a);
        });
        btngiohang.setOnClickListener(view -> navigateToCart());
        btndonhang.setOnClickListener(view -> navigateToOrder());
        btncanhan.setOnClickListener(view -> navigateToProfile());
    }

    private void navigateToCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        Intent intent;
        if (!isLoggedIn) {
            intent = new Intent(getApplicationContext(), Login_Activity.class);
        } else {
            intent = new Intent(getApplicationContext(), GioHang_Activity.class);
        }
        startActivity(intent);
    }

    private void navigateToOrder() {
        // Kiểm tra trạng thái đăng nhập của người dùng
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        Intent intent;
        if (isLoggedIn) {
            // Đã đăng nhập, chuyển đến trang đơn hàng
            intent = new Intent(getApplicationContext(), DonHangUser_Activity.class);

            // Truyền tendn qua Intent
            intent.putExtra("tendn", tendn);  // Thêm dòng này để truyền tendn

        } else {
            // Chưa đăng nhập, chuyển đến trang login
            intent = new Intent(getApplicationContext(), Login_Activity.class);
        }
        startActivity(intent);

    }

    private void navigateToProfile() {
        // Kiểm tra trạng thái đăng nhập của người dùng
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        Intent intent;
        if (isLoggedIn) {
            // Đã đăng nhập, chuyển đến trang đơn hàng
            intent = new Intent(getApplicationContext(), TrangChuNguoiDung_Activity.class);

            // Truyền tendn qua Intent
            intent.putExtra("tendn", tendn);  // Thêm dòng này để truyền tendn

        } else {
            // Chưa đăng nhập, chuyển đến trang login
            intent = new Intent(getApplicationContext(), Login_Activity.class);
        }
        startActivity(intent);
    }
}