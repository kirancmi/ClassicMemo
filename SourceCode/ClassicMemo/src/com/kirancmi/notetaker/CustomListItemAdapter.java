package com.kirancmi.notetaker;

import java.util.List;

import com.kirancmi.model.ListItemData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListItemAdapter extends ArrayAdapter<ListItemData> {

	private Context context = null;
	private List<ListItemData> noteDetailsList = null;
	
	public CustomListItemAdapter(Context context, List<ListItemData> nList)
	{
		super(context, R.layout.custom_list_item, nList);
		
		this.context = context;
		this.noteDetailsList = nList;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View view = convertView;
		
		if(position < noteDetailsList.size())
		{
			if (view == null) 
			{
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.custom_list_item, parent, false);
			}

			if(view != null)
			{
				ListItemData note = noteDetailsList.get(position);
				if(note != null)
				{
					TextView txtDate = (TextView) view.findViewById(R.id.cus_li_title);
					if(txtDate != null)
					{
						txtDate.setText(note.getTitle());
					}
					
					TextView txtModTime = (TextView) view.findViewById(R.id.cus_li_mod_time);
					if(txtModTime != null)
					{
						txtModTime.setText(note.getDateModified());
					}
				}
				else
				{
					note = null;
				}
			}
		}
		
		return view;
	}
}
