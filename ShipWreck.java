import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ShipWreck
{
    int boardWidth = 650;
    int boardHeight = 650;

    JFrame frame = new JFrame("Ship Wreck");
    JLabel textLabel1 = new JLabel();
    JPanel textPanel1 = new JPanel();
    JLabel textLabel2 = new JLabel();
    JPanel textPanel2 = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel();

    JButton[] board = new JButton[25];
    ImageIcon red;
    ImageIcon blue;
    ImageIcon yellow;
    ImageIcon explosion;

    JButton RedTile;
    JButton BlueTile;
    JButton YellowTile;
    JButton Restart;
    JButton tile;

    Random random = new Random();
    Timer setRedTimer;
    Timer setBlueTimer;
    Timer setYellowTimer;
    int score = 0;

    ShipWreck()
    {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel1.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        textLabel1.setHorizontalAlignment(JLabel.CENTER);
        textLabel1.setText("Score: " + Integer.toString(score));
        textLabel1.setOpaque(true);

        textPanel1.setLayout(new BorderLayout());
        textPanel1.add(textLabel1);
        frame.add(textPanel1, BorderLayout.NORTH);

        textLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        textLabel2.setHorizontalAlignment(JLabel.LEFT);
        textLabel2.setText("How to play: RED + 1, Yellow + 3, Blue = LOSE");
        textLabel2.setOpaque(true);

        textPanel2.setLayout(new BorderLayout());
        textPanel2.add(textLabel2);
        frame.add(textPanel2, BorderLayout.SOUTH);

        boardPanel.setLayout(new GridLayout(5, 5));
        frame.add(boardPanel);

        Restart = new JButton("Restart");
        Restart.setFocusable(false);
        Restart.addActionListener(e -> restart());
        controlPanel.add(Restart);
        frame.add(controlPanel, BorderLayout.EAST);

        Image blueImg = new ImageIcon(getClass().getResource("./blueShip.jpg")).getImage();
        blue = new ImageIcon(blueImg.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH));

        Image redImg = new ImageIcon(getClass().getResource("./redShipRemoved.png")).getImage();
        red = new ImageIcon(redImg.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH));

        Image yellowImg = new ImageIcon(getClass().getResource("./yellowShip.png")).getImage();
        yellow = new ImageIcon(yellowImg.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH));

        Image explosionImg = new ImageIcon(getClass().getResource("./explosion.png")).getImage();
        explosion = new ImageIcon(explosionImg.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH));


        for (int i = 0; i < 25; i++)
        {
            tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);

            tile.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JButton tile = (JButton) e.getSource();
                    if (tile == RedTile) {
                        score = score + 1;
                        textLabel1.setText("Score: " + Integer.toString(score));
                        RedTile.setIcon(explosion);
                        Timer explosionTimer = new Timer(500, event ->
                        {
                            tile.setIcon(null);
                            RedTile = null;
                        });
                        explosionTimer.setRepeats(false);
                        explosionTimer.start();
                    }
                    else if (tile == YellowTile){
                        score= score + 3;
                        textLabel1.setText("Score: " + Integer.toString(score));
                        YellowTile.setIcon(explosion);
                        Timer explosionTimer = new Timer(500, event ->
                        {
                            tile.setIcon(null);
                            YellowTile = null;
                        });
                        explosionTimer.setRepeats(false);
                        explosionTimer.start();
                    }
                    else if (tile == BlueTile)
                    {
                        textLabel1.setText("Game Over: " + Integer.toString(score));
                        BlueTile.setIcon(explosion);
                        Timer explosionTimer = new Timer(500, event ->
                        {
                            tile.setIcon(null);
                            BlueTile = null;
                        });
                        explosionTimer.setRepeats(false);
                        explosionTimer.start();
                        setRedTimer.stop();
                        setBlueTimer.stop();
                        setYellowTimer.stop();
                        for (int i = 0; i < 25; i++)
                        {
                            board[i].setEnabled(false);
                        }
                    }
                }
            });
        }

        setRedTimer = new Timer(2000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (RedTile != null) {
                    RedTile.setIcon(null);
                    RedTile = null;
                }

                int num = random.nextInt(25);
                tile = board[num];

                if (BlueTile == tile || YellowTile == tile ) return;

                RedTile = tile;
                RedTile.setIcon(red);
            }
        });

        setYellowTimer = new Timer(2000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (YellowTile != null) {
                    YellowTile.setIcon(null);
                    YellowTile = null;
                }

                int num = random.nextInt(25);
                tile = board[num];

                if (BlueTile == tile || RedTile == tile) return;

                YellowTile = tile;
                YellowTile.setIcon(yellow);
            }
        });

        setBlueTimer = new Timer(1500, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (BlueTile != null)
                {
                    BlueTile.setIcon(null);
                    BlueTile = null;
                }

                int num = random.nextInt(25);
                tile = board[num];

                if (RedTile == tile || YellowTile == tile) return;

                BlueTile = tile;
                BlueTile.setIcon(blue);
            }
        });

        setRedTimer.start();
        setBlueTimer.start();
        setYellowTimer.start();
        frame.setVisible(true);

    }
    private void restart()
    {
        score = 0;
        textLabel1.setText("Score: " + Integer.toString(score));

        for(int i =0; i<25; i++)
        {
            board[i].setIcon(null);
            board[i].setEnabled(true);
        }

        RedTile = null;
        YellowTile = null;
        BlueTile = null;

        setRedTimer.start();
        setBlueTimer.start();
        setYellowTimer.start();
    }
    public static void main(String[] args) {
        ShipWreck shipWreck = new ShipWreck();
    }
}
