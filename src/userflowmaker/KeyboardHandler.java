package userflowmaker;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyboardHandler implements NativeKeyListener {
	
	public static void setup() {
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
		GlobalScreen.addNativeKeyListener(new KeyboardHandler());
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent key) {
		//System.out.println(key.getRawCode());
		if (key.getRawCode() >= 97 && key.getRawCode() <= 105) {
			UserFlowMaker.instance.createCapture(key.getRawCode() - 96);
		}
		if (key.getRawCode() >= 48 && key.getRawCode() <= 57) {
			UserFlowMaker.instance.createCapture(key.getRawCode() - 48);
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
