package androi30_b.my_mp3_app;

import android.app.Application;
import android.content.Context;

/**
 * Created by Duc Nguyen on 4/14/2017.
 */

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public static Context getContext(){
        return context;
    }
}
