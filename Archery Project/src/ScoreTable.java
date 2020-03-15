import java.io.Serializable;

/**
 * Υλοποιεί ένα πίνακακι σε φύλο αγώνα
 */
class ScoreTable implements Serializable {
    private int[][] scores;
    private int totalScore;
    private int total9;
    private int total10;
    private int totalX;
    private int numberofSets;
    private int numberofShots;
    private String format;

    ScoreTable(int sets, int shots, String format){
        totalScore=0;
        numberofSets=sets;
        numberofShots = shots;
        this.format = format;
        scores = new int[sets][shots+3];//όπου οι τρεις τελευταίες στύλες είναι 9,10,Χ
    }
    ScoreTable(String format){
        totalScore=0;
        if(format.equals("inside")) {
            numberofSets = 20;
            numberofShots = 3;
            this.format = format;
            scores = new int[20][3 + 3];//όπου οι τρεις τελευταίες στύλες είναι 9,10,Χ
        }
        if(format.equals("outside")) {
            numberofSets = 12;
            numberofShots = 6;
            this.format = format;
            scores = new int[12][6 + 3];//όπου οι τρεις τελευταίες στύλες είναι 9,10,Χ
        }
    }
    ScoreTable(){
        totalScore=0;
        numberofShots = 0;
        numberofSets = 0;
        format = null;
        scores = null;
    }

    int[][] getScores(){return scores;}
    String getFormat(){return format;}

    public int getNumberofSets() {
        return numberofSets;
    }

    public int getNumberofShots() {
        return numberofShots;
    }

    public int getTotalScore() {
        return totalScore;
    }

    int getScore(int set, int shot){return scores[set][shot];}

    int getNumberOf9(int set){return scores[set][numberofShots];}
    int getNumberOf10(int set){return scores[set][numberofShots+1];}
    int getNumberOfX(int set){return scores[set][numberofShots+2];}

    public int getTotal9() {
        return total9;
    }

    public int getTotal10() {
        return total10;
    }

    public int getTotalX() {
        return totalX;
    }

    /**
     * @param set ο σετ
     * @param shot η βολή
     * @param value η αξία
     * @return Αν υπάρχει λάθως: set -1 , shot -2 , value -3 αλλιώς γηρνά 1
     */
    int checkScoreToAdd(int set,int shot , int value){
        if (value<0 || value >10){ return -3;}
        if(set < 0 || set >= numberofSets){return -1;}
        if(shot < 0 || shot >= numberofShots){return -2;}
        return 1;
    }

    int fixTotalScore(int previous,int correct){
        totalScore-=previous;
        totalScore+=correct;
        return correct;
    }

    /**
     * @param set το σετ
     * @param shot η βολή
     * @param value η αξία του βέλους
     * @return Αν υπάρχει λάθως: set -1 , shot -2 , value -3 αλλιώς γηρνά την αξία
     */
    int addValue(int set,int shot , int value){
        int temp=checkScoreToAdd(set,shot ,value);
        if(temp!=1){return temp;}

        scores[set][shot] = value;
        totalScore+=value;

        return value;
    }

    /**
     * @param numberof9 αριθμός 9
     * @param set το σετ
     * @return Αν υπύρξε πρόβλημα με το numberof9 -1 , set -2 αλλιώς numberof9
     */
    int addNumberof9(int numberof9 , int set){
        if(numberof9<0||numberof9>numberofShots){return -1;}
        if(numberofSets<0 || set>=numberofSets){return -2;}
        scores[set][numberofShots] = numberof9;
        return numberof9;
    }
    /**
     * @param numberof10 αριθμός 10
     * @param set το σετ
     * @return Αν υπύρξε πρόβλημα με το numberof10 -1 , set -2 αλλιώς numberof10
     */
    int addNumberof10(int numberof10 , int set){
        if(numberof10<0||numberof10>numberofShots){return -1;}
        if(numberofSets<0 || set>=numberofSets){return -2;}
        scores[set][numberofShots+1] = numberof10;
        return numberof10;
    }
    /**
     * @param numberofX αριθμός X
     * @param set το σετ
     * @return Αν υπύρξε πρόβλημα με το numberofΧ -1 , set -2 αλλιώς numberofX
     */
    int addNumberofX(int numberofX , int set){
        if(numberofX<0||numberofX>numberofShots){return -1;}
        if(numberofSets<0 || set>=numberofSets){return -2;}
        scores[set][numberofShots+2] = numberofX;
        return numberofX;
    }
}
