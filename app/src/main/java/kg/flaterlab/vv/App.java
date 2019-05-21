package kg.flaterlab.vv;

import android.app.Application;
import android.content.Context;

import io.paperdb.Paper;
import kg.flaterlab.vv.data.model.User;

public class App extends Application {
    User user;

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        Paper.init(context);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
