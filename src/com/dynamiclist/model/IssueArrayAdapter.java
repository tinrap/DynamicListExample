/**
 * 
 */
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
 * @author Parnit Sainion
 * @since 31 May 2014
 * Description: This adapter sets up the list of github issues into the listview
 */
public class IssueArrayAdapter extends ArrayAdapter<Issue> {

	private ArrayList<Issue> issues;
	
	/**
	 * 
	 */
	public IssueArrayAdapter(Context context, int textViewResourceId, ArrayList<Issue> issues) {
		super(context, textViewResourceId, issues);
		this.issues = issues;
	}
	
	public View getView (int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.issue_list_row, null, false);
		}
		
		Issue issue = issues.get(position);
		
		if(issue != null)
		{
			TextView title = (TextView) convertView.findViewById(R.id.title);
			title.setText(issue.getTitle());
			
			TextView body = (TextView) convertView.findViewById(R.id.body);
			body.setMaxEms(140);
			body.setText(issue.getDescription());
		}
		
		return convertView;
	}

}
