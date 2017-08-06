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
import iboxbd.broadband.statistics.sqlite.DB_BASIC;
import iboxbd.broadband.statistics.sqlite.DataBaseHandler;
import iboxbd.broadband.statistics.utils.DateUtils;

public class Home_Activity extends AppCompatActivity {

    private DataBaseHandler _dbhandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_home);


        //Connection d = new Connection();
        try{
            GetDB();
            Connection c = new Connection();
            c.setDateTime(DateUtils.getDateTime());
            c.setConnected(false);
            c.setSynced(false);
            _dbhandler.AddNewObject(c);

            Connection c1 = new Connection();
            c1.setDateTime(DateUtils.getDateTime());
            c1.setConnected(false);
            c1.setSynced(false);
            _dbhandler.AddNewObject(c1);

            List<DB_BASIC> dataList = new ArrayList<DB_BASIC>();
            //dataList.addAll(_dbhandler.GetTableData(Connection.class));
            dataList = _dbhandler.GetTableData(Connection.class);

            for (int i = 0; i < dataList.size(); i++) {
                System.out.println(dataList.get(i));


            }

            for (DB_BASIC temp : dataList) {
                Toast.makeText(this,temp.ID.toString(),Toast.LENGTH_LONG).show();
                //temp.ID
            }
            //dd = _dbhandler.GetValue()


            _dbhandler.close();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public DataBaseHandler GetDB() {
        if (_dbhandler == null) {
            InitDB();
        }
        return _dbhandler;
    }

    private void InitDB() {
        //Initialize DB handler
        _dbhandler = new DataBaseHandler(this);
        //Adding table based on class TEST reflection
        _dbhandler.CreateTable(new Connection());
    }
}
