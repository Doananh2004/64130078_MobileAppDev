package th.trandoananh.th_bai8_quizappgui2fullcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView intro = findViewById(R.id.intro);
        Glide.with(this)
                .asGif()
                .load(R.drawable.congrat)
                .into(intro);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onTouchEvent(event);
    }
}