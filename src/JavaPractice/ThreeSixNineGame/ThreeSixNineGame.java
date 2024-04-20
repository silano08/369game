package JavaPractice.ThreeSixNineGame;

import JavaPractice.ThreeSixNineGame.Rule.Busan369;
import JavaPractice.ThreeSixNineGame.Rule.ClapRule;
import JavaPractice.ThreeSixNineGame.Rule.Seoul369;

public class ThreeSixNineGame {

    /**
     number 에 3,6,9가 포함되면 "clap", 아니면 입력받은 숫자를 String으로 리턴
     do369(16) -> "clap"
     do369(12) -> "12"
     do369(33) -> "clap", 3이 두번나왔지만 clap은 한번
     */
    public String do369(int number) {
        String numStr = Integer.toString(number);

        if(numStr.contains("3") || numStr.contains("6") || numStr.contains("9")) return "clap";
        return numStr;
    }

    /**
     게임에 참가하는 플레이어의 이름 배열을 받아서 게임을 진행.
     결과를 System.out.println 메소드로 화면에 출력해주세요.
     정확히 100회의 게임이 진행 되도록 해주세요.
     예제. "영수", "광수", "영철", "상철" 이 입력된경우
     영수: 1
     광수: 2
     영철: clap
     상철: 4
     ..중략..
     상철: 100
     */
    public void playGame(Player[] players, ClapRule rule) {
        int i=1;
        while (true){
            // 오답률에 의한 랜덤확률 생성
            // 0또는 1이 나오는기준에 오답률을 반영해라
            double rate = Math.random(); // 3 34 83
            String result = rule.do369(i);
            if(rate < players[(i-1)%4].getIncorrectRate()){
                // 오답을 말하는 경우
                if(result.equals(Integer.toString(i))) {
                    System.out.println(players[(i - 1) % 4].getName() + "님이 숫자를 얘기해야하는데 박수를 쳤습니다.");
                    break;
                }else{
                    System.out.println(players[(i - 1) % 4].getName() + "님이 박수를 쳐야하는데 숫자를 얘기했습니다.");
                    break;
                }
            }else{
                System.out.println(players[(i-1)%4].getName() + ": " + result);
            }
            i+=1;
        }
    }

    public static void main(String[] args) {
        Player[] players = new Player[4];
        players[0] = new Player("상철",0.1);
        players[1] = new Player("영철",0.1);
        players[2] = new Player("영숙",0.1);
        players[3] = new Player("동칠",0.01);

        Context context = new Context();

        context.set369RuleSeoul(new Busan369());

        new ThreeSixNineGame().playGame(players, context.rule);
    }
}
