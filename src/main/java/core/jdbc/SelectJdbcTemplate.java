package core.jdbc;

import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class SelectJdbcTemplate {

    public List query(String sql) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);

            rs = pstmt.executeQuery();

            List values = Lists.newArrayList();

            while (rs.next()) {
                values.add(mapRow(rs));
            }

            return values;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public Object queryForObject(String sql) throws SQLException {
        List values = query(sql);
        if (values.isEmpty()) {
            return null;
        }
        return values.get(0);

    }

    public abstract Object mapRow(ResultSet rs) throws SQLException;

    public abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
