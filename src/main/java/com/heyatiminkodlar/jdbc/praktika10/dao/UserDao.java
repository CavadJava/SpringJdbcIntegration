package com.heyatiminkodlar.jdbc.praktika10.dao;

import com.heyatiminkodlar.jdbc.praktika10.domain.Group;
import com.heyatiminkodlar.jdbc.praktika10.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by root on 11/30/16.
 */
@Repository("userJdbcDao")
public class UserDao implements IUserDao{

    private JdbcTemplate jdbcTemplate;

    @Resource
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user, int gid) {
        String sql = "insert into User (username,password,nickname,gid) value(?,?,?,?)";
        jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getNickname(),gid);
    }

    public void update(User user) {
        jdbcTemplate.update("update User set username=?,password=?,nickname=? where uid=?",
                user.getUsername(),user.getPassword(),user.getNickname(),user.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from User where uid=?",id);
    }

    public User load(int id) {
        String sql = "select t1.uid uid,t1.*,t2.* from springteacher.`User` t1 left join springteacher.`Group` t2 on(t1.gid=t2.gid) where t1.uid=?";
		/*
		 * The first parameter is a SQL statement
         * The second parameter is the value of the SQL statement parameters, the need to pass an array of objects
         * The third parameter is a RowMapper, the rowMapper can complete a database object and the corresponding field, the need to achieve this RowMapper
         * MapRow method to achieve, in the mapRow method rs = this parameter, through the rs can be effective access to the database field
         * If this method in the DAO will be reused, it is recommended to resolve through the internal category, rather than using the anonymous internal class
		 */
        User u = (User)jdbcTemplate.queryForObject(sql, new Object[]{id},new UserMapper());
        return u;
    }

    public List<User> list(String sql,Object[] args) {
        String sqlCount = "select count(*) from User";
        //Gets an integer value
        int count = jdbcTemplate.queryForInt(sqlCount);
        System.out.println(count);
        String nCount = "select nickname from User";
        //Gets a list of String s
        List<String> ns = jdbcTemplate.queryForList(nCount,String.class);
        for(String n:ns) {
            System.out.println("--->"+n);
        }
        String tSql = "select username,nickname from User";
        //Unable to remove user
		List<User> us = jdbcTemplate.queryForList(tSql, User.class);
		for(User u:us) {
			System.out.println(u);
		}
        //The object array can not be returned
		List<Object[]> os = jdbcTemplate.queryForList(tSql, Object[].class);
		for(Object[] oo:os) {
			System.out.println(oo[0]+","+oo[1]);
		}



        us = jdbcTemplate.query(tSql,new RowMapper<User>(){
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User u = new User();
                u.setNickname(rs.getString("nickname"));
                u.setUsername(rs.getString("username"));
                return u;
            }
        });
        for(User u:us) {
            System.out.println(u);
        }
        return jdbcTemplate.query(sql, args, new UserMapper());
    }



    private class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group g = new Group();
            g.setName(rs.getString("name"));
            g.setId(rs.getInt("gid"));
            User u = new User();
            u.setGroup(g);
            u.setId(rs.getInt("uid"));
            u.setNickname(rs.getString("nickname"));
            u.setPassword(rs.getString("password"));
            u.setUsername(rs.getString("username"));
            return u;
        }
    }

}