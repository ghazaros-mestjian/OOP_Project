import core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuoridorGUI extends JFrame {

    private static final int SIZE = 9;
    private JButton[][] cells = new JButton[SIZE][SIZE];

    private Player[] players;
    private int currentPlayer = 0;

    public QuoridorGUI() {
        setTitle("Quoridor");
        setSize(700, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initializePlayers();

        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton button = new JButton();

                int r = row;
                int c = col;

                button.addActionListener(e -> movePlayer(r, c));

                cells[row][col] = button;
                boardPanel.add(button);
            }
        }

        add(boardPanel);
    }

    private void initializePlayers() {
        players = new Player[2];

        players[0] = new HumanPlayer(Board.HEIGHT - 1, Board.WIDTH / 2, Direction.UP);
        players[1] = new HumanPlayer(0, Board.WIDTH / 2, Direction.DOWN);
    }



    private void movePlayer(int row, int col) {
        Player player = players[currentPlayer];

        int currentX = player.getX();
        int currentY = player.getY();

        int dx = row - currentX;
        int dy = col - currentY;

        try {
            if (dx == -1 && dy == 0) {
                player.perform(new StepAction(Direction.UP));
            }
            else if (dx == 1 && dy == 0) {
                player.perform(new StepAction(Direction.DOWN));
            }
            else if (dx == 0 && dy == -1) {
                player.perform(new StepAction(Direction.LEFT));
            }
            else if (dx == 0 && dy == 1) {
                player.perform(new StepAction(Direction.RIGHT));
            }
            else {
                return;
            }

            if (player.hasWon()) {
                JOptionPane.showMessageDialog(this,
                        "Player " + (currentPlayer + 1) + " Wins!");
                System.exit(0);
            }

            currentPlayer = (currentPlayer + 1) % players.length;


        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuoridorGUI gui = new QuoridorGUI();
            gui.setVisible(true);
        });
    }
}