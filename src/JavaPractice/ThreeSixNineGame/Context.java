package JavaPractice.ThreeSixNineGame;

import JavaPractice.ThreeSixNineGame.Rule.ClapRule;
import groovyjarjarantlr4.v4.tool.Rule;

public class Context {
    ClapRule rule;

    void set369RuleSeoul(ClapRule rule){
        this.rule = rule;
    }

    String do369(int number){
        return rule.do369(number);
    }
}
