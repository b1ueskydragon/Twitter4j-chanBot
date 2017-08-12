package jp.chan;


import jp.keys.Keys;
import jp.tools.Now;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ijrd1
 *
 * https://groups.google.com/forum/#!topic/twitter4j-j/lzctj0PDThU 参考
 * http://twitter4j.org/ja/configuration.html できれば
 */
public class ChanBotForReply {

    private static final String FROMBOT = " --- ちゃんbotより";
    private static String NOW = Now.whatTimeIsIt();

    public static void main(String[] args) {

        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(Keys.CONSUMERKEY.toString(), Keys.CONSUMERSECRET.toString());
        twitter.setOAuthAccessToken(new AccessToken(Keys.ACCESSTOKEN.toString(), Keys.ACCESSTOKENSECRET.toString()));

        try {

            // TODO 一定時間繰り返してID取得 -> 新たなつぶやきIDだと判断したら返事する

            // timeline (mentioned) 取得
            ResponseList<Status> statuses = twitter.getUserTimeline();
            List<String> idList = new ArrayList<>(); // ターゲットさんのつぶやきID
            List<String> messList = new ArrayList<>(); // ターゲットさんがつぶやいた内容
            // idの数字のところだけ取得
            for (Status s : statuses) {
                String[] statusArray = s.toString().split(",");
                idList.add(statusArray[1].trim().substring(3));
                messList.add(statusArray[2].trim().substring(5));
            }

            // 一番最新のid取得
            BigInteger target = new BigInteger(idList.get(0));
            System.out.println(idList.get(0) + "::" + messList.get(0));

            String annoTarget = Keys.ME.toString();

            int idx = 0;
            String targetMess = messList.get(idx);
            String message = "...";
            if (targetMess.contains("どこ") && targetMess.contains("行")) {
                message = "スタバ！";
            } else if (targetMess.contains("ねむ") || targetMess.contains("眠い") || targetMess.contains("寝")) {
                message = "睡眠をとるのはどうですか";
            } else {
                message = "(・ω・)";
            }

            twitter.updateStatus(new StatusUpdate("@" + annoTarget + " " + message + FROMBOT)
                    .inReplyToStatusId(target.longValue()));

            System.out.println("yes ok !");

        } catch (TwitterException e) {
            System.out.println("no !" + e.getMessage());
        }

    }
}
