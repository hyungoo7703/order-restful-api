package toyproject.ataglance.menu.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class CreateOrAddOrder {

	private int tableNumber;
	private Map<String, String> orders = new HashMap<>(); // { 메뉴 * 수량 : 가격 } 형태로 전송
	
}
