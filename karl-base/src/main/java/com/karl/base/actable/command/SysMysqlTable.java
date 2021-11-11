package com.karl.base.actable.command;

import lombok.Data;

import java.util.Date;

/**
 * @author Think
 */
@Data
public class SysMysqlTable {

    /**
     * 表注释
     */
    /**
     * 字符集的后缀
     */
    public static final String TABLE_COLLATION_SUFFIX = "_general_ci";
    /**
     * 字符集
     */
    public static final String TABLE_COLLATION_KEY = "table_collation";
    /**
     * 注释
     */
    public static final String TABLE_COMMENT_KEY = "table_comment";
    /**
     * 引擎
     */
    public static final String TABLE_ENGINE_KEY = "engine";

    private String table_catalog;
    private String table_schema;
    private String table_name;
    private String table_type;
    private String engine;
    private Long version;
    private String row_format;
    private Long table_rows;
    private Long avg_row_length;
    private Long data_length;
    private Long max_data_length;
    private Long index_length;
    private Long data_free;
    private Long auto_increment;
    private Date create_time;
    private Date update_time;
    private Date check_time;
    private String table_collation;
    private Long checksum;
    private String create_options;
    private String table_comment;


}
