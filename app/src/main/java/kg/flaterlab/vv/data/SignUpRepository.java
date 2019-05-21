package kg.flaterlab.vv.data;

import io.paperdb.Paper;
import kg.flaterlab.vv.data.model.User;
import kg.flaterlab.vv.helper.DB;

public class SignUpRepository {
    private static volatile SignUpRepository instance;

    private SignUpDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private User user = null;

    // private constructor : singleton access
    private SignUpRepository(SignUpDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static SignUpRepository getInstance(SignUpDataSource dataSource) {
        if (instance == null) {
            instance = new SignUpRepository(dataSource);
        }
        return instance;
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        Paper.book().write(DB.USER_NODE, user);
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<User> signUp(String username, String password, String password2) {
        // handle login
        Result<User> result = dataSource.signUp(username, password, password2);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<User>) result).getData());
        }
        return result;
    }
}
