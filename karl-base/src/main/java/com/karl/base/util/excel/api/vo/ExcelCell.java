package com.karl.base.util.excel.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Map;

/**
 * cell
 *
 * @author 杜永军
 * @date 2018/12/29
 */
@Data
@AllArgsConstructor
public class ExcelCell {
    /**
     * 单元cell
     */
    private Cell cell;
    /**
     * 公共map,用于上下文传输公共信息,如style
     */
    private Map<String, Object> commonMap;


}
