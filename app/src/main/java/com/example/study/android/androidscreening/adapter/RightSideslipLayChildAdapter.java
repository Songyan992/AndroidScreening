package com.example.study.android.androidscreening.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.study.android.androidscreening.R;
import com.example.study.android.androidscreening.model.AttrList;
import com.example.study.android.androidscreening.ui.OnClickListenerWrapper;

import java.util.HashSet;
import java.util.List;

/**
 */

public class RightSideslipLayChildAdapter extends SimpleBaseAdapter<AttrList.Attr.Vals> {

    private List<AttrList.Attr.Vals> seachData;

    public List<AttrList.Attr.Vals> getSeachData() {
        return seachData;
    }

    public void setSeachData(List<AttrList.Attr.Vals> seachData) {
        this.seachData = seachData;
    }

    public RightSideslipLayChildAdapter(Context context, List<AttrList.Attr.Vals> data,
                                        List<AttrList.Attr.Vals> selectVals) {
        super(context, data);
        this.seachData = selectVals;
    }

    @Override
    public int getItemResource() {
        return R.layout.gv_right_sideslip_child_layout;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        final CheckBox itemFrameRb = holder.getView(R.id.item_frameRb);


        AttrList.Attr.Vals vals = getData().get(position);

        itemFrameRb.setText(vals.getV());


        itemFrameRb.setTag(position);
        itemFrameRb.setChecked(vals.isChick());
        if ("查看更多 >".equals(vals.getV())) {
            itemFrameRb.setBackgroundResource(0);
            itemFrameRb.setTextColor(itemFrameRb.getResources().getColor(R.color.colorPrimary));
        }
        itemFrameRb.setOnCheckedChangeListener(mOnCheckedChangeListener);
        return convertView;
    }

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int position = (int) buttonView.getTag();
            final AttrList.Attr.Vals vals = getData().get(position);
            buttonView.setOnClickListener(new OnClickListenerWrapper() {
                @Override
                protected void onSingleClick(View v) {
                    if ("查看更多 >".equals(vals.getV())) {
                        mShowPopCallBack.setupShowPopCallBack(seachData);
                    }
                }
            });

            if (isChecked) {
                if ("查看更多 >".equals(vals.getV())) {
                    return;
                }
                vals.setChick(true);
                seachData.add(vals);
            } else {
                if ("查看更多 >".equals(vals.getV())) {
                    return;
                }
                vals.setChick(false);
                seachData.remove(vals);

            }
            notifyDataSetChanged();
            slidLayFrameChildCallBack.CallBackSelectData(seachData);


        }
    };


    //去除重复数据
    public List<AttrList.Attr.Vals> removeDuplicate(List<AttrList.Attr.Vals> list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    private SlidLayFrameChildCallBack slidLayFrameChildCallBack;

    public interface SlidLayFrameChildCallBack {
        void CallBackSelectData(List<AttrList.Attr.Vals> seachData);
    }

    public void setSlidLayFrameChildCallBack(SlidLayFrameChildCallBack slidLayFrameChildCallBack) {
        this.slidLayFrameChildCallBack = slidLayFrameChildCallBack;
    }

    private ShowPopCallBack mShowPopCallBack;

    public interface ShowPopCallBack {
        void setupShowPopCallBack(List<AttrList.Attr.Vals> seachData);
    }

    public void setShowPopCallBack(ShowPopCallBack mShowPopCallBack) {
        this.mShowPopCallBack = mShowPopCallBack;
    }

}