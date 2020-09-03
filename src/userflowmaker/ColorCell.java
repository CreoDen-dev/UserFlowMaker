package userflowmaker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColorCell extends Component implements MouseListener {
	private static final long serialVersionUID = -3159675562895342697L;
	
	private ColorCellSelectionListener listener;
	public Color color;
	public Color selectedColor;
	public boolean selcted;
	
	public ColorCell(Color color, ColorCellSelectionListener listener) {
		this.color = color;
		this.selectedColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
		this.listener = listener;
		this.selcted = false;
		this.setSize(20, 20);
		this.addMouseListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(this.color);
		g.fillRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
		if (this.selcted) {
			g.setColor(this.selectedColor);
			g.drawRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
		}
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		this.listener.onSelected(this);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
}