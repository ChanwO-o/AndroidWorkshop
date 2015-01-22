package com.example.androidworkshop;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MemoListAdapter extends ArrayAdapter<Memo> {
	
	ArrayList<Memo> myList;
	
	public MemoListAdapter(Context context, int resource, ArrayList<Memo> memoList) {
		super(context, resource, memoList);
		myList = memoList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.memo_layout, null);
		}
		
		Memo m = myList.get(position);
		if (m != null) {
			TextView title = (TextView) v.findViewById(R.id.tvMemoTitle);
			TextView body = (TextView) v.findViewById(R.id.tvMemoBody);
			
			title.setText(m.getTitle());
			body.setText(m.getBody());
		}
		return v;
	}
}
