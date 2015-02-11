package tickspot.application.sev.tickspot.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tickspot.application.sev.tickspot.R;
import tickspot.application.sev.tickspot.model.NavigationItem;

public class MenuAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final List<NavigationItem> items;
    private int mSelectedPosition = 0;
    private final int mPrimaryColor;
    private final int mIconPadding;

    public MenuAdapter(Context context, List<NavigationItem> items) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = items;
        mPrimaryColor = context.getResources().getColor(R.color.primary);
        mIconPadding = 2 * context.getResources().getDimensionPixelSize(R.dimen.grid_double);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public NavigationItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_navigation, parent, false);

        NavigationItem item = getItem(position);

        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        tv.setText(item.title);
        Drawable drawable = context.getResources().getDrawable(item.icon);
        if (mSelectedPosition == position) {
            tv.setTextColor(mPrimaryColor);
            tv.setCompoundDrawablesWithIntrinsicBounds(new BitmapDrawable(context.getResources(), tintIcon(item.icon)), null, null, null);
        } else {
            tv.setCompoundDrawablesWithIntrinsicBounds(item.icon, 0, 0, 0);
        }
        tv.setCompoundDrawablePadding(mIconPadding);
        return view;
    }

    public void selectItem(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();
    }

    private Bitmap tintIcon(int icon) {
        Bitmap ob = BitmapFactory.decodeResource(context.getResources(), icon);
        Bitmap result = Bitmap.createBitmap(ob.getWidth(), ob.getHeight(), ob.getConfig());
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(mPrimaryColor, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(ob, 0f, 0f, paint);
        return result;
    }
}