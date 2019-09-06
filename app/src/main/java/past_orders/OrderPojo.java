package past_orders;

import java.util.ArrayList;

/**
 * Created by Kapil Katiyar on 9/3/2018.
 */

public class OrderPojo
{



    private  String orderListIdl;
    private  String qty;
    private  String code;
    private  String totalAmt;
    private ArrayList<items> items;
    private ArrayList<appliedOffer> appliedOfferA;
    private ArrayList<Tax> taxArrayList;


    public String getOrderListIdl() {
        return orderListIdl;
    }

    public void setOrderListIdl(String orderListIdl) {
        this.orderListIdl = orderListIdl;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public ArrayList<OrderPojo.items> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderPojo.items> items) {
        this.items = items;
    }

    public ArrayList<appliedOffer> getAppliedOfferA() {
        return appliedOfferA;
    }

    public void setAppliedOfferA(ArrayList<appliedOffer> appliedOfferA) {
        this.appliedOfferA = appliedOfferA;
    }






  public class items
   {
       private  String product_id;
       private  String product_name;
       private  String product_description;
       private  String productImage;
       private  String brand;
       private  String bar_code;
       private  String expiry;

       public String getProduct_id() {
           return product_id;
       }

       public void setProduct_id(String product_id) {
           this.product_id = product_id;
       }

       public String getProduct_name() {
           return product_name;
       }

       public void setProduct_name(String product_name) {
           this.product_name = product_name;
       }

       public String getProduct_description() {
           return product_description;
       }

       public void setProduct_description(String product_description) {
           this.product_description = product_description;
       }

       public String getProductImage() {
           return productImage;
       }

       public void setProductImage(String productImage) {
           this.productImage = productImage;
       }

       public String getBrand() {
           return brand;
       }

       public void setBrand(String brand) {
           this.brand = brand;
       }

       public String getBar_code() {
           return bar_code;
       }

       public void setBar_code(String bar_code) {
           this.bar_code = bar_code;
       }

       public String getExpiry() {
           return expiry;
       }

       public void setExpiry(String expiry) {
           this.expiry = expiry;
       }

       public String getRetail_price() {
           return retail_price;
       }

       public void setRetail_price(String retail_price) {
           this.retail_price = retail_price;
       }

       public String getQuantity() {
           return quantity;
       }

       public void setQuantity(String quantity) {
           this.quantity = quantity;
       }

       private  String retail_price;
       private  String quantity;
   }


  public class  appliedOffer
  {

      private  String storeOffersId;
      private  String offerText;
      private  String offerApplied;
      private  String value;

      public String getOfferApplied() {
          return offerApplied;
      }

      public void setOfferApplied(String offerApplied) {
          this.offerApplied = offerApplied;
      }

      private  String amount;
      private  String valueType;

      public String getStoreOffersId() {
          return storeOffersId;
      }

      public void setStoreOffersId(String storeOffersId) {
          this.storeOffersId = storeOffersId;
      }

      public String getOfferText() {
          return offerText;
      }

      public void setOfferText(String offerText) {
          this.offerText = offerText;
      }

      public String getValue() {
          return value;
      }

      public void setValue(String value) {
          this.value = value;
      }

      public String getAmount() {
          return amount;
      }

      public void setAmount(String amount) {
          this.amount = amount;
      }

      public String getValueType() {
          return valueType;
      }

      public void setValueType(String valueType) {
          this.valueType = valueType;
      }
  }

 public class Tax
 {
     private String gst;
     private String cgst;
     private String sgst;
     private String taxable;
     private String netAmt;

     public String getNetAmt() {
         return netAmt;
     }

     public void setNetAmt(String netAmt) {
         this.netAmt = netAmt;
     }

     public String getCgst() {
         return cgst;
     }

     public void setCgst(String cgst) {
         this.cgst = cgst;
     }

     public String getSgst() {
         return sgst;
     }

     public void setSgst(String sgst) {
         this.sgst = sgst;
     }



     public String getGst() {
         return gst;
     }

     public void setGst(String gst) {
         this.gst = gst;
     }

     public String getTaxable() {
         return taxable;
     }

     public void setTaxable(String taxable) {
         this.taxable = taxable;
     }
 }

    public class StorePojo
    {
        private String idx;
        private String name;
        private String image;

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }


    }
}
