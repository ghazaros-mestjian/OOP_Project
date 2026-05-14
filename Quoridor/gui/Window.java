package gui;

import javax.swing.*;
import java.awt.*;

public class Window {
	public static void errorMessage(String message, JFrame parent) {
		parent.setEnabled(false);
		
		JWindow window = new JWindow();
		
		JLabel label = new JLabel(message);
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		label.setBackground(new Color(255, 144, 144));
		label.setOpaque(true);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		
		window.add(label);
		window.setMinimumSize(new Dimension(0, 50));
		window.pack();
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - window.getWidth()) / 2 + 126;
		int y = (screen.height - window.getHeight()) / 2 - 12;
		
		window.setLocation(x, y);
		window.setAlwaysOnTop(true);
		window.setVisible(true);
		
		// close after 2 seconds
		Timer timer = new Timer(1800, e -> {
			window.dispose();
			parent.setEnabled(true);
		});
		timer.setRepeats(false);
		timer.start();
	}
	
	public static void winMessage(String winner, Runnable onReplay, JFrame parent) {
		parent.setEnabled(false); // block game interaction
		
		JWindow window = new JWindow();
		
		JPanel panel = new JPanel(new BorderLayout(30, 30));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		panel.setBackground(new Color(255, 208, 144, 219));
		
		JLabel label = new JLabel(winner + " has won! Congratulations!",
				SwingConstants.CENTER
		);
		
		label.setFont(new Font("Arial", Font.PLAIN, 18));
		label.setForeground(new Color(0, 0, 0));
		
		JButton replay = new JButton("Play Again");
		
		replay.addActionListener(e -> {
			window.dispose();
			parent.setEnabled(true); // re-enable game
			
			if (onReplay != null) {
				onReplay.run();
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(255, 208, 144));
		buttonPanel.add(replay);
		
		panel.add(label, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		window.add(panel);
		window.pack();
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(
				(screen.width - window.getWidth()) / 2 + 126,
				(screen.height - window.getHeight()) / 2 - 12
		);
		
		window.setAlwaysOnTop(true);
		window.setVisible(true);
	}
}