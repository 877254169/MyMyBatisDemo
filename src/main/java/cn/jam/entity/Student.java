package cn.jam.entity;

import lombok.Data;

/**
 * @author jam
 * @date 2020/8/12 21:17
 */
@Data
public class Student {
    private long id;

    private String name;

    private Classes classes;
}
