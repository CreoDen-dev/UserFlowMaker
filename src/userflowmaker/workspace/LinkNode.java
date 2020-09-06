package userflowmaker.workspace;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class LinkNode extends javafx.scene.Node {
	
	private DoubleProperty xProperty;
	private DoubleProperty yProperty;
	
	public LinkNode() {
		super();
		this.xProperty = new SimpleDoubleProperty(0);
		this.yProperty = new SimpleDoubleProperty(0);
	}
	
	public LinkNode(Node node) {
		this();
		this.xProperty.bind(node.xProperty());
		this.yProperty.bind(node.yProperty());
	}
	
	public DoubleProperty xProperty() {
		return this.xProperty;
	}
	
	public DoubleProperty yProperty() {
		return this.yProperty;
	}
}
