package cn.jam.entity;

import lombok.Data;

import java.util.List;

/**
 * @author jam
 * @date 2020/8/13 21:30
 */
@Data
public class Goods {

    private long id;

    private String name;

    private List<Customer> customers;
}
