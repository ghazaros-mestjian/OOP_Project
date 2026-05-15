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

/**
 * Graphical User Interface for the Quoridor game.
 * <p>
 * Handles all UI rendering, player interaction, setup screen,
 * game board visualization, and turn processing including AI moves.
 * </p>
 * <p>
 * The interface is divided into:
 * start screen, setup screen, and game screen (CardLayout).
 * </p>
 */
public class QuoridorGUI extends JFrame {

	/**
	 * Delay in milliseconds before a computer player performs its move.
	 */
	private final int COMPUTER_TURN_TIME = 1200;

	/**
	 * All possible players in the game (supports up to 4 players).
	 * Can be HumanPlayer or ComputerPlayer depending on setup.
	 */
	private final Player[] players = {
			new HumanPlayer("Player 1", Board.HEIGHT - 1, Board.WIDTH / 2, Direction.UP),
			new HumanPlayer("Player 2", 0, Board.WIDTH / 2, Direction.DOWN),
			new HumanPlayer("Player 3", Board.HEIGHT / 2, Board.WIDTH - 1, Direction.LEFT),
			new HumanPlayer("Player 4", Board.HEIGHT / 2, 0, Direction.RIGHT),
	};

	/**
	 * Number of active players in the current game (2 or 4).
	 */
	private int playerCount = 2;

	/**
	 * Indicates whether the game is in 4-player mode.
	 */
	private boolean fourPlayers = false;

	/**
	 * Index of the current player whose turn is active.
	 */
	private int currentPlayer = 0;

	/**
	 * Number of eliminated players during the game.
	 */
	private int deadPlayerCount = 0;

	/**
	 * True if the game has started.
	 */
	private boolean gameStarted = false;

	/**
	 * True if a computer player is currently executing its move.
	 */
	private boolean computerTurn = false;

	/**
	 * Border highlighting the active player's position.
	 */
	private static final Border ACTIVE_BORDER =
			BorderFactory.createLineBorder(Color.BLACK, 4);

	/**
	 * Default border for non-active board cells.
	 */
	private static final Border NORMAL_BORDER =
			BorderFactory.createEmptyBorder(3, 3, 3, 3);

	/**
	 * Grid of buttons representing player movement cells.
	 */
	private final JButton[][] boardButtons =
			new JButton[Board.HEIGHT][Board.WIDTH];

	/**
	 * Grid of buttons representing vertical walls.
	 */
	private final JButton[][] verticalWButtons =
			new JButton[Board.HEIGHT][Board.WIDTH - 1];

	/**
	 * Grid of buttons representing horizontal walls.
	 */
	private final JButton[][] horizontalWButtons =
			new JButton[Board.HEIGHT - 1][Board.WIDTH];

	/**
	 * Labels showing each player's wall count or status.
	 */
	private final JLabel[] wallLabels = new JLabel[4];

	/**
	 * Panels holding player information in the side UI.
	 */
	private final JPanel[] playerField = new JPanel[4];

	/**
	 * Player colors used in UI representation.
	 */
	private static final Color[] colors = {
			new Color(0xFF9090),
			new Color(0x9090FF),
			new Color(0x90FF90),
			new Color(0xFFFF90)
	};

	/**
	 * Default board background color.
	 */
	private static final Color defaultColor = new Color(0xE7ECFF);

	/**
	 * Start screen icon used for play button.
	 */
	private final ImageIcon startIcon =
			new ImageIcon(getClass().getResource("/resources/startIcon.png"));

	/**
	 * Player piece icons (one per player color).
	 */
	private final ImageIcon[] playerIcons = {
			resizeIcon("/resources/redPlayer.png", 80, 80),
			resizeIcon("/resources/bluePlayer.png", 80, 80),
			resizeIcon("/resources/greenPlayer.png", 80, 80),
			resizeIcon("/resources/yellowPlayer.png", 80, 80)
	};

