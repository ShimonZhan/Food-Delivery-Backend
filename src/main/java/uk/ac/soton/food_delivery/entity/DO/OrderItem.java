package uk.ac.soton.food_delivery.entity.DO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

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
@TableName("order_item")
@ApiModel(value = "OrderItem对象")
public class OrderItem {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("order_id")
    private Long orderId;

    @TableField("name")
    private String name;

    @TableField("price")
    private Float price;

    @TableField("description")
    private String description;

    @TableField("quantity")
    private Integer quantity;

}
