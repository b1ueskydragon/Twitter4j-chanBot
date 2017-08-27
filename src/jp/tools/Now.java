package jp.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Now {

    public static String whatTimeIsIt() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        String formatedDate = format.format(new Date());

        return  formatedDate.replace(":","時") + "分";
    }
}
