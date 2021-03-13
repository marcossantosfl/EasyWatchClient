package controller;

import javafx.scene.text.Font;

public class FontController {

	public Font getFontOpenSansBold(int size) {
		return Font.loadFont(getClass().getResource("/view/font/Open_Sans/OpenSans-Bold.ttf").toString(), size);
	}
	
	public Font getFontOpenSansRegular(int size) {
		return Font.loadFont(getClass().getResource("/view/font/Open_Sans/OpenSans-Regular.ttf").toString(), size);
	}

}
