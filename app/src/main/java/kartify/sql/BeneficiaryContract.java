package kartify.sql;

import android.provider.BaseColumns;

/**
 * Created by delaroy on 5/11/17.
 */
public class BeneficiaryContract {

    public static final class BeneficiaryEntry implements BaseColumns {

        public static final String TABLE_NAME = "item_table";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRODUCT_DESCRIPTION = "product_des";
        public static final String COLUMN_PRODUCT_PRICE = "product_price";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_MANUFACTURER = "manufacturer";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_CATEGORY_NAME = "category_name";
        public static final String COLUMN_ITEM_QUANTITY = "item_quantity";
        public static final String COLUMN_IMAGE_PATH = "image_path";
        public static final String COLUMN_OFFERS = "offer";
        public static final String COLUMN_GST = "gst";
        public static final String COLUMN_RETAIL_PRICE = "retail_price";
    }
}
