package userflowmaker;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

public class ScreenSpaceCapture extends JPanel implements MouseInputListener, KeyListener, WindowFocusListener {
	private static final long serialVersionUID = -873686036116837437L;
	
	private JFrame parent;
	private Rectangle region;
	private Point onScreenOffset;
	private Robot robot;
	private static final BasicStroke stroke = new BasicStroke(3);
	
	public ScreenSpaceCapture() {
		/*this.parent = new JDialog();
		this.parent.setModal(true);
		this.parent.setUndecorated(true);
		this.parent.setResizable(false);
		this.parent.setAlwaysOnTop(true);
		this.parent.setAutoRequestFocus(true);
		this.parent.setOpacity(0.3f);
		this.parent.add(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.parent.addKeyListener(this);
		this.parent.pack();
		*/
		this.parent = new JFrame();
		this.parent.setUndecorated(true);
		this.parent.setExtendedState(0);
		this.parent.setResizable(false);
		this.parent.setAlwaysOnTop(true);
		this.parent.setAutoRequestFocus(true);
		this.parent.setOpacity(0.3f);
		this.parent.add(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.parent.addWindowFocusListener(this);
		this.parent.addKeyListener(this);
		//System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
		try {
			this.robot = new Robot();
		}
		catch(AWTException ex) {
			ex.printStackTrace();
		}
		this.region = new Rectangle();
		this.onScreenOffset = new Point();
	}
	
	public void beginSelection() {
		this.parent.setVisible(true);
	}
	
	public BufferedImage createCapture() {
		return this.robot.createScreenCapture(this.region);
	}
	
	public Rectangle getSelectionRegion() {
		return this.region;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.region.x = e.getXOnScreen();
		this.region.y = e.getYOnScreen();
		this.region.width = 0;
		this.region.height = 0;
		this.onScreenOffset.x = e.getX() - e.getXOnScreen();
		this.onScreenOffset.y = e.getX() - e.getYOnScreen();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.region.x = Math.min(this.region.x, this.region.x + this.region.width);
		this.region.y = Math.min(this.region.y, this.region.y + this.region.height);
		this.region.width = Math.abs(this.region.width);
		this.region.height = Math.abs(this.region.height);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		this.region.width = e.getXOnScreen() - this.region.x;
		this.region.height = e.getYOnScreen() - this.region.y;
		this.parent.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		((Graphics2D)g).setStroke(stroke);
		g.setColor(Color.red);
		//g.translate(this.onScreenOffset.x, this.onScreenOffset.y);
		g.drawRect(Math.min(this.region.x, this.region.x + this.region.width), Math.min(this.region.y, this.region.y + this.region.height),
				Math.abs(this.region.width), Math.abs(this.region.height));
		//g.translate(-this.onScreenOffset.x, -this.onScreenOffset.y);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.mouseReleased(null);
			this.parent.setVisible(false);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void windowGainedFocus(WindowEvent arg0) {
		SwingUtilities.invokeLater(() -> {
			this.parent.setExtendedState(JFrame.MAXIMIZED_BOTH);
		});
		
	}

	@Override
	public void windowLostFocus(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
