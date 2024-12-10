package th.trandoananh.th_bai3_chuyenmanhinh_fragments;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEnglish = findViewById(R.id.btn_english);
        Button btnMath = findViewById(R.id.btn_math);
        Button btnCoding = findViewById(R.id.btn_coding);

        btnEnglish.setOnClickListener(v -> replaceFragment(new EnglishFragment()));
        btnMath.setOnClickListener(v -> replaceFragment(new MathFragment()));
        btnCoding.setOnClickListener(v -> replaceFragment(new CodingFragment()));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}