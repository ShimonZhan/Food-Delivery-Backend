package uk.ac.soton.food_delivery.entity.DO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import uk.ac.soton.food_delivery.enums.MenuStatus;

import java.time.LocalDateTime;

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
@TableName("menu")
@ApiModel(value = "Menu对象")
public class Menu {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("price")
    private Float price;

    @TableField("photo")
    private String photo;

    @TableField("status")
    private MenuStatus status;

    @TableField("restaurant_id")
    private Long restaurantId;

    @TableField("description")
    private String description;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonIgnore
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private LocalDateTime updateTime;

    public static OrderItem toOrderItem(Menu menu) {
        if (menu == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setName(menu.getName());
        orderItem.setPrice(menu.getPrice());
        orderItem.setDescription(menu.getDescription());
        return orderItem;
    }

}
