package gui;

import core.*;
import core.action.*;
import core.exception.ActionFormatException;
import core.exception.IllegalActionException;
import core.player.ComputerPlayer;
import core.player.HumanPlayer;
import core.player.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Arrays;

public class QuoridorGUI extends JFrame {
    private Player[] players;
    private int playerCount;
    private boolean fourPlayers;
    private int currentPlayer;
    private int deadPlayerCount;
    private boolean gameStarted = false;
    private JPanel root;
    private ImageIcon icon;

    private static final Border ACTIVE_BORDER =
            BorderFactory.createLineBorder(Color.BLACK, 4);

    private static final Border NORMAL_BORDER =
            BorderFactory.createEmptyBorder(3, 3, 3, 3);

    private JButton[][] boardButtons;
    private JButton[][] verticalWButtons;
    private JButton[][] horizontalWButtons;

    JLabel currentPlayerLabel;
    private JLabel[] wallLabels;

    private static final Color[] colors = {new Color(0xFF9090),
            new Color(0x9090FF),
            new Color(0x90FF90),
            new Color(0xFFFF90)
    };

    private final ImageIcon[] playerIcons = {
            resizeIcon("/gui/redPlayer.png", 40, 40),
            resizeIcon("/gui/bluePlayer.png", 40, 40),
            resizeIcon("/gui/greenPlayer.png", 40, 40),
            resizeIcon("/gui/purplePlayer.png", 40, 40)
    };

    private static final Color defaultColor = new Color(0xE7ECFF);

    public QuoridorGUI() {
        icon = new ImageIcon((getClass().getResource("/gui/startIcon.png")));
        root = new JPanel(new CardLayout());
        boardButtons = new JButton[Board.HEIGHT][Board.WIDTH];
        verticalWButtons = new JButton[Board.HEIGHT][Board.WIDTH - 1];
        horizontalWButtons = new JButton[Board.HEIGHT - 1][Board.WIDTH];
        currentPlayerLabel = new JLabel("Current Player");
        wallLabels = new JLabel[4];

        initializePlayers();

        setTitle("Quoridor");
        setSize(768, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel gameScreen = new JPanel(new BorderLayout());
        JPanel startScreen = startGamePanel();
        startScreen.setBackground(defaultColor);
        gameScreen.add(createControlPanel(), BorderLayout.WEST);
        gameScreen.add(createBoardPanel(), BorderLayout.CENTER);

        root.add(startScreen, "start");
        root.add(gameScreen, "game");
        add(root);
//		add(createControlPanel(), BorderLayout.WEST);
//		add(createBoardPanel(), BorderLayout.CENTER);
        CardLayout cl = (CardLayout) root.getLayout();
        cl.show(root, "start");
        setResizable(false);
        pack();

        paintPlayers();
        paintWalls();
    }

    private ImageIcon resizeIcon(String path, int width, int height) {
        Image img = new ImageIcon(getClass().getResource(path))
                .getImage()
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(img);
    }

    private void paintPlayers() {
        // clear previous player drawings
        for (int r = 0; r < Board.HEIGHT; r++) {
            for (int c = 0; c < Board.WIDTH; c++) {
                boardButtons[r][c].setIcon(null);
                boardButtons[r][c].setBorder(NORMAL_BORDER);
            }
        }

        for (int i = 0; i < 4; i++) {
            int x = players[i].getX();
            int y = players[i].getY();

            boardButtons[x][y].setBorder(NORMAL_BORDER);
            if (i < playerCount && !players[i].isDead()) {
                boardButtons[x][y].setIcon(playerIcons[i]);

                if (i == currentPlayer)
                    boardButtons[x][y].setBorder(ACTIVE_BORDER);
            } else
                boardButtons[x][y].setBackground(defaultColor);
        }
    }

    private void paintWalls() {
        for (int i = 0; i < Board.HEIGHT; i++) {
            for (int j = 0; j < Board.WIDTH; j++) {
                if (Board.checkWall(i, j, true))
                    verticalWButtons[i][j].setBackground(Color.DARK_GRAY);

                if (Board.checkWall(i, j, false)) {
                    horizontalWButtons[i][j].setBackground(Color.DARK_GRAY);
                }
            }
        }
    }

    private void initializePlayers() {
        players = new Player[4];
        players[0] = new HumanPlayer(Board.HEIGHT - 1, Board.WIDTH / 2, Direction.UP);
        players[1] = new HumanPlayer(0, Board.WIDTH / 2, Direction.DOWN);
        players[2] = new HumanPlayer(Board.HEIGHT / 2, Board.WIDTH - 1, Direction.LEFT);
        players[3] = new HumanPlayer(Board.HEIGHT / 2, 0, Direction.RIGHT);
        playerCount = 2;
        fourPlayers = false;
        currentPlayer = 0;
        deadPlayerCount = 0;
    }

    private JPanel startGamePanel() {

        JPanel startScreen = new JPanel(new BorderLayout());
        startScreen.setBackground(Color.DARK_GRAY);

        JButton playButton = new JButton(icon);
        playButton.setBounds(250, 200, 50, 50);
        playButton.setBorderPainted(false);
        playButton.setFocusPainted(false);

        playButton.addActionListener(e -> {
            gameStarted = true;
            CardLayout cl = (CardLayout) root.getLayout();
            cl.show(root, "game");
        });

        startScreen.add(playButton);

        return startScreen;
    }

    private JPanel createControlPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(0, 75));
        JButton switchButton = new JButton("Mode: 2 Players");
        switchButton.setBackground(defaultColor);
        switchButton.setPreferredSize(new Dimension(200, 40));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JPanel playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playersPanel.add(row1);
        playersPanel.add(row2);

