package toyproject.ataglance.menu.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class Menus {

	private String theme;
	private Map<String, String> menus = new HashMap<>();
	
	protected Menus() {}

	public Menus(String theme) {
		this.theme = theme;
	}
	
}