	/**
	 * Constructs the Quoridor GUI window and initializes all screens
	 * (start, setup, game) using CardLayout.
	 */
	public QuoridorGUI() {
		setTitle("Quoridor");
		setLayout(new CardLayout());
		setSize(768, 768);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel gameScreen = new JPanel(new BorderLayout());
		gameScreen.add(createInfoPanel(), BorderLayout.WEST);
		gameScreen.add(createBoardPanel(), BorderLayout.CENTER);

		add(createStartPanel(), "start");
		add(createSetupPanel(), "setup");
		add(gameScreen, "game");

		CardLayout cl = (CardLayout) getContentPane().getLayout();
		cl.show(getContentPane(), "start");

		pack();
	}

	/**
	 * Resizes an image icon to the specified dimensions.
	 *
	 * @param path   resource path of the image
	 * @param width  desired width
	 * @param height desired height
	 * @return resized ImageIcon
	 */
	private ImageIcon resizeIcon(String path, int width, int height) {
		return new ImageIcon(new ImageIcon(getClass().getResource(path))
				.getImage()
				.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	/**
	 * Updates player positions on the board UI.
	 * Clears previous icons and highlights the active player.
	 */
	private void paintPlayers() {
		for (int r = 0; r < Board.HEIGHT; r++) {
			for (int c = 0; c < Board.WIDTH; c++) {
				boardButtons[r][c].setIcon(null);
				boardButtons[r][c].setBorder(NORMAL_BORDER);
			}
		}

		for (int i = 0; i < playerCount; i++) {
			int x = players[i].getX();
			int y = players[i].getY();

			if (!players[i].isDead()) {
				boardButtons[x][y].setIcon(playerIcons[i]);
				if (i == currentPlayer)
					boardButtons[x][y].setBorder(ACTIVE_BORDER);
			} else {
				boardButtons[x][y].setBackground(defaultColor);
			}
		}
	}

	/**
	 * Updates wall UI based on current board state.
	 */
	private void paintWalls() {
		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				if (j < Board.WIDTH - 1)
					verticalWButtons[i][j].setBackground(
							Board.checkWall(i, j, true) ? Color.DARK_GRAY : defaultColor);

				if (i < Board.HEIGHT - 1)
					horizontalWButtons[i][j].setBackground(
							Board.checkWall(i, j, false) ? Color.DARK_GRAY : defaultColor);
			}
		}
	}

	/**
	 * Updates the wall labels for all players.
	 */
	private void updateWallLabels() {
		for (int i = 0; i < playerCount; i++) {
			if (players[i].isDead())
				wallLabels[i].setText(players[i].getName() + " - Dead");
			else
				wallLabels[i].setText(players[i].getName() + " - Walls: " + players[i].getWallCount());
		}
	}

	/**
	 * Creates the start screen panel.
	 *
	 * @return start panel
	 */
	private JPanel createStartPanel() {
		JPanel startScreen = new JPanel(new BorderLayout());

		JButton playButton = new JButton(startIcon);
		playButton.setBounds(250, 200, 500, 500);
		playButton.setBorderPainted(false);
		playButton.setFocusPainted(false);

		playButton.addActionListener(e ->
				((CardLayout) getContentPane().getLayout())
						.show(getContentPane(), "setup"));

		startScreen.add(playButton);
		return startScreen;
	}

