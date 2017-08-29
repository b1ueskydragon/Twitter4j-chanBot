package jp.tools;

import com.sun.tools.corba.se.idl.StringGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPick {

    // TODO Refactoring..............
    public static String pickUpChanBot(String target) {

        List<String> targetList = new ArrayList<>();
        if (target.contains("[") && target.contains("]")) {
            for (char t : target.toCharArray()) {
                targetList.add(String.valueOf(t));
            }
        } else {
            return "選べないよ！";
        }

        // [ から ] の間の取り出し
        int startIdx = targetList.indexOf("[");
        int endIdx = targetList.indexOf("]");
        List<String> elementsList = new ArrayList<>();
        for (int i = startIdx ; i <= endIdx; i++) {
            elementsList.add(targetList.get(i));
        }

        // 詰め直し
        String[] splitList = new String[elementsList.size()];
        int index = 0;
        for (int i = 0; i < elementsList.size(); i++) {
            splitList[index] = elementsList.get(i);
            index += 1;
        }

        String beforeSplit = "";
        for (int i = 1; i < splitList.length - 1; i++) {
            beforeSplit += splitList[i];
        }

        //String[] afterSplitArray = beforeSplit.split(",");
        String[] afterSplitArray = beforeSplit.split(" "); // なぜか , だと読み込めないのでとりあえず
        String [] resultTargetArray = new String[afterSplitArray.length];
        int idx = 0;
        for (int i = 0; i < afterSplitArray.length; i++) {
            resultTargetArray[idx] = afterSplitArray[i];
            idx += 1;
        }

        int rdmIdx = new Random().nextInt(afterSplitArray.length);

        return "’ω’)/ " + afterSplitArray[rdmIdx];
    }

//    // テスト
//    public static void main(String [] args) {
//        // 選んでちゃんbot contains
//        String target = "選んでちゃんbot[チキン ブロッコリ ココア お米 半導体]";
//        System.out.println(pickUpChanBot(target));
//    }
}
