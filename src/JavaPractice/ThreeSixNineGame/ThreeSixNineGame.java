package JavaPractice.ThreeSixNineGame;

import JavaPractice.ThreeSixNineGame.Rule.Busan369;
import JavaPractice.ThreeSixNineGame.Rule.ClapRule;
import JavaPractice.ThreeSixNineGame.Rule.Seoul369;

public class ThreeSixNineGame {


    private RandomProvider randomProvider;
    private Context clapRule;

    public ThreeSixNineGame(RandomProvider randomProvider, Context clapRule) {
        this.randomProvider = randomProvider;
        this.clapRule = clapRule;
    }

    /**
     * 게임에 참가하는 플레이어의 이름 배열을 받아서 게임을 진행.
     * 결과를 System.out.println 메소드로 화면에 출력해주세요.
     * 정확히 100회의 게임이 진행 되도록 해주세요.
     * 예제. "영수", "광수", "영철", "상철" 이 입력된경우
     * 영수: 1
     * 광수: 2
     * 영철: clap
     * 상철: 4
     * ..중략..
     * 상철: 100
     */
    public void playGame(Player[] players, ClapCounter clapCounter) {
        int i = 1;
        while (true) {
            // 오답률에 의한 랜덤확률 생성
            // 0또는 1이 나오는기준에 오답률을 반영해라
            double rate = randomProvider.getRandom(); // 3 34 83
            String result = clapRule.do369(i);
            if (rate < players[(i - 1) % 4].getIncorrectRate()) {
                // 오답을 말하는 경우
                if (result.equals(Integer.toString(i))) {
                    System.out.println(clapRule.getLocalName() + " - " + players[(i - 1) % 4].getName() + "님이 숫자를 얘기해야하는데 박수를 쳤습니다.");
                    break;
                } else {
                    System.out.println(clapRule.getLocalName() + " - " +players[(i - 1) % 4].getName() + "님이 박수를 쳐야하는데 숫자를 얘기했습니다.");
                    break;
                }
            } else {
                clapCounter.countClap(result);
                System.out.println(clapRule.getLocalName() + " - " +players[(i - 1) % 4].getName() + ": " + result);
            }
            i += 1;
        }
    }

    public static void main(String[] args) {
        Player[] players = new Player[4];
        players[0] = new Player("상철", 0.01);
        players[1] = new Player("영철", 0.01);
        players[2] = new Player("영숙", 0.0021);
        players[3] = new Player("동칠", 0.001);

        ClapCounter clapCounter = new ClapCounter();

        // 부산의 룰로 play
        Context context1 = new Context();
        context1.set369Rule(new Busan369());
        ThreeSixNineGame game1 = new ThreeSixNineGame(new RandomProvider(), context1);

        // 서울의 룰로 play
        Context context2 = new Context();
        context2.set369Rule(new Seoul369());
        ThreeSixNineGame game2 = new ThreeSixNineGame(new RandomProvider(), context2);

        // 각 게임을 별도의 스레드에서 실행
        Thread thread1 = new Thread(() -> game1.playGame(players,clapCounter), "BusanGameThread");
        Thread thread2 = new Thread(() -> game2.playGame(players,clapCounter), "SeoulGameThread");

        // 스레드 시작
        thread1.start();
        thread2.start();

        // 모든 스레드가 종료될 때까지 대기
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Game interrupted: " + e.getMessage());
        }

        clapCounter.printClapCount();
    }

}
