package com.example.food.constant;

/*
 * Declare constants used in the project
 */
public interface Constant {
    public static String IMAGE_USER_DEFAULT = "https://firebasestorage.googleapis.com/v0/b/zingmp3-project.appspot.com/o/user_default.png?alt=media&token=ed94ea59-cd93-4171-8aa7-bf06227fa016";
    public static String IMAGE_MERCHANT_DEFAULT = "https://firebasestorage.googleapis.com/v0/b/zingmp3-project.appspot.com/o/user_default.png?alt=media&token=ed94ea59-cd93-4171-8aa7-bf06227fa016";

    //Declare TABLE used in the DATABASE
    interface TABLE {
        public static final String USER = "user";
        public static final String ROLE = "role";
    }

    public enum UserStatus {
        ACTIVATE,
        INACTIVATE
    }
    public enum MerchantStatus {
        ACTIVATE,
        INACTIVATE,
        VIP
    }

    public enum FoodStatus {
        SERVING,
        STOP,
    }

    public enum ChannelName {
        ADMIN,
        MERCHANT,
        USER,
    }

    public enum TypeName {
        QUICKSEARCH,
        BREAKFAST,
        LUNCH,
        DINNER
    }
    public enum OrderStatus{
        PENDING,
        PROCESS,
        ACCEPTED,
        REJECT
    }
}
