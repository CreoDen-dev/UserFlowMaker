package userflowmaker.workspace;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Link extends Pane {
	
	private ArrayList<LinkNode> nodes;
	
	public Link(Node a, Node b) {
		super();
		this.nodes = new ArrayList<>();
		LinkNode an = new LinkNode(a);
		LinkNode bn = new LinkNode(b);
		this.nodes.add(an);
		this.nodes.add(bn);
		this.buildLine();
	}
	
	public void buildLine() {
		this.getChildren().clear();
		ObservableList<javafx.scene.Node> children = this.getChildren();
		for (int i = 0; i < this.nodes.size() - 2; i++) {
			Line line = new Line();
			LinkNode start = this.nodes.get(i);
			LinkNode end = this.nodes.get(i + 1);
			line.startXProperty().bind(start.xProperty());
			line.startYProperty().bind(start.yProperty());
			line.endXProperty().bind(end.xProperty());
			line.endYProperty().bind(end.yProperty());
			children.add(line);
		}
	}
}
