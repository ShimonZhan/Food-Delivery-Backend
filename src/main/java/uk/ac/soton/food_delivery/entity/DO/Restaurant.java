package uk.ac.soton.food_delivery.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import uk.ac.soton.food_delivery.enums.RestaurantStatus;

/**
 * <p>
 *
 * </p>
 *
 * @author ShimonZhan
 * @since 2022-03-11
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("restaurant")
@ApiModel(value = "Restaurant对象")
public class Restaurant {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("owner_id")
    private Long ownerId;

    @TableField("address_id")
    private Long addressId;

    @TableField("name")
    private String name;

    @TableField("description")
    private String description;

    @TableField("phone")
    private String phone;

    @TableField("status")
    private RestaurantStatus status;

    @TableField("category_id")
    private Long categoryId;

    @TableField("certification_file")
    private String certificationFile;

    @TableField("average_cooking_time")
    private Integer averageCookingTime;

    @TableField("mark")
    private Float mark;

    @TableField("avatar")
    private String avatar;


}
