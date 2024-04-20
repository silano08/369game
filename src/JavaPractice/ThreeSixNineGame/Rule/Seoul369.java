package JavaPractice.ThreeSixNineGame.Rule;

public class Seoul369 implements ClapRule {
    @Override
    public String do369(int number) {
        String numStr = Integer.toString(number);

        if(numStr.contains("3") || numStr.contains("6") || numStr.contains("9")) return "clap";
        return numStr;
    }
}
