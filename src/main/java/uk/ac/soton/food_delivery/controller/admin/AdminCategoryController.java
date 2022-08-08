package uk.ac.soton.food_delivery.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.soton.food_delivery.entity.DO.Category;
import uk.ac.soton.food_delivery.service.CategoryService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;

/**
 * @author ShimonZhan
 * @since 2022-04-22 22:35:24
 */
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {
    @Resource
    private CategoryService categoryService;

    @PreAuthorize("@ex.hasAuthority('system:category:add')")
    @PostMapping("/addCategory")
    public R addCategory(String categoryName) {
        return categoryService.addCategory(categoryName);
    }

    @PreAuthorize("@ex.hasAuthority('system:category:remove')")
    @DeleteMapping("/removeCategory")
    public R removeCategory(Long categoryId) {
        return categoryService.removeCategory(categoryId);
    }

    @PreAuthorize("@ex.hasAuthority('system:category:update')")
    @PutMapping("/updateCategory")
    public R updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @GetMapping("/getCategories")
    public R getCategories() {
        return R.ok().data("data", categoryService.list());
    }

    @PreAuthorize("@ex.hasAuthority('system:category:updatePhoto')")
    @PutMapping("/updateCategoryPhoto")
    public R updateCategoryPhoto(Long categoryId, @RequestPart("file") MultipartFile file) {
        return categoryService.updateCategoryPhoto(categoryId, file);
    }
}
