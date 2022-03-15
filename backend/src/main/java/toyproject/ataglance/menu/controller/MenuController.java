package toyproject.ataglance.menu.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import toyproject.ataglance.formatter.MyNumberFormatter;
import toyproject.ataglance.menu.entity.Detail;
import toyproject.ataglance.menu.entity.Theme;
import toyproject.ataglance.menu.model.CreateDetail;
import toyproject.ataglance.menu.model.CreateTheme;
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
	private final MyNumberFormatter myNumberFormatter;

	/* 차후 주문 시스템이라든지 연결을 위해 메뉴판 형태로 가공하여 출력 */
	
	//== Menu ==//
	
	@GetMapping // 메뉴판 조회
	public ResponseEntity<List<Menus>> findAll() {
		
		List<Menus> theMenus = new ArrayList<>(); // 실제 메뉴판(The Menus) 출력

		Iterable<Theme> themes = themeRepository.findAll(); 
		for (Theme theme : themes) {
			Menus menu = new Menus(theme.getName());
			Map<String, String> menus = menu.getMenus(); // name : price 형태를 위해 Map으로 담아준다.
			
			Iterable<Detail> details = detailRepository.findByThemeId(theme.getId()); 
			for (Detail detail : details) {
				menus.put(detail.getName(), myNumberFormatter.print(detail.getPrice(), Locale.KOREA));
			}
			theMenus.add(menu);
		}
		
		if(theMenus.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(theMenus, HttpStatus.OK);
	}
	
	//== Theme ==//
	
	@PostMapping("/theme") // 주제 등록 (예: 메인, 사이드 등등)
	public ResponseEntity<Theme> save(@RequestBody CreateTheme createTheme) {
		
		Theme theme = themeRepository.save(new Theme(createTheme.getThemeId(), createTheme.getThemeName(), true)); // default: enabled = true	
		
		return new ResponseEntity<>(theme, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/theme/{themeId}") // 주제 하나를 삭제 -> 주제 하위 메뉴 전부 같이 삭제
	public ResponseEntity<HttpStatus> deleteByThemeId(@PathVariable("themeId") String themeId) {
		
		detailRepository.deleteAllByThemeId(themeId);	
		themeRepository.deleteById(themeId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//== Detail ==//
	
	@PostMapping("/detail") // 주제에 맞춰 메뉴 상세 등록
	public ResponseEntity<Detail> save(@RequestBody CreateDetail createDetail) throws ParseException {
		
		Detail detail = detailRepository.save(new Detail(
														 createDetail.getDetailId(), 
														 createDetail.getDetailName(), 
														 myNumberFormatter.parse(createDetail.getPrice(), Locale.KOREA), 
														 createDetail.getMemo(), 
														 true, 
														 createDetail.getThemeId()
														));
		
		return new ResponseEntity<>(detail, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/detail/{detailId}") // 메뉴 하나를 삭제 
	public ResponseEntity<HttpStatus> deleteByDetailId(@PathVariable("detailId") String detailId) {
		
		detailRepository.deleteById(detailId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	// TODO 수정 기능은 불필요 하다 느낌, 단 enabled = false는 추가 (활성화, 비활성화) -> 추후 이를 통해 어떻게 표현 할 것인지 고민
	// TODO 셋트메뉴 
}
