package com.siddarthglobalschool.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.siddarthglobalschool.Fragnemt.Result;
import com.siddarthglobalschool.R;
import com.siddarthglobalschool.Util.NoticeData;
import com.siddarthglobalschool.Util.ResultData;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {

	private ArrayList<ResultData> dataSet;
	private ArrayList<ResultData> dataSetNew;
	Activity context;
	String charText = "";

	public static class MyViewHolder extends RecyclerView.ViewHolder {

		TextView subject, max, min;


		public MyViewHolder(View itemView) {
			super(itemView);
			this.max = (TextView) itemView.findViewById(R.id.max);
			this.subject = (TextView) itemView.findViewById(R.id.subject);
			this.min = (TextView) itemView.findViewById(R.id.min);

		}
	}

	public ResultAdapter(ArrayList<ResultData> data, Activity context) {
		this.dataSet = data;
		this.context = context;
		this.dataSetNew = new ArrayList<>();
		this.dataSetNew.addAll(dataSet);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_adapter_r, parent, false);

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

			holder.subject.setText(dataSet.get(listPosition).getSubject_name());
			holder.min.setText(dataSet.get(listPosition).getMin_marks().trim() + "");
			holder.max.setText(dataSet.get(listPosition).getMax_marks().trim() + "");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public int getItemCount() {
		return dataSet.size();
	}


}
