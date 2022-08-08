package uk.ac.soton.food_delivery.mapper;

import uk.ac.soton.food_delivery.entity.DO.Authority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import uk.ac.soton.food_delivery.enums.UserRole;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-12
 */
@Mapper
public interface AuthorityMapper extends BaseMapper<Authority> {

    List<String> selectPermsByUserId(Long userId);

    List<String> selectPermsByRole(UserRole userRole);

    Boolean userPermExist(UserRole userRole, String perm);

    Long getLastInsertId();
}
