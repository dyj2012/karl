package com.karl.base.actable.command;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Think
 */
@Data
@AllArgsConstructor
public class MySqlTypeAndLength {
    private Integer lengthCount;
    private Integer length;
    private Integer decimalLength;
    private String type;

    public MySqlTypeAndLength() {
    }
}
