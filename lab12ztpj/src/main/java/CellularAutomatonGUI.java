import javax.script.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class CellularAutomatonGUI extends JFrame {
    private CellularAutomaton automaton;
    private int[] generation;
    private JButton loadButton;

    public CellularAutomatonGUI(String scriptName) {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        automaton = new CellularAutomaton(scriptName);
        generation = new int[31];
        generation[generation.length / 2] = 1;

        JPanel panel = new JPanel(new GridLayout(0, 1)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < generation.length; i++) {
                    if (generation[i] == 1) {
                        g.fillRect(i * 15, 0, 15, 15);
                    }
                }
            }
        };
        add(panel);

        new Timer(1000, e -> {
            generation = (int[]) automaton.callFunction("nextGeneration", new Object[]{generation});
            panel.repaint();
        }).start();

        loadButton = new JButton("Load Script");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JavaScript Files", "js");
            fileChooser.setFileFilter(filter);
            int returnVal = fileChooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String scriptPath = fileChooser.getSelectedFile().getPath();
                automaton.loadScript(scriptPath);
            }
        });

        add(loadButton, BorderLayout.PAGE_END);

        setVisible(true);
    }

    public static void main(String[] args) {
        new CellularAutomatonGUI("C:\\Users\\aleks\\Desktop\\lab12ztpjrepo\\lab12ztpj\\src\\main\\java\\Automat2.js");
    }
}
