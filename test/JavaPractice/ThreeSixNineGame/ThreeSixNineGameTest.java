package JavaPractice.ThreeSixNineGame;

import JavaPractice.ThreeSixNineGame.Rule.Busan369;
import JavaPractice.ThreeSixNineGame.Rule.Seoul369;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreeSixNineGameTest {

    @Test
    void playGame() {
    }

    @Test
    void ruleChangeTest(){

        Context context = new Context();

        // 서울의 룰로하다가

        context.set369Rule(new Seoul369());
        assertEquals("clap", context.do369(33));
        assertEquals("clap", context.do369(369));

        // 부산의 룰로 체인지

        context.set369Rule(new Busan369());
        assertEquals("clapclapclap", context.do369(369));
        assertEquals("clapclap", context.do369(33));

    }

}