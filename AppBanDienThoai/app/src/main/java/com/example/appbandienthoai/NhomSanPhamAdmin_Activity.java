package com.example.appbandienthoai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class NhomSanPhamAdmin_Activity extends AppCompatActivity {
    private Database database;
    private ListView lv;
    private FloatingActionButton addButton;
    private ArrayList<NhomSanPham> mangNSP;
    private NhomSanPhamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhom_san_pham_admin);
        initializeViews();
        setupDatabase();
        loadData();
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
        ImageButton btndonhang = findViewById(R.id.btndonhang);
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
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ThemNhomSanPham_Activity.class);
            startActivity(intent);
        });
    }

    private void initializeViews() {
        lv = findViewById(R.id.listtk);
        addButton = findViewById(R.id.btnthem);
        mangNSP = new ArrayList<>();

        adapter = new NhomSanPhamAdapter(NhomSanPhamAdmin_Activity.this, mangNSP, true);

        lv.setAdapter(adapter);
    }

    private void setupDatabase() {
        database = new Database(this, "banhang.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS nhomsanpham(maso INTEGER PRIMARY KEY AUTOINCREMENT, tennsp NVARCHAR(200), anh BLOB)");
    }

    private void loadData() {
        Cursor cursor = database.GetData("SELECT * FROM nhomsanpham");
        mangNSP.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String maso = cursor.getString(0);
                String tennsp = cursor.getString(1);
                byte[] blob = cursor.getBlob(2);
                mangNSP.add(new NhomSanPham(maso, tennsp, blob));
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(this, "Null load dữ liệu", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }
    private byte[] convertBitmapToByteArray(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}