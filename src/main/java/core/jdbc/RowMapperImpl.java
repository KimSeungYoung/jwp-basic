package core.jdbc;

import next.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperImpl implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs) throws SQLException {
        return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
    }
}
