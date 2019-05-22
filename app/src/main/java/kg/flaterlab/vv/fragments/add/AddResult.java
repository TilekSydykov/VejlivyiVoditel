package kg.flaterlab.vv.fragments.add;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import kg.flaterlab.vv.data.model.Number;


public class AddResult {
    @Nullable
    private ArrayList<Number> success;
    @Nullable
    private Integer error;

    AddResult(@Nullable Integer error) {
        this.error = error;
    }

    AddResult(@Nullable ArrayList<Number> success) {
        this.success = success;
    }

    @Nullable
    ArrayList<Number> getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
