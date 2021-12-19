package com.app.vault.utils;

public class Constants {

    public static final String CAPITAL_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String SMALL_ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMBERS = "0123456789";
    public static final String SYMBOLS = "!@#$%&*()-+[{}]/|";

    public static final String CHARACTERS_STRING = CAPITAL_ALPHABETS + SMALL_ALPHABETS + NUMBERS + SYMBOLS;

    public static final int TAG_PASSWORDS_ALL = 1000;
    public static final int TAG_PASSWORDS_GOOGLE_ACCOUNT = 1001;
    public static final int TAG_PASSWORDS_SECURITY_PIN = 1002;
    public static final int TAG_PASSWORDS_BUSINESS = 1003;
    public static final int TAG_PASSWORDS_WALLET = 1004;
    public static final int TAG_PASSWORDS_MASTER_PASSWORD = 1005;
    public static final int TAG_PASSWORDS_E_COMMERCE = 1006;
    public static final int TAG_PASSWORDS_FINANCE = 1007;
    public static final int TAG_PASSWORDS_SOCIAL_MEDIA = 1008;
    public static final int TAG_PASSWORDS_OTHER = 1009;

    public static final int TAG_DOCUMENTS_ALL = 1010;
    public static final int TAG_DOCUMENTS_PERSONAL = 1011;
    public static final int TAG_DOCUMENTS_GOVERNMENT = 1012;
    public static final int TAG_DOCUMENTS_EDUCATION = 1013;
    public static final int TAG_DOCUMENTS_PAYMENT_BILLS = 1014;
    public static final int TAG_DOCUMENTS_INSURANCE = 1015;
    public static final int TAG_DOCUMENTS_BUSINESS = 1016;
    public static final int TAG_DOCUMENTS_FINANCE = 1017;
    public static final int TAG_DOCUMENTS_MEDICAL = 1018;
    public static final int TAG_DOCUMENTS_OTHER = 1019;

    public static final String ACTIVITY_ACTION = "activity_action";
    public static final String ACTION_ADD = "action_add";
    public static final String ACTION_EDIT = "action_edit";


    //PASSWORDS FRAGMENT
    public static final String TABLE_NAME_PASSWORDS = "table_passwords";
    public static final String COL_PASSWORDS_ID = "column_passwords_id";
    public static final String COL_PASSWORDS_TITLE = "column_passwords_title";
    public static final String COL_PASSWORDS_DATE = "column_passwords_date";
    public static final String COL_PASSWORDS_WEBSITE_NAME = "column_passwords_website_name";
    public static final String COL_PASSWORDS_USER_NAME = "column_passwords_user_name";
    public static final String COL_PASSWORDS_EMAIL_ADDRESS = "column_passwords_email_address";
    public static final String COL_PASSWORDS_PHONE_NUMBER = "column_passwords_phone_number";
    public static final String COL_PASSWORDS_PASSWORD = "column_passwords_password";
    public static final String COL_PASSWORDS_TAG = "column_passwords_tags";
    public static final String COL_PASSWORDS_IS_IMPORTANT = "column_passwords_is_important";
    public static final String COL_PASSWORDS_IS_HIDDEN = "column_passwords_is_hidden";

    //CARDS FRAGMENT
    public static final String TABLE_NAME_CARDS = "table_cards";
    public static final String COL_CARDS_ID = "column_cards_id";
    public static final String COL_CARDS_TITLE = "column_cards_title";
    public static final String COL_CARDS_DATE = "column_cards_date";
    public static final String COL_CARDS_FIRST_NAME = "column_cards_first_name";
    public static final String COL_CARDS_LAST_NAME = "column_cards_last_name";
    public static final String COL_CARDS_CARD_NUMBER = "column_cards_card_number";
    public static final String COL_CARDS_EXPIRY_DATE = "column_cards_expiry_date";
    public static final String COL_CARDS_CARD_CVV = "column_cards_card_cvv";
    public static final String COL_CARDS_BANK_NAME = "column_cards_bank_name";
    public static final String COL_CARDS_TAG = "column_cards_tags";
    public static final String COL_CARDS_IS_IMPORTANT = "column_cards_is_important";
    public static final String COL_CARDS_IS_HIDDEN = "column_cards_is_hidden";

    //DOCUMENTS FRAGMENT
    public static final String TABLE_NAME_DOCUMENTS = "table_documents";
    public static final String COL_DOCUMENTS_ID = "column_documents_id";
    public static final String COL_DOCUMENTS_TITLE = "column_documents_title";
    public static final String COL_DOCUMENTS_DATE = "column_documents_date";
    public static final String COL_DOCUMENTS_FILES = "column_documents_files";
    public static final String COL_DOCUMENTS_META_DATA = "column_documents_meta_data";
    public static final String COL_DOCUMENTS_TAG = "column_documents_tags";
    public static final String COL_DOCUMENTS_IS_IMPORTANT = "column_documents_is_important";
    public static final String COL_DOCUMENTS_IS_HIDDEN = "column_documents_is_hidden";
}
