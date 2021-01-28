package com.example.teaching236pad.model;

import java.io.Serializable;

/**
 * 资源模型类
 *
 * @author 2021.01.26
 */
public class Resource implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
