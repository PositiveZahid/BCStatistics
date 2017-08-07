package iboxbd.broadband.statistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import iboxbd.broadband.statistics.sqlite.Connection;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.utils.DateUtils;


public class Home_Activity extends AppCompatActivity {

    private DatabaseHelper _dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_home);
        GetDB();

        //Connection d = new Connection();
        try{

            /*Connection c = new Connection();
            c.setDateTime(DateUtils.getDateTime());
            c.setIsConnected("false");
            c.setIsSynced("false");
            _dbHelper.createConnection(c);

            Connection c1 = new Connection();
            c1.setDateTime(DateUtils.getDateTime());
            c1.setIsConnected("false");
            c1.setIsSynced("false");
            _dbHelper.createConnection(c1);

            List<Connection> dataList = new ArrayList<Connection>();
            //dataList.addAll(_dbhandler.GetTableData(Connection.class));
            //dataList = _dbHelper.getAllConnections();


            for (Connection temp : _dbHelper.getAllConnections()) {
                Toast.makeText(this,temp.getID().toString(),Toast.LENGTH_LONG).show();
                //temp.ID
            }
            //dd = _dbhandler.GetValue()


            _dbHelper.close();*/
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public DatabaseHelper GetDB() {
        if (_dbHelper == null) {
            InitDB();
        }
        return _dbHelper;
    }

    private void InitDB() {
        //Initialize DB handler
        _dbHelper = new DatabaseHelper(this);
        //Adding table based on class TEST reflection
        //_dbHelper.CreateTable(new Connection());
    }
}
