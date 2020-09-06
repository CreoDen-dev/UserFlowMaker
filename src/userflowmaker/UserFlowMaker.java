package userflowmaker;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import userflowmaker.screenshotEditor.ShotDrawer;
import userflowmaker.workspace.Workspace;

public class UserFlowMaker extends JFrame {
	private static final long serialVersionUID = 6147145829028296960L;
	
	public static String projectPath = "./data/";
	
	public static UserFlowMaker instance;
	
	private ScreenSpaceCapture scrCapture;
	private Workspace workspace;
	
	//TMP
	public static int id = 0;
	
	public UserFlowMaker() {
		this.setTitle("UserFlowMaker");
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
		
		projectPath = "./data_" + System.currentTimeMillis() + "/";
	}
	
	public void createCapture(int type) {
		BufferedImage scr = this.scrCapture.createCapture();
		final BufferedImage bi = Utils.resizeImageWithOutline(scr, scr.getWidth(), scr.getHeight(), 3);
		if (type == 2) {
			ShotDrawer sd = new ShotDrawer(bi, (image) -> {
				saveImage(image, projectPath);
				Platform.runLater(() -> {
					this.workspace.createNode(image);
				});
			});
			sd.show();
		}
		else {
			saveImage(bi, projectPath);
			Platform.runLater(() -> {
				this.workspace.createNode(bi);
			});
		}
	}
	
	private void saveImage(BufferedImage image, String dir) {
		File fout = new File(dir);
		fout.mkdirs();
		File file = new File(dir + (id++) + ".png");
		while (file.exists()) {
			file = new File(dir + (id++) + ".png");
		}
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
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
	*/
	
	public static void main(String[] args) {
		if (UpdateManager.isUpdateavailable()) {
			if (JOptionPane.showConfirmDialog(null, "New version available. Update now?") == JOptionPane.YES_OPTION) {
				UpdateManager.autoUpdate();
				return;
			}
		}
		UserFlowMaker.instance = new UserFlowMaker();
		UserFlowMaker.instance.setVisible(true);
		KeyboardHandler.setup();
	}
}
