/**
 * 
 */
package com.dynamiclist.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.dynamiclist.model.Comment;
import com.dynamiclist.model.CommentsArrayAdapter;
import com.dynamiclist.model.Issue;
import com.dynamiclist.model.IssueArrayAdapter;
import com.dynamiclist.model.JsonParser;

/**
 * @author Parnit Sainion
 * @since 18 May 2014
 * Description: This fragment displays the list of issues for the ruby on rails repo
 */
public class IssueFragment extends Fragment {

	ProgressDialog progressDialog;
	private ListView listview;
	private ArrayList<Issue> issues;
	private IssueArrayAdapter listAdapter;
	private Dialog dialog;
	private TextView noIssues;
    private final String URL ="https://api.github.com/repos/rails/rails/issues";
    private final String KEY = "issues";
	
	/**
	 * 
	 */
	public IssueFragment() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		if(savedInstanceState != null)
			issues = (ArrayList<Issue>) savedInstanceState.get(KEY);
		
	    // saves fragment instance across configuration changes.
	    setRetainInstance(true);
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      
        return rootView;
    }
    
	@Override 
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
    	super.onViewCreated(view, savedInstanceState);

    	//initialize view items
        listview = (ListView) view.findViewById(R.id.issue_list_view);
        noIssues = (TextView) view.findViewById(R.id.noResults);
    	
    	
    	if(issues == null) //case if issues is equal to null    			
    	{
    		new GetIssuesTask().execute(URL);
    	}
		else    		
			initializeView(issues);
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    }
    
    @Override
    public void onPause()
    {
    	if(progressDialog != null)
    		progressDialog.dismiss();
    	
    	if(dialog != null)
    		dialog.dismiss();
    	
    	super.onPause();
    }
    
    /**
     * @author Parnit Sainion
     * Descriptions: Obtains all issues for a given github repo
     */
    private class GetIssuesTask extends AsyncTask<String, String, String>{

    	@Override
    	protected void onPreExecute()
    	{
    		progressDialog = new ProgressDialog(getActivity());
    		progressDialog.setIndeterminate(true);
    		progressDialog.setCancelable(false);
    		progressDialog.setMessage("Getting Issues. Please Wait");
    		progressDialog.show();
    	}
    	
		@Override
		protected String doInBackground(String... params) {
			
			String response = "";
			HttpClient httpclient = new DefaultHttpClient();
	    	HttpGet httpget = new HttpGet(params[0]);
	    	
	    	ResponseHandler<String> handler = new BasicResponseHandler();
			try {
				response =  httpclient.execute(httpget,handler);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			issues = JsonParser.parseIssues(result);			
			Collections.sort(issues);			
			initializeView(issues);
		}
    	
    }
    
    /**
     * @author Parnit Sainion
     * Descriptions: Obtains comments for a given issue
     */
    private class GetCommentsTask extends AsyncTask<String, String, String>{

    	//declare variables
    	int position;
    	
    	public GetCommentsTask(int position){
    		this.position = position;
    	}
    	
    	@Override
    	protected void onPreExecute()
    	{
    		progressDialog = new ProgressDialog(getActivity());
    		progressDialog.setIndeterminate(true);
    		progressDialog.setCancelable(false);
    		progressDialog.setMessage("Getting Comments. Please Wait");
    		progressDialog.show();
    	}
    	
		@Override
		protected String doInBackground(String... params) {
			
			String response = "";
			HttpClient httpclient = new DefaultHttpClient();
	    	HttpGet httpget = new HttpGet(params[0]);
	    	
	    	ResponseHandler<String> handler = new BasicResponseHandler();
			try {
				response =  httpclient.execute(httpget,handler);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			ArrayList<Comment> comments= JsonParser.parseComments(result);

			issues.get(position).setComments(comments);
						
			progressDialog.dismiss();
			
			openDialog(comments);
		}    	
    }
    
    /**
     * initialize the listview
     * @param issues list of issues to display
     */
    public void initializeView(final ArrayList<Issue> issues)
    {

		listAdapter = new IssueArrayAdapter(getActivity(), R.layout.issue_list_row, issues);
		listview.setAdapter(listAdapter);
		
		//set up on click listener
		listview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Issue issue = issues.get(position);
				ArrayList<Comment> comments = issue.getComments();
		
				if(comments != null )
					openDialog(comments);
				else
				{
					GetCommentsTask task = new GetCommentsTask(position);
					task.execute(issue.getCommentsUrl());
				}
			}	
		});
		
		if(issues.size() == 0)
		{				
			noIssues.setVisibility(View.VISIBLE);
		}
		
		if(progressDialog != null)
			progressDialog.dismiss();
    }
    
    /**
     * opens a dialog to display comments for an issue
     * @param comments list of comments for a issue
     */
    public void openDialog(ArrayList<Comment> comments)
    {
		dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.comments_layout);
		dialog.setTitle("Comments");

		// set the custom dialog components - text, image and button
		ListView commentsList = (ListView) dialog.findViewById(R.id.listView1);
		
		CommentsArrayAdapter commentListAdapter = new CommentsArrayAdapter(getActivity(), R.layout.comments_list_row, comments);
		commentsList.setAdapter(commentListAdapter);
		
		if(comments.size() == 0)
		{
			TextView noResult = (TextView) dialog.findViewById(R.id.noResults);
			noResult.setVisibility(View.VISIBLE);
		}

		dialog.show();
	}
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { 
    	if(issues != null)
    		savedInstanceState.putSerializable(KEY, issues);
        super.onSaveInstanceState(savedInstanceState);
      }
}
