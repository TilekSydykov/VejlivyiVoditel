package kg.flaterlab.vv.data;

import java.util.ArrayList;

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
}
