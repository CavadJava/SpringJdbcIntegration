package com.heyatiminkodlar.jdbc.praktika10.dao;

import com.heyatiminkodlar.jdbc.praktika10.domain.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * Created by root on 11/30/16.
 */
@Repository("groupJdbcDao")
public class GroupDao implements IGroupDao{
    private JdbcTemplate jdbcTemplate;

    @Resource
    public void setJdbcTemplate(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void add(final Group group) {
        /**
         * You can add an object by the following methods,
         * and get the object automatically incremented id
         */
        //getJdbcTemplate().update("INSERT INTO `springteacher`.`Group` (`name`) VALUES ('')",group.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            public PreparedStatement createPreparedStatement(Connection con)
                    throws SQLException {
                String sql = "INSERT INTO `springteacher`.`Group` (`name`) VALUES (?)";
                PreparedStatement ps = con.prepareStatement(sql,new String[]{"id"});
                ps.setString(1, group.getName());
                return ps;
            }
        },keyHolder);
        group.setId(keyHolder.getKey().intValue());
    }
}
