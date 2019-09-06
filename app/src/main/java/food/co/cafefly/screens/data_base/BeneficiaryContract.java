package food.co.cafefly.screens.data_base;

import android.provider.BaseColumns;

/**
 * Created by delaroy on 5/11/17.
 */
public class BeneficiaryContract {

    public static final class BeneficiaryEntry implements BaseColumns
    {

        public static final String TABLE_NAME = "rest_item_table";
        public static final String REST_ID = "rest_id";
        public static final String ITEM_ID = "item_id";
        public static final String ITEM_NAME = "item_name";
        public static final String ITEM_QUANTITY = "item_quan";
        public static final String ITEM_PRICE = "item_price";
        public static final String ITEM_EXTRAS_ID = "extras_ids";
        public static final String ITEM_TYPE = "item_type";

    }
}
