package com.imb.swat.helper;

import java.net.HttpURLConnection;

/**
 * Created by marcelsantoso.
 * <p/>
 * 6/12/15
 */
public class Constants {
    public static final String GET                   = "get";
    public static final String POST                  = "post";
    public static final int    TIMEOUT               = 15000;
    public static final int    STATUS_SUCCESS        = 200;
    public static final int    STATUS_BAD_REQUEST    = 400;
    public static final int    STATUS_NOT_FOUND      = 404;
    public static final int    STATUS_TIMEOUT        = HttpURLConnection.HTTP_CLIENT_TIMEOUT;
    public static final int    STATUS_NO_DATA        = HttpURLConnection.HTTP_NO_CONTENT;
    public static final int    STATUS_NO_CONNECTION  = HttpURLConnection.HTTP_NOT_ACCEPTABLE;
    public static final int    MAX_IMAGE_SIZE        = 720;
    public static final int    THUMBNAIL_SIZE        = 400;
    public static final String MIME_JPEG             = "image/JPEG";
    public static final String MIME_PNG              = "image/PNG";
    public static final String MIME_CSV              = "text/csv";
    public static final String TEMP_PHOTO_FILE       = "tmp.JPEG";
    public static final String DATE_JSON             = "yyyy-MM-dd";
    public static final String DATE_JSON_FULL        = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_YMD              = "yyyy-MM-dd";
    public static final String DATE_MD               = "dd MMM";
    public static final String DATE_MDY              = DATE_MD + ", yyyy";
    public static final String DATE_EDMY             = "EE, dd MMMM yy";
    public static final String DATE_EDMYHMS          = "EE, dd MMMM yyyy HH:mm:ss";
    public static final String DATE_HA               = "h a";
    public static final String DATE_HMA              = "h mm a";
    public static final String DATE_HM               = "HH:mm";
    public static final String DATE_YMDHIS           = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_YMDHIS_GMT       = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FACEBOOK         = "MM/dd/yyyy";
    public static final String YES                   = "Y";
    public static final String NO                    = "N";
    public static final String PLAY_STORE_LINK       = "https://play.google.com/store/apps/details?id=";
    public static final float  PREVIEW_SIZE          = 250;
    // ================================================================================
    // Keys
    // ================================================================================
    public static final String SECRET                = "secret";
    public static final String CLIENT_SECRET         = "client_secret";
    public static final String SOURCE                = "source";
    public static final String RESULTS               = "results";
    public static final String STATUS_CODE           = "status_code";
    public static final String KEY                   = "key";
    public static final String NAME                  = "name";
    public static final String FILEPATH              = "filepath";
    public static final String MESSAGE               = "message";
    public static final String ID                    = "id";
    public static final String ACC_TOKEN             = "access_token";
    public static final String ACC_ID                = "account_id";
    public static final String IS_VERIFIED           = "is_verified";
    public static final String ROLE                  = "role";
    public static final String ROLE_ID               = "role_id";
    public static final String TITLE                 = "title";
    public static final String VALUE                 = "value";
    public static final String IMAGE                 = "image";
    public static final String USERNAME              = "username";
    public static final String PASSWORD              = "password";
    public static final String TAB                   = "tab";
    public static final String DEVICE_TYPE           = "device_type";
    public static final String DEVICE_TOKEN          = "device_token";
    public static final String LOCATION_NAME         = "location_name";
    public static final String LATITUDE              = "latitude";
    public static final String LONGITUDE             = "longitude";
    public static final String PAGE                  = "page";
    public static final String LIMIT                 = "limit";
    public static final String STARTED_AT            = "started_at";
    public static final String ENDED_AT              = "ended_at";
    public static final String TOTAL                 = "total";
    public static final String FOLDER                = "folder";
    public static final String PHOTO                 = "photo";
    public static final String CONTACT               = "contact";
    public static final String EMAIL                 = "email";
    public static final String SEARCH                = "search";
    public static final String REMARKS               = "remarks";
    public static final String HOURS                 = "hours";
    public static final String ACCOUNTS              = "accounts";
    public static final String NOTIF_TYPE_ID         = "notification_type_id";
    public static final String REF_ID                = "ref_id";
    public static final String DESC                  = "desc";
    public static final String OPENED_AT             = "opened_at";
    public static final String CREATED_AT            = "created_at";
    public static final String NOTIFICATION_ID       = "notification_id";
    public static final String DATE                  = "date";
    public static final String DATE_                 = "Date";
    public static final String LOCATION_ID           = "location_id";
    public static final String FROM_TIME             = "from_time";
    public static final String TO_TIME               = "to_time";
    public static final String LOCATION              = "location";
    public static final String NEW_PASS              = "new_password";
    public static final String OLD_PASS              = "old_password";
    public static final String MIME                  = "mime";
    public static final String SMALL_IMAGE_FOLDER    = "s";
    public static final String MEDIUM_IMAGE_FOLDER   = "m";
    public static final String BIG_IMAGE_FOLDER      = "b";
    public static final String ORIGINAL_IMAGE_FOLDER = "o";
    public static final String STATUS_MESSAGE        = "status_message";
    public static final String FACEBOOK_ID           = "id";
    public static final String FACEBOOK_NAME         = "name";
    public static final String FACEBOOK_FIRST_NAME   = "first_name";
    public static final String FACEBOOK_LAST_NAME    = "last_name";
    public static final String FACEBOOK_EMAIL        = "email";
    public static final String FACEBOOK_GENDER       = "gender";
    public static final String FACEBOOK_MALE         = "male";
    public static final String FACEBOOK_FEMALE       = "female";
    public static final String FACEBOOK_BDAY         = "birthday";
    public static final String FACEBOOK_PICTURE      = "picture";
    public static final String TYPE                  = "type";
    public static final String LARGE                 = "large";
    // ================================================================================
    // Webservice
    // ================================================================================
    public static final String EXACT                 = "exact";
    public static final String START                 = "start";
    public static final String END                   = "end";
    public static final String ANYWHERE              = "anywhere";
}
