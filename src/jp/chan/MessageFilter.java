package jp.chan;

import jp.tools.Now;
import jp.tools.Tenki;

import java.util.Scanner;

public class MessageFilter {

    private static final String NOW = Now.whatTimeIsIt();

     //TODO reply パターン追加
    public static String makeReply(String targetMess) {

        String botReply;

        if (targetMess.contains("どこ") && targetMess.contains("行")) {
            botReply = "アップルストア";
        } else if (targetMess.contains("ねむ") || targetMess.contains("眠い") || targetMess.contains("寝")) {
            botReply = "睡眠をとるのはどうですか";
        } else if (targetMess.contains("天気は")) {
            botReply = Tenki.tenki(targetMess);
        } else if (targetMess.contains("時間") || targetMess.contains("何時")) {
            botReply = NOW + " だよ";
        } else {
            botReply = inputHere();
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

        return freeReply;
    }
}
