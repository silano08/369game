package JavaPractice.ThreeSixNineGame;

public class Player {
    //이름과 오답율을 가지고 있는 클래스 만들기
    public String name;
    public double incorrectRate;

    public double getIncorrectRate(){
        return this.incorrectRate;
    }

    public String getName(){
        return this.name;
    }

    public Player(String name,double incorrectRate){
        this.name = name;
        this.incorrectRate = incorrectRate;
    }
}
