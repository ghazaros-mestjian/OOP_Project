import core.*;
import java.util.Scanner;

public class Quoridor {
	private Player[] players;

	private static final char vWall = '|';
	private static final char hWall = '\u2014';
	private static final char emptyCell = '\u00B7';

	public Quoridor(int playerCount) throws PlayerCountException {
		if (playerCount != 2 && playerCount != 4)
			throw new PlayerCountException("Wrong number of players: " + playerCount + ".");

		players = new Player[playerCount];

		players[0] = new HumanPlayer(Board.HEIGHT - 1, Board.WIDTH / 2, Direction.UP);
		players[1] = new HumanPlayer(0, Board.WIDTH / 2, Direction.DOWN);
		
		if (playerCount == 4) {
			players[2] = new HumanPlayer(Board.HEIGHT / 2, 0, Direction.LEFT);
			players[3] = new HumanPlayer(Board.HEIGHT / 2, Board.WIDTH - 1, Direction.RIGHT);
		}
	}

	public void print() {
		char[][] layout = new char[Board.HEIGHT * 2 - 1][Board.WIDTH * 2 - 1];

		for (int i = 0; i < Board.HEIGHT * 2 - 1; i++) {
			if (i % 2 != 0) {
				for (int j = 0; j < Board.WIDTH * 2 - 1; j++) {
					if (j % 2 != 0) layout[i][j] = ' ';
					else layout[i][j] = (Board.checkWall(i / 2, j / 2, i / 2 + 1, j / 2) ? hWall : ' ');
				}
			}
			else {
				for (int j = 0; j < Board.WIDTH * 2 - 1; j++) {
					if (j % 2 != 0) layout[i][j] = (Board.checkWall(i / 2, j / 2, i / 2, j / 2 + 1) ? vWall : ' ');
					else layout[i][j] = emptyCell;
				}
			}
		}

		for (Player p : players)
			layout[p.getX() * 2][p.getY() * 2] = p.getDirection().name().charAt(0);
		
		for (char[] row : layout) {
			for (char c : row)
				System.out.print(c);
			System.out.println();
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java quoridor.Quoridor.java NumberOfPlayers.");
			return;
		}

		Quoridor quoridor;
		try {
			int playerCount = Integer.parseInt(args[0]);
			quoridor = new Quoridor(playerCount);
		} catch (NumberFormatException e) {
			System.out.println("The number of players should be an integer.");
			return;
		} catch (PlayerCountException e) {
			System.out.println(e.getMessage());
			return;
		}

        Scanner input = new Scanner(System.in);
        quoridor.print();
        for (int index = 0; ; index=(index+1)%quoridor.players.length) {
            String actionType = input.next();
            Action action = null;
            try {
                if (actionType.equals("w")) {
                    action = new WallAction(new Wall(input.nextLine()));
                }
                else if (actionType.equals("s")) {
                    action = new StepAction(Direction.makeDirection(input.nextLine()));
                }
            } catch (ActionFormatException e) {
                System.out.println(e.getMessage());
            }

            quoridor.players[index].perform(action);
            quoridor.print();

            if (quoridor.players[index].hasWon()){
                System.out.println("Winner: Player " + index);
                break;
            }
        }

        //wrong action exception
        //play method
	}
}