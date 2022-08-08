package uk.ac.soton.food_delivery.service;

import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import uk.ac.soton.food_delivery.utils.R;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
public interface CategoryService extends IService<Category> {

    R addCategory(String name);

    R removeCategory(Long categoryId);

    R updateCategory(Category category);

    R updateCategoryPhoto(Long categoryId, MultipartFile file);
}
