package cn.jam.repository;

import cn.jam.entity.Goods;
import lombok.Data;

/**
 * @author jam
 * @date 2020/8/13 23:34
 */
public interface GoodsRepository {

    Goods findById(long id);
}
