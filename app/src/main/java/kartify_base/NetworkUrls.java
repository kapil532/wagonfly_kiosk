package kartify_base;

/**
 * Created by Kapil Katiyar on 3/19/2018.
 */

public interface NetworkUrls
{

//    https://prod-api.wagonfly.com/guest/requestOtp
//    http://dev-api.wagonfly.com/guest/requestOtp
//    public static String FINAL_URL ="http://dev-api.wagonfly.com/";
    public static String FINAL_URL ="https://prod-api.wagonfly.com/";


    public static String FINAL_URL2 ="https://kiosk.wagonfly.com/";
    public static String STORES = FINAL_URL2+"stores/login";
    public static String RFIDS = FINAL_URL2+"rfpage";
    public static String STORES_ALL = FINAL_URL2+"stores/all";
    public static String GENERATE_RFID = FINAL_URL2+"rfids/generateRfidRecord?";
    public static String GET_RFID_ORDERS = FINAL_URL2+"orders/getrfidOrder?store=";
    public static String RFID_CREATE_ORDER = FINAL_URL2+"orders/createOrder?store=";
    public static String CUSTOMER_CREATE = FINAL_URL2+"customers/create";
    public static String CREATE_PAYMENT = FINAL_URL2+"payments/createPayment?store=";
    public static String DEACTIVATE_RFIDS = FINAL_URL2+"rfids/deactivateRfid?store=1&aa=";
//    rfid=e28011700000020d35823a31&barcode=8907568297823






    public static String GET_OTP = FINAL_URL+"guest/requestOtp";
    public static String GUEST_AUTH = FINAL_URL+"guest/authenticate";
    public static String ADD_VEHICLE = FINAL_URL+"guest/addVehicles";

    public static String PRODUCT = FINAL_URL+"product/";
    public static String CREATE_ORDER = FINAL_URL+"order/createOrder";
    public static String ORDER = FINAL_URL+"order/";
    public static String PAYMENT_CHARGE = FINAL_URL+"payment/charge";
    public static String PUSH_NOTIFICATION = FINAL_URL+"pushnotification/register";
    public static String ZONE_GETDETAIL = FINAL_URL+"schemes/";
    public static String PAYMENT_CHARGE_DAILY= FINAL_URL+"payments/chargeDailyPass";
    public static String UPDATE_PROFILE= FINAL_URL+"guest/updateProfile";
    public static String GET_GUEST_SUBSCRIPTION_DETAILS = FINAL_URL+"getGuestSubscriptionDetails";
    public static String PARKING_EVENT_BRING_MYCAR = FINAL_URL+"parkingEvent/bringmycar";
    public static String SUBSCIPTION_CREATE = FINAL_URL+"subscription/create";
    public static String GET_PROFILE_DETAILS = FINAL_URL+"guest/getProfile";
    public static String DAILY_PASS_LIST = FINAL_URL+"dailypass/list";
    public static String VEHICLE_STATUS = FINAL_URL+"parkingEvent/guestVehicleStatus";
//    public static String PRIVACY_POLICY = "http://wagonfly.com/privacy";
    public static String PRIVACY_POLICY = "https://www.wagonfly.com/privacy.html";

//Restaurant

    public static String RESTAURANTS = FINAL_URL+"restaurant";
}
