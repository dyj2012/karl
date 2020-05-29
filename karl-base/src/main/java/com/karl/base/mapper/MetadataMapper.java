package com.karl.base.mapper;

import com.karl.base.mapper.vo.TableColumn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 元数据接口
 *
 * @author 杜永军
 * @date 2020/5/29
 */
public interface MetadataMapper {

    /**
     * 获取列信息
     *
     * @param tableName
     * @return
     */
    @Select("SELECT COLUMN_NAME, " +
            "COLUMN_TYPE, " +
            "IS_NULLABLE, " +
            "COLUMN_DEFAULT, " +
            "COLUMN_COMMENT, " +
            "CHARACTER_MAXIMUM_LENGTH, " +
            "NUMERIC_PRECISION, " +
            "NUMERIC_SCALE, " +
            "ORDINAL_POSITION " +
            "FROM information_schema.`COLUMNS` a " +
            "WHERE a.TABLE_NAME = #{tableName} " +
            "AND a.TABLE_SCHEMA = SCHEMA ()")
    @Results({
            @Result(column = "CHARACTER_MAXIMUM_LENGTH", property = "length"),
            @Result(column = "NUMERIC_PRECISION", property = "precision"),
            @Result(column = "NUMERIC_SCALE", property = "scale"),
            @Result(column = "ORDINAL_POSITION", property = "position")
    })
    List<TableColumn> getColumns(@Param("tableName") String tableName);
}
