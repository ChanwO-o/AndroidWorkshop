package com.example.androidworkshop;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MemoDisplay extends ActionBarActivity implements OnClickListener {

	ArrayList<Memo> mList;
	private Memo memo;
	private int position;
	private TextView memoText;
	
	AlertDialog editMemoDialog;
	EditText titleInput;
	EditText bodyInput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_memo_display);
		
		mList = new ArrayList<Memo>();
		Intent i = getIntent();	
		memo = (Memo) i.getSerializableExtra("memo");
		position = i.getIntExtra("position", 0);
		setTitle(memo.getTitle());
		memoText = (TextView) findViewById(R.id.tvMemoBodyText);
		memoText.setText(memo.getBody());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		loadlist();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.memo_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.delete_memo) {
			mList.remove(position);
			saveList();
			Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
			finish();
			return true;
		}
		else if (id == R.id.edit_memo) {
			editMemoDialog = new AlertDialog.Builder(this)
					.setTitle("Edit")
					.setView(getLayoutInflater().inflate(R.layout.dialog_add_memo_layout, null))
					.setPositiveButton("Save", this)
					.setNegativeButton("Cancel", this).create();
			editMemoDialog.show();
			titleInput = (EditText) editMemoDialog
					.findViewById(R.id.etTitleInput);
			bodyInput = (EditText) editMemoDialog.findViewById(R.id.etBodyInput);
			titleInput.setText(memo.getTitle());
			bodyInput.setText(memo.getBody());
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void loadlist() {
		try {
			FileInputStream fis = getApplicationContext().openFileInput("memo_list.txt");
			ObjectInputStream is = new ObjectInputStream(fis);
			mList = (ArrayList<Memo>) is.readObject();
			is.close();
		} catch (Exception e) {
		}
	}
	
	public void saveList() {
		try {
			FileOutputStream fos = getApplicationContext().openFileOutput("memo_list.txt", Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(mList);
			os.close();
		} catch (Exception e) {
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_POSITIVE) {
			String title = titleInput.getText().toString();
			String body = bodyInput.getText().toString();
			memo.setTitle(title);
			memo.setBody(body);
			mList.remove(position);
			mList.add(memo);
			saveList();
			finish();
		}
	}
}