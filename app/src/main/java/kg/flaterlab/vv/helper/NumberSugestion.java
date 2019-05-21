package kg.flaterlab.vv.helper;

import android.os.Parcel;


import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class NumberSugestion implements SearchSuggestion {

    String CREATOR = "f";

    String body;

    public NumberSugestion(String body) {
        this.body = body;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
