package th.trandoananh.th_bai2_basicgui_bmi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextHeight = findViewById(R.id.editTextHeight);
        EditText editTextWeight = findViewById(R.id.editTextWeight);
        CheckBox checkBoxAsian = findViewById(R.id.checkBoxAsian);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        TextView textViewResult = findViewById(R.id.textViewResult);
        TextView textViewEvaluation = findViewById(R.id.textViewEvaluation);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onClick(View v) {
                try {
                    // Lấy dữ liệu chiều cao và cân nặng
                    double height = Double.parseDouble(editTextHeight.getText().toString()) / 100; // cm -> m
                    double weight = Double.parseDouble(editTextWeight.getText().toString());

                    // Tính BMI
                    double bmi = weight / (height * height);
                    textViewResult.setText("Kết quả BMI: " + String.format("%.2f", bmi));

                    // Kiểm tra người dùng có chọn Châu Á hay không
                    boolean isAsian = checkBoxAsian.isChecked();
                    String evaluation;

                    // Đánh giá dựa trên tiêu chuẩn
                    if (isAsian) {
                        // Tiêu chuẩn BMI mới cho người Châu Á
                        if (bmi < 17.5) {
                            evaluation = "Underweight";
                        } else if (bmi < 23) {
                            evaluation = "Normal weight";
                        } else if (bmi < 28) {
                            evaluation = "Overweight";
                        } else {
                            evaluation = "Obese";
                        }
                    } else {
                        // Tiêu chuẩn BMI cho WHO
                        if (bmi < 18.5) {
                            evaluation = "Underweight";
                        } else if (bmi < 25) {
                            evaluation = "Normal weight";
                        } else if (bmi < 30) {
                            evaluation = "Overweight";
                        } else {
                            evaluation = "Obese";
                        }
                    }

                    // Hiển thị đánh giá
                    textViewEvaluation.setText("Đánh giá: " + evaluation);

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}