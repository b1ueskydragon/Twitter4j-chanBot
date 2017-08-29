package jp.tools;

import java.util.ArrayList;
import java.util.List;

public class Sort {

  public static void swap(int[] array, int ndx, int mdx) {
    int tmp = array[ndx];
    array[ndx] = array[mdx];
    array[mdx] = tmp;
  }

  public static int[] quickSort(int[] array, int leftdx, int rightdx) {

    int c_left = leftdx;
    int c_right = rightdx;
    int centerValue = array[(c_left + c_right) / 2];

    while (true) {

      while (array[c_left] < centerValue) c_left++;
      while (array[c_right] > centerValue) c_right--;

      if (c_left <= c_right) swap(array, c_left++, c_right--);

      if (c_left > c_right) break;

    }
    if (leftdx < c_right) quickSort(array, leftdx, c_right);
    if (c_left < rightdx) quickSort(array, c_left, rightdx);

    return array;
  }

  // TODO REFACTOR (いらない if-else condition ないか確認)
  public static String arrayFilter(String target) {

    String resultMessage = "";
    List<String> targetList = new ArrayList<>();
    // ソート かつ [ かつ ] が含まれたらソート開始
    try {
 //     if (target.contains("ソート")) {
        if (target.contains("[") && target.contains("]")) {
          for (char t : target.toCharArray()) {
            targetList.add(String.valueOf(t));
          }
        } else {
          resultMessage = "ソートできないよ！";
        }

        // [ から ] の間の取り出し
        int startIdx = targetList.indexOf("[");
        int endIdx = targetList.indexOf("]");
        List<String> targetNumList = new ArrayList<>();
        for (int i = startIdx; i <= endIdx; i++) {
          targetNumList.add(targetList.get(i));
        }

        // 詰め直し
        String[] targetNumArray = new String[targetNumList.size()];
        int index = 0;
        for (int i = 0; i < targetNumList.size(); i++) {
          targetNumArray[index] = targetNumList.get(i);
          index += 1;
        }

        String beforeSplit = "";
        for (int i = 1; i < targetNumArray.length - 1; i++) {
          beforeSplit += targetNumArray[i];
        }

        //String[] afterSplitArray = beforeSplit.split(",");
        String[] afterSplitArray = beforeSplit.split(" "); // なぜか , だと読み込めないのでとりあえず
        int[] resultTargetArray = new int[afterSplitArray.length];
        int idx = 0;
        for (int i = 0; i < afterSplitArray.length; i++) {
          resultTargetArray[idx] = Integer.parseInt(afterSplitArray[i]);
          idx += 1;
        }

        quickSort(resultTargetArray, 0, resultTargetArray.length - 1);

        for (int i = 0; i < resultTargetArray.length; i++) {
            resultMessage += resultTargetArray[i] + ",";
          //resultMessage += resultTargetArray[i] + " "; // 書き出しは , 大丈夫だけど、統一性のために
        }
        resultMessage = "[" + resultMessage.substring(0, resultMessage.length() - 1) + "]" + "かな！";

//      } else {
//        resultMessage = "ソートできないよ！";
//      }

      return resultMessage;

    } catch (Exception e) {
        resultMessage = "ソートできないよ！";
        return resultMessage;
    }
  }


//  // テスト
//  public static void main(String[] args) {
//    String target = "これをソートして3,4,5,2,1,23]";
//    String target2 = "これをソートして[aaa]";
//    String target3 = "ソート[3 32 -2]";
//    System.out.println(arrayFilter(target));
//    System.out.println(arrayFilter(target2));
//    System.out.println(arrayFilter(target3));
//  }
}
