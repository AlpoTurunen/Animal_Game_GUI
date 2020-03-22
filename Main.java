import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends JFrame implements ActionListener{
    private JLabel labelTxt;
    private JLabel inputInfoTxt;
    private JTextField inputName;
    private JButton buttonTellMe;
    private JButton buttonStartGame;
    private JButton buttonHighScores;
    private JLabel background;
    private JButton buttonInfo;
    private String message = new String("");

    
    public Main() {
        super("Main menu");
        initComponents();
        
    }
    
    private void initComponents() {
        // JFrame
        setSize(680, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        // Title
        labelTxt = new JLabel("Welcome to species identification game!");
        labelTxt.setFont(new Font("Serif", Font.BOLD, 30));
        c.insets = new Insets(30,0,50,0);
        c.anchor = GridBagConstraints.NORTH;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        add(labelTxt, c);
        
        // Info button 
        buttonInfo = new JButton("?");
        buttonInfo.setFont(new Font("Serif", Font.BOLD, 31));
        buttonInfo.setBackground(new Color(238, 238, 238));
        buttonInfo.setForeground(Color.BLUE);
        buttonInfo.setBorderPainted(false);
        c.insets = new Insets(0,0,0,0);
        c.gridx = 2;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHEAST;
        c.gridwidth = 1;
        buttonInfo.setPreferredSize(new Dimension(50, 50));
        add(buttonInfo, c);
        
        // Info text
        inputInfoTxt = new JLabel("Player name:");
        inputInfoTxt.setFont(new Font("Serif", Font.PLAIN, 20));
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(20,0,0,0);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        c.weighty = 0.5;
        add(inputInfoTxt, c);
        
        // Input text field
        inputName = new JTextField(15);
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 0;
        add(inputName, c);
        inputName.setText(HighScoresWindow.readFile("nickname.txt"));
        

        // Save nickname button
        buttonTellMe = new JButton("Save New Nickname");
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 2;
        c.gridy = 0;
        add(buttonTellMe, c);       
        
        // Start game button
        buttonStartGame = new JButton("START GAME!");
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(10,10,10,10);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        buttonStartGame.setOpaque(true);
        add(buttonStartGame, c);
        
        // Show top scores button
        buttonHighScores = new JButton("Top Players");
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 2;
        add(buttonHighScores, c); 
        
        // Set listeners
        buttonTellMe.addActionListener(this);
        buttonStartGame.addActionListener(this);
        buttonHighScores.addActionListener(this);
        buttonInfo.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent event) {
         
        // Start game
        if(event.getSource()==buttonStartGame){
            GameWindow gamewindow = new GameWindow();

        }
        
        
        // Save nickname
        if(event.getSource()==buttonTellMe){ 
            try {
                String name = inputName.getText();
                //if (name.length() <= 15 & name.indexOf(",") == -1){  
               if (name.length() > 15 || name.matches(".*[,.*^`?!#¤%&/(\n\t].*")){
                    message = "Maximum length is 15 character and please don't use special characters!";
                    inputName.setText("");
                } else {
                    FileWriter myWriter = new FileWriter("nickname.txt");
                    myWriter.write(name);
                    myWriter.close();
                    message = "Nickname saved!";
                }
            } catch (IOException e) {
                e.printStackTrace();
                message = "Something is broken or file not found!";
            }    
            JOptionPane.showMessageDialog(this, message);
            
            
        }
        
        // Open high scores window
        if(event.getSource()==buttonHighScores){
            HighScoresWindow highscoreswindow = new HighScoresWindow();
        }
        
        // Open info message
        if(event.getSource()==buttonInfo){
            message = (
            "SPECIES IDENTIFICATION GAME\n"+
            "___________________________\n"+
            "ABOUT:\n"+
            "Version: 1.0\n"+
            "Made by: Alpo Turunen\n"+
            "Date: 22.3.2020\n"+
            "___________________________\n"+
            "RULES:\n"+
            "Choose nickname and\n"+
            "Start playing.\n"+
            "___________________________\n"+
            "NOTES:\n"+
            "If you want to change or add\n"+
            "more images, just go to the\n"+
            "'images' folder and edit files.\n"+
            "\n"+
            "Images should be 500 x 300 pixels\n"+
            "and file name is corrext answer.\n"+
            "For example: 'answer.jpg'.\n"+
            "\n"+
            "If you want to clear high scores,\n"+
            "open file topscores.txt and \n"+
            "clear all text");
            
            JOptionPane.showMessageDialog(this, message, "INFO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    public static void main(String[] args) {
        new Main().setVisible(true);
        
    }    
}