package userflowmaker.screenshotEditor;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import userflowmaker.screenshotEditor.ShotDrawer.Palette;

public class PaintSelector extends JPanel {
	private static final long serialVersionUID = 5794883830681869475L;

	protected JFrame parent;
	
	private Palette palette;
	private ColorCell primary, secondary;
	
	public PaintSelector(Palette palette) {
		this.parent = new JFrame("Palette");
		this.parent.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		int xcells = palette.colors.size();
		int ycells = 2;
		
		this.palette = palette;
		
		JPanel pprim = new JPanel();
		pprim.setLayout(new BoxLayout(pprim, BoxLayout.X_AXIS));
		JPanel psec = new JPanel();
		psec.setLayout(new BoxLayout(psec, BoxLayout.X_AXIS));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		for (Color it : this.palette.colors) {
			pprim.add(new ColorCell(it, (cell) -> {
				this.primary.selcted = false;
				this.primary.repaint();
				this.primary = cell;
				this.primary.selcted = true;
				this.primary.repaint();
			}));
			psec.add(new ColorCell(it, (cell) -> {
				this.secondary.selcted = false;
				this.secondary.repaint();
				this.secondary = cell;
				this.secondary.selcted = true;
				this.secondary.repaint();
			}));
			
			if (it == Color.BLACK) {
				this.primary = (ColorCell)pprim.getComponent(pprim.getComponentCount() - 1);
				this.primary.selcted = true;
			}
			if (it == Color.RED) {
				this.secondary = (ColorCell)psec.getComponent(psec.getComponentCount() - 1);
				this.secondary.selcted = true;
			}
		}
		
		this.add(pprim);
		this.add(psec);
		this.setPreferredSize(new Dimension(xcells * 60, ycells * 60));
		this.parent.add(this);
		this.parent.pack();
	}
	
	public void close() {
		this.parent.dispose();
	}
	
	public void show() {
		this.parent.setVisible(true);
	}
	
	public Color getPrimary() {
		return this.primary.color;
	}
	
	public Color getSecondary() {
		return this.secondary.color;
	}
}
