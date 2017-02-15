/**
 * Created by Pedrum on 1/15/2017.
 */
import DateTime.DateTime;
import MySqlJava.Chunk;
import MySqlJava.SqlConnect;
import MySqlJava.dbParams;
import java.sql.*;

public class Counts {
    private SqlConnect sqlConnect;
    private Chunk chunk;
    private ResultSet rs;

    public Counts(dbParams __dbParams) throws SQLException{
        sqlConnect = new SqlConnect(__dbParams);
        chunk = new Chunk(__dbParams, "SELECT * FROM D12.labels where dest = 9 order by startTime");
        rs = chunk.Next();
    }

    public int getCounts(DateTime __max) throws SQLException{
        int _output =0;
        DateTime _startTime;
        while (rs != null){
            _startTime = new DateTime(rs.getTimestamp("startTime"));
            if (__max.isBigger(_startTime)==true){
                _output +=1;
            }
            else{
                return _output;
            }
            rs = chunk.Next();
        }
        return -1;
    }





}
