package com.example.food.constant;

public interface TemplateEmail {
    interface LOGIN {
        public static final String SUBJECT = "Đăng nhập thành công";
        public static final String TEMPLATE_NAME = "template_login.ftl";
        public static final String USERNAME = "USERNAME";
        public static final String PASSWORD = "PASSWORD";
        public static final String DATE_TIME = "DATE_TIME";
    }

    interface SIGNUP {
        public static final String SUBJECT = "Đăng ký tài khoản thành công, vui lòng xác thực email";
        public static final String TEMPLATE_NAME = "template_signup.ftl";
        public static final String TOKEN = "TOKEN";
        public static final String USERNAME = "USERNAME";
        public static final String PASSWORD = "PASSWORD";
        public static final String FIRST_NAME = "FIRST_NAME";
        public static final String LAST_NAME = "LAST_NAME";
        public static final String EMAIL = "EMAIL";
        public static final String ADDRESS = "ADDRESS";
        public static final String PHONE = "PHONE";
        public static final String DATE_TIME = "DATE_TIME";
    }

    interface VERIFY_EMAIL {
        public static final String SUBJECT = "Chúc mừng, bạn đã xác thực email thành công";
        public static final String TEMPLATE_NAME = "template_verify_email.ftl";
        public static final String USERNAME = "USERNAME";
        public static final String FIRST_NAME = "FIRST_NAME";
        public static final String LAST_NAME = "LAST_NAME";
        public static final String EMAIL = "EMAIL";
        public static final String ADDRESS = "ADDRESS";
        public static final String PHONE = "PHONE";
        public static final String DATE_TIME = "DATE_TIME";
    }

    interface RESET_PASSWORD_BY_ADMIN {
        public static final String SUBJECT = "Tài khoản của bạn vừa được quản trị hệ thống đặt lại mật khẩu.";
        public static final String TEMPLATE_NAME = "template_reset_password_by_admin.ftl";
        public static final String USERNAME = "USERNAME";
        public static final String PASSWORD = "PASSWORD";
        public static final String DATE_TIME = "DATE_TIME";
    }

    interface OTP_RESET_PASSWORD_BY_USER {
        public static final String SUBJECT = "Mã OTP dùng để đặt lại mật khẩu.";
        public static final String TEMPLATE_NAME = "template_otp_reset_password_by_user.ftl";
        public static final String USERNAME = "USERNAME";
        public static final String OTP = "OTP";
        public static final String EXPIRY_TIME = "EXPIRY_TIME";
        public static final String DATE_TIME = "DATE_TIME";
    }

    interface RESET_PASSWORD_BY_USER_SUCCESS {
        public static final String SUBJECT = "Bạn đã đặt lại mật khẩu thành công.";
        public static final String TEMPLATE_NAME = "template_reset_password_by_user_success.ftl";
        public static final String USERNAME = "USERNAME";
        public static final String PASSWORD = "PASSWORD";
        public static final String DATE_TIME = "DATE_TIME";
    }

    interface CHANGE_PASSWORD_BY_USER_SUCCESS {
        public static final String SUBJECT = "Bạn đã thay đổi mật khẩu thành công.";
        public static final String TEMPLATE_NAME = "template_change_password_by_user_success.ftl";
        public static final String USERNAME = "USERNAME";
        public static final String PASSWORD_OLD = "PASSWORD_OLD";
        public static final String PASSWORD_NEW = "PASSWORD_NEW";
        public static final String DATE_TIME = "DATE_TIME";
    }
}
