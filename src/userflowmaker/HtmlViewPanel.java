package userflowmaker;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.net.URL;
import javax.swing.JPanel;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

public class HtmlViewPanel extends JPanel {
	private static final long serialVersionUID = -8321362697083183706L;
	
	private static final BasicStroke stroke = new BasicStroke(2);
	
	private WebView htmlView;
	private Pane viewCanvas;
	private Rectangle selection;
	private javafx.scene.shape.Rectangle fxSelection;
	private URL source;
	
	public HtmlViewPanel(URL html) {
		this.source = html;
		this.setLayout(new BorderLayout());
		JFXPanel jfxPanel = new JFXPanel();
		Platform.runLater( () -> {
		   WebView webView = new WebView();
		   this.htmlView = webView;
		   webView.getEngine().load(this.source.toString());
		   this.fxSelection = new javafx.scene.shape.Rectangle();
		   this.fxSelection.setStrokeWidth(3);
		   this.fxSelection.setStroke(javafx.scene.paint.Color.RED);
		   this.fxSelection.setFill(javafx.scene.paint.Color.TRANSPARENT);
		   this.viewCanvas = new Pane(this.fxSelection);
		   StackPane stack = new StackPane(webView, this.viewCanvas);
		   jfxPanel.setScene( new Scene( stack ) );
		});
		this.add(jfxPanel);
		this.selection = null;
	}
	
	public void setSelection(Rectangle selection) {
		this.selection = selection;
	}
	
	public void updateHtml() {
		Platform.runLater(() -> {
			this.htmlView.getEngine().reload();
		});
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (this.selection != null) {
			Platform.runLater(() -> {
				this.fxSelection.setX(this.selection.x);
				this.fxSelection.setY(this.selection.y);
				this.fxSelection.setWidth(this.selection.width);
				this.fxSelection.setHeight(this.selection.height);
			});
		}
	}
}
