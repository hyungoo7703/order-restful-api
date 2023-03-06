package toyproject.ataglance.menu.service;

import toyproject.ataglance.menu.dto.CategoryCreatedDto;
import toyproject.ataglance.menu.dto.CategoryDto;
import toyproject.ataglance.menu.dto.DetailCreatedDto;
import toyproject.ataglance.menu.dto.ThemeCreatedDto;
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
    카테고리 저장
    @param CategoryModel
    @return CategoryCreatedDto
     */
    CategoryCreatedDto save(CategoryModel categoryModel);

    /*
    테마 저장
    @param ThemeModel
    @return ThemeCreatedDto
     */
    ThemeCreatedDto save(ThemeModel themeModel);

    /*
    상세 메뉴 저장
    @param DetailModel
    @return DetailCreatedDto
     */
    DetailCreatedDto save(DetailModel detailModel);
}
