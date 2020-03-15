import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.HashMap;
import java.util.HashSet;


public class SheetUI extends JFrame {
    protected JPanel archerData;
    protected JPanel scorePanel;
    protected JPanel valuePanel;
    protected JPanel centerPanel;


    protected JButton[][] scoreButtons;
    protected JButton[] valueButtons;

    protected JTextField nameTextField;
    protected JTextField clubTextField;
    protected JTextField categoryTextField;
    protected JTextField shootingdistanceTextField;
    protected JFormattedTextField dateTextField;

    protected JLabel nameLabel;
    protected JLabel clubLabel;
    protected JLabel categoryLabel;
    protected JLabel shootingdistanceLabel;
    protected JLabel dateLabel;

    protected Sheet sheet ;

    protected JButton lastButtonPresed;
    protected int currentX;
    protected int currentY;

    protected JPanel indicatorsPanel;
    protected JPanel setCounterPanel;


    class scoreButtonListener implements ActionListener{

        private int x;
        private int y;
        private JButton button;

        public scoreButtonListener(int i , int j , JButton b){
            x = i;
            y = j;
            button = b;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(lastButtonPresed != null){lastButtonPresed.setBackground(Color.WHITE);}
            lastButtonPresed = button;
            currentX = x;
            currentY = y;
            button.setBackground(Color.YELLOW);
        }
    }



    class valueButtonListener implements ActionListener{
        String value;
        valueButtonListener(String val){
            value = val;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (lastButtonPresed == null)return;
            if(currentY<sheet.getScoreTable().getNumberofShots()) {
                if (sheet.getScoreTable().addValue(currentX, currentY, Integer.parseInt(value)) < 0) return;
            }else if(currentY==sheet.getScoreTable().getNumberofShots()){
                if (sheet.getScoreTable().addNumberof9(Integer.parseInt(value),currentX) < 0) return;
            }else if(currentY==sheet.getScoreTable().getNumberofShots()+1){
                if (sheet.getScoreTable().addNumberof10(Integer.parseInt(value),currentX) < 0) return;
            }else if(currentY==sheet.getScoreTable().getNumberofShots()+2){
                if (sheet.getScoreTable().addNumberofX(Integer.parseInt(value),currentX) < 0) return;
            }

            lastButtonPresed.setBackground(Color.WHITE);
            lastButtonPresed.setText(value);
            lastButtonPresed = null;
        }
    }



