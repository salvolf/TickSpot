package tickspot.application.sev.tickspot.adapters;

/**
 * Created by Sev on 07/02/15.

public class TasksSpinnerAdapter extends ArrayAdapter {
    private List<Tasks> tasks;

    public TasksSpinnerAdapter(Context context, int resource, List<Tasks> objects) {
        super(context, resource, objects);
        tasks = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView label = (TextView) row.findViewById(android.R.id.text1);
        label.setText(tasks.get(position).name);
        return row;
    }
}
 */