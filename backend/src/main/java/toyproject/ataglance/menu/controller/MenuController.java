package toyproject.ataglance.menu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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

	/* 차후 주문 시스템이라든지 연결을 위해 메뉴판 형태로 가공하여 출력 */
	
	//== Menu ==//
	
	@GetMapping // 메뉴판 조회
	public ResponseEntity<List<Menus>> findAll() {
		List<Menus> theMenus = new ArrayList<>(); // 실제 메뉴판(The Menus) 출력

		Iterable<Theme> themes = new ArrayList<>();
		themes = themeRepository.findAll();
		for (Theme theme : themes) {
			Menus menu = new Menus(); 
			menu.setTheme(theme.getName()); 
			
			Iterable<Detail> details = new ArrayList<>(); 
			details = detailRepository.findByThemeId(theme.getId());
			
			Map<String, Integer> menus = new HashMap<String, Integer>(); // name : price 형태를 위해 Map으로 담아준다.
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
	
	//== Theme ==//
	
	@PostMapping("/theme") // 주제 등록 (예: 메인, 사이드 등등)
	public ResponseEntity<Theme> save(@RequestBody CreateTheme createTheme) {
		Theme theme = themeRepository.save(new Theme(createTheme.getThemeId(), createTheme.getThemeName(), true)); // default: enabled = true	
		
		return new ResponseEntity<>(theme, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/theme") // 주제 하나를 삭제 -> 주제 하위 메뉴 전부 같이 삭제
	public ResponseEntity<HttpStatus> deleteByThemeId(@RequestParam("themeId") String themeId) {
		if(detailRepository.findByThemeId(themeId).iterator().hasNext()) {
			detailRepository.deleteAllByThemeId(themeId);
		}
		themeRepository.deleteById(themeId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//== Detail ==//
	
	@PostMapping("/detail") // 주제에 맞춰 메뉴 상세 등록
	public ResponseEntity<Detail> save(@RequestBody CreateDetail createDetail) {
		Detail detail = detailRepository.save(new Detail(
														 createDetail.getDetailId(), 
														 createDetail.getDetailName(), 
														 createDetail.getPrice(), 
														 createDetail.getMemo(), 
														 true, 
														 createDetail.getThemeId()
														));
		
		return new ResponseEntity<>(detail, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/detail") // 메뉴 하나를 삭제 
	public ResponseEntity<HttpStatus> deleteByDetailId(@RequestParam("detailId") String detailId) {
		detailRepository.deleteById(detailId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	// TODO 수정 기능은 불필요 하다 느낌, 단 enabled = false는 추가 (활성화, 비활성화) 따라서 Update 관련 model 삭제
	// TODO theme 삭제 부분 성능 개선 필요할 듯 -> 생각해보기
	// TODO 셋트메뉴 
}
