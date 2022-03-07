package toyproject.ataglance.menu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import toyproject.ataglance.menu.entity.Detail;
import toyproject.ataglance.menu.entity.Theme;
import toyproject.ataglance.menu.model.Menus;
import toyproject.ataglance.menu.repository.DetailRepository;
import toyproject.ataglance.menu.repository.ThemeRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

	private final ThemeRepository themeRepository;
	private final DetailRepository detailRepository;

	/* 차후 주문 시스템이라든지 연결을 위해 메뉴판 형태로 가공하여 출력 */
	@GetMapping
	public ResponseEntity<List<Menus>> findAll() {
		List<Menus> theMenus = new ArrayList<>(); // 실제 메뉴판 출력

		Iterable<Theme> themes = new ArrayList<>();
		themes = themeRepository.findAll();
		for (Theme theme : themes) {
			Menus menu = new Menus(); 
			menu.setTheme(theme.getName()); 
			
			Iterable<Detail> details = new ArrayList<>(); 
			details = detailRepository.findByThemeId(theme.getId());
			
			Map<String, Integer> menus = new HashMap<String, Integer>();
			for (Detail detail : details) {
				menus.put(detail.getName(), detail.getPrice());
			}
			menu.setMenus(menus);
			
			theMenus.add(menu);
		}
		if(theMenus.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(theMenus, HttpStatus.OK);
	}

}
