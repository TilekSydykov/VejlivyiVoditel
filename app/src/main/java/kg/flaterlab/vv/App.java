package kg.flaterlab.vv;

import android.app.Application;
import android.content.Context;

import io.paperdb.Paper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        Paper.init(context);
    }
}
