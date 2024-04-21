package JavaPractice.ThreeSixNineGame;

import JavaPractice.ThreeSixNineGame.Rule.Busan369;
import JavaPractice.ThreeSixNineGame.Rule.ClapRule;
import JavaPractice.ThreeSixNineGame.Rule.Seoul369;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {

    @Test
    void set369RuleSeoul() {
    }

    @ParameterizedTest
    @CsvSource({
            "3, clap",
            "6, clap",
            "9, clap",
            "369, clap",
            "1, '1'",
            "10, '10'",
            "29, 'clap'",
            "30, clap",
            "39, clap",
            "40, '40'"
    })
    void doSeoul369(int number, String expected) {
        ClapRule rule = new Seoul369();
        assertEquals(expected, rule.do369(number));
    }

    @ParameterizedTest
    @CsvSource({
            "3, clap",
            "66, clapclap",
            "9, clap",
            "369, clapclapclap",
            "1, '1'",
            "10, '10'",
            "29, 'clap'",
            "30, clap",
            "39, clapclap",
            "40, '40'"
    })
    void doBusan369(int number, String expected) {
        ClapRule rule = new Busan369();
        assertEquals(expected, rule.do369(number));
    }

}