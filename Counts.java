/**
 * Created by Pedrum on 1/15/2017.
 */
import DateTime.DateTime;
import MySqlJava.Chunk;
import MySqlJava.SqlConnect;
import MySqlJava.dbParams;
import java.sql.*;

//Returns the number of flows that are between the input time interval.
public class Counts {
    private SqlConnect sqlConnect;
    private Chunk chunk;
    private ResultSet rs;

    public Counts(dbParams __dbParams, String __query) throws SQLException{
        sqlConnect = new SqlConnect(__dbParams);
        chunk = new Chunk(__dbParams, __query, 1000);
        //remove null record
        chunk.next();
        rs = chunk.next();
    }

    //returns the number of flows that are smaller than __max. The lower bound is the current
    //row Chunk is on.
    public int getCount(DateTime __max) throws SQLException{
        int _output =0;
        DateTime _startTime;
        while (rs != null){
            _startTime = new DateTime(rs.getTimestamp("startTime"));
            if (__max.isBigger(_startTime)){
                _output +=1;
            }
            else{
                return _output;
            }
            rs = chunk.next();
        }
        return -1;
    }





}
