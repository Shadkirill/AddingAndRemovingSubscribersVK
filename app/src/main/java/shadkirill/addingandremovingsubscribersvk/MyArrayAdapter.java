package shadkirill.addingandremovingsubscribersvk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shadk on 12.01.2016.
 */
public class MyArrayAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Subscriber> mSubscribers;

    MyArrayAdapter(Context context, ArrayList<Subscriber> subscribers) {
        mContext = context;
        mSubscribers = subscribers;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mSubscribers.size();
    }

    @Override
    public Object getItem(int position) {
        return mSubscribers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.list_item_subscribers, parent, false);
        }

        Subscriber subscriber = getSubscriber(position);

        ((TextView) view.findViewById(R.id.text_name)).
                setText(subscriber.getFirstName() + " " + subscriber.getLastName());
        ((TextView) view.findViewById(R.id.text_id)).
                setText(String.valueOf(subscriber.getId()));

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(myCheckChangeListener);
        // пишем позицию
        checkBox.setTag(position);
        return view;
    }

    // товар по позиции
    private Subscriber getSubscriber(int position) {
        return ((Subscriber) getItem(position));
    }

    CompoundButton.OnCheckedChangeListener myCheckChangeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getSubscriber((Integer) buttonView.getTag()).setChecked(isChecked);
        }
    };
}
