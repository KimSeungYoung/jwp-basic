package core.jdbc;

import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplate {

    private core.jdbc.RowMapper rowMapper;
    private PreparedStatementSetter pstmtsetter;

    public JdbcTemplate(RowMapper rowMapper, PreparedStatementSetter pstmtsetter) {
        this.rowMapper = rowMapper;
        this.pstmtsetter = pstmtsetter;
    }

    public void update(String sql, Object... objects) throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmtsetter.setValues(pstmt, objects);
            pstmt.executeUpdate();
        }
    }

    public List query(String sql, Object... objects) throws SQLException {
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

    public Object queryForObject(String sql, Object... objects) throws SQLException {
        List values = query(sql, objects);
        if (values.isEmpty()) {
            return null;
        }
        return values.get(0);

    }
}
