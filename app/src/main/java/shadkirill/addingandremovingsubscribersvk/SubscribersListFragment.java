package shadkirill.addingandremovingsubscribersvk;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
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
    ArrayAdapter<Subscriber> mAdapter;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubscribers = new ArrayList<Subscriber>();
        mAdapter = new ArrayAdapter<Subscriber>(getActivity(), android.R.layout.simple_list_item_1, mSubscribers);
        setListAdapter(mAdapter);
        update();
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
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

    private void update() {
        showMessage("Updating...");
        VKRequest request = VKApi.users().getFollowers(VKParameters.from(VKApiConst.FIELDS,
                "id, first_name, last_name"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    mSubscribers.clear();
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
