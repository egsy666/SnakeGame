import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener {
	
	static final int GAME_HEIGHT = 500;
	static final int GAME_WIDTH = 500;
	static final Dimension size = new Dimension(GAME_HEIGHT,GAME_WIDTH);
	static final int gridSize = 25;
	static final int availableGrid = GAME_WIDTH / gridSize;
	static final int DELAY= 100;
	Random random = new Random();
	int [] x = new int[gridSize*2];
	int [] y = new int[gridSize*2];
	int bodylength = 3;
	int foodX;
	int foodY;
	int apples = 0;
	Graphics graphic;
	Image image;
	boolean running;
	char direction = 'D';
	Timer timer; // man braucht einen timer damit sich das bild bewegt, und einen delay
	
	
	
	SnakePanel()  {
		
		this.setPreferredSize(size);
		this.setFocusable(true);
		this.setBackground(Color.BLACK);
		this.addKeyListener(new AL());
		startGame();
		
	}
	
	public void startGame() {
		newFood();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
	}
	
	public void newFood() {
		foodX = random.nextInt(availableGrid)*gridSize;
		foodY = random.nextInt(availableGrid)*gridSize;
	}
	

	public void move() {
		System.out.println(direction);
		for(int i = bodylength; i > 0; i--) {
			System.out.println(i);
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			x[0] += 0;
			y[0] -= gridSize;
			break;
		
		case'D':
			x[0] += 0;
			y[0] += gridSize;
			break;
		
		case 'R':
			x[0] += gridSize;
			y[0] += 0;
			break;
		
		case 'L':
			x[0] -= gridSize;
			y[0] += 0;
			break;
		}
	}
	
	public void checkCollission() {
		if(x[0] < 0 || x[0] > GAME_HEIGHT-gridSize || y[0] > GAME_HEIGHT-gridSize || y[0] < 0) {
			running = false;
		}
		
		for(int i = bodylength; i>0; i--) {
			if(x[i] == x[0] && y[i] == y[0]) {
				running = false;
			}
		}
		
		if(x[0] == foodX && y[0] == foodY) {
			bodylength++;
			apples++;
			newFood();
		}
		
	}
	
	public void paintComponent(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphic = image.getGraphics();
		draw(graphic);
		g.drawImage(image,0,0,this);
		
	}
	
	public void draw(Graphics g) {
		if(running) {
			for(int i = 0; i<GAME_WIDTH; i+=gridSize) {
				g.setColor(Color.white);
				g.drawLine(i,0,i,GAME_HEIGHT);
			}
			
			for(int i = 0; i<GAME_HEIGHT; i+=gridSize) {
				g.setColor(Color.white);
				g.drawLine(0,i,GAME_WIDTH,i);
			}
			
			g.setColor(Color.red);
			g.fillRect(foodX, foodY, gridSize, gridSize);
			
			for(int i = 0; i<bodylength; i++) {
				if(i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], gridSize, gridSize);
				} else {
					g.setColor(Color.BLUE);
					g.fillRect(x[i], y[i], gridSize, gridSize);
				}
				
			}
		} else {
			g.setColor(Color.red);
			
			Font myFont = new Font("Default",Font.BOLD,30);
			g.setFont(myFont);
			FontMetrics metrics = getFontMetrics(g.getFont()); // um die Maße der Font rauszufinden
			g.drawString("GAME OVER", (GAME_WIDTH - metrics.stringWidth("GAME OVER"))/ 2, GAME_HEIGHT/2);
			g.setColor(Color.red);
			g.setFont(myFont);
			g.drawString(String.valueOf(apples), (GAME_WIDTH-metrics.stringWidth(String.valueOf(apples))) / 2, 25);
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) { // die Panel Klasse implementiert einen ActionListener, der eig den gameloop macht;
		if(running) { // ein if, damit er nicht im while loop steckt und alles andere auch noch machen kann;
			move();
			checkCollission();
			
		}
		repaint();
		
	}
	
	public class AL extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) { // make sure keyPressed is NOT written KeyPressed
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				if(direction != 'D') {
					direction = 'U';
				}break;
				
			case KeyEvent.VK_S:
				if(direction != 'U') {
					direction = 'D';
				}break;
				
			case KeyEvent.VK_D:
				if(direction !='L') {
					direction = 'R';
				}break;
				
			case KeyEvent.VK_A:
				if(direction !='R') {
					direction = 'L';
				}break;
				
			}
	
		}
		
	}
}
