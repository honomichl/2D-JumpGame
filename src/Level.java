import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Level extends JPanel implements ActionListener {

    private Timer timer;
    private int playerSize = 400; // Výška hráče
    private int playerJump = 0; // Rychlost skoku
    private final int GRAVITY = 1;

    public Level(JFrame frame) {
        this.setBackground(AppSettings.getBackgroundColor());
        this.setFocusable(true); // Nutné, aby panel vnímal klávesnici

        // Posluchač kláves (ovládání)
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && playerSize >= 400) {
                    playerJump = -15; // Skok nahoru
                }
            }
        });

        // Game Loop - tikne každých 16 ms (~60 snímků za vteřinu)
        timer = new Timer(16, this);
        timer.start();
    }

    // TADY SE DĚJE VEŠKERÁ MAGIE KRESLENÍ
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Zapneme vyhlazování, aby to vypadalo hezky
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Nakreslíme podlahu
        g2d.setColor(Color.CYAN);
        g2d.fillRect(0, 450, 800, 5);

        // Nakreslíme hráče (kostku)
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(100, playerSize, 50, 50);

        // Nakreslíme obrys (aby to vypadalo jako v GD)
        g2d.setColor(Color.BLACK);
        g2d.drawRect(100, playerSize, 50, 50);
    }

    // TADY SE POČÍTÁ POHYB (Logika)
    @Override
    public void actionPerformed(ActionEvent e) {
        // Gravitace a pohyb
        playerSize += playerJump;

        if (playerSize < 400) {
            playerJump += GRAVITY; // Padá dolů
        } else {
            playerSize = 400; // Zastaví se na zemi
            playerJump = 0;
        }

        repaint(); // Znovu zavolá paintComponent
    }
}