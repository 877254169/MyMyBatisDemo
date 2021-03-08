package cn.jam.repository;

import cn.jam.entity.Classes;

/**
 * @author jam
 * @date 2020/8/13 20:21
 */
public interface ClassesRepository {

    Classes findById(long id);

    /**
     * 测试延迟加载
     * @param id
     * @return
     */
    Classes findByIdLazy(long id);
}
