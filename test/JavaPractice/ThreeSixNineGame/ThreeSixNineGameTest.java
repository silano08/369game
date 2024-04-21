package JavaPractice.ThreeSixNineGame;

import JavaPractice.ThreeSixNineGame.Rule.Busan369;
import JavaPractice.ThreeSixNineGame.Rule.ClapRule;
import JavaPractice.ThreeSixNineGame.Rule.Seoul369;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ThreeSixNineGameTest {

    @Test
    public void testGameEndsAtThirteen() {
        Player[] players = { new Player("상철", 0.1), new Player("영철", 0.1), new Player("영숙", 0.1), new Player("동칠", 0.1) };
        Context context = new Context();
        context.set369Rule(new Seoul369());
        when(context.do369(13)).thenReturn("clap");
        RandomProvider mockRandomProvider = mock(RandomProvider.class);
        when(mockRandomProvider.getRandom()).thenReturn(0.05);

        ThreeSixNineGame game = new ThreeSixNineGame(mockRandomProvider,context);
        game.playGame(players);

        verify(context, atLeastOnce()).do369(13);
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