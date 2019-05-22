package kg.flaterlab.vv.fragments.add;

import androidx.annotation.Nullable;

public class AddFormState {
    @Nullable
    private Integer numError;
    private boolean isDataValid;

    AddFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.numError = usernameError;
        this.isDataValid = false;
    }

    AddFormState(boolean isDataValid) {
        this.numError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getNumError() {
        return numError;
    }


    boolean isDataValid() {
        return isDataValid;
    }
}
