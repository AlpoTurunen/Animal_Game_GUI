import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import java.util.Random;
import javax.swing.text.JTextComponent;


public class GameWindow extends JFrame implements ActionListener{
    private int rightAnswerCount = 0;
    private int questionCount = 0;
    private int imgNro;
    private JTextComponent answerTxt;
    private List<String> listName;
    private JButton btnCheckAnswer;
    private JLabel questionCountTxt;
    private JLabel lblShowImg;
  
    public GameWindow() throws HeadlessException {  
        readImageNameToList();
        initComponents();
    }
    
    // Read image names to list
    private void readImageNameToList() {
        File f = new File("Images");
        File[] listFiles = f.listFiles();
        listName = new ArrayList<>();
        for (File listFile : listFiles) {
            listName.add(listFile.getName());
        }
    }
    
    // If user click checkAnswer-button
    public void actionPerformed(ActionEvent event) {
        if(event.getSource()==btnCheckAnswer){
            checkAnswer();
            ShowImage();         
        }
    }
    

    // Set a new image
    private void ShowImage() throws HeadlessException {
        Random r = new Random();
        imgNro = r.nextInt(listName.size());
        
        ImageIcon icon = new ImageIcon("Images/"+listName.get(imgNro));
        lblShowImg.setIcon(icon);
        questionCount++;
        questionCountTxt.setText("Question nro: "+questionCount+"/10");
        answerTxt.setText("");
    }
    
    // Check answer and show dialog
    private void checkAnswer() {
        String answer = answerTxt.getText();
        String rightAnswer = listName.get(imgNro).toLowerCase();
        rightAnswer = rightAnswer.replace(".jpg", "");
        listName.remove(imgNro);

        if (answer.toLowerCase().equals(rightAnswer)){
           rightAnswerCount++;
           String message = new String("Well done");
           JOptionPane.showMessageDialog(this, message, "CORRECT!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("smile.png"));
        } else {
            String message = new String("Right answer was: "+rightAnswer);
            JOptionPane.showMessageDialog(this, message, "WRONG!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("sad.png"));
        }
        
        if (questionCount > 9){
            gameOver();
        }
    }
    
    // Show dialog when game is over
    private void gameOver(){
        Object[] options = {"Save the Result","Main menu"};
        int n = JOptionPane.showOptionDialog(this,
            "Your points: "+rightAnswerCount+"/10",
            "GAME OVER!",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,     //do not use a custom Icon
            options,  //the titles of buttons
            options[0]); //default button title
        if (n == 0) {
            saveResult();
            dispose();
        } else if (n == 1) {
            dispose();
        }
    }
    
    // Write player name and results to text file
    private void saveResult(){
        String data = HighScoresWindow.readFile("topscores.txt");
        String name = HighScoresWindow.readFile("nickname.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("topscores.txt"));
            writer.write(data);
            writer.newLine();
            writer.write(name+","+rightAnswerCount);
            writer.flush();
            writer.close();
            String message = new String("Result saved!");
            JOptionPane.showMessageDialog(this, message);
        } catch (IOException ex) {
            System.out.println("File ERROR");
        }
    }
    
    private void initComponents() {
        // JFrame settings
        setTitle("Game Window");
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600,600);
        
        // Text fields
        questionCountTxt = new JLabel("Question nro: "+questionCount+"/10");
        questionCountTxt.setFont(new Font("Serif", Font.BOLD, 20));
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.NORTH;
        add(questionCountTxt,c);
        
        JLabel infoTxt = new JLabel("What is the animal name in Finnish?");
        infoTxt.setFont(new Font("Serif", Font.PLAIN, 20));
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        add(infoTxt,c);
        
        // Image field
        lblShowImg = new JLabel();
        JScrollPane scrShowImg = new JScrollPane(lblShowImg);
        scrShowImg.setPreferredSize(new Dimension(505,305));
        c.gridx = 1;
        c.gridy = 1;
        add(scrShowImg,c);
        
        // Answer user input
        answerTxt = new JTextField(15);
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.NORTH;
        add(answerTxt,c);
        
        // catch the enter key in the answerTxt and perform an OK click on the btnCheckAnswer
        answerTxt.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                    btnCheckAnswer.doClick();
                }
            }
        });

        // Check answer button
        btnCheckAnswer = new JButton("Check your answer");
        btnCheckAnswer.setPreferredSize(new Dimension(200,30));
        btnCheckAnswer.addActionListener(this);
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.CENTER;
        add(btnCheckAnswer,c);

        setVisible(true); 
        ShowImage();
    }
}