package com.tdr.familytreasure.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/8/2 15:26
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GetBannerInfo {

    /**
     * ResultCode : 0
     * ResultText : ok
     * DataTypeCode : GetBannerInfo
     * TaskID : 1
     * Content : {"BANNERLIST":[{"BANNERID":null,"PICTUREURL":"http://ad8.laoren.iotone.cn/header_bg2.png",
     * "URLADD":"http://wz.ga.iotone.cn/hospital.html","ISACTIVE":null,"PUBLISHTIME":null,"PUBLISHER":"天地人",
     * "UNSHELFTIME":null,"TITLE":"专家门诊"}]}
     */

    private String ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    private ContentBean Content;

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String ResultCode) {
        this.ResultCode = ResultCode;
    }

    public String getResultText() {
        return ResultText;
    }

    public void setResultText(String ResultText) {
        this.ResultText = ResultText;
    }

    public String getDataTypeCode() {
        return DataTypeCode;
    }

    public void setDataTypeCode(String DataTypeCode) {
        this.DataTypeCode = DataTypeCode;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String TaskID) {
        this.TaskID = TaskID;
    }

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        private List<BANNERLISTBean> BANNERLIST;

        public List<BANNERLISTBean> getBANNERLIST() {
            return BANNERLIST;
        }

        public void setBANNERLIST(List<BANNERLISTBean> BANNERLIST) {
            this.BANNERLIST = BANNERLIST;
        }

        public static class BANNERLISTBean {
            /**
             * BANNERID : null
             * PICTUREURL : http://ad8.laoren.iotone.cn/header_bg2.png
             * URLADD : http://wz.ga.iotone.cn/hospital.html
             * ISACTIVE : null
             * PUBLISHTIME : null
             * PUBLISHER : 天地人
             * UNSHELFTIME : null
             * TITLE : 专家门诊
             */

            private Object BANNERID;
            private String PICTUREURL;
            private String URLADD;
            private Object ISACTIVE;
            private Object PUBLISHTIME;
            private String PUBLISHER;
            private Object UNSHELFTIME;
            private String TITLE;

            public Object getBANNERID() {
                return BANNERID;
            }

            public void setBANNERID(Object BANNERID) {
                this.BANNERID = BANNERID;
            }

            public String getPICTUREURL() {
                return PICTUREURL;
            }

            public void setPICTUREURL(String PICTUREURL) {
                this.PICTUREURL = PICTUREURL;
            }

            public String getURLADD() {
                return URLADD;
            }

            public void setURLADD(String URLADD) {
                this.URLADD = URLADD;
            }

            public Object getISACTIVE() {
                return ISACTIVE;
            }

            public void setISACTIVE(Object ISACTIVE) {
                this.ISACTIVE = ISACTIVE;
            }

            public Object getPUBLISHTIME() {
                return PUBLISHTIME;
            }

            public void setPUBLISHTIME(Object PUBLISHTIME) {
                this.PUBLISHTIME = PUBLISHTIME;
            }

            public String getPUBLISHER() {
                return PUBLISHER;
            }

            public void setPUBLISHER(String PUBLISHER) {
                this.PUBLISHER = PUBLISHER;
            }

            public Object getUNSHELFTIME() {
                return UNSHELFTIME;
            }

            public void setUNSHELFTIME(Object UNSHELFTIME) {
                this.UNSHELFTIME = UNSHELFTIME;
            }

            public String getTITLE() {
                return TITLE;
            }

            public void setTITLE(String TITLE) {
                this.TITLE = TITLE;
            }
        }
    }
}
