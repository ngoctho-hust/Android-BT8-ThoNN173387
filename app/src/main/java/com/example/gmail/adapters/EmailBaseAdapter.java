package com.example.gmail.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gmail.R;
import com.example.gmail.models.Email;

import java.util.ArrayList;
import java.util.List;

public class EmailBaseAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<Email> items;
    List<Email> itemListFiltered;
    int filerType = 1;
    public static final int FILTER_TYPE_IMPORTANT = 1;
    public static final int FILTER_TYPE_SEARCH = 2;

    public EmailBaseAdapter(Context context, List<Email> items) {
        this.context = context;
        this.items = items;
        this.itemListFiltered = items;
    }

    @Override
    public int getCount() {
        return itemListFiltered.size();
    }

    @Override
    public Object getItem(int position) {
        return itemListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.email_list_row, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.from = convertView.findViewById(R.id.from);
            viewHolder.subject = convertView.findViewById(R.id.txt_primary);
            viewHolder.message = convertView.findViewById(R.id.txt_secondary);
            viewHolder.iconText = convertView.findViewById(R.id.icon_text);
            viewHolder.timestamp = convertView.findViewById(R.id.timestamp);
            viewHolder.iconImp = convertView.findViewById(R.id.icon_star);
            viewHolder.imgProfile = convertView.findViewById(R.id.icon_profile);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

            Email item = itemListFiltered.get(position);
            // displaying text view data
            viewHolder.from.setText(item.getFrom());
            viewHolder.subject.setText(item.getSubject());
            viewHolder.message.setText(item.getMessage());
            viewHolder.timestamp.setText(item.getTimestamp());

            // displaying the first letter of From in icon text
            viewHolder.iconText.setText(item.getFrom().substring(0, 1));
            viewHolder.imgProfile.setImageResource(R.drawable.bg_circle);
            viewHolder.imgProfile.setColorFilter(item.getColor());

            if (item.isImportant()) {
                viewHolder.iconImp.setImageResource(R.drawable.ic_baseline_star_rate_24);
            } else {
                viewHolder.iconImp.setImageResource(R.drawable.ic_baseline_star_border_24);
            }


            viewHolder.iconImp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setImportant(!item.isImportant());
                    notifyDataSetChanged();
                }
            });
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter importantFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = items.size();
                    filterResults.values = items;
                } else {
                    List<Email> resultsModel = new ArrayList<>();

                    for (Email item : items) {
                        if (item.isImportant()) {
                            resultsModel.add(item);
                        }
                    }

                    filterResults.count = resultsModel.size();
                    filterResults.values = resultsModel;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                itemListFiltered = (List<Email>) results.values;
                EmailBaseAdapter.this.notifyDataSetChanged();
            }
        };

        Filter searchFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = items.size();
                    filterResults.values = items;
                } else {
                    List<Email> resultsModel = new ArrayList<>();

                    for (Email item : items) {
                        if (item.getFrom().contains(constraint) || item.getSubject().contains(constraint)) {
                            resultsModel.add(item);
                        }
                    }

                    filterResults.count = resultsModel.size();
                    filterResults.values = resultsModel;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                itemListFiltered = (List<Email>) results.values;
                EmailBaseAdapter.this.notifyDataSetChanged();
            }
        };

        if (filerType == FILTER_TYPE_IMPORTANT)
            return importantFilter;
        else {
            return searchFilter;
        }
    }

    private class ViewHolder {
        TextView from, subject, message, iconText, timestamp;
        ImageView iconImp, imgProfile;
    }

    public void setFilerType(int filerType) {
        this.filerType = filerType;
    }
}
