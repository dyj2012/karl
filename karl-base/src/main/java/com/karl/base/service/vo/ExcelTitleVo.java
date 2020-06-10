package com.karl.base.service.vo;

import com.karl.base.annotation.ExcelCol;
import lombok.Data;

/**
 * excel 表头信息
 *
 * @author 杜永军
 * @date 2020/6/5
 */
@Data
public class ExcelTitleVo {

    private String key;
    private String title;
    private int order;
    ExcelCol excelCol;

}
