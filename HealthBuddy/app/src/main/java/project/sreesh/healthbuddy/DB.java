package project.sreesh.healthbuddy;

import android.provider.BaseColumns;

public class DB {
    public DB()
    {

    }
    public static abstract class info implements BaseColumns
    {
        public static final String USER_NAME= "user_name" ;
        public static final String PASSWORD= "password" ;
        public static final String MAIL_ID= "mail";
        public static final String QUESTION= "question";
        public static final String ANSWER= "answer";
        public static final String DATABASE_NAME= "user_details" ;
        public static final String TABLE_NAME= "users" ;

        public static final String TABLE2_NAME= "hotel" ;
        public static final String HOTEL_LA= "hotel_la";
        public static final String HOTEL_LO= "hotel_lo" ;

        public static final String TABLE3_NAME= "cal" ;
        public static final String DATE= "date";
        public static final String CALB= "calb";
        public static final String CALE= "cale";
        public static final String CALC= "calc";
        public static final String CALT= "calt";

        public static final String TABLE4_NAME= "hotel" ;
        public static final String GYM_LA= "hotel_la";
        public static final String GYM_LO= "hotel_lo" ;

        public static final String TABLE5_NAME= "food" ;
        public static final String FOOD= "food";
        public static final String CALORIES= "calories" ;


    }

}
