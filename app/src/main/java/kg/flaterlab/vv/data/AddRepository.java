package kg.flaterlab.vv.data;

import java.util.ArrayList;

import io.paperdb.Book;
import io.paperdb.Paper;
import kg.flaterlab.vv.data.model.Number;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.helper.DB;

public class AddRepository {
    private static volatile AddRepository instance;

    private AddDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private ArrayList<Number> nums = null;

    // private constructor : singleton access
    private AddRepository(AddDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static AddRepository getInstance(AddDataSource dataSource) {
        if (instance == null) {
            instance = new AddRepository(dataSource);
        }
        return instance;
    }


    private void setResults(ArrayList<Number> nums) {
        this.nums = nums;

        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<ArrayList<Number>> search(String search) {
        // handle login
        Result<ArrayList<Number>> result = dataSource.search(search);
        if (result instanceof Result.Success) {
            setResults(((Result.Success<ArrayList<Number>>) result).getData());
        }
        return result;
    }

    public Result<ArrayList<Number>> list(String filter, String user) {

        Book b = Paper.book();
        // handle top
        switch (filter){
            case DB.FILTER_PLUS:
                if(!b.contains(DB.LAST_PLUS_SAVED_TIME)){
                    break;
                }
                long oldTime1 = Integer.valueOf( (String ) b.read(DB.LAST_PLUS_SAVED_TIME)) / 1000;
                long now1 = System.currentTimeMillis() / 1000;
                long timePassed1 = (now1 - oldTime1) / 1800;
                if(b.contains(DB.RATING_PLUS_NOTE ) && timePassed1 > 1){
                    return new Result.Success<>(b.read(DB.RATING_PLUS_NOTE, new ArrayList<Number>()));
                }
                break;
            case DB.FILTER_VOTE:
                if(!b.contains(DB.LAST_VOTE_SAVED_TIME)){
                    break;
                }
                long oldTime = Integer.valueOf( (String ) b.read(DB.LAST_VOTE_SAVED_TIME)) / 1000;
                long now = System.currentTimeMillis() / 1000;
                long timePassed = (now - oldTime) / 1800;
                if( b.contains(DB.RATING_VOTE_NOTE) && timePassed > 1){
                    return new Result.Success<>(b.read(DB.RATING_PLUS_NOTE, new ArrayList<Number>()));
                }
                break;
            case DB.FILTER_MINUS:
                if(!b.contains(DB.LAST_MINUS_SAVED_TIME)){
                    break;
                }
                long oldTime2 = Integer.valueOf( (String ) Paper.book().read(DB.LAST_PLUS_SAVED_TIME)) / 1000;
                long now2 = System.currentTimeMillis() / 1000;
                long timePassed2 = (now2 - oldTime2) / 1800;
                if(b.contains(DB.RATING_MINUS_NOTE) && timePassed2 > 1){
                    return new Result.Success<>(b.read(DB.RATING_PLUS_NOTE, new ArrayList<Number>()));
                }
                break;
        }
        Result<ArrayList<Number>> result = dataSource.list(filter, user);
        if (result instanceof Result.Success) {
            setResults(((Result.Success<ArrayList<Number>>) result).getData());
        }

        return result;
    }
}
