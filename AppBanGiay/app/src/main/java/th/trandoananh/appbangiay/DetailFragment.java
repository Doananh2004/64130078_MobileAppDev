package th.trandoananh.appbangiay;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private ImageView imageView;
    private TextView nameTextView;
    private TextView priceTextView;
    private TextView descriptionTextView;
    private final ArrayList<Button> sizeButtons = new ArrayList<>();
    private Button addToCartButton;

    private String selectedSize = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        // Initialize views
        imageView = view.findViewById(R.id.imageView);
        nameTextView = view.findViewById(R.id.nameTextView);
        priceTextView = view.findViewById(R.id.priceTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        addToCartButton = view.findViewById(R.id.addToCartButton);

        // Initialize size buttons
        sizeButtons.add(view.findViewById(R.id.sizeButton1));
        sizeButtons.add(view.findViewById(R.id.sizeButton2));
        sizeButtons.add(view.findViewById(R.id.sizeButton3));
        sizeButtons.add(view.findViewById(R.id.sizeButton4));
        sizeButtons.add(view.findViewById(R.id.sizeButton5));

        // Set size buttons click listeners
        for (int i = 0; i < sizeButtons.size(); i++) {
            int finalI = i;
            sizeButtons.get(i).setOnClickListener(v -> {
                updateButtonState(sizeButtons.get(finalI));
                selectedSize = sizeButtons.get(finalI).getText().toString();
            });
        }

        // Retrieve arguments
        Bundle args = getArguments();
        if (args != null) {
            nameTextView.setText(args.getString("name"));
            priceTextView.setText(args.getString("price"));
            imageView.setImageResource(args.getInt("drawableId"));
            descriptionTextView.setText(args.getString("description"));

            ArrayList<Integer> sizes = args.getIntegerArrayList("size");
            // Update size buttons
            for (int i = 0; i < sizeButtons.size(); i++) {
                if (sizes != null && i < sizes.size()) {
                    sizeButtons.get(i).setVisibility(View.VISIBLE);
                    sizeButtons.get(i).setText(String.valueOf(sizes.get(i)));
                } else {
                    sizeButtons.get(i).setVisibility(View.GONE);
                }
            }

            // Add to cart button click listener
            addToCartButton.setOnClickListener(v -> {
                if (selectedSize != null) {
                    Product product = new Product(
                            nameTextView.getText().toString(),
                            priceTextView.getText().toString(),
                            sizes, // Pass the sizes list to Product
                            args.getInt("drawableId"),
                            descriptionTextView.getText().toString()
                    );

                    product.setSelectedSize(selectedSize); // Set selected size to product
                    CartManager.getInstance().addProductToCart(product);

                    // Notify MainActivity to update cart badge
                    MainActivity mainActivity = (MainActivity) requireActivity();
                    mainActivity.updateCartBadge();

                    Toast.makeText(getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Vui lòng chọn kích thước", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

    private void updateButtonState(Button selectedButton) {
        for (Button button : sizeButtons) {
            if (button == selectedButton) {
                button.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black));
                button.setBackgroundResource(R.drawable.selected_button_background);
            } else {
                button.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white));
                button.setBackgroundResource(R.drawable.default_button_background);
            }
        }
    }
}