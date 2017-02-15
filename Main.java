import DateTime.DateTime;
import MySqlJava.dbParams;

public class Main {
    static  InitCountTable _init;

    public static void main(String[] args) throws Exception {

        dbParams _dbParams = new dbParams("192.168.20.12", "fafdRE$3", "D12" );
        _init = new InitCountTable(_dbParams);
        _init.createConnectionTable(1);

        _init.setConnectionRows();


    }
}
