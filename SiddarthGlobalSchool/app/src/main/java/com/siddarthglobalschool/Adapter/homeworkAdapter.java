package com.siddarthglobalschool.Adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.ApplicationConstant;
import com.siddarthglobalschool.Util.HomeWorkList;
import com.siddarthglobalschool.Util.HomeWorkResponse;
import com.siddarthglobalschool.Util.UtilMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class homeworkAdapter extends RecyclerView.Adapter<homeworkAdapter.MyViewHolder> {

	private ArrayList<HomeWorkList> dataSet;
	private ArrayList<HomeWorkList> dataSetNew;
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

	public homeworkAdapter(ArrayList<HomeWorkList> data, Activity context) {
		this.dataSet = data;
		this.context = context;
		this.dataSetNew = new ArrayList<>();
		this.dataSetNew.addAll(dataSet);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_adapter, parent, false);

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


			String faqsearchDescription = dataSet.get(listPosition).getSubject_name().toLowerCase(Locale.getDefault());

			if (faqsearchDescription.contains(charText)) {
				Log.e("test", faqsearchDescription + " contains: " + charText);
				System.out.println("if search text" + faqsearchDescription);
				int startPos = faqsearchDescription.indexOf(charText);
				int endPos = startPos + charText.length();
				Spannable spanText = Spannable.Factory.getInstance().newSpannable(dataSet.get(listPosition).getSubject_name()); // <- EDITED: Use the original string, as `country` has been converted to lowercase.
				spanText.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				spanText.setSpan(new StyleSpan(Typeface.ITALIC), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				if (dataSet.get(listPosition).getSubject_name() != null)
					holder.subject.setText(spanText, TextView.BufferType.SPANNABLE);
			} else {
				if (dataSet.get(listPosition).getSubject_name() != null)
					holder.subject.setText(dataSet.get(listPosition).getSubject_name());
			}
			holder.date.setText(dataSet.get(listPosition).getHome_work_date().trim() + "");
			holder.desc.setText("Description    :"+dataSet.get(listPosition).getDescription().trim() + "");
      holder.submission.setText("Submission Date: "+dataSet.get(listPosition).getSubmission_date().trim() + "");
holder.download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {


        try {
            DownloadManager downloadmanager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(ApplicationConstant.INSTANCE.baseUrl
            +"/schoolManagement/uploads/homework/"+dataSet.get(listPosition).getFile_name());
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setTitle("My File");
            request.setDescription("Downloading");
			String finalPath =  "/SidthartGlobal/" + dataSet.get(listPosition).getFile_name() ;
			finalPath = Environment.getExternalStorageDirectory() + finalPath;
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
			request.setDestinationUri(Uri.fromFile(new File(finalPath)));
			downloadmanager.enqueue(request);
			UtilMethods.INSTANCE.dialogOk(context,"Attention!!","File Download succesfully in SidthartGlobal folder.");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public int getItemCount() {
		return dataSet.size();
	}


}
