package com.example.study.android.androidscreening.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.study.android.androidscreening.R;
import com.example.study.android.androidscreening.adapter.RightSideslipLayAdapter;
import com.example.study.android.androidscreening.model.AttrList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性选择的布局及逻辑
 */
public class RightSideslipLay extends RelativeLayout {
    private Context mCtx;
    private ListView selectList;
    private Button resetBrand;
    private Button okBrand;
    private ImageView backBrand;
    private RelativeLayout mRelateLay;
    private RightSideslipLayAdapter slidLayFrameAdapter;
    private String JsonStr = "{\"attr\": [{ \"isoPen\": true,\"single_check\": 0,\"key\": \"品牌\", \"vals\": [ { \"val\": \"雅格\"}, {\"val\": \"志高/Chigo\" }, {\"val\": \"格东方\" },{\"val\": \"Chigo\" }, {\"val\": \"格OW\" },{\"val\": \"志go\" }, {\"val\": \"格LLOW\" },{\"val\": \"志o\" }, {\"val\": \"LLOW\" }, {\"val\": \"众桥\"},{\"val\": \"超人/SID\" },{ \"val\": \"扬子342\" }, { \"val\": \"扬舒服\" }, { \"val\": \"扬子东方\"},{ \"val\": \"荣事达/Royalstar\"}]},{\"single_check\": 0,\"key\": \"灭蚊器类型\", \"vals\": [{ \"val\": \"光触媒灭蚊器\"}]},{\"single_check\": 0,\"key\": \"买的多，价更低！！！\", \"vals\": [{\"val\": \"1个\"}]},{ \"single_check\": 0, \"key\": \"超人灭蚊器型号\",\"vals\": [{\"val\": \"SI23\" }]}]}";

    public RightSideslipLay(Context context) {
        super(context);
        mCtx = context;
        inflateView();
    }

    private void inflateView() {
        View.inflate(getContext(), R.layout.include_right_sideslip_layout, this);
        selectList = (ListView) findViewById(R.id.selsectFrameLV);
        backBrand = (ImageView) findViewById(R.id.select_brand_back_im);
        resetBrand = (Button) findViewById(R.id.fram_reset_but);
        mRelateLay = (RelativeLayout) findViewById(R.id.select_frame_lay);
        okBrand = (Button) findViewById(R.id.fram_ok_but);
        resetBrand.setOnClickListener(mOnClickListener);
        okBrand.setOnClickListener(mOnClickListener);
        backBrand.setOnClickListener(mOnClickListener);
        mRelateLay.setOnClickListener(mOnClickListener);
        setUpList();
    }

    private List<AttrList.Attr.Vals> ValsData;

    private List<AttrList.Attr> setUpBrandList(List<AttrList.Attr> mAttrList) {
        if ("品牌".equals(mAttrList.get(0).getKey())) {
            ValsData = mAttrList.get(0).getVals();
            mAttrList.get(0).setVals(getValsDatas(mAttrList.get(0).getVals()));
        }
        return mAttrList;
    }

    private AttrList attr;

    private void setUpList() {
        attr = new Gson().fromJson(JsonStr.toString(), AttrList.class);
        if (slidLayFrameAdapter == null) {
            slidLayFrameAdapter = new RightSideslipLayAdapter(mCtx, setUpBrandList(attr.getAttr()));
            selectList.setAdapter(slidLayFrameAdapter);
        } else {
            slidLayFrameAdapter.replaceAll(attr.getAttr());
        }
        slidLayFrameAdapter.setAttrCallBack(new RightSideslipLayAdapter.SelechDataCallBack() {
            @Override
            public void setupAttr(List<String> mSelectData, String key) {

            }
        });

        slidLayFrameAdapter.setMoreCallBack(new RightSideslipLayAdapter.SelechMoreCallBack() {

            @Override
            public void setupMore(List<AttrList.Attr.Vals> mSelectData) {
                getPopupWindow(mSelectData);
                mDownMenu.setOnMeanCallBack(meanCallBack);
            }
        });

    }

    //在第二个页面改变后，返回时第一个界面随之改变，使用的接口回调
    private RightSideslipChildLay.onMeanCallBack meanCallBack = new RightSideslipChildLay.onMeanCallBack() {
        @Override
        public void isDisMess(boolean isDis, List<AttrList.Attr.Vals> mBrandData,String str) {
            if (mBrandData!=null&&!TextUtils.isEmpty(str)){
                if (attr.getAttr().size() > 0) {
                    ((AttrList.Attr) attr.getAttr().get(0)).setVals(getValsDatas(mBrandData));
                    ((AttrList.Attr) attr.getAttr().get(0)).setShowStr(str);
                }
                slidLayFrameAdapter.replaceAll(attr.getAttr());
            }

            dismissMenuPop();
        }
    };

    private List<AttrList.Attr.Vals> getValsDatas(List<AttrList.Attr.Vals> mBrandData) {
        List<AttrList.Attr.Vals> mVals = new ArrayList<AttrList.Attr.Vals>();
        if (mBrandData != null && mBrandData.size() > 0) {
            for (int i = 0; i < mBrandData.size(); i++) {
                if (mVals.size() >= 8) {
                    AttrList.Attr.Vals valsAdd = new AttrList.Attr.Vals();
                    valsAdd.setV("查看更多 >");
                    mVals.add(valsAdd);
                    continue;
                } else {
                    mVals.add(mBrandData.get(i));
                }
            }
            mVals = mVals.size() >= 9 ? mVals.subList(0, 9) : mVals;
            return mVals;

        }
        return null;
    }

    private OnClickListenerWrapper mOnClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.fram_reset_but:
                case R.id.select_brand_back_im:
                case R.id.fram_ok_but:
                    menuCallBack.setupCloseMean();
                    break;
            }
        }
    };

    /**
     * 关闭窗口
     */
    private void dismissMenuPop() {
        if (mMenuPop != null) {
            mMenuPop.dismiss();
            mMenuPop = null;
        }

    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow(List<AttrList.Attr.Vals> mSelectData) {
        if (mMenuPop != null) {
            dismissMenuPop();
            return;
        } else {
            initPopuptWindow(mSelectData);
        }
    }

    /**
     * 创建PopupWindow
     */
    private PopupWindow mMenuPop;
    public RightSideslipChildLay mDownMenu;

    protected void initPopuptWindow(List<AttrList.Attr.Vals> mSelectData) {
        mDownMenu = new RightSideslipChildLay(getContext(), ValsData, mSelectData);
        if (mMenuPop == null) {
            mMenuPop = new PopupWindow(mDownMenu, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        }
        mMenuPop.setBackgroundDrawable(new BitmapDrawable());
        mMenuPop.setAnimationStyle(R.style.popupWindowAnimRight);
        mMenuPop.setFocusable(true);
        mMenuPop.showAtLocation(RightSideslipLay.this, Gravity.TOP, 100, UiUtils.getStatusBarHeight(mCtx));
        mMenuPop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                dismissMenuPop();
            }
        });
    }


    private CloseMenuCallBack menuCallBack;

    public interface CloseMenuCallBack {
        void setupCloseMean();
    }

    public void setCloseMenuCallBack(CloseMenuCallBack menuCallBack) {
        this.menuCallBack = menuCallBack;
    }
}
