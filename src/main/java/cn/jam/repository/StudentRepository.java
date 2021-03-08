package cn.jam.repository;

import cn.jam.entity.Student;

/**
 * @author jam
 * @date 2020/8/12 21:24
 */
public interface StudentRepository {

    Student findById(long id);

    /**
     * 测试延迟加载
     * @param id
     * @return
     */
    Student findByIdLazy(long id);
}
