package jp.chan;

import jp.keys.Keys;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ChanBotForReplyTrue {

    private static final String FROMBOT = " --- ちゃんbotより";

    public static void main(String[] args) {

        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(Keys.CONSUMERKEY.toString(), Keys.CONSUMERSECRET.toString());
        twitter.setOAuthAccessToken(new AccessToken(Keys.ACCESSTOKEN.toString(), Keys.ACCESSTOKENSECRET.toString()));

        while (true) {
            try {
                /**
                 * 自分とのやりとり
                 */
                ResponseList<Status> statuses = twitter.getUserTimeline(); // 全タイムライン読み込み
                List<String> idList = new ArrayList<>(); // ターゲットさんのつぶやきID
                List<String> messList = new ArrayList<>(); // ターゲットさんがつぶやいた内容

                StatusFilter(statuses, idList, messList);

                BigInteger target = new BigInteger(idList.get(0)); // 一番最新のid取得
                System.out.println(target + " :: " + messList.get(0));

                /**
                 * Who's the target, ME or not.
                 */
                String annoTarget =
                        messList.get(0).substring(1, 2).equals("@")
                                ? messList.get(0).split(" ")[0].substring(2) : Keys.ME.toString();

                // TODO メンション -> 自つぶやき -> bot で返信する時、annoTarget 再設定問題 (行ナンバーずらし)

                /**
                 * target is not ME
                 */
                if (!annoTarget.equals(Keys.ME.toString())) {
                    // 変数入れ替え
                    statuses = twitter.getMentionsTimeline(); // メンションのみ読み込み
                    idList = new ArrayList<>();
                    messList = new ArrayList<>();

                    StatusFilter(statuses, idList, messList);

                    target = new BigInteger(idList.get(0));
                    System.out.println(target + " :: " + messList.get(0));
                }

                int idx = 0;
                String targetMess = messList.get(idx);
                MessageFilter messageFilter = new MessageFilter();
                String botReply = messageFilter.makeReply(targetMess);

                if (targetMess.contains(FROMBOT)) {
                    System.out.println("まだかなー？");
                    Thread.sleep(10000);
                } else {
                    System.out.println("きたきたー！");
                    // 更新される内容をセット
                    StatusUpdate statusUpdate = new StatusUpdate("@" + annoTarget + " " + botReply + FROMBOT);
                    // 画像がある場合
                    if (messageFilter.getPicFlag()) MessageFilter.getInagon(statusUpdate);
                    // 更新
                    twitter.updateStatus(statusUpdate.inReplyToStatusId(target.longValue()));

                    System.out.println("yes ok !");
                    Thread.sleep(20000);
                }

            } catch (TwitterException e) {
                System.out.println("no !" + e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 様々なステータスに対応する凡用フィルター
     *
     * @param statuses Twitter Statuses のうちどれか
     * @param idList  長い桁の ID リスト
     * @param messList ID ごとのメッセージリスト
     */
    private static void StatusFilter(ResponseList<Status> statuses, List<String> idList, List<String> messList)
    {
        for (Status s : statuses) {
            String[] statusArray = s.toString().split(",");
            idList.add(statusArray[1].trim().substring(3));
            messList.add(statusArray[2].trim().substring(5));
        }
    }


}
