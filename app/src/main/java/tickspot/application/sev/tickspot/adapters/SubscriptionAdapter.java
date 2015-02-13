package tickspot.application.sev.tickspot.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.List;

import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;
import tickspot.application.sev.tickspot.managers.ResponsesManager;
import tickspot.application.sev.tickspot.restservice.models.Subscription;

/**
 * Created by Sev on 13/02/15.
 */
public class SubscriptionAdapter extends BaseAdapter {

    private final String title;
    private LayoutInflater inflater;
    private Spinner mSpinner;

    @Inject
    private MyDatabaseHelper databaseHelper;

    @Inject
    private ResponsesManager responsesManager;

    public SubscriptionAdapter(Context context, String title, List<Subscription> subscriptions) {
        this.inflater = LayoutInflater.from(context);
        this.title = title;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, true);
    }

    @Override
    public int getCount() {
        return responsesManager.getSubscriptions().size();
    }

    @Override
    public Subscription getItem(int position) {
        return responsesManager.getSubscriptions().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent, boolean isHeader) {

        View row = inflater.inflate(isHeader ? R.layout.spinner_sim_header : R.layout.spinner_sim_item, parent, false);

        TextView bigTitle = (TextView) row.findViewById(android.R.id.text1);

        if (isHeader) {
            bigTitle.setText(title);
        } else {
            if (position == mSpinner.getSelectedItemPosition()) {
                bigTitle.setTypeface(null, Typeface.BOLD);
            } else {
                bigTitle.setTypeface(null, Typeface.NORMAL);
            }
        }

        return row;
    }

}
