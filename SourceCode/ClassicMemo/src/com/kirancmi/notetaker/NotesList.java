package com.kirancmi.notetaker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.kirancmi.model.ListItemData;
import com.kirancmi.model.Note;
import com.kirancmi.model.NoteManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class NotesList extends Activity {

	ListView lvNotesList = null;
	int nEditingNode = -1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_notes_list);

		lvNotesList = (ListView)findViewById(R.id.lvNotes);
		
		lvNotesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int itemNumber,
					long id) {
				// TODO Auto-generated method stub
				Intent cIntent = new Intent(view.getContext(), EditNote.class);
				//cIntent.putExtra("Note", notesList.get(itemNumber));
				List<Note> noteDetailsList = NoteManager.getInstance(view.getContext()).getNoteDetailsList();
				cIntent.putExtra("Note", noteDetailsList.get(itemNumber));
				nEditingNode = itemNumber;
				startActivityForResult(cIntent, 1);
			}
			
		});
		
		registerForContextMenu(lvNotesList);		
		
		NoteManager.getInstance(this).ReadAllNoteDetails();
		PopulateNotesList();
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		NoteManager.getInstance(this).WriteAllNoteDetails();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes_list, menu);
		
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		
		//outState.puts
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		Intent editIntent = new Intent(this, EditNote.class);
		startActivityForResult(editIntent, 1);
		
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		if(resultCode == RESULT_CANCELED)
		{
			nEditingNode = -1;
			return;
		}
		
		if(resultCode == EditNote.RESULT_DELETE)
		{
			//notesList.remove(nEditingNode);
			NoteManager.getInstance(this).DeleteNote(nEditingNode);
			nEditingNode = -1;
			PopulateNotesList();
		}
		
		Serializable extra = data.getSerializableExtra("Note");
		if(extra != null)
		{
			Note note = (Note)extra;
			if(nEditingNode > -1)
			{
				//notesList.set(nEditingNode, note);
				NoteManager.getInstance(this).UpdateNote(nEditingNode, note);
				nEditingNode = -1;
			}
			else
			{
				//notesList.add(note);
				NoteManager.getInstance(this).AddNote(note);
			}
			PopulateNotesList();
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		
		//notesList.remove(info.position);
		NoteManager.getInstance(this).DeleteNote(info.position);
		PopulateNotesList();
		
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.context_menu, menu);
	}

	private void PopulateNotesList()
	{
		CustomListItemAdapter adapter = null;

		List<ListItemData> notesTitles = null;
		List<Note> noteDetailsList = NoteManager.getInstance(this).getNoteDetailsList();
		
		if(noteDetailsList.size() > 0)
		{
			notesTitles = new ArrayList<ListItemData>();
			if(notesTitles != null)
			{
				for(Note note : noteDetailsList)
				{
					ListItemData data = new ListItemData(note.getTitle(), note.getDateModified());
					notesTitles.add(data);
				}
				adapter = new CustomListItemAdapter(this, notesTitles);
			}
		}
		else
		{
			Context context = getApplicationContext();
			CharSequence text = "To add a note press menu...!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			if(toast != null)
			{
				toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
			}
		}

		lvNotesList.setAdapter(adapter);
	}
	
	/*
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//NoteManager.getInstance(this).ReadAllNoteDetails();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
		//NoteManager.getInstance(this).ReadAllNoteDetails();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		//NoteManager.getInstance(this).WriteAllNoteDetails();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		//NoteManager.getInstance(this).WriteAllNoteDetails();
	}
	*/
	
	
}
