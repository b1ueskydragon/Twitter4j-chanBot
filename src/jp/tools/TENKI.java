package jp.tools;

import jp.keys.Keys;

public class TENKI {
    public static String tenki(String targetMess) {
        String day = "今日"; // default
        if (targetMess.contains("明日")) day = "明日"; // 単語分析・・?
        String TENKI_URL =
                Keys.TENKEY.toString();
        return day + "の天気を検索 " + TENKI_URL;
    }
}