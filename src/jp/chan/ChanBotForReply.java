package jp.chan;


import jp.keys.Keys;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by b1ueskydragon
 */
public class ChanBotForReply {

    // TODO 一定時間繰り返してID取得 -> 新たなつぶやきIDだと判断したら返事する
    private static final String FROMBOT = " --- ちゃんbotより";

    public static void main(String[] args) {

        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(Keys.CONSUMERKEY.toString(), Keys.CONSUMERSECRET.toString());
        twitter.setOAuthAccessToken(new AccessToken(Keys.ACCESSTOKEN.toString(), Keys.ACCESSTOKENSECRET.toString()));

        try {
            /**
             * 自分とのやりとり
             */
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


            /**
             * Who's the target, ME or not.
             */
            String annoTarget =
                    messList.get(0).substring(1,2).equals("@")
                            ? messList.get(0).split(" ")[0].substring(2): Keys.ME.toString();

            /**
             * target is not ME
             */
            // TODO Refactor
            ResponseList<Status> statuses_ = twitter.getMentionsTimeline();
            List<String> idList_ = new ArrayList<>(); // ターゲットさんのつぶやきID
            List<String> messList_ = new ArrayList<>(); // ターゲットさんがつぶやいた内容
            // idの数字のところだけ取得
            for (Status s : statuses_) {
                String[] statusArray = s.toString().split(",");
                idList_.add(statusArray[1].trim().substring(3));
                messList_.add(statusArray[2].trim().substring(5));
            }

            if (!annoTarget.equals(Keys.ME.toString())) {
                target = new BigInteger(idList_.get(0));
                System.out.println(target);
                System.out.println(target + "::" + messList_.get(0));
            }

            int idx = 0;
            String targetMess = messList.get(idx);
            String botReply = MessageFilter.makeReply(targetMess);

            twitter.updateStatus(new StatusUpdate("@" + annoTarget + " " + botReply + FROMBOT)
                    .inReplyToStatusId(target.longValue()));

            System.out.println("yes ok !");

        } catch (TwitterException e) {
            System.out.println("no !" + e.getMessage());
        }
    }
}
