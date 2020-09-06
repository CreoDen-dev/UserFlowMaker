package userflowmaker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

public class UpdateManager {
	
	public static final String currentVersion = "UserFlowMaker_v1.1";
	private static String updateSource = "https://github.com/CreoDen-dev/UserFlowMaker/raw/master/build/";
	
	public static boolean isUpdateavailable() {
		try {
			URL log = new URL(updateSource + "versions.txt");
			Scanner scanner = new Scanner(log.openStream());
			String line = "";
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
			}
			scanner.close();
			if (!line.contentEquals(currentVersion)) {
				return true;
			}
		}
		catch (Exception e) {
		}
		return false;
	}
	
	public static boolean autoUpdate() {
		try {
			URL log = new URL(updateSource + "versions.txt");
			Scanner scanner = new Scanner(log.openStream());
			String line = "";
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
			}
			scanner.close();
			if (!line.contentEquals(currentVersion)) {
				URL update = new URL(updateSource + line + ".jar");
				ReadableByteChannel rbc = Channels.newChannel(update.openStream());
				FileOutputStream fos = new FileOutputStream(new File("./" + line + ".jar"));
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				rbc.close();
				
				File self = new File(UpdateManager.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				self.deleteOnExit();
				return true;
			}
		} catch (IOException e) {
		}
		return false;
	}
}
