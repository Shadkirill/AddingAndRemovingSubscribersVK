package shadkirill.addingandremovingsubscribersvk;

import android.os.AsyncTask;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;

/**
 * Created by shadk on 12.01.2016.
 */
public class Adder extends AsyncTask <Void, Void, Void> {
    private Subscriber [] mSubscribers;

    Adder (Subscriber [] subscribers) {
        mSubscribers = subscribers;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (Subscriber subscriber : mSubscribers) {
            VKRequest request =  VKApi.friends().add(VKParameters.from(
                    VKApiConst.USER_ID, subscriber.getId()));
            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    super.onComplete(response);
                }

                @Override
                public void onError(VKError error) {
                    super.onError(error);
                }
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
