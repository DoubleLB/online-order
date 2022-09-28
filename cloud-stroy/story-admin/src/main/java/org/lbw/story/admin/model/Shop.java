package org.lbw.story.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    private Integer shop_id;
    private String shop_name;
    private String shop_addr;
    private String shop_open;
}
