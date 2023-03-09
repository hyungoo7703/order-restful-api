package toyproject.ataglance.menu.service;

import toyproject.ataglance.menu.dto.*;
import toyproject.ataglance.menu.model.CategoryModel;
import toyproject.ataglance.menu.model.DetailModel;
import toyproject.ataglance.menu.model.ThemeModel;

import java.util.List;

public interface MenuService {

    /*
    모든 메뉴 조회하기
    @return List<Menus>
     */
    List<CategoryDto> findAll();

    /*
    카테고리 비활성화
    @param String
    @return CategoryCreatedDto
     */
    CategoryUpdatedDto categoryDisabled(String id);

    /*
    카테고리 저장
    @param CategoryModel
    @return CategoryCreatedDto
     */
    CategoryCreatedDto save(CategoryModel categoryModel);

    /*
    테마 비활성화
    @param String
    @return ThemeUpdatedDto
     */
    ThemeUpdatedDto themeDisabled(String id);

    /*
    테마 저장
    @param ThemeModel
    @return ThemeCreatedDto
     */
    ThemeCreatedDto save(ThemeModel themeModel);

    /*
    상세 메뉴 비활성화
    @param String
    @return DetailUpdatedDto
     */
    DetailUpdatedDto detailDisabled(String id);

    /*
    상세 메뉴 저장
    @param DetailModel
    @return DetailCreatedDto
     */
    DetailCreatedDto save(DetailModel detailModel);
    
    /*
    상세 메뉴 수정
    @param DetailModel
    @return DetailCreatedDto
     */
    DetailUpdatedDto updateDetail(String id, DetailUpdateDto updateDto);
}
