package th.trandoananh.appbangiay;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DangKy extends AppCompatActivity {

    TextView tvDangNhap;
    EditText edtTK, edtMK, etdNhapLaiMK;
    Button btnDangKy;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        databaseHelper = new DatabaseHelper(this);

        edtTK = findViewById(R.id.edtTK);
        edtMK = findViewById(R.id.etdMK);
        etdNhapLaiMK = findViewById(R.id.etdNhapLaiMK);
        btnDangKy = findViewById(R.id.btnDangKy);
        tvDangNhap = findViewById(R.id.tvDangNhap);

        btnDangKy.setOnClickListener(v -> {
            String username = edtTK.getText().toString().trim();
            String password = edtMK.getText().toString().trim();
            String confirmPassword = etdNhapLaiMK.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(DangKy.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(DangKy.this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Thêm tài khoản vào cơ sở dữ liệu
            databaseHelper.insertData(username, password);

            // Lưu thông tin đăng nhập
            saveLoginInfo(username, password);

            Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            finish();
        });

        tvDangNhap.setOnClickListener(v -> finish());
    }

    private void saveLoginInfo(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password); // Lưu mật khẩu
        editor.apply();
    }
}