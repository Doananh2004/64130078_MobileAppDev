package th.trandoananh.appbangiay;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<Product> cartProductList = new ArrayList<>();
    private final List<Product> purchasedProductList = new ArrayList<>();

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<Product> getCartProductList() {
        return cartProductList;
    }

    public void addProductToCart(Product product) {
        cartProductList.add(product);
    }

    public void removeProductFromCart(Product product) {
        cartProductList.remove(product);
    }

    public void clearCart() {
        cartProductList.clear();
    }

    public List<Product> getPurchasedProductList() {
        return purchasedProductList;
    }

    public void addPurchasedProducts(List<Product> products) {
        purchasedProductList.addAll(products);
    }
}
