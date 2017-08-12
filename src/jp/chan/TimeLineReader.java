package jp.chan;


import jp.keys.Keys;
import twitter4j.*;
import twitter4j.auth.AccessToken;

public class TimeLineReader {

    public static void main(String [] args) {

        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(Keys.CONSUMERKEY.toString(), Keys.CONSUMERSECRET.toString());
        twitter.setOAuthAccessToken(new AccessToken(Keys.ACCESSTOKEN.toString(), Keys.ACCESSTOKENSECRET.toString()));

        try {
            ResponseList<Status> statuses = twitter.getUserTimeline();

            for(Status s : statuses) {
                String [] statusArray = s.toString().split(",");
                System.out.println(statusArray[1].trim().substring(3)); // つぶやきのid
                System.out.println(statusArray[2].trim().substring(5)); // つぶやきの内容
            }

        } catch (TwitterException e) {
            System.err.println(e.getMessage());
        }
    }

}
