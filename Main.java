import DateTime.DateTime;
import MySqlJava.dbParams;

public class Main {


    static  Queries _queries;
    public static int _COUNTROWS = 25200;

    public static void main(String[] args) throws Exception {

        DateTime _minTime;
        DateTime _maxTime;
        dbParams _dbParams = new dbParams("192.168.20.12", "fafdRE$3", "D12" );
        _queries = new Queries(_dbParams);
        _queries.createConnectionTable(1);
        _minTime =_queries.getMinTime();
        _maxTime = _queries.getMaxTime();
        _queries.setConnectionRows(_minTime, _maxTime);
    }





}
