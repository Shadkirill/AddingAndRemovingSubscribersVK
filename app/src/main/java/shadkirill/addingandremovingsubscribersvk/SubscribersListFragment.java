package shadkirill.addingandremovingsubscribersvk;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shadk on 09.01.2016.
 */
public class SubscribersListFragment extends ListFragment {
    private ArrayList<Subscriber> mSubscribers = null;
    private MyArrayAdapter mAdapter;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubscribers = new ArrayList<Subscriber>();
        mAdapter = new MyArrayAdapter(getActivity(), mSubscribers);
        setListAdapter(mAdapter);
        update();
    }

    public Subscriber[] getCheckedSubscribers() {
        ArrayList<Subscriber> subscribers = new ArrayList<Subscriber>();
        for (Subscriber subscriber : mSubscribers) {
            if (subscriber.isChecked())
                subscribers.add(subscriber);
        }
        return subscribers.toArray(new Subscriber[subscribers.size()]);
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private boolean isAllChecked() {
        return (getCheckedSubscribers().length == mSubscribers.size());
    }

    private void checkAll(boolean check) {
        int size = mAdapter.getCount();
        for(int i = 0; i < size; i++) {
            CheckBox checkBox = (CheckBox)getViewByPosition(i).findViewById(R.id.checkbox);
            checkBox.setChecked(check);
            mSubscribers.get(i).setChecked(check);
        }
    }

    public void checkAll() {
        if (isAllChecked()) {
            checkAll(false);
        } else {
            checkAll(true);
        }
    }

    public View getViewByPosition(int pos) {
        ListView listView = getListView();
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return mAdapter.getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
   /* private static Drawable loadImage(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public void update() {
        showMessage("Updating...");
        VKRequest request = VKApi.users().getFollowers(VKParameters.from(VKApiConst.FIELDS,
                "id, first_name, last_name"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    mSubscribers.clear();
                    //mSubscribers.clear();
                    JSONObject responseJSON = response.json.getJSONObject("response");
                    //long count = responseJSON.getLong("count");
                    JSONArray subscribersArray = responseJSON.getJSONArray("items");
                    for (int i = 0; i < subscribersArray.length(); ++i) {
                        JSONObject subscriberJSON = subscribersArray.getJSONObject(i);
                        Subscriber subscriber = new Subscriber();
                        subscriber.setId(subscriberJSON.getLong("id"));
                        subscriber.setFirstName(subscriberJSON.getString("first_name"));
                        subscriber.setLastName(subscriberJSON.getString("last_name"));
                        mSubscribers.add(subscriber);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showMessage("Something wrong");
                    getActivity().finish();
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }
}
