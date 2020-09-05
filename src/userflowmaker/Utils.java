package userflowmaker;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Utils {
	
	public static BufferedImage resizeImageWithOutline(BufferedImage originalImage, int targetWidth, int targetHeight, int outlineWidth) {
	    BufferedImage resizedImage = new BufferedImage(targetWidth + 2 * outlineWidth, targetHeight + 2 * outlineWidth, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = resizedImage.createGraphics();
	    graphics2D.drawImage(originalImage, outlineWidth, outlineWidth, targetWidth, targetHeight, null);
	    if (outlineWidth > 0) {
	    	graphics2D.setColor(Color.BLACK);
	    	graphics2D.setStroke(new BasicStroke(outlineWidth));
	    	graphics2D.drawRect(0, 0, resizedImage.getWidth(), resizedImage.getHeight());
	    }
	    graphics2D.dispose();
	    return resizedImage;
	}
	
	public static int colorToInt(Color color) {
		int Red = (color.getRed() << 16) & 0x00FF0000;
	    int Green = (color.getGreen() << 8) & 0x0000FF00;
	    int Blue = color.getBlue() & 0x000000FF;
	    return 0xFF000000 | Red | Green | Blue;
	}
	
	public static void clearBufferedImage(BufferedImage image) {
		Graphics2D g = (Graphics2D)image.getGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		g.fillRect(0, 0, image.getWidth(), image.getHeight());
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}
	
	public static Rectangle normalizeRect(Rectangle v) {
		return new Rectangle(Math.min(v.x, v.x + v.width), Math.min(v.y, v.y + v.height), Math.abs(v.width), Math.abs(v.height));
	}
}
