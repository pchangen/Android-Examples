package com.pce_mason.qi.cardviewswipetest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pce_mason.qi.cardviewswipetest.dummy.DummyContent.DummyItem;

import java.util.List;

import static android.support.v7.widget.helper.ItemTouchHelper.Callback.getDefaultUIUtil;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class MySensorRecyclerViewAdapter2 extends RecyclerView.Adapter<MySensorRecyclerViewAdapter2.ViewHolder> {

    private final List<DummyItem> mValues;
    Context context;

    public MySensorRecyclerViewAdapter2(List<DummyItem> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sensor2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.extendViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.showExtendView();
            }
        });
        holder.hideExtendViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.hideExtendView();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView mIdView;
        public final TextView mContentView;
        public final RelativeLayout foregroundView, backgorundView;
        public final FrameLayout itemLayout;
        public final Button extendViewButton, hideExtendViewButton;
        public final LinearLayout extendViewLayout;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            foregroundView = (RelativeLayout) view.findViewById(R.id.view_foreground);
            backgorundView = (RelativeLayout) view.findViewById(R.id.view_background);
            itemLayout = (FrameLayout) view.findViewById(R.id.item_layout);
            extendViewLayout = (LinearLayout) view.findViewById(R.id.extend_view_layout);
            extendViewButton = (Button) view.findViewById(R.id.extend_view_btn);
            hideExtendViewButton = (Button) view.findViewById(R.id.hide_extend_view_btn);
        }
        public void hideExtendView(){
            extendViewLayout.setVisibility(View.GONE);
            extendViewButton.setVisibility(View.VISIBLE);
        }
        public void showExtendView(){
            extendViewLayout.setVisibility(View.VISIBLE);
            extendViewButton.setVisibility(View.GONE);
        }
        public void sensorDeleteAlert(){
            final int position = getAdapterPosition();
            final String delId = mValues.get(position).id;
            final String delContent = mValues.get(position).content;
            final String delDetails = mValues.get(position).details;
            mValues.remove(position);
            notifyItemRemoved(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.dialog_alert_dialog)
                    .setPositiveButton(R.string.discard, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
//                            notifyItemRemoved(position);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
//                            getDefaultUIUtil().clearView(foregroundView);
                            mValues.add(position,new DummyItem(delId,delContent,delDetails));
                            notifyItemInserted(position);
                        }
                    });
            builder.show();
        }

    }
}
