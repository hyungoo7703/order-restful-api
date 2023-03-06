package toyproject.ataglance.menu.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
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
import toyproject.ataglance.menu.dto.*;
import toyproject.ataglance.menu.entity.Category;
import toyproject.ataglance.menu.model.*;
import toyproject.ataglance.menu.repository.CategoryRepository;
import toyproject.ataglance.menu.repository.DetailRepository;
import toyproject.ataglance.menu.repository.ThemeRepository;
import toyproject.ataglance.menu.service.MenuService;

@CrossOrigin
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@Slf4j
public class MenuController {

    private final MenuService menuService;

    /* 차후 주문 시스템이라든지 연결을 위해 메뉴판 형태로 가공하여 출력 */

    //== Menu ==//

    @GetMapping // 메뉴판 조회 (enabled 활성화만)
    public ResponseEntity<List<CategoryDto>> findAll() {
        List<CategoryDto> categoryDtos = menuService.findAll();
        if (categoryDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    //== Category ==//

    @PostMapping("/category") // 카테고리 등록 (예: 메인, 사이드 등등)
    public ResponseEntity<CategoryCreatedDto> save(@RequestBody CategoryCreateDto createDto) {
        return new ResponseEntity<>(menuService.save(new CategoryModel(createDto.getId(), createDto.getName())), HttpStatus.CREATED);
    }

    //== Theme ==//

    @PostMapping("/theme") // 테마 등록 (예: 스테이크, 여름, 그릴, 야채 등등)
    public ResponseEntity<ThemeCreatedDto> save(@RequestBody ThemeCreateDto createDto) {
        return new ResponseEntity<>(menuService.save(new ThemeModel(createDto.getId(), createDto.getName(), createDto.getCategoryId())), HttpStatus.CREATED);
    }

    //== Detail ==//

    @PostMapping("/detail") // 테마에 맞춰 메뉴 상세 등록
    public ResponseEntity<DetailCreatedDto> save(@RequestBody DetailCreateDto createDto) {
        log.info("isEvent = {}", createDto.isOnEvent());
        DetailCreatedDto createdDto = menuService.save(new DetailModel(createDto.getId(), createDto.getName(), createDto.getPrice(), createDto.getMargin(), createDto.getMemo(), createDto.isOnEvent(), createDto.getThemeId()));
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

}
