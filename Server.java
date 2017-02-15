import MySqlJava.SqlConnect;
import MySqlJava.dbParams;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Server {

    public static int GetServerIP(dbParams __dbParams) throws SQLException{
        SqlConnect sqlConnect = new SqlConnect(__dbParams);
        String _query = "Select count(*) as count, Dest from " + __dbParams.dbName + ".labels "  +
                "group by Dest " +
                "order by count desc " +
                "limit 1";
        ResultSet _rs = sqlConnect.executeQuery(_query);
        if (_rs.next()){
            return (int)sqlConnect.getValAtIndex(_rs, "Dest", 0, SqlConnect.TYPE.INT );
        }
        else{
            return -1;
        }

    }
}
