package controller;

import javafx.scene.text.Font;

//controller
public class FontController {

	//load font bold
	public Font getFontOpenSansBold(int size) {
		return Font.loadFont(getClass().getResource("/view/font/Open_Sans/OpenSans-Bold.ttf").toString(), size);
	}
	
	//load fonte regular
	public Font getFontOpenSansRegular(int size) {
		return Font.loadFont(getClass().getResource("/view/font/Open_Sans/OpenSans-Regular.ttf").toString(), size);
	}

}
