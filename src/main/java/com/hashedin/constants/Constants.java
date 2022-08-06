package com.hashedin.constants;

import com.amazonaws.services.dynamodbv2.xspec.S;

import javax.swing.plaf.PanelUI;

public class Constants {

    public static final String TOKEN = "token";
    public static final String TYPE = "type";
    public static final String FIRST_NAME = "fname";
    public static final String BUSINESS_NAME = "bname";
    public static final String BOOLEAN = "boolean";
    public static final String GUID = "guid";
    public static final String BUYER = "BUYER";
    public static final String SELLER = "SELLER";
    public static final String GUEST = "GUEST";
    public static final String ID = "id";
    public static final String FORGOT = "Forgot Password for buyer or seller";
    public static final String FORGOT_CHECKER = "Forgot Password, verifying the OTP.";
    public static final String FORGOT_UPDATE = "Forgot Password, Updating the new password.";
    public static final String OTP_MESSAGE_1 = "\nYour ONE TIME PASSWORD is \n";
    public static final String OTP_MESSAGE_2 = "\nEnter the OTP to update password";
    public static final String NUMBERS_2 = "123456789";
    public static final String MOBILE_NUMBER_NOT_REGISTERED = "The Mobile Number is not registered";
    public static final String LOGIN_INFO = "Logging in into the application";
    public static final String LOGGED_IN = "Logged in to the application";
    public static final String LOGIN_MESSAGE = "Welcome to Extended Warranty Market Place";
    public static final String OTP_SENT = "OTP sent to user's registered mobile number";
    public static final String OTP_VALIDATED = "OTP Validated";
    public static final String PASSWORD_RESET = "Password Sucessfully Set";
    public static final String PASSWORD_UPDATED = "Password Successfully Updated";
    public static final String FIRST_TIME_LOGIN_MESSAGE_2 = "User Id(username): ";
    public static final String FIRST_TIME_LOGIN_MESSAGE_3 = "Password: ";
    public static final String FIRST_TIME_LOGIN_MESSAGE_4 = "Use the password generated for logging in.";
    public static final String KEY = "rzp_test_qgTJdyeBbdd7Vo";
    public static final String SECRET = "8CvJFiTPwLDGloDnjm9QhxmC";
    public static final String OrderAddedMSG = "Order Added";
    public static final String ADDEDTOCART = "Product added to Cart";
    public static final String DELETEDFROMCART = "Product deleted from CartItems";
    public static final String LISTADDEDTOCART = "Product List added to Cart";
    public static final String PRODUCTADDED = "Product Added";
    public static final String PRODUCTDELETED = "Product Deleted";
    public static final String CHANGESUPDATED = "New Changes updated";

    //Interceptor constants
    public static final String AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_HEADER_MISSING = "Authorization header missing";
    public static final String ROLE_UNAUTHORIZED = "Role unauthorized";
    public static final String ROLE_FORBIDDEN = "Role forbidden for this end point";
    public static final String CLIENT_ID = "clientId";
    public static final String TRACE_ID = "traceId";
    public static final String REGISTERING_USER = "Registering new user";
    public static final long OTP_DURATION = 60000;


    //Email
    public  static  final  String SENTFROM="pradhumna.guru@gmail.com";
//    public  static  final  String SENTTOADMIN="kanikaratra01@gmail.com";
    public  static  final  String SENTTOADMIN="vipulsinghraghuvanshi08@gmail.com";
    public  static  final  String MAILSUBJECTSELLER="Verify Seller";
    public  static  final  String MAILCONTENT="Seller Verification";

    public static final String MAILSUBJECTBUYER="Verify Buyer info";



    // seller verification
    public  static final String SELLERINFO="Seller info received";
    public  static final String SELLERVERIFIED=" Seller is verified";
    public static  final String SELLERNOTVERIFIED="Seller not verified ";
    public static final String SELLERVERIFICATIONSTATUS="Seller verification status sent";


    //buyer verification


    public  static final String BUYERINFO="Buyer info received";
    public  static final String BUYERVERIFIED=" Buyer is verified";
    public static  final String BUYERNOTVERIFIED="Buyer not verified ";
    public static final String CHANGE_PASSWORD = "Change Password";
 public static final String BUYERVERIFICATIONSTATUS="Buyer verification status sent";
    //path
    public  static  final String PATH="https://buysell-backend-huex-urtjok3rza-wl.a.run.app";




    //email to user order confirmation
    public  static  final String MAILSUBJECTORDERCONFIRMATION="ORDER CONFIRMATION";
    public  static  final  String MAILCONTENTORDERCONFIRMATION="Order confirmation of user";



}

// <li> <a href=${h}>${h_index+1} link for image  </a> </li>
