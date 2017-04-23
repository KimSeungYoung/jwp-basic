package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.PreparedStatementSetterImple;
import core.jdbc.RowMapperImpl;
import next.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private core.jdbc.RowMapper rowMapper = new RowMapperImpl();
    private PreparedStatementSetter preparedStatementSetter = new PreparedStatementSetterImple();
    private JdbcTemplate template = new JdbcTemplate(rowMapper, preparedStatementSetter);

    public void insert(User user) throws SQLException {

        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        template.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) throws SQLException {

        String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
        template.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public User findByUserId(String userId) throws SQLException {

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return (User) template.queryForObject(sql, userId);
    }

    public List<User> findAll() throws SQLException {

        String sql = "SELECT userId, password, name, email FROM USERS";
        return (List<User>) template.query(sql);
    }
}
