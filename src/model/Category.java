package model;

public class Category {
	public static String getCategory(int category) {
		switch (category) {
		case 1:
			return "ADVENTURE";
		case 2:
			return "ACTION";
		case 3:
			return "FANTASY";
		case 4:
			return "FICTION";
		case 5:
			return "ROMANCE";
		case 6:
			return "DRAMA";
		case 7:
			return "CRIME";
		case 8:
			return "HORROR";
		}

		return null;
	}
}
