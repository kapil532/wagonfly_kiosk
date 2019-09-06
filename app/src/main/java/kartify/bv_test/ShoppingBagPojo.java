package kartify.bv_test;

/**
 * Created by Kapil Katiyar on 7/26/2018.
 */

public class ShoppingBagPojo
{


  private String product_id;
  private String title;
  private String price_with_tax;
  private String brand;
  private String image_urls;
  private String quantity;
  private String price;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice_with_tax() {
        return price_with_tax;
    }

    public void setPrice_with_tax(String price_with_tax) {
        this.price_with_tax = price_with_tax;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(String image_urls) {
        this.image_urls = image_urls;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
