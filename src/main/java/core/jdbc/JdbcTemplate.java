package core.jdbc;

import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pstmtsetter, Object... objects) throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmtsetter.setValues(pstmt, objects);
            pstmt.executeUpdate();
        }
    }

    public List query(String sql, RowMapper rowMapper, PreparedStatementSetter pstmtsetter, Object... objects) throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmtsetter.setValues(pstmt, objects);
            ResultSet rs = pstmt.executeQuery();
            List values = Lists.newArrayList();
            while (rs.next()) {
                values.add(rowMapper.mapRow(rs));
            }

            return values;
        }
    }

    public Object queryForObject(String sql, RowMapper rowMapper, PreparedStatementSetter pstmtsetter, Object... objects) throws SQLException {
        List values = query(sql, rowMapper, pstmtsetter, objects);
        if (values.isEmpty()) {
            return null;
        }
        return values.get(0);

    }
}
