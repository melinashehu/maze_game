import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Labirinti extends JFrame {
    private Labirint labirinti;
    private MazePanel mazePanel;
    private JLabel treasuresLabel;

    public Labirinti() {
        JButton startGameButton = new JButton("Start a New Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labirinti = new Labirint();
                updateUI();
            }
        });

        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (labirinti != null) {
                    labirinti.ruajLojen();
                    showMessage("Game saved successfully!");
                } else {
                    showMessage("Start a new game first!");
                }
            }
        });

        JButton loadButton = new JButton("Load Game");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labirinti = loadLojen();
                if (labirinti != null) {
                    updateUI();
                    showMessage("Game loaded successfully!");
                } else {
                    showMessage("Failed to load the game.");
                }
            }
        });

        mazePanel = new MazePanel();
        treasuresLabel = new JLabel("Thesare: 0"); // Initialize the label with zero treasures
        add(treasuresLabel, BorderLayout.NORTH);

        setTitle("Loja e Labirintit");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton levizDjathtasButton = new JButton("Djathtas");
        JButton levizMajtasButton = new JButton("Majtas");
        JButton levizLartButton = new JButton("Lart");
        JButton levizPoshteButton = new JButton("Poshte");
        JButton exitButton = new JButton("Exit");

        levizDjathtasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMovement("R");
            }
        });

        levizMajtasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMovement("L");
            }
        });

        levizLartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMovement("U");
            }
        });

        levizPoshteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMovement("D");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                perfundoLojen();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));
        buttonPanel.add(levizDjathtasButton);
        buttonPanel.add(levizMajtasButton);
        buttonPanel.add(levizLartButton);
        buttonPanel.add(levizPoshteButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(startGameButton);
        add(buttonPanel, BorderLayout.SOUTH);
        add(mazePanel, BorderLayout.CENTER);

        updateUI();
    }

    private void handleMovement(String direction) {
        if (labirinti != null) {
            if (labirinti.getHarte()[labirinti.getRreshta()][labirinti.getKolona()] != '|') {
                if (labirinti.getHarte()[labirinti.getRreshta()][labirinti.getKolona()] == '0') {
                    labirinti.nrThesareveTeMbledhura++;
                    treasuresLabel.setText("Thesare: " + labirinti.nrThesareveTeMbledhura);
                    showMessage("Ti ke mbledhur " + labirinti.nrThesareveTeMbledhura + " thesare.");
                    perfundoLojen();
                } else if (labirinti.getHarte()[labirinti.getRreshta()][labirinti.getKolona()] == 'X') {
                    showMessage("Urime arrite ne dalje!");
                    perfundoLojen();
                } else {
                    labirinti.levizLojtar(direction);
                    updateUI();
                    kontrolloStatusinLojes();
                }
            } else {
                showMessage("Godite nje mur! Game Over");
                perfundoLojen();
            }
        } else {
            showMessage("Start a new game first!");
        }
    }

    private void kontrolloStatusinLojes() {
        if (labirinti != null && labirinti.getHarte()[labirinti.getRreshta()][labirinti.getKolona()] == 'X') {
            showMessage("Urime arrite ne dalje!");
            perfundoLojen();
        }
    }

    private void perfundoLojen() {
        JOptionPane.showMessageDialog(null, "Game Over");
        System.exit(0);
    }

    protected Labirint loadLojen() {
        // Implement your logic to load the game
        return null; // Replace this with actual implementation
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void updateUI() {
        if (labirinti != null) {
            mazePanel.repaint();
            revalidate();
            repaint();
        }
    }

    private class MazePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (labirinti != null) {
                char[][] map = labirinti.getHarte();
                int madhesiaQelizave = 20;

                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        if (map[i][j] == '|') {
                            g.setColor(Color.BLACK);
                        } else if (map[i][j] == 'X') {
                            g.setColor(Color.RED);
                        } else if (map[i][j] == '0') {
                            g.setColor(Color.GREEN);
                        } else if (map[i][j] == '*') {
                            g.setColor(Color.YELLOW);
                        } else {
                            g.setColor(Color.WHITE);
                        }

                        g.fillRect(j * madhesiaQelizave, i * madhesiaQelizave, madhesiaQelizave, madhesiaQelizave);
                        g.setColor(Color.BLACK);
                        g.drawRect(j * madhesiaQelizave, i * madhesiaQelizave, madhesiaQelizave, madhesiaQelizave);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Labirinti().setVisible(true);
            }
        });
    }
}
