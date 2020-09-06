package userflowmaker.workspace;

import java.awt.image.BufferedImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

public class Node extends ImageView {
	
	public BufferedImage content;
	
	public double prevX, prevY;
	
	public Node(BufferedImage content) {
		super();
		this.content = content;
		this.setImage(SwingFXUtils.toFXImage(this.content, null));
	}
}
