/**
 * Created by Pedrum on 1/15/2017.
 */
import DateTime.DateTime;
import MySqlJava.Chunk;
import MySqlJava.SqlConnect;
import MySqlJava.dbParams;

import java.sql.*;

public class Queries {
    private SqlConnect sqlConnect;
    private Chunk chunk;


    public Queries(dbParams __dbParams) throws SQLException{
        sqlConnect = new SqlConnect(__dbParams);
        chunk = new Chunk(__dbParams);
}

    public void createConnectionTable(int __interval){
        String _query;
        _query = "CREATE TABLE D12.con_count_" + __interval + " (\n" +
                        "  idconcount1 INT NOT NULL AUTO_INCREMENT,\n" +
                        "  count INT NOT NULL,\n" +
                        "  tag CHAR(20) NOT NULL,\n" +
                        "  startTime DateTime NOT NULL,\n" +
                        "  endTime DateTime NOT NULL,\n" +
                        "  PRIMARY KEY (idconcount1));";
        sqlConnect.updateQuery(_query);

    }


    public DateTime getMinTime() throws SQLException{
        Object _res;
        Timestamp _timestamp;
        DateTime _output;
        String _query = "SELECT min(startTime) as min FROM D12.labels";
        if (sqlConnect.execGetQueryIndex(_query) == SqlConnect.SQLRET.SUCCESS) {
            _res = sqlConnect.getValAtIndex("min", 0, SqlConnect.TYPE.TIMESTAMP);
            _timestamp = (Timestamp)_res;
            _output = new DateTime(_timestamp);
            return _output;
        }
        else{
            return null;
        }
    }

    public DateTime getMaxTime() throws SQLException{
        Object _res;
        Timestamp _timestamp;
        DateTime _output;

        String _query = "SELECT max(startTime) as max FROM D12.labels";
        if (sqlConnect.execGetQueryIndex(_query) == SqlConnect.SQLRET.SUCCESS) {
            _res = sqlConnect.getValAtIndex("max", 0, SqlConnect.TYPE.TIMESTAMP);
            _timestamp = (Timestamp)_res;
            _output = new DateTime(_timestamp);
            return _output;
        }
        else{
            return null;
        }
    }

    //Initializes the rows of the connection count table. Sets the start end time and
    //sets the count to zero.
    public void setConnectionRows(DateTime __min, DateTime __max) throws SQLException{
        DateTime _start;
        DateTime _end;
        boolean _flag;
        _start = new DateTime(__min);
        _end = new DateTime(__min);
        _end.add(1);
        String _query;


        _flag =true;
        while (_flag){
            _query = "insert into D12.con_count_1(count, tag, startTime, endTime)" +
                    " value(0, \"Normal\", '" + _start.toString() + "', '" + _end.toString() +"')" ;
            sqlConnect.updateQuery(_query);
            if (_start.isBigger(__max)){
                _flag = false;
            }
            else{
                _start.add(1);
                _end.add(1);
            }

        }


    }






}
