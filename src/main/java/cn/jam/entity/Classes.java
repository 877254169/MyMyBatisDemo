package cn.jam.entity;

import lombok.Data;

import java.util.List;

/**
 * @author jam
 * @date 2020/8/12 21:18
 */
@Data
public class Classes {
    private long id;

    private String name;

    private List<Student> students;
}
