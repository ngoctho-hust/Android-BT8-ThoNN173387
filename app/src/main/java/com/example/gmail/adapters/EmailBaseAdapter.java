package com.example.gmail.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gmail.R;
import com.example.gmail.models.EmailItemModel;

import java.util.List;

public class EmailBaseAdapter extends BaseAdapter {
    Context context;
    List<EmailItemModel> items;

    public EmailBaseAdapter(Context context, List<EmailItemModel> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_row_email, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.logo = convertView.findViewById(R.id.logo);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.description = convertView.findViewById(R.id.description);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.star = convertView.findViewById(R.id.btn_star);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

            EmailItemModel item = items.get(position);
            viewHolder.logo.setText(item.getLogo());
            viewHolder.name.setText(item.getName());
            viewHolder.time.setText(item.getTime());
            viewHolder.description.setText(item.getDescription());

            if (item.isFavorite()) {
                viewHolder.star.setImageResource(R.drawable.ic_baseline_star_48);
            } else {
                viewHolder.star.setImageResource(R.drawable.ic_baseline_star_border_48);
            }

            viewHolder.star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setFavorite(!item.isFavorite());
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }

    private class ViewHolder {
        TextView logo;
        TextView name;
        TextView time;
        TextView description;
        ImageButton star;
    }
}
