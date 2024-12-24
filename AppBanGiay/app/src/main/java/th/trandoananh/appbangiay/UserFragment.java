package th.trandoananh.appbangiay;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserFragment extends Fragment {

    private ImageView avatarImageView;
    private TextView usernameTextView;
    private TextView donMuaTextView;
    private LinearLayout doiMatKhauLayout;
    private LinearLayout dangXuatLayout;
    private LinearLayout donMuaLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        avatarImageView = view.findViewById(R.id.avatarImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        donMuaTextView = view.findViewById(R.id.donMuaTextView);
        doiMatKhauLayout = view.findViewById(R.id.doiMatKhauLayout);
        dangXuatLayout = view.findViewById(R.id.dangXuatLayout);
        donMuaLayout = view.findViewById(R.id.donMuaLayout);

        // Đọc thông tin đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", getContext().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");

        // Hiển thị thông tin tài khoản
        usernameTextView.setText(username);
        avatarImageView.setImageResource(R.drawable.avatar); // thay avatar bằng resource của bạn

        // Đổi mật khẩu
        doiMatKhauLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        // Đăng xuất
        dangXuatLayout.setOnClickListener(v -> showLogoutConfirmationDialog());

        // Đơn mua
        donMuaLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DonMua.class);
            startActivity(intent);
        });

        return view;
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Xác nhận đăng xuất")
                .setMessage("Bạn có chắc muốn đăng xuất không?")
                .setPositiveButton("Có", (dialog, which) -> logout())
                .setNegativeButton("Không", null)
                .show();
    }

    private void logout() {
        // Clear shared preferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("LoginPrefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Chuyển đến màn hình đăng nhập
        Intent intent = new Intent(getActivity(), DangNhap.class);
        startActivity(intent);
        requireActivity().finish(); // Đóng MainActivity
    }
}