package com.example.study.android.androidscreening.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by songyan on 2017/2/5.
 */

 public  class AttrList implements Serializable {

    private List<Attr> attr;

    public List getAttr() {
        return attr;
    }

    public void setAttr(List attr) {
        this.attr = attr;
    }

    public static class Attr implements Serializable {
        private String key;
        private List<Vals> vals;
        //single_check=1单选 =0多选
        private String single_check;
        private boolean isoPen = false;
        private String showStr = "";
        private List<String> selectStrs;
        private List<Vals> SelectVals;

        public List<Vals> getSelectVals() {
            return SelectVals;
        }

        public void setSelectVals(List<Vals> selectVals) {
            SelectVals = selectVals;
        }

        public boolean isoPen() {
            return isoPen;
        }

        public void setIsoPen(boolean isoPen) {
            this.isoPen = isoPen;
        }

        public List<String> getSelectStrs() {
            return selectStrs;
        }

        public void setSelectStrs(List<String> selectStrs) {
            this.selectStrs = selectStrs;
        }

        public String getShowStr() {
            return showStr;
        }

        public void setShowStr(String showStr) {
            this.showStr = showStr;
        }

        public List<Vals> getVals() {
            return vals;
        }

        public void setVals(List<Vals> vals) {
            this.vals = vals;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }


        public String getSingle_check() {
            return single_check;
        }

        public void setSingle_check(String single_check) {
            this.single_check = single_check;
        }

        public static class Vals implements Serializable {
            private String val;
            private boolean isChick;

            public boolean isChick() {
                return isChick;
            }

            public void setChick(boolean chick) {
                isChick = chick;
            }

            public String getV() {
                return val;
            }

            public void setV(String v) {
                this.val = v;
            }
        }
    }
}
