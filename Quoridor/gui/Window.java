package gui;

import javax.swing.*;
import java.awt.*;

public class Window {
	public static void errorWindow(String message) {
		JWindow window = new JWindow();
		
		JLabel label = new JLabel(message);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setBackground(new Color(255, 200, 200));
		label.setOpaque(true);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		
		window.add(label);
		window.setMinimumSize(new Dimension(0, 50));
		window.pack();
		
		// center on screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - window.getWidth()) / 2;
		int y = (screen.height - window.getHeight()) / 2;
		
		window.setLocation(x, y);
		window.setAlwaysOnTop(true);
		window.setVisible(true);
		
		// close after 2 seconds
		Timer timer = new Timer(2000, e -> window.dispose());
		timer.setRepeats(false);
		timer.start();
	}

	public static void winMessage(int winner) {
		JWindow window = new JWindow();

		JLabel label = new JLabel(
				"🎉 Player " + winner + " has won! Congratulations!",
				SwingConstants.CENTER
		);

		label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		label.setBackground(new Color(200, 255, 200));
		label.setOpaque(true);
		label.setFont(new Font("Arial", Font.BOLD, 16));

		window.add(label);

		// small fixed size
		window.setSize(320, 80);

		// center on screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - window.getWidth()) / 2;
		int y = (screen.height - window.getHeight()) / 2;

		window.setLocation(x, y);
		window.setAlwaysOnTop(true);
		window.setVisible(true);

		// stays open until you close it manually later
	}
}
