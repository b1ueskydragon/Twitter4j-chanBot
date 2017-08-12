package jp.sample;

import jp.keys.Keys;
import jp.tools.Now;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 *  参考 http://gomyownway.hatenablog.com/entry/2012/08/05/151936
 */
public class BotSample {

    private static final String chanBot = " - ちゃんbotより"; // TODO 改行コード

    public static void main(String[] args){
        //Twitterオブジェクトを参照する。Factoryパターンだ！
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Keys.CONSUMERKEY.toString(), Keys.CONSUMERSECRET.toString());
        twitter.setOAuthAccessToken(new AccessToken(Keys.ACCESSTOKEN.toString(), Keys.ACCESSTOKENSECRET.toString()));
        try{
            String msg = "今日もいい一日!";
            twitter.updateStatus(msg + "" + Now.whatTimeIsIt() + chanBot);
            System.out.println("ok");
        } catch(TwitterException e){
            System.err.println("no"+e.getMessage());
        }

    }
}
