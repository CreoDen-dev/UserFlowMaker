package userflowmaker;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class ShotDrawer extends JPanel implements MouseInputListener, KeyListener {
	private static final long serialVersionUID = 6276947582746074137L;
	
	public static interface ShotDrawerCallback {
		public void onFinished(BufferedImage image);
	}
	
	private JFrame parent;
	private ShotDrawerCallback callback;
	private PaintSelector paintSelector;
	private BufferedImage image;
	private BufferedImage overlay;
	private BufferedImage shapesOverlay;
	private Rectangle shapeCoords;
	
	public ShotDrawer(BufferedImage image, ShotDrawerCallback callback) {
		this.parent = new JFrame();
		this.parent.setUndecorated(true);
		this.parent.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.parent.setSize(image.getWidth(), image.getHeight());
		this.parent.setLocationRelativeTo(null);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.parent.addKeyListener(this);
		this.parent.add(this);
		
		this.callback = callback;
		this.image = image;
		this.overlay = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.overlay.createGraphics();
		this.shapesOverlay = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.shapesOverlay.createGraphics();
		this.paintSelector = new PaintSelector(new Palette(Color.RED, Color.BLACK, Color.GREEN, Color.BLUE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.ORANGE, Color.MAGENTA));
	}
	
	public void show() {
		this.paintSelector.show();
		this.parent.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(this.image, 0, 0, null);
		g.drawImage(this.overlay, 0, 0, null);
		g.drawImage(this.shapesOverlay, 0, 0, null);
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
		this.shapeCoords = new Rectangle(e.getX(), e.getY(), 0, 0);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		Graphics2D g = (Graphics2D)this.overlay.getGraphics();
		g.drawImage(this.shapesOverlay, 0, 0, this.shapesOverlay.getWidth(), this.shapesOverlay.getHeight(), null);
		Utils.clearBufferedImage(this.shapesOverlay);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if ((e.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) != 0) {
			if (e.getX() < this.overlay.getWidth() && e.getY() < this.overlay.getHeight()) {
				this.shapeCoords.width = e.getX() - this.shapeCoords.x;
				this.shapeCoords.height = e.getY() - this.shapeCoords.y;
				redrawOverlayRect(this.paintSelector.getPrimary(), e.isShiftDown());
			}
		}
		if ((e.getModifiersEx() & MouseEvent.BUTTON3_DOWN_MASK) != 0) {
			if (e.getX() < this.shapesOverlay.getWidth() && e.getY() < this.shapesOverlay.getHeight()) {
				this.shapeCoords.width = e.getX() - this.shapeCoords.x;
				this.shapeCoords.height = e.getY() - this.shapeCoords.y;
				redrawOverlayRect(this.paintSelector.getSecondary(), e.isShiftDown());
			}
		}
		this.repaint();
	}
	
	private void redrawOverlayRect(Color color, boolean filled) {
		Graphics2D g = (Graphics2D)this.shapesOverlay.getGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g.fillRect(0, 0, this.shapesOverlay.getWidth(), this.shapesOverlay.getHeight());
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setColor(color);
		Rectangle shape = Utils.normalizeRect(this.shapeCoords);
		if (filled) {
			g.fillRect(shape.x, shape.y, shape.width, shape.height);
		}
		else {
			g.drawRect(shape.x, shape.y, shape.width, shape.height);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
	
	public static class Palette {
		
		ArrayList<Color> colors;
		
		public Palette(Color... colors) {
			this.colors = new ArrayList<>();
			for (Color it : colors) {
				this.colors.add(it);
			}
		}
		
		public void add(Color color) {
			this.colors.add(color);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_R) {
			Utils.clearBufferedImage(this.overlay);
			this.repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.paintSelector.close();
			this.parent.dispose();
			Graphics g = this.image.createGraphics();
			g.drawImage(this.overlay, 0, 0, null);
			if (this.callback != null) {
				this.callback.onFinished(image);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
