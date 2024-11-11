import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.*;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

	private class Tile {
		int x;
		int y;

		Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	int boardWidth;
	int boardHeight;
	int tileSize = 25;

	Tile snakeHead;
	ArrayList<Tile> snakeBody;

	Tile food;
	Random random;

	// game setting

	Timer gameLoop;
	int speedX;
	int speedY;
	boolean over = false;

	public SnakeGame(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.black);
		addKeyListener(this);
		setFocusable(true);

		snakeHead = new Tile(4, 4);
		snakeBody = new ArrayList<Tile>();

		food = new Tile(10, 10);
		random = new Random();
		placeFood();

		speedX = 0;
		speedY = 0;

		gameLoop = new Timer(100, this);
		gameLoop.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
        //Grid Lines
//        for(int i = 0; i < boardWidth/tileSize; i++) {
//            //(x1, y1, x2, y2)
//            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
//            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); 
//        }

        //Food
        g.setColor(Color.red);
        // g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);

        //Snake Head
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x, snakeHead.y, tileSize, tileSize);
        // g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);
        
        //Snake Body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
		}

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (over) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
	}


	public void placeFood() {

		food.x = random.nextInt(boardWidth / tileSize);
		food.y = random.nextInt(boardHeight / tileSize);

	}

	public boolean carptiMi(Tile tile1, Tile tile2) {
		return tile1.x == tile2.x && tile1.y == tile2.y;
	}

	public void move() {
		if (carptiMi(snakeHead, food)) {
			snakeBody.add(new Tile(food.x, food.y));
			placeFood();
		}

		// snake body

		for (int i = snakeBody.size() - 1; i >= 0; i--) {
			Tile snakePart = snakeBody.get(i);
			if (i == 0) {
				snakePart.x = snakeHead.x;
				snakePart.y = snakeHead.y;
			} else {
				Tile prevSnakePart = snakeBody.get(i - 1);
				snakePart.x = prevSnakePart.x;
				snakePart.y = prevSnakePart.y;
			}
		}
		// snake head

		snakeHead.x += speedX;
		snakeHead.y += speedY;
		
		
		// Game Over
		
		for(int i = 0; i < snakeBody.size(); i++) {
			Tile snakePart = snakeBody.get(i);
			if(carptiMi(snakeHead, snakePart) ) {
				over = true;
			}
		}
		
		if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > boardHeight) {
			over = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();  // Hareket fonksiyonu
		repaint(); // her 100 mili saniyede yeniden boyama işlemi yapmamızı sağlar
		if(over) {
			gameLoop.stop();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP && speedY != 1) {
			speedX = 0;
			speedY = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN && speedY != -1) {
			speedX = 0;
			speedY = 1;
		} else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT && speedX != 1) {
			speedX = -1;
			speedY = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT && speedX != -1) {
			speedX = 1;
			speedY = 0;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
