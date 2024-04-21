package JavaPractice.ThreeSixNineGame;

import JavaPractice.ThreeSixNineGame.Rule.ClapRule;

public class Context {
    ClapRule rule;

    void set369Rule(ClapRule rule) {
        this.rule = rule;
    }

    String do369(int number) {
        return rule.do369(number);
    }
}
