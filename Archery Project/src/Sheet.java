import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Κλάση που υλοποιεί την ένοια ενώς φύλου αγώνα ολόκληρου
 */
public class Sheet implements Serializable {
    private String name;
    private String club;
    private String category;
    private int shootingDistance;
    private LocalDate date;
    private ScoreTable scoreTable;

    Sheet(String name , String club , String category,int shootingDistance ,Date date , int sets, int shots, String format ){
        this.name = new String(name);
        this.date = LocalDate.now();
        this.category = new String(category);
        this.shootingDistance = shootingDistance;
        this.club = new String(club);
        this.scoreTable = new ScoreTable(sets,shots,format);
    }
    Sheet(String name , String club , String category,int shootingDistance ,LocalDate date,String format){
        this.name = new String(name);
        this.date = date;
        this.category = new String(category);
        this.shootingDistance = shootingDistance;
        this.club = new String(club);
        this.scoreTable = new ScoreTable(format);
    }
    Sheet(String format){
        this.name = "";
        this.date = LocalDate.now();
        this.category ="";
        this.shootingDistance = 0;
        this.club = "";
        this.scoreTable = new ScoreTable(format);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getShootingDistance() {
        return shootingDistance;
    }

    public ScoreTable getScoreTable() {
        return scoreTable;
    }

    public String getCategory() {
        return category;
    }

    public String getClub() {
        return club;
    }

    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScoreTable(ScoreTable scoreTable) {
        this.scoreTable = scoreTable;
    }

    public void setShootingDistance(int shootingDistance) {
        this.shootingDistance = shootingDistance;
    }

    int addValue(int set, int shot , int value){
        return scoreTable.addValue(set,shot,value);
    }
    int addNumberof9(int set , int value){
        return scoreTable.addNumberof9(set,value);
    }
    int addNumberof10(int set , int value){
        return scoreTable.addNumberof10(set,value);
    }
    int addNumberofX(int set , int value){
        return scoreTable.addNumberofX(set,value);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
