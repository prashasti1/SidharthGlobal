package com.siddarthglobalschool.Adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.ApplicationConstant;
import com.siddarthglobalschool.Util.HomeWorkList;
import com.siddarthglobalschool.Util.NoticeData;

import java.util.ArrayList;
import java.util.Locale;

public class circularAdapter extends RecyclerView.Adapter<circularAdapter.MyViewHolder> {

	private ArrayList<NoticeData> dataSet;
	private ArrayList<NoticeData> dataSetNew;
	Activity context;
	String charText = "";

	public static class MyViewHolder extends RecyclerView.ViewHolder {

		TextView date, subject, desc,submission;
		ImageView download;


		public MyViewHolder(View itemView) {
			super(itemView);
			this.date = (TextView) itemView.findViewById(R.id.date);
			this.subject = (TextView) itemView.findViewById(R.id.subject);
			this.desc = (TextView) itemView.findViewById(R.id.desc);
			this.submission = (TextView) itemView.findViewById(R.id.submission);
			this.download = (ImageView) itemView.findViewById(R.id.download);

		}
	}

	public circularAdapter(ArrayList<NoticeData> data, Activity context) {
		this.dataSet = data;
		this.context = context;
		this.dataSetNew = new ArrayList<>();
		this.dataSetNew.addAll(dataSet);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_adapter_n, parent, false);

		//view.setOnClickListener(MainActivity.myOnClickListener);

		MyViewHolder myViewHolder = new MyViewHolder(view);
		return myViewHolder;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return position;
	}
	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

		try {

			holder.subject.setText(dataSet.get(listPosition).getTitle());
			holder.date.setText(dataSet.get(listPosition).getCreated_date().trim() + "");
			holder.desc.setText(dataSet.get(listPosition).getDescription().trim() + "");
      holder.submission.setText(dataSet.get(listPosition).getCreated_date().trim() + "");
			//holder.submission.setVisibility(View.GONE);
			holder.download.setVisibility(View.GONE);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public int getItemCount() {
		return dataSet.size();
	}


}
