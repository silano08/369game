package JavaPractice.ThreeSixNineGame.Rule;

// 전략 알고리즘 B
public class Busan369 implements ClapRule {

    @Override
    public String do369(int number) {
        String numStr = Integer.toString(number);

        String result = "";
        for (int i = 0; i < numStr.length(); i++) {
            char c = numStr.charAt(i);
            if (c == '3' || c == '6' || c == '9') {
                result += "clap";
            }
        }

        // '3', '6', '9' 가 한번도 포함되지 않았다면, 숫자를 문자열로 반환
        if (result.isEmpty()) {
            return numStr;
        }
        return result;
    }

    @Override
    public String getLocalName() {
        return "부산";
    }
}
