import MySqlJava.SqlConnect;
import MySqlJava.dbParams;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Server {

    public static int GetServerIP(dbParams __dbParams, boolean __isAttack) throws SQLException{
        SqlConnect sqlConnect = new SqlConnect(__dbParams);
        String _query;
        if (__isAttack){
            _query =  "select count(*), dest from\n" +
                    "(\n" +
                    "SELECT * FROM "+__dbParams.dbName+".labels\n" +
                    "where tag <> \"Normal\"\n" +
                    ") as t\n" +
                    "group by dest\n" +
                    " order by count(*) desc" +
                    " limit 1";
        }
        else{
            _query =  "select count(*), dest from\n" +
                    "(\n" +
                    "SELECT * FROM "+__dbParams.dbName+".labels\n" +
                    ") as t\n" +
                    "group by dest\n" +
                    " order by count(*) desc" +
                    " limit 1";
        }

        ResultSet _rs = sqlConnect.executeQuery(_query);
        if (_rs.next()){
            return (int)sqlConnect.getValAtIndex(_rs, "dest", 0, SqlConnect.TYPE.INT );
        }
        else{
            return -1;
        }

    }
}
