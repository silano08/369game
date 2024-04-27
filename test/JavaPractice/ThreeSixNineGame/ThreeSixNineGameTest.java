package JavaPractice.ThreeSixNineGame;

import JavaPractice.ThreeSixNineGame.Rule.Busan369;
import JavaPractice.ThreeSixNineGame.Rule.ClapRule;
import JavaPractice.ThreeSixNineGame.Rule.Seoul369;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ThreeSixNineGameTest {

    @Test
    void testPlayGameCompletes100Rounds() {
        // 플레이어 배열 초기화
        Player[] players = {
                new Player("상철", 0.1),
                new Player("영철", 0.1),
                new Player("영숙", 0.1),
                new Player("동칠", 0.01)
        };

        // RandomProvider 및 Context 모킹
        RandomProvider mockRandomProvider = mock(RandomProvider.class);
        Context mockContext = mock(Context.class);

        // RandomProvider 및 ClapRule 동작 정의
        when(mockRandomProvider.getRandom()).thenReturn(0.1);  // 오답률을 초과하지 않도록 일관되게 반환
        when(mockContext.do369(anyInt())).thenAnswer(invocation -> {
            Integer num = invocation.getArgument(0, Integer.class);
            return (num % 3 == 0) ? "clap" : num.toString();
        });

        // ThreeSixNineGame 인스턴스 생성
        ThreeSixNineGame game = new ThreeSixNineGame(mockRandomProvider, mockContext);

        ClapCounter clapCounter = new ClapCounter();
        // 테스트 실행
        game.playGame(players,clapCounter);

        // do369 메서드 호출 검증
        verify(mockContext, times(100)).do369(anyInt());
        // 모든 플레이어가 게임을 완료했는지 확인
        verify(mockRandomProvider, times(100)).getRandom();
    }

    @Test
    void ruleChangeTest() {

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