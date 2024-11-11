import javax.swing.*;


public class Main {

	public static void main(String[] args) {

		int boardWidth = 600;
		int boardHeight = boardWidth;
		
		
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(boardWidth,boardHeight);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		SnakeGame sg = new SnakeGame(boardWidth,boardHeight);
		frame.add(sg);
		frame.pack();
		sg.requestFocus();
		
				
	}

}
