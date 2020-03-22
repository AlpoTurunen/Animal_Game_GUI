import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame; 
import javax.swing.JScrollPane; 
import javax.swing.JTable; 
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class HighScoresWindow extends JFrame {
    JTable j;
    
    public HighScoresWindow() {
        setTitle("High Scores");
        createTable();
    }
    
    // Get data from text file and prepare it for JTable
    private void createTable(){
        String data = readFile("topscores.txt");
        String[] datarows = data.split("\n");
        Object[][] matrix = new Object[datarows.length][2];
        int r = 0;
        for(String row : datarows) {
            String[] parts = row.split(",");
            String name = parts[0];
            int result = Integer.parseInt(parts[1].trim());
            Object[] tmp = {name, result};
            matrix[r++] = tmp;
        }
        String[] columnNames = {"Name", "Points"}; 
        
        // Create table
        DefaultTableModel model = new DefaultTableModel(matrix,columnNames) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };
        j = new JTable(model);
        j.setBounds(30,40,200,300);
        JScrollPane scroll = new JScrollPane(j);    
        j.setAutoCreateRowSorter(true);
        j.getRowSorter().toggleSortOrder(1);
        JOptionPane.showMessageDialog(null,scroll, "Best Players of All Time", JOptionPane.PLAIN_MESSAGE);
    }
    
    // Get data from text file and return string
    public static String readFile(String filename) {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}