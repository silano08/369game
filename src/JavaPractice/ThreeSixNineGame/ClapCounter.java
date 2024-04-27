package JavaPractice.ThreeSixNineGame;

public class ClapCounter {
    // 박수를 친 횟수를 가지고 있는 클래스 만들기
    private int clapTotalCount = 0;

    /**
     박수를 친 횟수를 출력하는 메소드 만들기
     결과를 sout 으로 화면에 출력해주세요.
     */
    void printClapCount() {
        System.out.println("모든 game의 박수 횟수:" + clapTotalCount);
    }

    synchronized void countClap(String clap) {
        clapTotalCount += (clap.length() / 4) ;
    }
}
