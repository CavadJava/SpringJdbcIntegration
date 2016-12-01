
import com.heyatiminkodlar.jdbc.praktika10.dao.IGroupDao;
import com.heyatiminkodlar.jdbc.praktika10.dao.IUserDao;
import com.heyatiminkodlar.jdbc.praktika10.domain.Group;
import com.heyatiminkodlar.jdbc.praktika10.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by root on 11/30/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans.xml")
public class TestJdbc {
    @Resource(name = "userJdbcDao")
    private IUserDao userJdbcDao;
    @Resource(name="groupJdbcDao")
    private IGroupDao groupJdbcDao;

    @Test
    public void testAdd() {
        Group g = new Group();
        g.setName("Software Developer");
        groupJdbcDao.add(g);
        System.out.println(g.getId());
        User u = new User("javad.b","java","cavad565");
        userJdbcDao.add(u, 1);
    }

    @Test
    public void testUpdate() {
        User u = new User("azad.b","azad655","azik");
        u.setId(2);
        userJdbcDao.update(u);
    }

    @Test
    public void testDelete() {
        userJdbcDao.delete(1);
    }

    @Test
    public void testLoad() {
        User u = userJdbcDao.load(2);
        System.out.println(u.getNickname()+","+u.getGroup().getName());
    }

    @Test
    public void testList() {
        List<User> us = userJdbcDao.list("select t1.uid id,t1.*,t2.* from springteacher.`User` t1 left join springteacher.`Group` t2 on(t1.gid=t2.gid)  where t1.uid=2", null);
        for(User u:us) {
            System.out.println(u);
        }
    }
}