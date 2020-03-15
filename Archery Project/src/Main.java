import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;


public class Main {
    public static void main(String[] arg) {
        HashMap<String,Archer> archers=null ;

        try(ObjectInputStream archersFile = new ObjectInputStream(new FileInputStream("archers.txt"))){
            archers = (HashMap<String,Archer>) archersFile.readObject();
        }catch(IOException e){

        }catch(ClassNotFoundException cnfe){

        }
        JFrame test = new SheetUI("outside",archers);
    }
}
