package com.kirancmi.notetaker;

import java.io.Serializable;
import java.util.Calendar;

import com.kirancmi.model.Note;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditNote extends Activity {

	public static final int RESULT_DELETE = -100;
	private boolean isSaving = true;
	private boolean isAddingNote = true;
	
	private Button saveButton = null;
	private Button cancelButton = null;
	private EditText etTitle = null;
	private EditText etNoteContent = null;
	//private TextView saveDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_edit_note);
        
        saveButton = (Button)findViewById(R.id.bSaveEdit);
        cancelButton = (Button)findViewById(R.id.bCancel);
		etTitle = (EditText)findViewById(R.id.etTitle);
		etNoteContent = (EditText)findViewById(R.id.etNoteContent);
		//saveDate = (TextView)findViewById(R.id.tvSavedDate);

		etTitle.requestFocus();
		
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etTitle, InputMethodManager.RESULT_HIDDEN);
		
		Serializable extra = getIntent().getSerializableExtra("Note");
		if(extra != null)
		{
			Note rNote = (Note)extra;
			etTitle.setText(rNote.getTitle());
			etNoteContent.setText(rNote.getNoteContent());
			//saveDate.setText(rNote.getDateModified());
			
			saveButton.setText("Edit");

			etTitle.setEnabled(false);
			etTitle.setTextColor(Color.argb(0xff,0x2a,0x15,0x06));
			
			etNoteContent.setEnabled(false);
			etNoteContent.setTextColor(Color.argb(0xff,0x2a,0x15,0x06));
			//etNoteContent.setTop(36);
			//etNoteContent.setHeight(230);
			
			isAddingNote = false;
			isSaving = false;
		}
		else
		{
			etTitle.requestFocus();
		}
		
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(RESULT_CANCELED, new Intent());
				finish();
			}
		});
		
        saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if( (etTitle.getText().length() > 0) &&
					(etNoteContent.getText().length() > 0) )
				{				
					if(isSaving)
					{
						Note note = new Note(	etTitle.getText().toString(),
												etNoteContent.getText().toString(),
												Calendar.getInstance().getTime().toString(),
												Calendar.getInstance().getTime().toString());
						Intent noteIntent = new Intent();
						noteIntent.putExtra("Note", note);
						setResult(RESULT_OK, noteIntent);
						finish();
						
					}
					else
					{
						saveButton.setText("Save");
						
						etTitle.setEnabled(true);
						etNoteContent.setEnabled(true);
						//etNoteContent.setTop(20);
						//etNoteContent.setHeight(60);
					}
	
					isSaving = (!(isSaving));
				}
				else
				{
					Context context = getApplicationContext();
					CharSequence text = "Either Title or note or both is empty...!";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					if(toast != null)
					{
						toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
						toast.show();
					}
				}				
			}
		});
        
		saveButton.requestFocus();

    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
    	
    	AlertDialog.Builder cAlert = new AlertDialog.Builder(this);
    	cAlert.setTitle("Confirm Delete");
    	cAlert.setMessage("Are you sure you want to delete this note? it can't be undone...!");
    	
    	cAlert.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				setResult(RESULT_DELETE, new Intent());
				finish();
			}
		});
    	
    	cAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	cAlert.create().show();
    	
		return true;
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        if(isAddingNote)
        {
        	menu.removeItem(R.id.delete);
        }
        return true;
    }
    
}
