package th.trandoananh.th_bai8_quizappgui2fullcode;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ResultActivity extends AppCompatActivity {

    private TextView scoreTxt, percent;
    private GridLayout gridLayout;

    private void getControl(){
        scoreTxt = findViewById(R.id.scoreTxt);
        percent = findViewById(R.id.percentTxt);
        gridLayout = findViewById(R.id.gridLayout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getControl();
        boolean[] check = getIntent().getBooleanArrayExtra("checkTF");
        int score = getIntent().getIntExtra("score", 0);
        double correctPercent = score;
        assert check != null;
        if(check.length!=0)
            correctPercent = (((double)score)/check.length)*100;
        @SuppressLint("DefaultLocale") String percentR = String.format("%.2f", correctPercent) + "%" ;
        scoreTxt.setText(String.valueOf(score));
        percent.setText(percentR);

        for (int i = 1; i <= check.length; i++) {
            // Tạo CardView mới
            CardView cardView = new CardView(this);
            LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            cardView.setCardElevation(4);
            cardView.setRadius(8);
            if(check[i-1])
                cardView.setCardBackgroundColor(getResources().getColor(R.color.correct));
            else
                cardView.setCardBackgroundColor(getResources().getColor(R.color.incorrect));

            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setPadding(50, 20, 50, 20);
            textView.setText(String.valueOf(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
            textView.setTextColor(getResources().getColor(android.R.color.black));

            cardView.addView(textView);
            gridLayout.addView(cardView);
        }
    }
}