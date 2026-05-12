package gui;

import core.*;
import core.action.StepAction;
import core.exception.ActionFormatException;
import core.exception.IllegalActionException;
import core.player.ComputerPlayer;
import core.player.HumanPlayer;
import core.player.Player;

import javax.swing.*;
import java.awt.*;

public class QuoridorGUI extends JFrame {
	private Player[] players;
	private int playerCount;
	private boolean fourPlayers;
	private int currentPlayer;
	private int deadPlayerCount;
	
	private JButton[][] boardButtons;
	JLabel currentPlayerLabel;
	
	private static final Color[] colors = { new Color(0xFF9090),
			new Color(0x9090FF),
			new Color(0x90FF90),
			new Color(0xFFFF90)
	};
	
	private static final Color defaultColor = new Color(0xE7ECFF);
	
	public QuoridorGUI() {
		initializePlayers();
		boardButtons = new JButton[Board.HEIGHT][Board.WIDTH];
		currentPlayerLabel = new JLabel("Current Player");
		
		
		setTitle("Quoridor");
		setSize(768, 768);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(createControlPanel(), BorderLayout.WEST);
		add(createBoardPanel(), BorderLayout.CENTER);
		
		pack();
		
		paintPlayers();
	}
	
	private void paintPlayers() {
		for (int i = 0; i < 4; i++) {
			if (i < playerCount && !players[i].isDead())
				boardButtons[players[i].getX()][players[i].getY()].setBackground(colors[i]);
			else
				boardButtons[players[i].getX()][players[i].getY()].setBackground(defaultColor);
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
				}
				else {
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
				}
				else if (r % 2 == 0) {
					JButton v = new JButton();
					v.setBackground(defaultColor);
					v.setPreferredSize(new Dimension(sep, cell));
					v.addActionListener(e -> placeWall(i, j, true));
					board.add(v, gbc);
				}
				else if (c % 2 == 0) {
					JButton h = new JButton();
					h.setBackground(defaultColor);
					h.setPreferredSize(new Dimension(cell, sep));
					h.addActionListener(e -> placeWall(i, j, false));
					board.add(h, gbc);
				}
				else {
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
		} catch (ActionFormatException e) {
			ErrorWindow.showError(e.getMessage());
		}
		
		try {
			p.perform(s);
		} catch (IllegalActionException e) {
			ErrorWindow.showError(e.getMessage());
		}
		
		for (int i = 0; i < playerCount; i++) {
			if (i != currentPlayer && players[i].getX() == x && players[i].getY() == y) {
				players[i].setDead();
				deadPlayerCount--;
			}
		}
		
		//if (deadPlayerCount == playerCount - 1)
		//ErrorWindow.winMessage(currentPlayer);
		
		do {
			currentPlayer = (currentPlayer + 1) % playerCount;
		} while (players[currentPlayer].isDead());
		
		paintPlayers();
	}
	
	private void placeWall(int x, int y, boolean isVertical) {
	
	}
	
	public static void main(String[] args) {
		QuoridorGUI gui = new QuoridorGUI();
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
	}
}