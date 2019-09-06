package smart_shopping_with_deal;

/**
 * Created by Kapil Katiyar on 6/26/2018.
 */

public class StartShoppingPojo
{

    private String deal_id;
    private String deal_type;
    private String deal_save_price;
    private String deal_des;
    private String deal_tnc;

    public String getDeal_type()
    {
        return deal_type;
    }

    public String getDeal_id()
    {
        return deal_id;
    }

    public void setDeal_id(String deal_id) {
        this.deal_id = deal_id;
    }

    public void setDeal_type(String deal_type) {

        this.deal_type = deal_type;
    }

    public String getDeal_save_price() {
        return deal_save_price;
    }

    public void setDeal_save_price(String deal_save_price) {
        this.deal_save_price = deal_save_price;
    }

    public String getDeal_des() {
        return deal_des;
    }

    public void setDeal_des(String deal_des) {
        this.deal_des = deal_des;
    }

    public String getDeal_tnc() {
        return deal_tnc;
    }

    public void setDeal_tnc(String deal_tnc) {
        this.deal_tnc = deal_tnc;
    }
}
