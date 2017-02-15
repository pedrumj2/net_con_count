/**
 * Created by Pedrum on 1/15/2017.
 */
import DateTime.DateTime;
import MySqlJava.SqlConnect;
import MySqlJava.dbParams;
import java.sql.*;


//This class is responsible for
//  - Creating the connection count table
//  - Setting the count values
public class InitCountTable {
    private SqlConnect sqlConnect;
    private Counts counts;
    private String database;
    private Tag tag;


    public InitCountTable(dbParams __dbParams, String __query) throws SQLException{
        sqlConnect = new SqlConnect(__dbParams);
        counts = new Counts(__dbParams,__query);
        tag = new Tag(__dbParams, __query);
        database = __dbParams.dbName;
    }

    //creates and emtpy connection count table
    public void createConnectionTable(int __interval){
        String _query = "Drop table if exists "+database+".con_count_"+__interval;
        sqlConnect.updateQuery(_query);
        _query = "CREATE TABLE "+database+".con_count_" + __interval + " (\n" +
                "  idconcount1 INT NOT NULL AUTO_INCREMENT,\n" +
                "  count INT NOT NULL,\n" +
                "  tag CHAR(20) NOT NULL,\n" +
                "  startTime DateTime NOT NULL,\n" +
                "  endTime DateTime NOT NULL,\n" +
                "  PRIMARY KEY (idconcount1));";
        sqlConnect.updateQuery(_query);
    }

    //returns the smallest start time value of flows. This is when logging began
    private DateTime getMinTime() throws SQLException{

        Timestamp _timestamp;
        DateTime _output;
        String _query = "SELECT min(startTime) as min FROM "+database+".labels";
        ResultSet _rs = sqlConnect.executeQuery(_query);
        if (_rs.next()){
            _timestamp = (Timestamp) sqlConnect.getValAtIndex(_rs, "min", 0, SqlConnect.TYPE.TIMESTAMP);
            _output = new DateTime(_timestamp);
            return _output;

        }


        else{
            return null;
        }
    }
    //returns the largest start time value of flows. This is when logging ended
    private DateTime getMaxTime() throws SQLException{
        Timestamp _timestamp;
        DateTime _output;
        String _query = "SELECT max(startTime) as max FROM "+database+".labels";
        ResultSet _rs = sqlConnect.executeQuery(_query);
        if (_rs.next()){
            _timestamp = (Timestamp) sqlConnect.getValAtIndex(_rs, "max", 0, SqlConnect.TYPE.TIMESTAMP);
            _output = new DateTime(_timestamp);
            return _output;

        }
        else{
            return null;
        }
    }

    //Initializes the rows of the connection count table. Sets the start end time and
    //sets the count to zero.
    public void setConnectionRows() throws SQLException{
        DateTime _max = getMaxTime();
        DateTime _start= new DateTime(getMinTime());;
        DateTime _end= new DateTime(getMinTime());;
        boolean _flag = true;
        _end.add(1);
        String _query;
        while (_flag){
            _query = "insert into "+database+".con_count_1(count, tag, startTime, endTime)" +
                    " value("+counts.getCount(_end)+", \""+tag.getType(_end)+"\", '" + _start.toString() + "', '" + _end.toString() +"')" ;
            sqlConnect.updateQuery(_query);
            if (_start.isBigger(_max)){
                _flag = false;
            }
            else{
                _start.add(1);
                _end.add(1);
            }

        }
    }
}
