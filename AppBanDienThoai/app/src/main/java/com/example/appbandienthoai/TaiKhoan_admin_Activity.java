package com.example.appbandienthoai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TaiKhoan_admin_Activity extends AppCompatActivity {
    Database database;
    ListView lv;
    int vitri;
    ArrayList<TaiKhoan> mangTK;
    TaiKhoanAdapter adapter;
    FloatingActionButton dauconggocphai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan_admin);
        dauconggocphai = findViewById(R.id.btnthem);
        lv = findViewById(R.id.listtk);
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


        dauconggocphai.setOnClickListener(view -> {
            Intent a=new Intent(getApplicationContext(),ThemTaiKhoan_Activity.class);
            startActivity(a);
        });
        mangTK = new ArrayList<>();
        adapter = new TaiKhoanAdapter(getApplicationContext(), R.layout.ds_taikhoan, mangTK);
        lv.setAdapter(adapter);
        database = new Database(this, "banhang.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS taikhoan(tendn VARCHAR(20) PRIMARY KEY, matkhau VARCHAR(50), quyen VARCHAR(50))");
        Loaddulieutaikhoan();
    }

    private void Loaddulieutaikhoan() {
        Cursor dataCongViec = database.GetData("SELECT * FROM taikhoan");
        mangTK.clear();
        while (dataCongViec.moveToNext()) {
            String tdn = dataCongViec.getString(0);
            String mk= dataCongViec.getString(1);
            String q = dataCongViec.getString(2);
            mangTK.add(new TaiKhoan(tdn, mk, q));
        }
        adapter.notifyDataSetChanged();
    }
}