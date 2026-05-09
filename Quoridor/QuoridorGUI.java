import core.*;

import javax.swing.*;
import java.awt.*;

public class QuoridorGUI extends JFrame {
	private Player[] players;
	private int playerCount;
	private boolean fourPlayers;
	private int currentPlayer;
	
	private JButton[][] boardButtons;
	JLabel currentPlayerLabel;
	
	private static final Color[] colors = { new Color(0xFF9090),
			new Color(0x9090FF),
			new Color(0x90FF90),
			new Color(0xFFFF90)
	};
	
	public QuoridorGUI() {
		initializePlayers();
		boardButtons = new JButton[Board.HEIGHT][Board.WIDTH];
		currentPlayerLabel = new JLabel("Current Player");
		
		
		setTitle("Quoridor");
		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		add(createControlPanel(), BorderLayout.WEST);
		add(createBoardPanel(), BorderLayout.CENTER);
		
		paintPlayers();
	}
	
	private void paintPlayers() {
		for (int i = 0; i < playerCount; i++)
			boardButtons[players[i].getX()][players[i].getY()].setBackground(colors[i]);
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
	}
	
	private JPanel createControlPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(0, 75));
		JButton switchButton = new JButton("Mode: 2 Players");
		switchButton.setBackground(new Color(0xE7E4FB));
		switchButton.setPreferredSize(new Dimension(200, 40));
		topPanel.add(switchButton);
		
		JPanel playersPanel = new JPanel();
		playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
		
		JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		
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
			
			if (fourPlayers) switchButton.setText("Mode: 4 Players"); // switch to 4
			else switchButton.setText("Mode: 2 Players"); // switch to 2
		});
		
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
		JPanel boardPanel = new JPanel(new GridLayout(Board.HEIGHT, Board.WIDTH));
		
		for (int i = 0; i < Board.HEIGHT; i++) {
			for (int j = 0; j < Board.WIDTH; j++) {
				boardButtons[i][j] = new JButton();
				int r = i, c = j;
				boardButtons[i][j].addActionListener(e -> movePlayer(r, c));
				boardPanel.add(boardButtons[i][j]);
			}
		}
		
		return boardPanel;
	}
	
	private void movePlayer(int x, int y) {
	
	}
	
	public static void main(String[] args) {
		QuoridorGUI gui = new QuoridorGUI();
		gui.setVisible(true);
	}
}