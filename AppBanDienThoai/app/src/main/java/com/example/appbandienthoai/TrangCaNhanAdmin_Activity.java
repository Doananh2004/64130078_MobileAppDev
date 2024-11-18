package com.example.appbandienthoai;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TrangCaNhanAdmin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan_admin);
        Button dangxuat = findViewById(R.id.btndangxuat);
        TextView textTendn = findViewById(R.id.tendn); // TextView hiển thị tên đăng nhập

        // Lấy tendn từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String tendn = sharedPreferences.getString("tendn", null);

        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(TrangCaNhanAdmin_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish(); // Kết thúc activity nếu chưa đăng nhập
            return;
        }

        ImageButton btntrangchu=findViewById(R.id.btntrangchu);
        btntrangchu.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(), TrangChuAdmin_Activity.class);
            startActivity(a);
        });
        ImageButton btncanhan=findViewById(R.id.btncanhan);
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
                intent = new Intent(getApplicationContext(), TrangCaNhanAdmin_Activity.class);
            }
            startActivity(intent);
        });
        ImageButton btndonhang=findViewById(R.id.btndonhang);
        btndonhang.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(), DonHangAdmin_Activity.class);
            startActivity(a);
        });
        ImageButton btnsanpham=findViewById(R.id.btnsanpham);
        btnsanpham.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(),SanPhamAdmin_Activity.class);
            startActivity(a);
        });
        ImageButton btnnhomsp=findViewById(R.id.btnnhomsp);
        btnnhomsp.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(),NhomSanPhamAdmin_Activity.class);
            startActivity(a);
        });
        ImageButton btntaikhoan=findViewById(R.id.btntaikhoan);
        btntaikhoan.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(), TaiKhoan_admin_Activity.class);
            startActivity(a);
        });

        btntrangchu.setOnClickListener(view -> {
            // Chuyển đến trang chính
            Intent intent = new Intent(getApplicationContext(), TrangChuAdmin_Activity.class);
            startActivity(intent);
        });

        dangxuat.setOnClickListener(v -> new AlertDialog.Builder(TrangCaNhanAdmin_Activity.this)
                .setTitle("Đăng Xuất")
                .setMessage("Bạn có chắc chắn muốn đăng xuất?")
                .setPositiveButton("Có", (dialog, which) -> {
                    // Xóa trạng thái đăng nhập
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", false);
                    editor.putString("tendn", null);
                    editor.apply();

                    // Quay lại Activity chính
                    Intent intent = new Intent(getApplicationContext(), TrangChuNguoiDung_Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish(); // Kết thúc activity
                })
                .setNegativeButton("Không", null)
                .show());
    }
}