package cn.jam;

import cn.jam.entity.Account;
import cn.jam.entity.Classes;
import cn.jam.entity.Customer;
import cn.jam.entity.Goods;
import cn.jam.entity.Student;
import cn.jam.repository.AccountRepository;
import cn.jam.repository.ClassesRepository;
import cn.jam.repository.CustomerRepository;
import cn.jam.repository.GoodsRepository;
import cn.jam.repository.StudentRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author jam
 * @date 2020/8/9 21:00
 */
public class AccountTest {

    private SqlSession getSqlSession() {
        InputStream resource = AccountTest.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resource);
        return sqlSessionFactory.openSession();
    }

    @Test
    public void testAccountSaveByMapper() {
        SqlSession sqlSession = getSqlSession();
        String statement = "mapper.AccountMapper.save";
        Account account = new Account("张三", "123", 18);
        sqlSession.insert(statement, account);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testAccountRepositorySave() {
        SqlSession sqlSession = getSqlSession();
        //获取实现接口的代理对象
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        //save
        Account account = new Account("Tom", "123", 18);
        accountRepository.save(account);
        sqlSession.commit();
        List<Account> allAccount = accountRepository.findAll();
        for(Account a: allAccount) {
            System.out.println(a.toString());
        }
        sqlSession.close();
    }

    @Test
    public void testAccountRepositoryFind() {
        SqlSession sqlSession = getSqlSession();
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        List<Account> accountList = accountRepository.findByUserName("Tom");
        System.out.println(accountList);
        System.out.println("----------------------------------------");
        accountList = accountRepository.findByUserNameAndAge("Tom", 18);
        System.out.println(accountList);
        sqlSession.close();
    }

    @Test
    public void testAccountRepositoryCount() {
        SqlSession sqlSession = getSqlSession();
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        int count = accountRepository.count();
        System.out.println(count);
        sqlSession.close();
    }

    @Test
    public void testStudentRepositoryFind() {
        SqlSession sqlSession = getSqlSession();
        StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
        Student student = studentRepository.findById(1);
        System.out.println(student);
        sqlSession.close();
    }

    @Test
    public void testClassesRepositoryFind() {
        SqlSession sqlSession = getSqlSession();
        ClassesRepository classesRepository = sqlSession.getMapper(ClassesRepository.class);
        Classes classes = classesRepository.findById(1L);
        System.out.println(classes);
        sqlSession.close();
    }

    /** 延迟加载 */
    @Test
    public void testStudentRepositoryLazy() {
        SqlSession sqlSession = getSqlSession();
        StudentRepository studentRepository = sqlSession.getMapper(StudentRepository.class);
        Student student = studentRepository.findByIdLazy(1L);
        System.out.println(student.getName());
        sqlSession.close();
    }

    @Test
    public void testCustomerRepositoryFind() {
        SqlSession sqlSession = getSqlSession();
        CustomerRepository customerRepository = sqlSession.getMapper(CustomerRepository.class);
        Customer customer = customerRepository.findById(2L);
        System.out.println(customer);
        sqlSession.close();
    }

    @Test
    public void testGoodsRepositoryFind() {
        SqlSession sqlSession = getSqlSession();
        GoodsRepository goodsRepository = sqlSession.getMapper(GoodsRepository.class);
        Goods goods = goodsRepository.findById(2L);
        System.out.println(goods);
        sqlSession.close();
    }

    /**
     * 测试缓存
     * 一级缓存是SqlSession级的
     * 二级缓存是Mapper级的，同一个namespace里共享，二级缓存还是在SqlSessionFactory级别的，SqlSessionFactory不一样的话，缓存就拿不到
     */
    @Test
    public void testCache() {
        //为了测试二级缓存，用同一个SQLSessionFactory
        InputStream resource = AccountTest.class.getClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resource);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        //第一次查，会查库
        Account account = accountRepository.findById(9L);
        System.out.println(account);
        //第二次查，走sqlSession缓存
        account = accountRepository.findById(9L);
        System.out.println(account);
        sqlSession.close();

        //关闭sqlSession后，测试二级缓存
        sqlSession = sqlSessionFactory.openSession();
        accountRepository = sqlSession.getMapper(AccountRepository.class);
        //走二级缓存
        account = accountRepository.findById(9L);
        System.out.println(account);
        sqlSession.close();
    }

    /**
     * 测试ehcache缓存，用这个做二级缓存，entity不用实现序列化接口，也不要求用同一个SqlSessionFactory
     */
    @Test
    public void testEhcache() {
        SqlSession sqlSession = getSqlSession();
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        //第一次查，会查库
        Account account = accountRepository.findById(9L);
        System.out.println(account);
        //第二次查，走sqlSession缓存
        account = accountRepository.findById(9L);
        System.out.println(account);
        sqlSession.close();

        //关闭sqlSession后，测试二级缓存
        sqlSession = getSqlSession();
        accountRepository = sqlSession.getMapper(AccountRepository.class);
        //走二级缓存
        account = accountRepository.findById(9L);
        System.out.println(account);
        sqlSession.close();
    }

    /**
     * 测试动态Sql
     */
    @Test
    public void testDynamicSql() {
        SqlSession sqlSession = getSqlSession();
        AccountRepository accountRepository = sqlSession.getMapper(AccountRepository.class);
        Account account = new Account();
        account.setId(9L);
//        account.setUsername("Tom");
        account.setPassword("123");
        account.setAge(0);
        //测试where 标签
        Account account1 = accountRepository.findByAccount(account);
        System.out.println(account1);
        //测试choose/when标签
        Account account2 = accountRepository.findByAccount2(account);
        System.out.println(account2);
        //测试trim标签
        Account account3 = accountRepository.findByAccount3(account);
        System.out.println(account3);
        //测试set标签
        account.setUsername("Jack");
        System.out.println(accountRepository.update2(account));
        System.out.println(account);
        //测试foreach标签
        account.setIds(Arrays.asList(9L, 10L, 11L));
        List<Account> accountList = accountRepository.findByIds(account);
        System.out.println(accountList);
        sqlSession.close();
    }
}
