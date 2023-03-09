package toyproject.ataglance.menu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.ataglance.exception.ResourceNotFoundException;
import toyproject.ataglance.formatter.MyNumberFormatter;
import toyproject.ataglance.menu.dto.*;
import toyproject.ataglance.menu.entity.Category;
import toyproject.ataglance.menu.entity.Detail;
import toyproject.ataglance.menu.entity.Theme;
import toyproject.ataglance.menu.model.CategoryModel;
import toyproject.ataglance.menu.model.DetailModel;
import toyproject.ataglance.menu.model.ThemeModel;
import toyproject.ataglance.menu.repository.CategoryRepository;
import toyproject.ataglance.menu.repository.DetailRepository;
import toyproject.ataglance.menu.repository.ThemeRepository;
import toyproject.ataglance.menu.service.MenuService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final CategoryRepository categoryRepository;
    private final ThemeRepository themeRepository;
    private final DetailRepository detailRepository;
    private final MyNumberFormatter myNumberFormatter;

    @Override
    public List<CategoryDto> findAll() {
        Iterable<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for(Category category : categories) {
            //활성화 되지 않은 카테고리 제외
            if (!category.isEnabled()) {
                continue;
            }

            CategoryModel categoryModel = CategoryModel.fromEntity(category);
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(categoryModel.getId());
            categoryDto.setName(categoryModel.getName());

            List<ThemeDto> themeDtos = new ArrayList<>();
            for (ThemeModel themeModel : categoryModel.getThemeModels()) {
                //활성화 되지 않은 테마 제외
                if (!themeModel.isEnabled()) {
                    continue;
                }

                ThemeDto themeDto = new ThemeDto();
                themeDto.setId(themeModel.getId());
                themeDto.setName(themeModel.getName());

                List<DetailDto> detailDtos = new ArrayList<>();
                for (DetailModel detailModel : themeModel.getDetailModels()) {
                    //활성화 되지 않은 디테일 제외
                    if (!detailModel.isEnabled()) {
                        continue;
                    }

                    DetailDto detailDto = new DetailDto();
                    detailDto.setId(detailModel.getId());
                    detailDto.setName(detailModel.getName());
                    detailDto.setPrice(myNumberFormatter.print(detailModel.getPrice(), Locale.KOREA));
                    if (detailModel.isOnEvent()) {
                        detailDto.setEventDiscountPrice(myNumberFormatter.print((int) (detailModel.getPrice() * 0.9), Locale.KOREA));
                    }
                    detailDto.setMemo(detailModel.getMemo());

                    detailDtos.add(detailDto);
                }
                themeDto.setDetails(detailDtos);
                themeDtos.add(themeDto);
            }
            categoryDto.setThemes(themeDtos);
            categoryDtos.add(categoryDto);
        }

        return categoryDtos;
    }

    @Override
    @Transactional
    public CategoryUpdatedDto categoryDisabled(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("카테고리를 찾을 수 없습니다.")
        );
        boolean enabled = (category.isEnabled() == true) ? false : true;
        categoryRepository.updateCategoryEnabledStatus(id, enabled);
        categoryRepository.updateThemeEnabledStatus(id, enabled);
        categoryRepository.updateDetailEnabledStatus(id, enabled);

        // 트랜잭션 범위 내에서 조회하여 최신값을 가져옴
        Category updatedCategory = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("카테고리를 찾을 수 없습니다.")
        );
        CategoryUpdatedDto updatedDto = new CategoryUpdatedDto();
        updatedDto.setId(updatedCategory.getId());
        updatedDto.setName(updatedCategory.getName());
        updatedDto.setDateUpdated(updatedCategory.getDateUpdated());
        updatedDto.setEnabled(updatedCategory.isEnabled());
        return updatedDto;
    }


    @Override
    public CategoryCreatedDto save(CategoryModel categoryModel) {
        Category category = categoryRepository.save(categoryModel.toEntity());
        CategoryCreatedDto createdDto = new CategoryCreatedDto();
        createdDto.setId(category.getId());
        createdDto.setName(category.getName());
        createdDto.setDateCreated(category.getDateCreated());
        return createdDto;
    }

    @Override
    @Transactional
    public ThemeUpdatedDto themeDisabled(String id) {
        Theme theme = themeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("테마를 찾을 수 없습니다.")
        );
        boolean enabled = (theme.isEnabled() == true) ? false : true;
        themeRepository.updateThemeEnabledStatus(id, enabled);
        themeRepository.updateDetailEnabledStatus(id, enabled);

        Theme updatedTheme = themeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("테마를 찾을 수 없습니다.")
        );
        ThemeUpdatedDto updatedDto = new ThemeUpdatedDto();
        updatedDto.setId(updatedTheme.getId());
        updatedDto.setName(updatedTheme.getName());
        updatedDto.setDateUpdated(updatedTheme.getDateUpdated());
        updatedDto.setEnabled(updatedTheme.isEnabled());
        return updatedDto;
    }

    @Override
    public ThemeCreatedDto save(ThemeModel themeModel) {
        Theme theme = themeRepository.save(themeModel.toEntity());
        ThemeCreatedDto createdDto = new ThemeCreatedDto();
        createdDto.setId(theme.getId());
        createdDto.setName(theme.getName());
        createdDto.setDateCreated(theme.getDateCreated());
        createdDto.setCategoryId(theme.getCategoryId());
        return createdDto;
    }

    @Override
    @Transactional
    public DetailUpdatedDto detailDisabled(String id) {
        Detail detail = detailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("메뉴를 찾을 수 없습니다.")
        );
        boolean enabled = (detail.isEnabled() == true) ? false : true;
        detailRepository.updateDetailEnabledStatus(id, enabled);
        Detail updatedDetail = detailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("메뉴를 찾을 수 없습니다.")
        );
        DetailUpdatedDto updatedDto = new DetailUpdatedDto();
        updatedDto.setId(updatedDetail.getId());
        updatedDto.setName(updatedDetail.getName());
        updatedDto.setPrice(updatedDetail.getPrice());
        updatedDto.setMargin(updatedDetail.getMargin());
        updatedDto.setDateUpdated(updatedDetail.getDateUpdated());
        updatedDto.setEnabled(updatedDetail.isEnabled());
        updatedDto.setOnEvent(updatedDetail.isOnEvent());
        return updatedDto;
    }

    @Override
    public DetailCreatedDto save(DetailModel detailModel) {
        Detail detail = detailRepository.save(detailModel.toEntity());
        DetailCreatedDto createdDto = new DetailCreatedDto();
        createdDto.setId(detail.getId());
        createdDto.setName(detail.getName());
        createdDto.setPrice(myNumberFormatter.print(detail.getPrice(), Locale.KOREA));
        if (detail.isOnEvent()) {
            createdDto.setEventDiscountPrice(myNumberFormatter.print((int) (detail.getPrice() * 0.9), Locale.KOREA));
        }
        createdDto.setMemo(detail.getMemo());
        createdDto.setDateCreated(detail.getDateCreated());
        createdDto.setOnEvent(detail.isOnEvent());
        createdDto.setThemeId(detail.getThemeId());
        return createdDto;
    }

    @Override
    @Transactional
    public DetailUpdatedDto updateDetail(String id, DetailUpdateDto updateDto) {
        Detail detail = detailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("메뉴를 찾을 수 없습니다.")
        );
        detailRepository.updateDetail(id, updateDto.getPrice(), updateDto.getMargin(), updateDto.isOnEvent());
        Detail updatedDetail = detailRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("메뉴를 찾을 수 없습니다.")
        );
        DetailUpdatedDto updatedDto = new DetailUpdatedDto();
        updatedDto.setId(updatedDetail.getId());
        updatedDto.setName(updatedDetail.getName());
        updatedDto.setPrice(updatedDetail.getPrice());
        updatedDto.setMargin(updatedDetail.getMargin());
        updatedDto.setDateUpdated(updatedDetail.getDateUpdated());
        updatedDto.setEnabled(updatedDetail.isEnabled());
        updatedDto.setOnEvent(updatedDetail.isOnEvent());
        return updatedDto;
    }
}
