package tickspot.application.sev.tickspot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.restservice.models.Project;

/**
 * Created by Sev on 07/02/15.
 */
public class ProjectsSpinnerAdapter extends ArrayAdapter {
    private List<Project> projects;

    public ProjectsSpinnerAdapter(Context context, int resource, List<Project> objects) {
        super(context, resource, objects);
        projects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       return getCustomView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView label = (TextView) row.findViewById(R.id.text_project);
        label.setText(String.valueOf(projects.get(position).name));
        return row;
    }
}
