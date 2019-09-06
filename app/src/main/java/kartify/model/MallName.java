package kartify.model;

import java.io.Serializable;
import java.util.ArrayList;

import past_orders.OrderPojo;

/**
 * Created by Kapil Katiyar on 11/24/2017.
 */

public class MallName implements Serializable {

    private String mallName;
    private String mallImage;
    private String mallAddress;
    private String mallOffers;
    private String mallId;
    private String sLatitude;
    private String sLongitude;
    private String distance;
    private String idx;
    private String contact;

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    private String email;
    private boolean active;
    private String gst;
    private ArrayList<Offers> offers;

    public ArrayList<Offers> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<Offers> offers) {
        this.offers = offers;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public String getsLatitude() {
        return sLatitude;
    }

    public void setsLatitude(String sLatitude) {
        this.sLatitude = sLatitude;
    }

    public String getsLongitude() {
        return sLongitude;
    }

    public void setsLongitude(String sLongitude) {
        this.sLongitude = sLongitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getMallImage() {
        return mallImage;
    }

    public void setMallImage(String mallImage) {
        this.mallImage = mallImage;
    }

    public String getMallAddress() {
        return mallAddress;
    }

    public void setMallAddress(String mallAddress) {
        this.mallAddress = mallAddress;
    }

    public String getMallOffers() {
        return mallOffers;
    }

    public void setMallOffers(String mallOffers) {
        this.mallOffers = mallOffers;
    }

    public String getMallId() {
        return mallId;
    }

    public void setMallId(String mallId) {
        this.mallId = mallId;
    }


   public class Offers implements Serializable
    {

        private String storeOffersId;
        private String offerText;
        private ArrayList<OffersValues> offerValues;

        public ArrayList<OffersValues> getOfferValues() {
            return offerValues;
        }

        public void setOfferValues(ArrayList<OffersValues> offerValues) {
            this.offerValues = offerValues;
        }

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



      public   class OffersValues implements Serializable
        {
            private String option1;
            private String value1;
            private String value1Type;

            private String option2;
            private String value2;
            private String value2Type;

            private String option3;
            private String value3;
            private String value3Type;

            public String getOption1() {
                return option1;
            }

            public void setOption1(String option1) {
                this.option1 = option1;
            }

            public String getValue1() {
                return value1;
            }

            public void setValue1(String value1) {
                this.value1 = value1;
            }

            public String getValue1Type() {
                return value1Type;
            }

            public void setValue1Type(String value1Type) {
                this.value1Type = value1Type;
            }

            public String getOption2() {
                return option2;
            }

            public void setOption2(String option2) {
                this.option2 = option2;
            }

            public String getValue2() {
                return value2;
            }

            public void setValue2(String value2) {
                this.value2 = value2;
            }

            public String getValue2Type() {
                return value2Type;
            }

            public void setValue2Type(String value2Type) {
                this.value2Type = value2Type;
            }

            public String getOption3() {
                return option3;
            }

            public void setOption3(String option3) {
                this.option3 = option3;
            }

            public String getValue3() {
                return value3;
            }

            public void setValue3(String value3) {
                this.value3 = value3;
            }

            public String getValue3Type() {
                return value3Type;
            }

            public void setValue3Type(String value3Type) {
                this.value3Type = value3Type;
            }
        }
    }

}
