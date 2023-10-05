import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EquivalenceRelationsVisualizer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EquivalenceRelationsVisualizer().createGUI());
    }


    private void createGUI() {
        JFrame frame = new JFrame("Equivalence Relations Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());


        EquivalencePanel equivalencePanel = new EquivalencePanel();
        frame.add(equivalencePanel, BorderLayout.CENTER);


        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());


        JTextField inputField = new JTextField(10);
        controlPanel.add(inputField);


        JButton addButton = new JButton("Add Relation");
        controlPanel.add(addButton);


        frame.add(controlPanel, BorderLayout.SOUTH);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                String[] elements = input.split(",");
                if (elements.length == 2) {
                    equivalencePanel.addRelation(elements[0].trim(), elements[1].trim());
                    equivalencePanel.repaint();
                    inputField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid relation (e.g., 'a, b')",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        frame.setVisible(true);
    }
}