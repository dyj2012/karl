package com.karl.module.school.api.entity;

import com.karl.base.model.BaseEntity;
import lombok.Data;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/5/25
 */
@Data
public class SchoolEntity extends BaseEntity {

    private String name;
    private String addr;
}
