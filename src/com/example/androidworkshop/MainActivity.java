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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	ArrayList<Memo> mList;
	ListView memoListView;
	ArrayAdapter<Memo> adapter;

	AlertDialog addMemoDialog;
	EditText titleInput;
	EditText bodyInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mList = new ArrayList<Memo>();
		Memo m1 = new Memo("Title here", "Body here");
		Memo m2 = new Memo("This is a test", "This is also a test");
		mList.add(m1);
		mList.add(m2);
		
		memoListView = (ListView) findViewById(R.id.memo_list);
		memoListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Memo memo = mList.get(position);
				Intent i = new Intent(MainActivity.this, MemoDisplay.class);
				i.putExtra("memo", memo);
				i.putExtra("position", position);
				startActivity(i);
			}

		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadlist();
		adapter = new ArrayAdapter<Memo>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mList);
		memoListView.setAdapter(adapter);
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (item.getItemId() == R.id.add_memo) {
			addMemoDialog = new AlertDialog.Builder(this)
					.setTitle("Add new memo")
					.setView(
							getLayoutInflater().inflate(
									R.layout.dialog_add_memo_layout, null))
					.setPositiveButton("Save", this)
					.setNegativeButton("Cancel", this).create();
			addMemoDialog.show();

			titleInput = (EditText) addMemoDialog
					.findViewById(R.id.etTitleInput);
			bodyInput = (EditText) addMemoDialog.findViewById(R.id.etBodyInput);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_POSITIVE) {
			String title = titleInput.getText().toString();
			String body = bodyInput.getText().toString();
			Memo memo = new Memo(title, body);
			mList.add(memo);
			saveList();
		}
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
}
