package app.z.com.contactexchanging.Commons;

import android.content.Context;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class Commons {
    private Context mContext;

    public Commons(Context context) {
    }

    public String convertToString(int i) {
        return i + "";
    }
}
