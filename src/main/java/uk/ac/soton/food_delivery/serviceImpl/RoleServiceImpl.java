package uk.ac.soton.food_delivery.serviceImpl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import uk.ac.soton.food_delivery.entity.DO.Authority;
import uk.ac.soton.food_delivery.entity.DO.Role;
import uk.ac.soton.food_delivery.enums.UserRole;
import uk.ac.soton.food_delivery.handler.ResultCode;
import uk.ac.soton.food_delivery.handler.ServicesException;
import uk.ac.soton.food_delivery.mapper.AuthorityMapper;
import uk.ac.soton.food_delivery.mapper.RoleMapper;
import uk.ac.soton.food_delivery.service.RoleService;
import uk.ac.soton.food_delivery.utils.R;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public R getRoleAuthorities(UserRole userRole) {
        List<String> authorities = authorityMapper.selectPermsByRole(userRole);
        return R.ok().data("authorities", authorities);
    }

    @Override
    public R grantAuthority(UserRole userRole, String name, String perm) {
        if (authorityMapper.userPermExist(userRole, perm)) {
            throw new ServicesException(ResultCode.PERM_EXIST);
        }
        Authority authority = Authority.builder()
                .enabled(true)
                .roleId(userRole)
                .name(name)
                .perms(perm)
                .build();
        authorityMapper.insert(authority);
        return R.ok();
    }

    @Override
    public R deleteAuthority(UserRole userRole, String perm) {
        if (!authorityMapper.userPermExist(userRole, perm)) {
            throw new ServicesException(ResultCode.PERM_NOT_EXIST);
        }
        authorityMapper.delete(
                Wrappers.<Authority>lambdaQuery()
                .eq(Authority::getRoleId, userRole.getCode())
                .eq(Authority::getPerms, perm)
        );
        return R.ok();
    }
}
