package com.karl.base.mybatis.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * <description>
 *
 * @author 杜永军
 * @date 2020/6/4
 */
@Data
public class ForeignTableInfo {

    boolean foreign;

    Map<String, ForeignInfo> foreignTableInfoMap = new HashMap<>();


}
