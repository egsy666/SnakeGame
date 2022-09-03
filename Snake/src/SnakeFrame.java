import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class SnakeFrame extends JFrame{
	
	SnakeFrame() {
		
		this.add(new SnakePanel());
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}

}
