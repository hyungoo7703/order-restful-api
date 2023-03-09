package toyproject.ataglance.menu.controller;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final CategoryRepository categoryRepository;
    private final ThemeRepository themeRepository;
    private final DetailRepository detailRepository;

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

    @PatchMapping("/category/{id}") // 카테고리 활성화/비활성화 (하위 활성화/비활성화 포함)
    public ResponseEntity<CategoryUpdatedDto> categoryDisabled(@PathVariable String id) {
        return new ResponseEntity<>(menuService.categoryDisabled(id), HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}") // 카테고리 삭제 (하위 삭제 포함)
    public ResponseEntity<String> deleteCategoryById(@PathVariable String id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>("카테고리 삭제가 완료 되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("카테고리 삭제가 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //== Theme ==//

    @PostMapping("/theme") // 테마 등록 (예: 스테이크, 여름, 그릴, 야채 등등)
    public ResponseEntity<ThemeCreatedDto> save(@RequestBody ThemeCreateDto createDto) {
        return new ResponseEntity<>(menuService.save(new ThemeModel(createDto.getId(), createDto.getName(), createDto.getCategoryId())), HttpStatus.CREATED);
    }

    @PatchMapping("/theme/{id}") // 테마 활성화/비활성화 (하위 활성화/비활성화 포함)
    public ResponseEntity<ThemeUpdatedDto> themeDisabled(@PathVariable String id) {
        return new ResponseEntity<>(menuService.themeDisabled(id), HttpStatus.OK);
    }
    
    @DeleteMapping("/theme/{id}") // 테마 삭제 (하위 삭제 포함)
    public ResponseEntity<String> deleteThemeById(@PathVariable String id) {
        try {
            themeRepository.deleteById(id);
            return new ResponseEntity<>("테마 삭제가 완료 되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("테마 삭제가 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //== Detail ==//

    @PostMapping("/detail") // 테마에 맞춰 메뉴 상세 등록
    public ResponseEntity<DetailCreatedDto> save(@RequestBody DetailCreateDto createDto) {
        log.info("isEvent = {}", createDto.isOnEvent());
        DetailCreatedDto createdDto = menuService.save(new DetailModel(createDto.getId(), createDto.getName(), createDto.getPrice(), createDto.getMargin(), createDto.getMemo(), createDto.isOnEvent(), createDto.getThemeId()));
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PatchMapping("/detail/{id}") // 메뉴 상세 활성화/비활성화
    public ResponseEntity<DetailUpdatedDto> detailDisabled(@PathVariable String id) {
        return new ResponseEntity<>(menuService.detailDisabled(id), HttpStatus.OK);
    }

    /*
    메뉴 상세는 수정 가능하도록 해야한다.
    (가격변동, 마진변동, 이벤트 적용여부 등 변경할 것들이 있기에)
     */
    @PutMapping("/detail/{id}")
    public ResponseEntity<DetailUpdatedDto> updateDetail(@PathVariable String id, @RequestBody DetailUpdateDto updateDto) {
        return new ResponseEntity<>(menuService.updateDetail(id, updateDto), HttpStatus.OK);
    }

    @DeleteMapping("/detail/{id}") // 디테일 삭제
    public ResponseEntity<String> deleteDetailById(@PathVariable String id) {
        try {
            detailRepository.deleteById(id);
            return new ResponseEntity<>("디테일(메뉴) 삭제가 완료 되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("디테일(메뉴) 삭제가 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
