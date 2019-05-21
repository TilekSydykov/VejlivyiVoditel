package kg.flaterlab.vv.ui.login;

import android.support.annotation.Nullable;

public class SignUpResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;

    SignUpResult(@Nullable Integer error) {
        this.error = error;
    }

    SignUpResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
