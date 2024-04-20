package JavaPractice.ThreeSixNineGame.Rule;

// 전략 알고리즘 B
public class Busan369 implements ClapRule {

    @Override
    public String do369(int number) {
        String numStr = Integer.toString(number);

        String result = "";
        for(int i=0; i<numStr.length(); i++){
            if(numStr.contains("3") || numStr.contains("6") || numStr.contains("9")) result+= "clap";
        }

        if(result.equals("")){
            return numStr;
        }else{
            return result;
        }
    }
}
