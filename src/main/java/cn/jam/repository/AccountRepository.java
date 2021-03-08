package cn.jam.repository;

import cn.jam.entity.Account;

import java.util.List;

/**
 * @author jam
 * @date 2020/8/10 20:12
 */
public interface AccountRepository {
    /**
     * 保存
     * @param account 要保存的对象
     * @return
     */
    int save(Account account);

    /**
     * 更新
     * @param account 要更新的对象
     * @return
     */
    int update(Account account);

    int update2(Account account);

    /**
     * 通过id删除
     * @param id 要删除对象的id
     * @return
     */
    int deleteById(long id);

    /**
     * 查询所有数据
     * @return
     */
    List<Account> findAll();

    /**
     * 通过id查找
     * @param id id
     * @return
     */
    Account findById(long id);

    /**
     * 通过username查找
     * @param username username
     * @return
     */
    List<Account> findByUserName(String username);

    /**
     * 通过username、age查找
     * @param userName
     * @param age
     * @return
     */
    List<Account> findByUserNameAndAge(String userName, int age);

    /**
     *统计总数
     * @return
     */
    int count();

    Account findByAccount(Account account);
    Account findByAccount2(Account account);
    Account findByAccount3(Account account);

    List<Account> findByIds(Account account);
}
