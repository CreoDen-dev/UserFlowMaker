package userflowmaker;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import userflowmaker.ShotDrawer.Palette;

public class PaintSelector extends JPanel implements ColorCellSelectionListener {
	private static final long serialVersionUID = 5794883830681869475L;

	protected JFrame parent;
	
	private Palette palette;
	private ColorCell selection;
	private ArrayList<ColorCellSelectionListener> listeners;
	
	public PaintSelector(Palette palette) {
		this.parent = new JFrame();
		this.parent.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		int xcells = Math.min(palette.colors.size(), 10);
		int ycells = palette.colors.size() / xcells + 1;
		this.parent.setSize(xcells * 20, ycells * 20 + 40);
		
		this.palette = palette;
		this.listeners = new ArrayList<>();
		this.setLayout(new GridLayout(ycells, xcells));
		
		for (Color it : this.palette.colors) {
			this.add(new ColorCell(it, this));
		}
		
		this.selection = (ColorCell)this.getComponent(0);
		this.selection.selcted = true;
		
		this.parent.add(this);
	}
	
	public void close() {
		this.parent.dispose();
	}
	
	public void show() {
		this.parent.setVisible(true);
	}
	
	public void addColorCellSelectionListener(ColorCellSelectionListener listener) {
		if (listener != null) {
			this.listeners.add(listener);
		}
	}
	
	public Color getSelected() {
		return this.selection.color;
	}

	@Override
	public void onSelected(ColorCell cell) {
		this.selection.selcted = false;
		this.selection.repaint();
		this.selection = cell;
		this.selection.selcted = true;
		this.selection.repaint();
		for (ColorCellSelectionListener it : this.listeners) {
			it.onSelected(cell);
		}
	}
}
