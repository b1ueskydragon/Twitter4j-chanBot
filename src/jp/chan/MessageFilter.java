package jp.chan;

import jp.keys.Keys;
import jp.tools.Now;
import jp.tools.RandomPick;
import jp.tools.Sort;
import jp.tools.Tenki;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;

public class MessageFilter {
    private static final String NOW = Now.whatTimeIsIt();

    public String makeReply(String targetMess) {
        String botReply;
        setPicFlag(false);
        setByeFlag(false);

        if (targetMess.contains("いなごん") && !targetMess.contains("絵文字")) {
            botReply = "いなごんだよ！";
            setPicFlag(true);
        } else if (targetMess.contains("いなごん") && targetMess.contains("絵文字")){
            botReply = getInagonMoji();
        } else if (targetMess.contains("ソート")) {
            botReply = Sort.arrayFilter(targetMess);
        } else if (targetMess.contains("選んでちゃんbot")) {
            botReply = RandomPick.pickUpChanBot(targetMess);
        } else if (targetMess.contains("どこ") && targetMess.contains("行")) {
            botReply = "アップルストア";
        } else if (targetMess.contains("ねむ") || targetMess.contains("眠い") || targetMess.contains("寝")) {
            botReply = "睡眠をとるのはどうですか";
        } else if (targetMess.contains("天気は")) {
            botReply = Tenki.tenki(targetMess);
        } else if (targetMess.contains("時間") || targetMess.contains("何時")) {
            botReply = NOW + " だよ";
        } else if (targetMess.contains("ありがとう") && targetMess.contains("bot")) {
            botReply = "どーいたしましてー";
        } else if (targetMess.contains("おやすみ")) {
            botReply = "zzzZzzZ...";
        } else if (targetMess.contains("ちゃんbot") && targetMess.contains("バイバイ")) {
            botReply = "またね！";
            setByeFlag(true);
        } else {
           // botReply = inputHere();
            botReply = "( ´・ω・` )";
        }
        return botReply;
    }

    // 自由投稿
    public static String inputHere() {
        String freeReply;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("直接書いて");
                freeReply = sc.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("再入力");
                continue;
            }
        }
        sc.close();
        return freeReply + " (中の人) ";
    }

    public static String getInagonMoji(){
        return
 "　　　　　　　　\n" +
     "　　__　　___\n" +
     "　　\\　\\__\\　\\\n" +
     "____\\　　　　|\n" +
     "\\　　　|  |　|\n" +
     "　\\_____　　　\n" +
     "　　　　/　　　|\n" +
     "　　　　　　　　　";
    }

    public static StatusUpdate getInagon(StatusUpdate statusUpdate) throws TwitterException {
        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(Keys.INAGON_PIC.toString());
        File file = path.toFile();
        statusUpdate.setMedia(file);
        return statusUpdate;
    }

    private Boolean picFlag;
    public Boolean getPicFlag() {
        return picFlag;
    }
    public void setPicFlag(Boolean picFlag) {
        this.picFlag = picFlag;
    }

    private Boolean byeFlag;
    public Boolean getByeFlag() {
        return byeFlag;
    }
    public void setByeFlag(Boolean byeFlag) {
        this.byeFlag = byeFlag;
    }
}
