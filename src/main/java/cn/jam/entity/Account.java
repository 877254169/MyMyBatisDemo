package cn.jam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author jam
 * @date 2020/8/9 19:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements Serializable {
    private long id;
    private String username;
    private String password;
    private int age;
    private List<Long> ids;

    public Account(String username, String password, int age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }
}
