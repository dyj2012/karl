package com.karl.base.util.excel.api.vo;

import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * 写入参数
 *
 * @author 杜永军
 * @date 2019/01/14
 */
@Data
public class ExcelWriteParam {

    /**
     * 页签名称可以为null,不写名称默认为sheet0
     */
    private String sheetName;
    /**
     * 导出表头不可为null
     */
    private List<ExcelKeyTitle> keyTitleList;
    /**
     * 导出参数,可以为null
     */
    private ExportParam exportParam;
    /**
     * 可以为null,默认创建公共Map
     */
    private Map<String, Object> commonMap;

    /**
     * 可以为null,为空则创建一个新的workbook
     */
    private Workbook workbook;
    /**
     * 内部使用,可以为null
     */
    private List<ExcelKeyTitle> writeKeyList;


    public void setKeyTitleList(List<ExcelKeyTitle> keyTitleList) {
        this.keyTitleList = keyTitleList;
        writeKeyList = null;
    }

}
