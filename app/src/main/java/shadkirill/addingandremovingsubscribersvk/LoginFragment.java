package shadkirill.addingandremovingsubscribersvk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;

/**
 * Created by shadk on 10.01.2016.
 */
public class LoginFragment extends android.support.v4.app.Fragment {
    private static final String[] mScope = new String[]{
            VKScope.FRIENDS,
    };

    public LoginFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        v.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKSdk.login(getActivity(), mScope);
            }
        });
        return v;
    }

}
