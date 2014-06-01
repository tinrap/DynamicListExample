package com.dynamiclist.model;

import java.util.ArrayList;

import com.dynamiclist.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


/**
  * @author 
 * @since 18 May 2014
 * Description: Array adapter for displaying comments
 */
public class CommentsArrayAdapter extends ArrayAdapter<Comment> {

	private ArrayList<Comment> comments;
	
	/**
	 * 
	 */
	public CommentsArrayAdapter(Context context, int textViewResourceId, ArrayList<Comment> comments) {
		super(context, textViewResourceId, comments);
		this.comments = comments;
	}
	
	public View getView (int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.comments_list_row, null, false);
		}
		
		Comment comment = comments.get(position);
		
		if(comment != null)
		{
			TextView title = (TextView) convertView.findViewById(R.id.user);
			title.setText("User: " + comment.getUser());
			
			TextView body = (TextView) convertView.findViewById(R.id.body);
			body.setText(comment.getDescription());
		}
		
		return convertView;
	}

}
