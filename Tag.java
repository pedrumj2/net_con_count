/**
 * Created by Pedrum on 1/15/2017.
 */

import DateTime.DateTime;
import MySqlJava.Chunk;
import MySqlJava.SqlConnect;
import MySqlJava.dbParams;

import java.sql.ResultSet;
import java.sql.SQLException;

//Returns the number of flows that are between the input time interval.
public class Tag {
    private SqlConnect sqlConnect;
    private Chunk chunk;
    private ResultSet rs;

    public Tag(dbParams __dbParams, String __query) throws SQLException{
        sqlConnect = new SqlConnect(__dbParams);
        chunk = new Chunk(__dbParams, __query);
        rs = chunk.Next();
    }

    //Determines if the current interval contains an attack or is normal
    public String getType(DateTime __max) throws SQLException{
        String _tag;
        DateTime _startTime;
        while (rs != null){
            _startTime = new DateTime(rs.getTimestamp("startTime"));
            if (__max.isBigger(_startTime)){
                _tag = rs.getString("Tag");
                if (!_tag.equals("Normal")) {
                    return "Attack";
                }
            }
            else{
                return "Normal";
            }
            rs = chunk.Next();
        }
        return "Normal";
    }







}