        JButton[] playerButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            playerButtons[i] = new JButton("Human");
            playerButtons[i].setBackground(colors[i]);
            playerButtons[i].setPreferredSize(new Dimension(100, 30));
            int j = i;
            playerButtons[i].addActionListener(e -> {
                if (players[j] instanceof HumanPlayer) {
                    players[j] = new ComputerPlayer(players[j]);
                    playerButtons[j].setText("Computer");
                } else {
                    players[j] = new HumanPlayer(players[j]);
                    playerButtons[j].setText("Human");
                }
            });

            if (i < 2) row1.add(playerButtons[i]);
            else {
                row2.add(playerButtons[i]);
                playerButtons[i].setVisible(fourPlayers);
            }
        }

        switchButton.addActionListener(e -> {
            playerCount = 6 - playerCount;
            fourPlayers = !fourPlayers;

            playerButtons[2].setVisible(fourPlayers);
            playerButtons[3].setVisible(fourPlayers);

            paintPlayers();

            if (fourPlayers) switchButton.setText("Mode: 4 Players"); // switch to 4
            else switchButton.setText("Mode: 2 Players"); // switch to 2
        });

        topPanel.add(switchButton);

        JPanel modesPanel = new JPanel(new BorderLayout());
        modesPanel.setPreferredSize(new Dimension(0, 200));
        modesPanel.add(topPanel, BorderLayout.NORTH);
        modesPanel.add(playersPanel, BorderLayout.CENTER);

        currentPlayerLabel.setOpaque(true);
        currentPlayerLabel.setBackground(colors[currentPlayer]);
        currentPlayerLabel.setPreferredSize(new Dimension(100, 50));
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentPlayerLabel.setVerticalAlignment(SwingConstants.CENTER);

        JPanel currentPlayerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 200));
        currentPlayerPanel.add(currentPlayerLabel);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setPreferredSize(new Dimension(250, 0));
        controlPanel.add(modesPanel, BorderLayout.NORTH);
        controlPanel.add(currentPlayerPanel, BorderLayout.CENTER);

        return controlPanel;
    }

    private JPanel createBoardPanel() {
        JPanel board = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int cell = 50;
        int sep = 15;

        for (int r = 0; r < Board.HEIGHT * 2 - 1; r++) {
            for (int c = 0; c < Board.WIDTH * 2 - 1; c++) {
                gbc.gridx = c;
                gbc.gridy = r;

                int i = r / 2, j = c / 2;
                if (r % 2 == 0 && c % 2 == 0) {
                    boardButtons[i][j] = new JButton();
                    boardButtons[i][j].setBackground(defaultColor);
                    boardButtons[i][j].setPreferredSize(new Dimension(cell, cell));
                    boardButtons[i][j].addActionListener(e -> movePiece(i, j));
                    board.add(boardButtons[i][j], gbc);
                } else if (r % 2 == 0) {
                    JButton v = new JButton();
                    verticalWButtons[i][j] = v;
                    v.setBackground(defaultColor);
                    v.setPreferredSize(new Dimension(sep, cell));
                    v.addActionListener(e -> placeWall(i, j, true));
                    board.add(v, gbc);
                } else if (c % 2 == 0) {
                    JButton h = new JButton();
                    horizontalWButtons[i][j] = h;
                    h.setBackground(defaultColor);
                    h.setPreferredSize(new Dimension(cell, sep));
                    h.addActionListener(e -> placeWall(i, j, false));
                    board.add(h, gbc);
                } else {
                    JPanel p = new JPanel();
                    p.setBackground(Color.WHITE);
                    p.setPreferredSize(new Dimension(sep, sep));
                    board.add(p, gbc);
                }
            }
        }

        return board;
    }

    private void movePiece(int x, int y) {
        Player p = players[currentPlayer];
        StepAction s = null;
        try {
            s = new StepAction(Direction.makeDirection(p.getX(), p.getY(), x, y));
            p.perform(s);
        } catch (ActionFormatException | IllegalActionException e) {
            ErrorWindow.showError(e.getMessage());
            return;
        }

        for (int i = 0; i < playerCount; i++) {
            if (i != currentPlayer && players[i].getX() == x && players[i].getY() == y) {
                players[i].setDead();
                deadPlayerCount++;
            }
        }

        if (deadPlayerCount == playerCount - 1 || p.hasWon()) {
            Window.winMessage(currentPlayer + 1);
            return;
        }

        do {
            currentPlayer = (currentPlayer + 1) % playerCount;
        } while (players[currentPlayer].isDead());

        paintPlayers();
    }

    private void placeWall(int x, int y, boolean isVertical) {
        Player p = players[currentPlayer];
        Wall w = new Wall(x, y, isVertical);
        WallAction wa = new WallAction(w);

        try {
            Board.checkWallValidity(w, Arrays.copyOf(players, playerCount));
            p.perform(wa);
        } catch (IllegalActionException e) {
            ErrorWindow.showError(e.getMessage());
            assert(false);
            return;
        }

        do {
            currentPlayer = (currentPlayer + 1) % playerCount;
        } while (players[currentPlayer].isDead());

        paintWalls();
        paintPlayers();
    }

    public static void main(String[] args) {
        QuoridorGUI gui = new QuoridorGUI();
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);
    }
}