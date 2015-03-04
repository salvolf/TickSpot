package tickspot.application.sev.tickspot.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import roboguice.RoboGuice;
import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.database.MyDatabaseHelper;

/**
 * Created by Sev on 07/02/15.
 */
public class ProjectsSpinnerAdapter extends ArrayAdapter {

    private ArrayList<String> spinnerElement;

    private MyDatabaseHelper databaseHelper;

    private boolean hasItemSelected = false;

    public ProjectsSpinnerAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        spinnerElement = objects;
        databaseHelper = RoboGuice.getInjector(getContext()).getInstance(MyDatabaseHelper.class);
    }

    @Override
    public int getCount() {
        return spinnerElement.size() - 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(hasItemSelected() ? position : getCount(), convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView label = (TextView) row.findViewById(R.id.spinner_text);

        if (databaseHelper.getClientsName().contains(spinnerElement.get(position))) {
            label.setTypeface(null, Typeface.BOLD);
            label.setClickable(true);
        } else {
            label.setTypeface(null, Typeface.NORMAL);
            label.setClickable(false);
        }
        label.setText(String.valueOf(String.valueOf(spinnerElement.get(position))));

        return row;
    }

    public boolean hasItemSelected() {
        return hasItemSelected;
    }

    public void setHasItemSelected(boolean hasItemSelected) {
        this.hasItemSelected = hasItemSelected;
    }
}
