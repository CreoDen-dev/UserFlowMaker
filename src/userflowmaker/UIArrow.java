package userflowmaker;

import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape;
import javafx.geometry.Point2D;

public class UIArrow extends Path {
	
	public UIArrow(Point2D... points) {
		super();
		strokeProperty().bind(fillProperty());
		setFill(Color.BLACK);
		
		getElements().add(new MoveTo(points[0].getX(), points[0].getY()));
		for (int i = 1; i < points.length; i++) {
			getElements().add(new LineTo(points[i].getX(), points[i].getY()));
		}
	}
	
	public int size() {
		return getElements().size();
	}

	public void setX(int n, double x) {
		PathElement e = getElements().get(n);
		if (e instanceof MoveTo) {
			((MoveTo)e).setX(x);
		}
		else if (e instanceof LineTo) {
			((LineTo)e).setX(x);
		}
	}
	
	public void setY(int n, double y) {
		PathElement e = getElements().get(n);
		if (e instanceof MoveTo) {
			((MoveTo)e).setX(y);
		}
		else if (e instanceof LineTo) {
			((LineTo)e).setX(y);
		}
	}
}
