package shadkirill.addingandremovingsubscribersvk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by shadk on 08.01.2016.
 */
public class SubscribersListActivity extends AppCompatActivity {
    private SubscribersListFragment mSubscribersListFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribers_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        VKSdk.wakeUpSession(this, new VKCallback<VKSdk.LoginState>() {
            @Override
            public void onResult(VKSdk.LoginState res) {
                switch (res) {
                    case LoggedOut:
                        showFragment(new LoginFragment());
                        break;
                    case LoggedIn:
                        mSubscribersListFragment = new SubscribersListFragment();
                        showFragment(mSubscribersListFragment);
                        break;
                    case Pending:
                        break;
                    case Unknown:
                        break;
                }
            }

            @Override
            public void onError(VKError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                showFragment(new SubscribersListFragment());
            }

            @Override
            public void onError(VKError error) {
                showMessage("Something wrong with authorization");
                finish();
            }
        };

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mSubscribersListFragment != null) {
            switch (item.getItemId()) {
                case R.id.add:
                    Adder adder = new Adder(this, mSubscribersListFragment.getCheckedSubscribers());
                    adder.execute(); //прикрутить прогрес диалог
                    return true;
                case R.id.delete:
                    Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.refresh:

                        mSubscribersListFragment.update();

                    return true;
                case R.id.select_all:
                    mSubscribersListFragment.checkAll();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subscribers_list, menu);
        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.container, fragment).commitAllowingStateLoss();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    public void updateSubscribersListFragment() {
        mSubscribersListFragment.update();
    }
}
