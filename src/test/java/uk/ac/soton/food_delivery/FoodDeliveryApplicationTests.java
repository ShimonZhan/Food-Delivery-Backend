package uk.ac.soton.food_delivery;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.maps.model.LatLng;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uk.ac.soton.food_delivery.entity.DO.Address;
import uk.ac.soton.food_delivery.entity.DTO.UserValidateDTO;
import uk.ac.soton.food_delivery.entity.Message;
import uk.ac.soton.food_delivery.entity.task.FinishOrder;
import uk.ac.soton.food_delivery.enums.MessageType;
import uk.ac.soton.food_delivery.mapper.AuthorityMapper;
import uk.ac.soton.food_delivery.service.AddressService;
import uk.ac.soton.food_delivery.service.MailService;
import uk.ac.soton.food_delivery.service.MessageService;
import uk.ac.soton.food_delivery.serviceImpl.GeoServiceImpl;
import uk.ac.soton.food_delivery.utils.JwtUtil;
import uk.ac.soton.food_delivery.utils.RedisCache;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodDeliveryApplicationTests {
    @Resource
    private MailService mailService;

    @Resource
    private RedisCache redisCache;

    @Resource
    private AuthorityMapper authorityMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void testEncrypt() {
        String zhanx = passwordEncoder.encode("112233");
        System.out.println(zhanx);
        System.out.println(passwordEncoder.matches("112233", zhanx));
    }

    @Test
    void test1() {
//        mailService.sendMail("zhanxinming1999@gmail.com", CheckCodeUtil.getCheckCode());
    }

    @Test
    @SneakyThrows
    void testJWT() {
        System.out.println(JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiZDhiYWZkYWE2OTA0YmIzOTExM2MxNTI4YzgxODllNiIsInN1YiI6IjE1MDI2NTA4NDY2ODAwMjcxMzciLCJpc3MiOiJGb29kLURlbGl2ZXJ5IiwiaWF0IjoxNjQ3MDk1NzQ2LCJleHAiOjE2NDcyNjg1NDZ9.eGEan6KBqLiQ5zkzk69jHeHZFUdseuurQzxiwvsl0lU").getSubject());
    }

    @Test
    void testSearchPermsByUserId() {
        System.out.println(authorityMapper.selectPermsByUserId(1L));
    }

    @Test
    void fastjson() {
        UserValidateDTO userValidateDTO = JSON.parseObject("""
                                {"email": "123@gmail.com","id":1,"username":"zhanx"}
                        """
                , new TypeReference<>() {
                });
        System.out.println(userValidateDTO.toString());
    }

    @Test
    void addFinishOrder() {
        redisCache.setCacheObject("finishJob:" + 8, new FinishOrder((long) 8, LocalDateTime.now()));
    }

    @Test
    void getBlur() {
        List<FinishOrder> cacheList = redisCache.getMultiCacheObject("finishJob:*");
        for (FinishOrder finishOrder : cacheList) {
            System.out.println(finishOrder.getOrderId() + " " + finishOrder.getFinishTime());
        }
        Collection<String> ck = redisCache.keys("finishJob:*");
        ck.forEach(System.out::println);

    }

    @Test
    void delete() {
        ArrayList<String> keys = Lists.newArrayList("finishJob:3", "finishJob:5", "finishJob:7");
        redisCache.deleteObject(keys);
        redisCache.deleteObject(redisCache.keys("finishJob:*"));
    }


    @Resource
    private AddressService addressService;


    @Resource
    private GeoServiceImpl geoUtils;

    @Test
    void testGeo() {
        Address address = addressService.getById(10L);
        System.out.println(address.toString());
        String fullAddress = address.fullAddress();
        System.out.println(fullAddress);
        LatLng latLng = geoUtils.address2GeoCode(fullAddress);
        System.out.println(latLng.toString());
    }

    @Test
    @SneakyThrows
    void testRedisCacheTTL() {
        Message test22 = new Message()
                .setType(MessageType.CHAT.getCode())
                .setId(IdUtil.simpleUUID())
                .setTo(22L)
                .setFrom(33L)
                .setContent("hello");
        redisCache.setCacheObject("test:22", test22, 30 * 1000, TimeUnit.MILLISECONDS);
        Thread.currentThread().sleep(40000);
        Object cacheObject = redisCache.getCacheObject("test:22");
        System.out.println(cacheObject == null);
    }
    @Resource
    private MessageService messageService;

    @Test
    void addMessage() {
//        messageService.cancelOrder(162L, 1L, 234L);
//        messageService.cancelOrder(162L, 1L, 235L);
//        messageService.cancelOrder(162L, 1L, 236L);
        List<Object> multiCacheObject = redisCache.getMultiCacheObject("chat:*:" + 1L + ":*");
        System.out.println(multiCacheObject);
    }
}
