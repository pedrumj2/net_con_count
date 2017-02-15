import MySqlJava.dbParams;

public class Main {
    static  InitCountTable _init;

    public static void main(String[] args) throws Exception {
        dbParams _dbParams = new dbParams("192.168.20.12", "fafdRE$3", args[0] );
        int _dest = Server.GetServerIP(_dbParams);
        _init = new InitCountTable(_dbParams,  "SELECT * FROM "+_dbParams.dbName+".labels where dest = "+_dest+" order by startTime",Integer.valueOf(args[1]) );
        _init.createConnectionTable();
        _init.setConnectionRows();
    }
}
