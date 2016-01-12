package shadkirill.addingandremovingsubscribersvk;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Created by shadk on 08.01.2016.
 */
public class Subscriber {
    private long mId;
    private String mFirstName = null;
    private String mLastName = null;
    private boolean checked = false;

    Subscriber (long id, String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
        mId = id;
    }

    Subscriber(){}

    @Override
    public String toString() {
        return mFirstName + " " + mLastName + '\n' +
                "ID = " + mId;
    }

    public String getLastName() {
        return mLastName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

}
