package com.example.study.android.androidscreening.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.study.android.androidscreening.R;
import com.example.study.android.androidscreening.model.AttrList;
import com.example.study.android.androidscreening.ui.AutoMeasureHeightGridView;
import com.example.study.android.androidscreening.ui.OnClickListenerWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class RightSideslipLayAdapter extends SimpleBaseAdapter<AttrList.Attr> {

    public RightSideslipLayAdapter(Context context, List<AttrList.Attr> data) {
        super(context, data);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_right_sideslip_lay;
    }


    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        TextView itemFrameTitleTv = holder.getView(R.id.item_frameTv);
        TextView itemFrameSelectTv = holder.getView(R.id.item_selectTv);
        LinearLayout layoutItem = holder.getView(R.id.item_select_lay);
        AutoMeasureHeightGridView itemFrameGv = holder.getView(R.id.item_selectGv);
        itemFrameGv.setVisibility(View.VISIBLE);
        AttrList.Attr mAttr = getData().get(position);
        itemFrameTitleTv.setText(mAttr.getKey());
        itemFrameSelectTv.setText(mAttr.getShowStr());
        if (mAttr.getVals() != null) {
            convertView.setVisibility(View.VISIBLE);
            if (mAttr.isoPen()) {
                itemFrameSelectTv.setTag(itemFrameGv);
                itemFrameSelectTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_prodcatelist, 0);
                fillLv2CateViews(mAttr, mAttr.getVals(), itemFrameGv);
                layoutItem.setTag(itemFrameGv);
            } else {
                fillLv2CateViews(mAttr, mAttr.getVals().subList(0, 0), itemFrameGv);
                itemFrameSelectTv.setText(mAttr.getShowStr());
                itemFrameSelectTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_prodcatelist, 0);
                layoutItem.setTag(itemFrameGv);
                itemFrameSelectTv.setVisibility(View.VISIBLE);
            }
            layoutItem.setOnClickListener(onClickListener);
        } else {
            convertView.setVisibility(View.GONE);
        }
        itemFrameGv.setTag(position);
        return convertView;
    }
    private void fillLv2CateViews(final AttrList.Attr mAttr, List<AttrList.Attr.Vals> list,
                                  AutoMeasureHeightGridView childLvGV) {
        final RightSideslipLayChildAdapter mChildAdapter;
        if (mAttr.getSelectVals() == null) {
            mAttr.setSelectVals(new ArrayList<AttrList.Attr.Vals>());
        }
        if (childLvGV.getAdapter() == null) {
            mChildAdapter = new RightSideslipLayChildAdapter(context, list, mAttr.getSelectVals());
            childLvGV.setAdapter(mChildAdapter);
        } else {
            mChildAdapter = (RightSideslipLayChildAdapter) childLvGV.getAdapter();
            mAttr.setSelectVals(mAttr.getSelectVals());
            mChildAdapter.setSeachData(mAttr.getSelectVals());
            mChildAdapter.replaceAll(list);
        }

        mChildAdapter.setSlidLayFrameChildCallBack(new RightSideslipLayChildAdapter.SlidLayFrameChildCallBack() {
            @Override
            public void CallBackSelectData( List<AttrList.Attr.Vals> seachData) {

                mAttr.setShowStr(setupSelectStr(seachData));
                mAttr.setSelectStrs(setupSelectDataStr(seachData));
                mAttr.setSelectVals(seachData);
                notifyDataSetChanged();
                selechDataCallBack.setupAttr(setupSelectDataStr(seachData), mAttr.getKey());

            }
        });
        mChildAdapter.setShowPopCallBack(new RightSideslipLayChildAdapter.ShowPopCallBack() {
            @Override
            public void setupShowPopCallBack(List<AttrList.Attr.Vals> seachData) {
                mAttr.setSelectStrs(setupSelectDataStr(seachData));
                mAttr.setSelectVals(seachData);
                mAttr.setShowStr(setupSelectStr(seachData));
                mSelechMoreCallBack.setupMore(seachData);
            }
        });

    }

    private String setupSelectStr(List<AttrList.Attr.Vals> data) {
        StringBuilder builder = new StringBuilder();
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                if (data.size() == 1) {
                    builder.append(data.get(i).getV());
                } else {
                    if (i == data.size() - 1) {
                        builder.append(data.get(i).getV());
                    } else {
                        builder.append(data.get(i).getV() + ",");
                    }
                }

            }
            return new String(builder);
        } else {
            return "";
        }
    }

    private List<String> setupSelectDataStr(List<AttrList.Attr.Vals> data) {
        List<String> mSelectData = new ArrayList<String>();
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                mSelectData.add(data.get(i).getV());
            }
            return mSelectData;
        } else {
            return null;
        }

    }


    OnClickListenerWrapper onClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {
            int id = v.getId();
            if (id == R.id.item_select_lay) {
                //    TextView moreTV = (TextView) v.getTag();
                AutoMeasureHeightGridView childLv3GV = (AutoMeasureHeightGridView) v.getTag();
                int pos = (int) childLv3GV.getTag();
                AttrList.Attr itemdata = data.get(pos);
                boolean isSelect = !itemdata.isoPen();
                // 再将当前选择CB的实际状态
                itemdata.setIsoPen(isSelect);
                notifyDataSetChanged();
            }
        }
    };

    public interface SelechDataCallBack {
        void setupAttr(List<String> mSelectData, String key);
    }

    public SelechDataCallBack selechDataCallBack;

    public void setAttrCallBack(SelechDataCallBack m) {
        selechDataCallBack = m;

    }

    public interface SelechMoreCallBack {
        void setupMore(List<AttrList.Attr.Vals> da);
    }

    public SelechMoreCallBack mSelechMoreCallBack;

    public void setMoreCallBack(SelechMoreCallBack m) {
        mSelechMoreCallBack = m;

    }
}
