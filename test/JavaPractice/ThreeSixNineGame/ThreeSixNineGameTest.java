package JavaPractice.ThreeSixNineGame;

import JavaPractice.ThreeSixNineGame.Rule.Busan369;
import JavaPractice.ThreeSixNineGame.Rule.ClapRule;
import JavaPractice.ThreeSixNineGame.Rule.Seoul369;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


class ThreeSixNineGameTest {

    @Test
    public void testGameConcurrency() throws InterruptedException {
        // 플레이어와 카운터 초기화
        Player[] players = new Player[4];
        players[0] = new Player("상철", 0.01);
        players[1] = new Player("영철", 0.01);
        players[2] = new Player("영숙", 0.0021);
        players[3] = new Player("동칠", 0.001);
        ClapCounter clapCounter = new ClapCounter();

        // 래치 초기화 (2개의 게임 스레드를 기다림)
        CountDownLatch latch = new CountDownLatch(2);

        // 부산 및 서울 규칙 설정
        Context context1 = new Context();
        context1.set369Rule(new Busan369());
        ThreeSixNineGame game1 = new ThreeSixNineGame(new RandomProvider(), context1);

        Context context2 = new Context();
        context2.set369Rule(new Seoul369());
        ThreeSixNineGame game2 = new ThreeSixNineGame(new RandomProvider(), context2);

        // 게임 스레드 생성 및 시작
        Thread thread1 = new Thread(() -> {
            game1.playGame(players, clapCounter);
            latch.countDown();
        }, "BusanGameThread");

        Thread thread2 = new Thread(() -> {
            game2.playGame(players, clapCounter);
            latch.countDown();
        }, "SeoulGameThread");

        thread1.start();
        thread2.start();

        // 스레드가 완료될 때까지 최대 10초 대기
        assertTrue(latch.await(10, TimeUnit.SECONDS));

        // 박수 횟수 출력을 테스트하는 부분 (선택적)
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream()));
        clapCounter.printClapCount();
    }

    @Test
    public void testPlayerShouldClapButSaysNumber() {
        // 1. 플레이어가 박수를 쳐야 하지만 숫자를 말하는 경우

        Player player = mock(Player.class);
        ClapCounter clapCounter = mock(ClapCounter.class);
        Context clapRule = mock(Context.class);
        RandomProvider randomProvider = mock(RandomProvider.class);

        when(player.getIncorrectRate()).thenReturn(0.5);
        when(randomProvider.getRandom()).thenReturn(0.3); // 오답률보다 낮은 값
        when(clapRule.do369(anyInt())).thenReturn("clap");
        when(clapRule.getLocalName()).thenReturn("게임");
        when(player.getName()).thenReturn("플레이어1");

        Player[] players = {player, null, null, null};
        ThreeSixNineGame game = new ThreeSixNineGame(randomProvider,clapRule);
        game.playGame(players, clapCounter);

        verify(player).getName();
        // Expected message assertion
        System.out.println("게임 - 플레이어1님이 박수를 쳐야하는데 숫자를 얘기했습니다.");
    }

    @Test
    public void testPlayerShouldSayNumberButClaps() {
        // 2. 플레이어가 숫자를 말해야 하지만 박수를 치는 경우

        Player player = mock(Player.class);
        ClapCounter clapCounter = mock(ClapCounter.class);
        Context clapRule = mock(Context.class);
        RandomProvider randomProvider = mock(RandomProvider.class);

        when(player.getIncorrectRate()).thenReturn(0.5);
        when(randomProvider.getRandom()).thenReturn(0.3); // 오답률보다 낮은 값
        when(clapRule.do369(anyInt())).thenReturn("1"); // 숫자 반환
        when(clapRule.getLocalName()).thenReturn("게임");
        when(player.getName()).thenReturn("플레이어1");

        Player[] players = {player, null, null, null};
        ThreeSixNineGame game = new ThreeSixNineGame(randomProvider,clapRule);
        game.playGame(players, clapCounter);

        verify(player).getName();
        // Expected message assertion
        System.out.println("게임 - 플레이어1님이 숫자를 얘기해야하는데 박수를 쳤습니다.");
    }

    @Test
    public void testPlayerClapsCorrectly() {
        // 4. 플레이어가 정상적으로 박수를 치는 경우
        // 초기 설정
        Player player = mock(Player.class);
        ClapCounter clapCounter = mock(ClapCounter.class);
        Context clapRule = mock(Context.class);
        RandomProvider randomProvider = mock(RandomProvider.class);
        Player[] players = {player, player, player, player};
        ThreeSixNineGame game = new ThreeSixNineGame(randomProvider,clapRule);

        // 플레이어 이름과 게임 이름 설정
        when(player.getName()).thenReturn("플레이어1");
        when(clapRule.getLocalName()).thenReturn("게임");

        when(player.getIncorrectRate()).thenReturn(0.5); // 플레이어의 오답률 설정
        when(randomProvider.getRandom()).thenReturn(0.6); // 오답률보다 높은 값으로 설정

        // 루프를 몇 번 실행한 후 테스트를 중단하도록 예외 발생 설정
        when(clapRule.do369(anyInt()))
                .thenReturn("clap")
                .thenReturn("clap")
                .thenReturn("clap")
                .thenThrow(new RuntimeException("Test loop break")); // 4번째 호출에서 예외 발생

        try {
            game.playGame(players, clapCounter);
        } catch (RuntimeException e) {
            // 예외를 잡아서 테스트 종료
            assertEquals("Test loop break", e.getMessage());
        }

        // 검증: do369 메소드가 4번 호출되었는지 확인
        verify(clapRule, times(4)).do369(anyInt());
    }

    @Test
    public void testPlayerSaysNumberCorrectly() {
        // 3. 플레이어가 정상적으로 숫자를 말하는 경우
        // 초기 설정
        Player player = mock(Player.class);
        ClapCounter clapCounter = mock(ClapCounter.class);
        Context clapRule = mock(Context.class);
        RandomProvider randomProvider = mock(RandomProvider.class);
        Player[] players = {player, player, player, player};
        ThreeSixNineGame game = new ThreeSixNineGame(randomProvider, clapRule);

        // 플레이어 이름과 게임 이름 설정
        when(player.getName()).thenReturn("플레이어1");
        when(clapRule.getLocalName()).thenReturn("게임");

        // do369 메소드가 숫자 "1"을 반환하도록 설정
        when(clapRule.do369(anyInt())).thenReturn("1");

        // 루프를 4번 실행 후 중단
        AtomicInteger count = new AtomicInteger(0);
        doAnswer(invocation -> {
            int i = count.incrementAndGet();
            if (i >= 4) { // 4번 실행한 후
                throw new RuntimeException("Stop loop"); // 예외를 발생시켜 루프를 강제로 중단
            }
            return "1"; // 숫자 "1" 반환
        }).when(clapRule).do369(anyInt());

        // 예외 처리
        try {
            game.playGame(players, clapCounter);
        } catch (RuntimeException e) {
            assertEquals("Stop loop", e.getMessage());
        }

        // do369 메소드가 정확히 4번 호출되었는지 검증
        verify(clapRule, times(4)).do369(anyInt());

        // 결과 출력
        System.out.println("정상적으로 숫자 말함");
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