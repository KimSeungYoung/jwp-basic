package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class InsertJdbcTemplate {

    public void insert() throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(createQuery())) {

            setValues(pstmt);
            pstmt.executeUpdate();
        }
    }

    public abstract void setValues(PreparedStatement pstmt) throws SQLException;

    public abstract String createQuery();
}