	/**
	 * Creates the setup screen where players are configured.
	 *
	 * @return setup panel
	 */
	private JPanel createSetupPanel() {
		JButton switchButton = new JButton("Mode: 2 Players");
		switchButton.setBackground(defaultColor);
		switchButton.setPreferredSize(new Dimension(200, 40));

		JPanel playersPanel = new JPanel(new GridLayout(4, 2, 20, 20));

		JPanel[] playerRows = new JPanel[4];
		JButton[] playerButtons = new JButton[4];
		JTextField[] nameFields = new JTextField[4];
		JPanel[] nameRows = new JPanel[4];

		for (int i = 0; i < 4; i++) {

			playerButtons[i] = new JButton("Human");
			playerButtons[i].setBackground(colors[i]);
			playerButtons[i].setPreferredSize(new Dimension(100, 30));

			int j = i;

			playerButtons[i].addActionListener(e -> {
				if (players[j] instanceof HumanPlayer) {
					players[j] = new ComputerPlayer("Computer " + (j + 1), players[j]);
					playerButtons[j].setText("Computer");
					nameFields[j].setText(players[j].getName());
					nameFields[j].setEditable(false);
				} else {
					players[j] = new HumanPlayer("Player " + (j + 1), players[j]);
					playerButtons[j].setText("Human");
					nameFields[j].setText(players[j].getName());
					nameFields[j].setEditable(true);
				}
			});

			playerRows[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
			playerRows[i].add(playerButtons[i]);

			nameRows[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
			nameRows[i].add(new JLabel("Player " + (i + 1) + ":"));

			nameFields[i] = new JTextField(players[i].getName(), 12);
			nameRows[i].add(nameFields[i]);

			if (i >= 2) {
				playerRows[i].setVisible(fourPlayers);
				nameRows[i].setVisible(fourPlayers);
			}

			playersPanel.add(playerRows[i]);
			playersPanel.add(nameRows[i]);
		}

		switchButton.addActionListener(e -> {
			playerCount = 6 - playerCount;
			fourPlayers = !fourPlayers;

			for (int i = 2; i < 4; i++) {
				playerRows[i].setVisible(fourPlayers);
				nameRows[i].setVisible(fourPlayers);
			}

			playersPanel.revalidate();
			playersPanel.repaint();
			paintPlayers();

			switchButton.setText(fourPlayers ? "Mode: 4 Players" : "Mode: 2 Players");
		});

		JPanel bottomPanel = new JPanel();
		JButton startButton = new JButton("Start Game");

		startButton.addActionListener(e -> {
			gameStarted = true;
			paintPlayers();
			paintWalls();

			for (int i = 0; i < 4; i++) {
				if (players[i] instanceof HumanPlayer hp)
					hp.setName(nameFields[i].getText());

				playerField[i].setVisible(i < playerCount);
				updateWallLabels();
			}

			((CardLayout) getContentPane().getLayout())
					.show(getContentPane(), "game");

			processNext();
		});

		bottomPanel.add(startButton);

		JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.add(switchButton, BorderLayout.NORTH);
		controlPanel.add(playersPanel, BorderLayout.CENTER);
		controlPanel.add(bottomPanel, BorderLayout.SOUTH);

		return controlPanel;
	}

	/**
	 * Creates the info panel showing player stats and reset button.
	 *
	 * @return info panel
	 */
	private JPanel createInfoPanel() {
		JPanel playersPanel = new JPanel();
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < 4; i++) {
			playerField[i] = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

			wallLabels[i] = new JLabel(players[i].getName() + " - Walls: " + players[i].getWallCount());
			wallLabels[i].setPreferredSize(new Dimension(200, 30));
			wallLabels[i].setOpaque(true);
			wallLabels[i].setBackground(colors[i]);

			playerField[i].add(wallLabels[i]);
			playerField[i].setVisible(i < playerCount);

			playersPanel.add(playerField[i]);
		}

		JButton stopButton = new JButton("Reset");
		stopButton.addActionListener(e -> resetGame());

		JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.add(playersPanel, BorderLayout.CENTER);
		controlPanel.add(stopButton, BorderLayout.SOUTH);

		return controlPanel;
	}

	/**
	 * Creates the main game board with movement and wall controls.
	 *
	 * @return board panel
	 */
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

					boardButtons[i][j].addActionListener(e -> {
						if (!computerTurn)
							movePiece(i, j);
					});

					board.add(boardButtons[i][j], gbc);
				} else if (r % 2 == 0) {
					verticalWButtons[i][j] = new JButton();
					verticalWButtons[i][j].setBackground(defaultColor);
					verticalWButtons[i][j].setPreferredSize(new Dimension(sep, cell));

					verticalWButtons[i][j].addActionListener(e -> {
						if (!computerTurn)
							placeWall(new Wall(i, j, true));
					});

					board.add(verticalWButtons[i][j], gbc);
				} else if (c % 2 == 0) {
					horizontalWButtons[i][j] = new JButton();
					horizontalWButtons[i][j].setBackground(defaultColor);
					horizontalWButtons[i][j].setPreferredSize(new Dimension(cell, sep));

					horizontalWButtons[i][j].addActionListener(e -> {
						if (!computerTurn)
							placeWall(new Wall(i, j, false));
					});

					board.add(horizontalWButtons[i][j], gbc);
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

	/**
	 * Processes the next turn in the game.
	 * If the current player is a computer, executes an AI move.
	 */
	private void processNext() {
		computerTurn = false;

		if (players[currentPlayer] instanceof ComputerPlayer cp) {
			computerTurn = true;

			Timer timer = new Timer(COMPUTER_TURN_TIME, e -> {
				while (true) {
					core.action.Action action = cp.randomAction();

					if (action instanceof StepAction sa) {
						if (movePiece(cp.getX() + sa.getDirection().dx,
								cp.getY() + sa.getDirection().dy)) break;
					} else if (action instanceof WallAction wa) {
						if (placeWall(wa.getWall())) break;
					} else {
						Window.errorMessage("Unexpected Error!", this);
						break;
					}
				}
			});

			timer.setRepeats(false);
			timer.start();
		}
	}

	/**
	 * Moves the current player to a new position.
	 *
	 * @param x target x coordinate
	 * @param y target y coordinate
	 * @return true if move was successful
	 */
	private boolean movePiece(int x, int y) {
		if (!gameStarted) return false;

		Player p = players[currentPlayer];

		try {
			StepAction sa =
					new StepAction(Direction.makeDirection(p.getX(), p.getY(), x, y));

			p.perform(sa);
		} catch (ActionFormatException | IllegalActionException e) {
			if (!computerTurn)
				Window.errorMessage(e.getMessage(), this);
			return false;
		}

		for (int i = 0; i < playerCount; i++) {
			if (i != currentPlayer &&
					!players[i].isDead() &&
					players[i].getX() == x &&
					players[i].getY() == y) {

				players[i].setDead();
				deadPlayerCount++;
				updateWallLabels();
				break;
			}
		}

		if (deadPlayerCount == playerCount - 1 || p.hasWon()) {
			paintPlayers();
			setEnabled(false);

			Window.winMessage(players[currentPlayer].getName(),
					this::resetGame, this);

			return true;
		}

		do {
			currentPlayer = (currentPlayer + 1) % playerCount;
		} while (players[currentPlayer].isDead());

		paintPlayers();
		processNext();

		return true;
	}

	/**
	 * Places a wall on the board for the current player.
	 *
	 * @param w wall to place
	 * @return true if placement was successful
	 */
	private boolean placeWall(Wall w) {
		if (!gameStarted) return false;

		Player p = players[currentPlayer];

		try {
			Board.checkWallValidity(w, Arrays.copyOf(players, playerCount));
			p.perform(new WallAction(w));
		} catch (IllegalActionException e) {
			if (!computerTurn)
				Window.errorMessage(e.getMessage(), this);
			return false;
		}

		updateWallLabels();
		paintWalls();

		do {
			currentPlayer = (currentPlayer + 1) % playerCount;
		} while (players[currentPlayer].isDead());

		paintPlayers();
		processNext();

		return true;
	}

	/**
	 * Resets the game and restarts a new GUI instance.
	 */
	private void resetGame() {
		Board.resetBoard();

		QuoridorGUI gui = new QuoridorGUI();
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);

		this.dispose();
	}

	/**
	 * Main entry point of the Quoridor game.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		QuoridorGUI gui = new QuoridorGUI();
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
	}
}