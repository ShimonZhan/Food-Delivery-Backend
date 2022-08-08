package uk.ac.soton.food_delivery.serviceImpl;

import uk.ac.soton.food_delivery.entity.DO.Authority;
import uk.ac.soton.food_delivery.mapper.AuthorityMapper;
import uk.ac.soton.food_delivery.service.AuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-12
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

}
