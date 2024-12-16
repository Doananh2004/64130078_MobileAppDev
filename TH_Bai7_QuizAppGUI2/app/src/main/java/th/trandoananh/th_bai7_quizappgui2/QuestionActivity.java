package th.trandoananh.th_bai7_quizappgui2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {
    Button option1, option2, option3, option4, nextButton;
    TextView resultText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        option1 = findViewById(R.id.option_1);
        option2 = findViewById(R.id.option_2);
        option3 = findViewById(R.id.option_3);
        option4 = findViewById(R.id.option_4);
        resultText = findViewById(R.id.result_text);
        nextButton = findViewById(R.id.next_button);

        // Đáp án đúng là option2
        option2.setOnClickListener(v -> {
            resultText.setText("Correct..");
            resultText.setTextColor(Color.GREEN);
            nextButton.setVisibility(View.VISIBLE);
        });

        // Các đáp án sai
        @SuppressLint("SetTextI18n") View.OnClickListener wrongAnswer = v -> {
            resultText.setText("Wrong..");
            resultText.setTextColor(Color.RED);
        };

        option1.setOnClickListener(wrongAnswer);
        option3.setOnClickListener(wrongAnswer);
        option4.setOnClickListener(wrongAnswer);

        nextButton.setOnClickListener(v -> {
            // Chuyển đến câu hỏi tiếp theo
        });
    }
}