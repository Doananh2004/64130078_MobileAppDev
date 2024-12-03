package th.trandoananh.th_bai1_basicgui_simplemath;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final int OPERATION_ADD = 1;
    public static final int OPERATION_SUBTRACT = 2;
    public static final int OPERATION_MULTIPLY = 3;
    public static final int OPERATION_DIVIDE = 4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextA = findViewById(R.id.editTextA);
        EditText editTextB = findViewById(R.id.editTextB);
        RadioGroup radioGroupOperations = findViewById(R.id.radioGroupOperations);
        Button buttonCalculate = findViewById(R.id.buttonCalculate);
        TextView textViewResult = findViewById(R.id.textViewResult);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                try {
                    double a = Double.parseDouble(editTextA.getText().toString());
                    double b = Double.parseDouble(editTextB.getText().toString());
                    double result;

                    // Lấy RadioButton được chọn
                    int selectedId = radioGroupOperations.getCheckedRadioButtonId();
                    if (selectedId == -1) {
                        Toast.makeText(MainActivity.this, "Vui lòng chọn phép toán", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Lấy tag từ RadioButton được chọn
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    int operation = Integer.parseInt(selectedRadioButton.getTag().toString());

                    // Xử lý phép toán dựa trên hằng số
                    switch (operation) {
                        case OPERATION_ADD:
                            result = a + b;
                            break;
                        case OPERATION_SUBTRACT:
                            result = a - b;
                            break;
                        case OPERATION_MULTIPLY:
                            result = a * b;
                            break;
                        case OPERATION_DIVIDE:
                            if (b != 0) {
                                result = a / b;
                            } else {
                                Toast.makeText(MainActivity.this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            break;
                        default:
                            throw new IllegalStateException("Phép toán không hợp lệ");
                    }

                    textViewResult.setText("Kết quả: " + result);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}