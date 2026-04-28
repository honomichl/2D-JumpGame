import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {
    private JFrame frame;
    private DefaultListModel<String> model;
    private JList<String> list;
    private JScrollPane scrollPane;
    private JTextField textField;

    public Menu(){
        frame = new JFrame("moje apka");
        model = new DefaultListModel<>();
        list = new JList<>(model);
        scrollPane = new JScrollPane(list);
        textField = new JTextField();
        init();
    }

    public void init(){

        frame.setSize(800,600);
        frame.setPreferredSize(new Dimension(800,600));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(scrollPane,BorderLayout.CENTER);

        JButton button = new JButton("Add");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(button,BorderLayout.EAST);

        textField.setText("write here");
        panel.add(textField, BorderLayout.CENTER);



        button.addActionListener(e -> {
            String text = textField.getText();
            if (!text.isEmpty()) {
                model.addElement(text);
            }
            textField.setText(" ");
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()== KeyEvent.VK_ENTER) {
                    String text = textField.getText();
                    if (!text.isEmpty()) {
                        model.addElement(text);
                    }
                    textField.setText(" ");
                }

            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    model.remove(list.getSelectedIndex());
                }
            }

        });

        frame.add(panel, BorderLayout.SOUTH);

        frame.pack();

    }
}
