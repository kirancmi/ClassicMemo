package com.kirancmi.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class NoteManager {

	private static NoteManager noteMgr = null;
	private static Context _appContext = null;
	private String masterFilePath = "MasterFile.txt";

	private List<String> noteNamesList = null;
	private List<Note> noteDetailsList = null;

	public static NoteManager getInstance(Context context)
	{
		if(context != null)
		{
			_appContext = context;
		}
		if(noteMgr == null)
		{
			noteMgr = new NoteManager();
		}
		return noteMgr;
	}

	public List<Note> getNoteDetailsList()
	{
		return noteDetailsList;
	}
	
	public boolean AddNote(Note note)
	{
		boolean bRetVal = false;
		
		if(note != null)
		{
			String filename = CreateFilename(note.getTitle());
			//noteNamesList.add(filename);
			noteNamesList.add(0, filename);
			noteDetailsList.add(0, note);
			bRetVal = true;
		}
		
		return bRetVal;
	}
	
	public void UpdateNote(int nIndex, Note note)
	{
		if( (nIndex >= 0) &&
			(nIndex < noteNamesList.size()) &&
			(note != null) )
		{
			//int num = (noteNamesList.size() - nIndex - 1);
			//noteDetailsList.set(num, note);
			noteDetailsList.set(nIndex, note);
		}
	}
	
	public boolean DeleteNote(int nIndex)
	{
		boolean bRetVal = false;
		
		if( (nIndex >= 0) &&
			(nIndex < noteNamesList.size()) )
		{
			String strFile = noteNamesList.get(nIndex);
			if(!strFile.isEmpty())
			{
				File file = new File(strFile);
				if(file != null)
				{
					boolean bDel = file.delete();
					
					noteNamesList.remove(nIndex);
					noteDetailsList.remove(nIndex);
					//int num = (noteNamesList.size() - nIndex - 1);
					//noteDetailsList.remove(num);
					
					//bRetVal = true;
					bRetVal = bDel;
				}
			}
		}
		
		return bRetVal;
	}
	
	public void ReadAllNoteDetails()
	{
		if(noteNamesList != null)
		{
			noteNamesList.clear();
		}
		
		if(noteDetailsList != null)
		{
			noteDetailsList.clear();
		}
		
		ReadMasterFile();
		
		for(String noteName : noteNamesList)
		{
			ReadNoteDetails(noteName);
		}
	}

	public void WriteAllNoteDetails()
	{
		Note note = null;
		
		for(int nIndex = 0; nIndex < noteNamesList.size(); nIndex++)
		{
			note = noteDetailsList.get(nIndex);
			if(note.IsModified())
			{
				WriteNoteDetails(noteNamesList.get(nIndex), note);
			}
		}
		WriteMasterFile();
	}

	private void ReadMasterFile()
	{
		try 
		{
            InputStream inputStream = _appContext.openFileInput(masterFilePath);
             
            if ( inputStream != null ) 
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                if(inputStreamReader != null)
                {
	                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	                if(bufferedReader != null)
	                {
		                String receiveString = "";
		                while ( (receiveString = bufferedReader.readLine()) != null )
		                {
		                	noteNamesList.add(receiveString);
		                }
	                }
                }
                
                inputStream.close();
                inputStream = null;
            }
        }
        catch (FileNotFoundException e) 
        {
            Log.e("Exception", "File not found: " + e.toString());
        } 
		catch (IOException e) 
        {
        	Log.d("Exception", "In LoadNoteNames() API " + e.toString() );
        }
	}

	public void WriteMasterFile()
	{
		try 
		{
            //OutputStream outputStream = _appContext.openFileOutput(masterFilePath, _appContext.MODE_PRIVATE);
			OutputStream outputStream = _appContext.openFileOutput(masterFilePath, Context.MODE_PRIVATE);
            
            if ( outputStream != null ) 
            {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                if(outputStreamWriter != null)
                {
	                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
	                if(bufferedWriter != null)
	                {
	                	
	                	for(String noteName : noteNamesList)
	            		{
	                		bufferedWriter.write(noteName);
			                bufferedWriter.newLine();
			                bufferedWriter.flush();
	            		}
	            		
	                	
	                	/*
	                	for(int nIndex = (noteNamesList.size() - 1); nIndex >= 0; nIndex--)
	            		{
	                		bufferedWriter.write(noteNamesList.get(nIndex));
			                bufferedWriter.newLine();
			                bufferedWriter.flush();
	            		}
	            		*/
	                }
                }
                
                outputStream.close();
                outputStream = null;
            }
        }
        catch (FileNotFoundException e) 
        {
            Log.e("Exception", "File not found: " + e.toString());
        } 
		catch (IOException e) 
        {
        	Log.d("Exception", "In WriteNoteDetails() API " + e.toString() );
        }
	}
	
	private String CreateFilename(String strTitle)
	{
		String strFilename = "";
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		if(formatter != null)
		{
			Date now = new Date();
			if(now != null)
			{
				strFilename = (strTitle + "_" + formatter.format(now) + ".txt");
			}
		}
		return strFilename;
	}
	
	private void ReadNoteDetails(String noteFilepath)
	{
		try 
		{
            InputStream inputStream = _appContext.openFileInput(noteFilepath);
             
            if ( inputStream != null ) 
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                if(inputStreamReader != null)
                {
	                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	                if(bufferedReader != null)
	                {
	                	Note note = new Note();
		                String receiveString = "";
		                
		                if(note != null)
		                {
			                // first line is title
			                receiveString = bufferedReader.readLine();
			                if(receiveString.isEmpty() == false)
			                {
			                	note.setTitle(receiveString);
			                }
			                
			                // second line is date created
			                receiveString = bufferedReader.readLine();
			                if(receiveString.isEmpty() == false)
			                {
			                	note.setDateCreated(receiveString);
			                }
			                
			                // third line is date modified
			                receiveString = bufferedReader.readLine();
			                if(receiveString.isEmpty() == false)
			                {
			                	note.setDateModified(receiveString);
			                }
	
			                // rest of the file is content of the note
			                StringBuilder strBuilder = new StringBuilder();
			                while ( (receiveString = bufferedReader.readLine()) != null )
			                {
			                	strBuilder.append(receiveString);
			                	strBuilder.append("\n");
			                }
			                if(strBuilder.length() != 0)
			                {
			                	note.setNoteContent(strBuilder.toString());
			                }
			                
			                noteDetailsList.add(note);
		                }
	                }
                }
                
                inputStream.close();
                inputStream = null;
            }
        }
        catch (FileNotFoundException e) 
        {
            Log.e("Exception", "File not found: " + e.toString());
        } 
		catch (IOException e) 
        {
        	Log.d("Exception", "In ReadNoteDetails() API " + e.toString() );
        }
	}

	private void WriteNoteDetails(String noteFilepath, Note note)
	{
		try 
		{
            //OutputStream outputStream = _appContext.openFileOutput(noteFilepath, _appContext.MODE_PRIVATE);
			OutputStream outputStream = _appContext.openFileOutput(noteFilepath, Context.MODE_PRIVATE);
             
            if ( outputStream != null )
            {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                if(outputStreamWriter != null)
                {
	                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
	                if(bufferedWriter != null)
	                {
		                if(note != null)
		                {
			                // first line is title
			                bufferedWriter.write(note.getTitle());
			                bufferedWriter.newLine();
			                bufferedWriter.flush();
			                
			                // second line is date created
			                bufferedWriter.write(note.getDateCreated());
			                bufferedWriter.newLine();
			                bufferedWriter.flush();
			                
			                // third line is date modified
			                bufferedWriter.write(note.getDateModified());
			                bufferedWriter.newLine();
			                bufferedWriter.flush();
			                
			                // from the next line it is note content
			                bufferedWriter.write(note.getNoteContent());
			                bufferedWriter.newLine();
			                bufferedWriter.flush();
		                }
	                }
                }
                
                outputStream.close();
                outputStream = null;
            }
        }
        catch (FileNotFoundException e) 
        {
            Log.e("Exception", "File not found: " + e.toString());
        } 
		catch (IOException e) 
        {
        	Log.d("Exception", "In WriteNoteDetails() API " + e.toString() );
        }
	}

	private NoteManager()
	{
		noteNamesList = new ArrayList<String>();
		noteDetailsList = new ArrayList<Note>();
	}

	/*
	private boolean IsMasterFileExists()
	{
		return IsFileExists(masterFilePath);
	}
	*/

	/*
	private boolean IsFileExists(String filePath)
	{
		boolean bRetval = false;
		
		try
		{
			//FileOutputStream fos = _appContext.openFileOutput(masterFilePath, _appContext.MODE_APPEND);
			FileOutputStream fos = _appContext.openFileOutput(masterFilePath, Context.MODE_APPEND);

			if(fos != null)
			{
				bRetval = true;
				fos.close();
			}
		}
		catch(Exception e)
		{
			Log.d("Exception", "In IsMasterFileExists() API " + e.toString() );
		}
		
		return bRetval;
	}
	*/
}
