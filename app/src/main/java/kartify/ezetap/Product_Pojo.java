package kartify.ezetap;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Product_Pojo implements Serializable {


    private String id;
    private String item_name;
    private String item_code;
    private String color;
    private String fabric;
    private String sleeves;
    private String pattern;
    private String size;
    private String mrp;
    private String unit;
    private String price;
    private String manipulate_price;
    private String discount;
    private String image_url;

    private String RFIDData;
    private String item_description;
    private String fit;
    private String item_theme ;

    public String getItem_theme() {
        return item_theme;
    }

    public void setItem_theme(String item_theme) {
        this.item_theme = item_theme;
    }

    public String getRFIDData() {
        return RFIDData;
    }

    public void setRFIDData(String RFIDData) {
        this.RFIDData = RFIDData;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }



    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFabric() {
        return fabric;
    }

    public void setFabric(String fabric) {
        this.fabric = fabric;
    }

    public String getSleeves() {
        return sleeves;
    }

    public void setSleeves(String sleeves) {
        this.sleeves = sleeves;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getManipulate_price() {
        return manipulate_price;
    }

    public void setManipulate_price(String manipulate_price) {
        this.manipulate_price = manipulate_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }



}
