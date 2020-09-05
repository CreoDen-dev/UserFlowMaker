package userflowmaker;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

public class Node extends ImageView {
	
	public BufferedImage content;
	
	public double prevX, prevY;
	
	private ArrayList<UIArrow> links;
	
	public Node(BufferedImage content) {
		super();
		this.content = content;
		this.setImage(SwingFXUtils.toFXImage(this.content, null));
		this.links = new ArrayList<>();
	}
	
	public void addLink(UIArrow link) {
		this.links.add(link);
	}
	
	public void doSetX(double x) {
		setX(x);
		for (UIArrow it : this.links) {
			it.setX(it.size() - 1, x);
		}
	}
	
	public void doSetY(double y) {
		setY(y);
		for (UIArrow it : this.links) {
			it.setY(it.size() - 1, y);
		}
	}
}
