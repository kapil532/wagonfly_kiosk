package food.co.cafefly.screens.data_base;

/**
 * Created by Kapil Katiyar on 8/29/2018.
 */

public class ItemAddPojo
{


    private   String REST_ID;
    private   String ITEM_ID ;
    private   String ITEM_NAME ;
    private   String ITEM_QUANTITY ;
    private   String ITEM_PRICE ;
    private   String ITEM_EXTRAS_ID;
    private   String ITEM_TYPE ;


    public String getREST_ID() {
        return REST_ID;
    }

    public void setREST_ID(String REST_ID) {
        this.REST_ID = REST_ID;
    }

    public String getITEM_ID() {
        return ITEM_ID;
    }

    public void setITEM_ID(String ITEM_ID) {
        this.ITEM_ID = ITEM_ID;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getITEM_QUANTITY() {
        return ITEM_QUANTITY;
    }

    public void setITEM_QUANTITY(String ITEM_QUANTITY) {
        this.ITEM_QUANTITY = ITEM_QUANTITY;
    }

    public String getITEM_PRICE() {
        return ITEM_PRICE;
    }

    public void setITEM_PRICE(String ITEM_PRICE) {
        this.ITEM_PRICE = ITEM_PRICE;
    }

    public String getITEM_EXTRAS_ID() {
        return ITEM_EXTRAS_ID;
    }

    public void setITEM_EXTRAS_ID(String ITEM_EXTRAS_ID) {
        this.ITEM_EXTRAS_ID = ITEM_EXTRAS_ID;
    }

    public String getITEM_TYPE() {
        return ITEM_TYPE;
    }

    public void setITEM_TYPE(String ITEM_TYPE) {
        this.ITEM_TYPE = ITEM_TYPE;
    }



}