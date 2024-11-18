package com.example.appbandienthoai;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SuaNhomSanPham_Activity extends AppCompatActivity {
    Database database;
    ArrayList<NhomSanPham> mangNSP;
    NhomSanPhamAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_nhom_san_pham);
        mangNSP = new ArrayList<>();
        adapter = new NhomSanPhamAdapter(SuaNhomSanPham_Activity.this, mangNSP, true);
        database = new Database(this, "banhang.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS nhomsanpham(maso INTEGER PRIMARY KEY AUTOINCREMENT, tennsp NVARCHAR(200), anh BLOB)");

    }
}