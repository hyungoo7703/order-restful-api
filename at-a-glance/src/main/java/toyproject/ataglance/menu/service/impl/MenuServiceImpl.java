package toyproject.ataglance.menu.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
            if (!category.isEnabled()) {
                continue;
            }

            CategoryModel categoryModel = CategoryModel.fromEntity(category);
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(categoryModel.getId());
            categoryDto.setName(categoryModel.getName());

            List<ThemeDto> themeDtos = new ArrayList<>();
            for (ThemeModel themeModel : categoryModel.getThemeModels()) {
                if (!themeModel.isEnabled()) {
                    continue;
                }

                ThemeDto themeDto = new ThemeDto();
                themeDto.setId(themeModel.getId());
                themeDto.setName(themeModel.getName());

                List<DetailDto> detailDtos = new ArrayList<>();
                for (DetailModel detailModel : themeModel.getDetailModels()) {
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
    public CategoryCreatedDto save(CategoryModel categoryModel) {
        Category category = categoryRepository.save(categoryModel.toEntity());
        CategoryCreatedDto createdDto = new CategoryCreatedDto();
        createdDto.setId(category.getId());
        createdDto.setName(category.getName());
        createdDto.setDateCreated(category.getDateCreated());
        return createdDto;
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
}
