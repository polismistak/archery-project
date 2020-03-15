import java.io.Serializable;
import java.util.HashSet;

public class Archer implements Serializable {
    String name;
    int highScore;
    HashSet<Sheet> sheetHashSet;

    Archer(String name,int highScore){
        this.name = name;
        this.highScore = highScore;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
