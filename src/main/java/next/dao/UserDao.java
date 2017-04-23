package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private RowMapper rowMapper = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
    private PreparedStatementSetter preparedStatementSetter = (pstmt, objects) -> {
        for (int i = 1; i <= objects.length; i++) {
            pstmt.setString(i, (String) objects[i - 1]);
        }
    };
    private JdbcTemplate template = new JdbcTemplate();

    public void insert(User user) throws SQLException {

        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        template.update(sql, preparedStatementSetter, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) throws SQLException {

        String sql = "UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?";
        template.update(sql, preparedStatementSetter, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public User findByUserId(String userId) throws SQLException {

        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return (User) template.queryForObject(sql, rowMapper, preparedStatementSetter, userId);
    }

    public List<User> findAll() throws SQLException {

        String sql = "SELECT userId, password, name, email FROM USERS";
        return (List<User>) template.query(sql, rowMapper, preparedStatementSetter);
    }
}
