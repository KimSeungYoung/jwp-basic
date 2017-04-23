package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedStatementSetterImple implements PreparedStatementSetter {

    @Override
    public void setValues(PreparedStatement pstmt, Object... objects) throws SQLException {
        for(int i = 1 ; i <= objects.length ; i++) {
            pstmt.setString(i, (String) objects[i-1]);
        }
    }
}
