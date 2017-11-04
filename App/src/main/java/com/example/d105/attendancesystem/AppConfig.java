package com.example.d105.attendancesystem;

/**
 * Created by ilogics-dv1 on 28/2/17.
 */

public class AppConfig {
    // Server user login url
    public static String URL_LOGIN = "http://bestinbead.com/freeapp/services/ws-user.php?type=SIGNIN";

    // Server user register url
   public static String URL_REGISTER = "http://bestinbead.com/freeapp/services/ws-user.php?type=SIGNUP";
    public static String URL_UPDATE = "http://bestinbead.com/glApi/api/userEdad/";

     public static  String URL_CUSTOMERS="http://bestinbead.com/glApi/api/users/";
    public static  String URL_PRODUCTS="http://bestinbead.com/glApi/api/products/";

    public static String URL_UPDATE_PRO="http://bestinbead.com/glApi/api/productEdad/";

    public static String URL_ADD_PRO="http://bestinbead.com/glApi/api/productEdad";
    public static String URL_ALLORDER="http://bestinbead.com/glApi/api/orders/";

    public static String URL_ALLCOUPONS="http://bestinbead.com/glApi/api/coupons/";
    public static String URL_ADDCOUPON="http://bestinbead.com/glApi/api/couponEdad";
    public static String URL_EDITCOUPON="http://bestinbead.com/glApi/api/couponEdad/";

    public static String URL_DELETE="http://bestinbead.com/glApi/api/delete/";

    public  static  String URL_NOTI="http://bestinbead.com/glApi/api/notify";

    public  static  String URL_ALLCOMPLAINS="http://bestinbead.com/glApi/api/getComp";



    public  static  String URL_ADDPOSTWORK="http://bestinbead.com/freeapp/services/ws-post.php?type=ADDPOST";
    public  static  String URL_COUNTRIES="http://bestinbead.com/freeapp/services/ws-user.php?type=GETCOUNTRYLIST";


    public static String URL_CONTACT_UPDATE = "http://bestinbead.com/freeapp/services/ws-user.php?type=CONTACT";
    public static String URL_PERSONAL_INFO_UPDATE = "http://bestinbead.com/freeapp/services/ws-user.php?type=CONTACT";





}