package userflowmaker;

import java.awt.image.BufferedImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Workspace extends Pane {
	
	private final int gridScaleX;
	private final int gridScaleY;
	
	private Pane contentsPane;
	private double prevX, prevY, zoom;
	
	public Workspace(int gridX, int gridY) {
		this.gridScaleX = gridX;
		this.gridScaleY = gridY;
		this.zoom = 0.05;
		
		this.contentsPane = new Pane();
		this.contentsPane.setPrefSize(this.getWidth(), this.getHeight());
		this.contentsPane.setMaxSize(this.getWidth(), this.getHeight());
		this.contentsPane.setMinSize(this.getWidth(), this.getHeight());
		this.getChildren().add(this.contentsPane);
		
		this.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		
		this.setOnMousePressed((MouseEvent e) -> {
			if (e.isAltDown() && e.getButton() == MouseButton.PRIMARY) {
				this.prevX = e.getX();
				this.prevY = e.getY();
				e.consume();
			}
		});
		this.setOnMouseDragged((MouseEvent e) -> {
			if (e.isAltDown() && e.getButton() == MouseButton.PRIMARY) {
				this.contentsPane.getChildren().forEach((node) -> {
					node.setTranslateX(node.getTranslateX() + e.getX() - this.prevX);
					node.setTranslateY(node.getTranslateY() + e.getY() - this.prevY);
				});
				//this.contentsPane.setTranslateX(this.contentsPane.getTranslateX() + e.getX() - this.prevX);
				//this.contentsPane.setTranslateY(this.contentsPane.getTranslateY() + e.getY() - this.prevY);
				this.prevX = e.getX();
				this.prevY = e.getY();
				e.consume();
			}
		});
		this.setOnScroll((ScrollEvent e) -> {
			if (e.isAltDown()) {
				double zoomFactor = 1.0 + (e.getDeltaY() > 0 ? this.zoom : -this.zoom);
				this.contentsPane.setScaleX(this.contentsPane.getScaleX() * zoomFactor);
				this.contentsPane.setScaleY(this.contentsPane.getScaleY() * zoomFactor);
			}
		});
	}
	
	public void createNode(BufferedImage image) {
		double prefX = -this.contentsPane.getTranslateX() + this.getWidth() / 2;
		double prefY = -this.contentsPane.getTranslateY() + this.getHeight() / 2;
		for (javafx.scene.Node it : this.getChildren()) {
			if (it instanceof Node) {
				Node nd = (Node)it;
				if (nd.getX() - prefX < gridScaleX) {
					prefX += gridScaleX;
				}
				if (nd.getY() - prefY < gridScaleY) {
					prefY += gridScaleY;
				}
			}
		}
		Node node = new Node(image);
		node.setX(prefX);
		node.setY(prefY);
		node.setOnMousePressed((MouseEvent e) -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				node.prevX = e.getX();
				node.prevY = e.getY();
				e.consume();
			}
		});
		node.setOnMouseDragged((MouseEvent e) -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				double dx = (e.getX() - node.prevX);// * this.contentsPane.getScaleX();
				double dy = (e.getY() - node.prevY);// * this.contentsPane.getScaleY();
				node.setX(node.getX() + dx);
				node.setY(node.getY() + dy);
				node.prevX = e.getX();
				node.prevY = e.getY();
				
				e.consume();
			}
		});
		node.setOnMouseReleased((MouseEvent e) -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				node.setX(Math.round(node.getX() / this.gridScaleX) * this.gridScaleX);
				node.setY(Math.round(node.getY() / this.gridScaleY) * this.gridScaleY);
				e.consume();
			}
		});
		this.contentsPane.getChildren().add(node);
	}
}
