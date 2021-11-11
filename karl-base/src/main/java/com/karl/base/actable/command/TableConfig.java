package com.karl.base.actable.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TableConfig {

    private List<Object> list = new ArrayList<Object>();

    private Map<String, Object> map = new HashMap<String, Object>();


    public TableConfig(List<Object> list) {
        if (list != null) {
            this.list = list;
        }
    }

    public TableConfig(Map<String, Object> map) {
        this.map = map;
    }


}
