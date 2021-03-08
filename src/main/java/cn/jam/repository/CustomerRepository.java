package cn.jam.repository;

import cn.jam.entity.Customer;

/**
 * @author jam
 * @date 2020/8/13 21:32
 */
public interface CustomerRepository {

    Customer findById(long id);
}
