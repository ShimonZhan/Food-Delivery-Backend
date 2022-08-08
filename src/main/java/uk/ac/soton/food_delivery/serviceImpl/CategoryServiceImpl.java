package uk.ac.soton.food_delivery.serviceImpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.Category;
import uk.ac.soton.food_delivery.entity.DO.Restaurant;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.mapper.CategoryMapper;
import uk.ac.soton.food_delivery.service.CategoryService;
import uk.ac.soton.food_delivery.service.OssService;
import uk.ac.soton.food_delivery.service.RestaurantService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;

/**
 * @author ShimonZhan
 * @since 2022-04-22 23:21:41
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private OssService ossService;

    @Resource
    private RestaurantService restaurantService;

    @Override
    public R addCategory(String name) {
        if (getOne(Wrappers.<Category>lambdaQuery().eq(Category::getName, name)) != null) {
            throw new ServicesException(ResultCode.CATEGORY_EXIST);
        }
        Category category = new Category();
        category.setName(name);
        save(category);
        return R.ok().data("categoryId", category.getId());
    }

    @Override
    public R removeCategory(Long categoryId) {
        if (getById(categoryId) == null) {
            throw new ServicesException(ResultCode.CATEGORY_NOT_EXIST);
        }
        if (restaurantService.count(Wrappers.<Restaurant>lambdaQuery().eq(Restaurant::getCategoryId, categoryId)) > 0) {
            throw new ServicesException(ResultCode.CATEGORY_HAS_RESTAURANT);
        }
        removeById(categoryId);
        return R.ok();
    }

    @Override
    public R updateCategory(Category category) {
        if (getById(category.getId()) == null) {
            throw new ServicesException(ResultCode.CATEGORY_NOT_EXIST);
        }
        updateById(category);
        return R.ok();
    }

    @Override
    public R updateCategoryPhoto(Long categoryId, MultipartFile file) {
        Category category = getById(categoryId);
        if (category == null) {
            throw new ServicesException(ResultCode.CATEGORY_NOT_EXIST);
        }
        String link = ossService.uploadFile("category/" + categoryId, file, "!category");
        category.setPhoto(link);
        updateById(category);
        return R.ok();
    }
}
