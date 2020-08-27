package userflowmaker;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

public class UserFlowMaker extends JFrame {
	private static final long serialVersionUID = 6147145829028296960L;
	
	public static final String projectPath = "./data/";
	
	public static UserFlowMaker instance;
	
	private ScreenSpaceCapture scrCapture;
	private Workspace workspace;
	
	public UserFlowMaker() {
		KeyboardHandler.setup();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(800, 460);
		this.setLocationRelativeTo(null);
		this.scrCapture = new ScreenSpaceCapture();
		this.scrCapture.beginSelection();
		
		JFXPanel jfxPanel = new JFXPanel();
		Platform.runLater(() -> {
			this.workspace = new Workspace(50, 50);
			jfxPanel.setScene(new Scene(this.workspace));
		});
		this.add(jfxPanel);
	}
	
	public void createCapture(int type) {
		Platform.runLater(() -> {
			this.workspace.createNode(this.scrCapture.createCapture());
		});
	}
	
	private void saveHtml(String nodes, String links) {
		String head = "<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<title>Page Title</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\n";
		String tail = "</body>\r\n" + 
				"</html>";
		File file = new File(projectPath + "sheet.html");
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(head + nodes + tail);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private URL prepareProject() {
		File file = new File(projectPath + "sheet.html");
		File dir = new File(projectPath);
		if (!file.exists()) {
			dir.mkdir();
			saveHtml("", "");
		}
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Rectangle getNodeCaptureFromGrid(Point pos) {
		Rectangle res = new Rectangle();
		Rectangle capt = UserFlowMaker.instance.scrCapture.getSelectionRegion();
		float wCoef = 2.5f;
		float hCoef = 2.0f;
		res.x = (int)(pos.x * capt.width * wCoef + capt.width * wCoef / 2.0 - capt.width / 2.0);
		res.y = (int)(pos.y * capt.height * hCoef + capt.height * hCoef / 2.0 - capt.height / 2.0);
		res.width = capt.width;
		res.height = capt.height;
		return res;
	}
	
	public static void main(String[] args) {
		UserFlowMaker.instance = new UserFlowMaker();
		UserFlowMaker.instance.setVisible(true);
	}
}