    SheetUI(String format, HashMap<String,Archer> archers){
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(chechAllFieldsCompleted()==false){
                    JOptionPane.showMessageDialog(null,"Mistake in the sheet. Please correct and then close !");
                    return;
                }
                passUItoSheet();



                try(ObjectOutputStream archersFile = new ObjectOutputStream(new FileOutputStream("archers.txt"))){
                    archersFile.writeObject(archers);
                }catch(IOException ioe){

                }

                dispose();
            }
        });


        sheet = new Sheet(format);
        lastButtonPresed = null;

        nameTextField = new JTextField(sheet.getName());
        clubTextField = new JTextField(sheet.getClub());
        categoryTextField = new JTextField(sheet.getCategory());
        shootingdistanceTextField = new JTextField(sheet.getShootingDistance());
        dateTextField = new JFormattedTextField(sheet.getDate());

        archerData = new JPanel();
        scorePanel = new JPanel();
        valuePanel = new JPanel();
        centerPanel = new JPanel();


        scoreButtons = new JButton[sheet.getScoreTable().getNumberofSets()][sheet.getScoreTable().getNumberofShots()+3];
        for(int i=0;i<sheet.getScoreTable().getNumberofSets();i++){
            int j=0;
            for(;j<sheet.getScoreTable().getNumberofShots();j++){
                scoreButtons[i][j] = new JButton();
                scoreButtons[i][j].setBackground(Color.WHITE);
                scoreButtons[i][j].addActionListener(new scoreButtonListener(i,j,scoreButtons[i][j]));
            }

            scoreButtons[i][j] = new JButton();
            scoreButtons[i][j].setBackground(Color.WHITE);
            scoreButtons[i][j].addActionListener(new scoreButtonListener(i,j,scoreButtons[i][j]));
            j++;
            scoreButtons[i][j] = new JButton();
            scoreButtons[i][j].setBackground(Color.WHITE);
            scoreButtons[i][j].addActionListener(new scoreButtonListener(i,j,scoreButtons[i][j]));
            j++;
            scoreButtons[i][j] = new JButton();
            scoreButtons[i][j].setBackground(Color.WHITE);
            scoreButtons[i][j].addActionListener(new scoreButtonListener(i,j,scoreButtons[i][j]));
        }

        valueButtons = new JButton[11];

        for (int i=0;i<11;i++){
            valueButtons[i] = new JButton(i+"");
            valueButtons[i].addActionListener(new valueButtonListener(valueButtons[i].getText()));
        }
        valueButtons[0].setText("M");


        scorePanel.setLayout(new GridLayout(sheet.getScoreTable().getNumberofSets(),sheet.getScoreTable().getNumberofShots()+3));
        for(JButton[] t:scoreButtons){
            for(JButton temp:t){
                scorePanel.add(temp);
            }
        }
        nameLabel = new JLabel("Name : ");
        clubLabel = new JLabel("Club : ");
        categoryLabel = new JLabel("Category : ");
        shootingdistanceLabel = new JLabel("Distance : ");
        dateLabel = new JLabel("Date : ");

        archerData.setLayout(new GridLayout(6,2));
        archerData.add(nameLabel);
        archerData.add(nameTextField);
        archerData.add(clubLabel);
        archerData.add(clubTextField);
        archerData.add(categoryLabel);
        archerData.add(categoryTextField);
        archerData.add(shootingdistanceLabel);
        archerData.add(shootingdistanceTextField);
        archerData.add(dateLabel);
        archerData.add(dateTextField);

        valuePanel.setLayout(new FlowLayout());
        for(int i=0;i<11;i++){
            valuePanel.add(valueButtons[i]);
        }

        indicatorsPanel = new JPanel();
        indicatorsPanel.setLayout(new GridLayout(1,sheet.getScoreTable().getNumberofShots()+3));
        JLabel[] shotLabels = new JLabel[sheet.getScoreTable().getNumberofShots()];
        for(int i=0;i<sheet.getScoreTable().getNumberofShots();i++){
            shotLabels[i] = new JLabel("        Shot"+(i+1));
            shotLabels[i].setVerticalTextPosition(JLabel.CENTER);
            indicatorsPanel.add(shotLabels[i]);
        }



        JLabel label9 = new JLabel("        9s");
        label9.setVerticalTextPosition(JLabel.CENTER);
        indicatorsPanel.add(label9);
        JLabel label10 = new JLabel("       10s");
        label10.setVerticalTextPosition(JLabel.CENTER);
        indicatorsPanel.add(label10);
        JLabel labelX = new JLabel("        Xs");
        labelX.setVerticalTextPosition(JLabel.CENTER);
        indicatorsPanel.add(labelX);


        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(indicatorsPanel,BorderLayout.PAGE_START);
        centerPanel.add(scorePanel,BorderLayout.CENTER);




        JLabel[] setCounters = new JLabel[20];
        for(int i=0;i<20;i++){
            setCounters[i] = new JLabel((i+1)+"");
        }

        setCounterPanel = new JPanel();
        setCounterPanel.setLayout(new GridLayout(sheet.getScoreTable().getNumberofSets(),1));
        for(int i=0;i<sheet.getScoreTable().getNumberofSets();i++){
            setCounterPanel.add(setCounters[i]);
        }


        centerPanel.add(setCounterPanel,BorderLayout.LINE_START);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int answ = JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?");
                if(answ!=0)return;
                dispose();
            }
        });

        valuePanel.add(quitButton);


        setLayout(new BorderLayout());
        add(centerPanel,BorderLayout.CENTER);
        add(archerData,BorderLayout.PAGE_START);
        add(valuePanel,BorderLayout.PAGE_END);






        this.pack();
        this.setVisible(true);
    }



    void passUItoSheet(){
        sheet.setName(nameTextField.getText());
        sheet.setClub(clubTextField.getText());
        sheet.setCategory(categoryTextField.getText());
        try {
            sheet.setShootingDistance(Integer.parseInt(shootingdistanceTextField.getText()));
        }catch (NumberFormatException e){
            sheet.setShootingDistance(0);
        }
        for(int i=0;i<sheet.getScoreTable().getNumberofSets();i++){
            for(int j=0;j<sheet.getScoreTable().getNumberofShots();j++){
                try {
                    sheet.addValue(i, j, Integer.parseInt(scoreButtons[i][j].getText()));
                }catch (NumberFormatException e){
                    sheet.addValue(i,j,0);
                }
            }
            try {
                sheet.addNumberof9(i, Integer.parseInt(scoreButtons[i][sheet.getScoreTable().getNumberofShots()].getText()));
            }catch (NumberFormatException e){
                sheet.addNumberof9(i,0);
            }
            try {
                sheet.addNumberof10(i, Integer.parseInt(scoreButtons[i][sheet.getScoreTable().getNumberofShots() + 1].getText()));
            }catch (NumberFormatException e ){
                sheet.addNumberof10(i,0);
            }
            try {
                sheet.addNumberofX(i, Integer.parseInt(scoreButtons[i][sheet.getScoreTable().getNumberofShots() + 2].getText()));
            }catch (NumberFormatException e){
                sheet.addNumberofX(i,0);
            }
        }
    }

    boolean chechAllFieldsCompleted(){
        for(JButton[] t:scoreButtons){
            for(JButton temp:t) {
                if (temp.getText().equals("")||temp.getText().equals(" ")){
                    return false;
                }
            }
        }
        if(nameTextField.getText().equals("")||nameTextField.getText().equals(" ")) return false;
        if(categoryTextField.getText().equals("")||categoryTextField.getText().equals(" ")) return false;
        if(shootingdistanceTextField.getText().equals("")||shootingdistanceTextField.getText().equals(" ")) return false;
        if(dateTextField.getText().equals("")||dateTextField.getText().equals(" ")) return false;


        return true;
    }
}
