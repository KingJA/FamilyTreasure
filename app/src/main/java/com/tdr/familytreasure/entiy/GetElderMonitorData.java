package com.tdr.familytreasure.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/8/24 7:31
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GetElderMonitorData {

    /**
     * ResultCode : 0
     * ResultText : 查询老人轨迹成功
     * DataTypeCode : GetElderMonitorData
     * TaskID : 1
     * Content : {"MONITORDATALIST":[{"BASESTATIONID":221178,"BASESTATIONNAME":"浙江省温州市瓯海区兴吕北路","LNG":120.604764,
     * "LAT":27.975517,"BASESATTIONTIME":"2017-08-24 07:53:44"},{"BASESTATIONID":136701,
     * "BASESTATIONNAME":"D场边高21-西岙村门口","LNG":120.73551,"LAT":27.84819,"BASESATTIONTIME":"2017-08-24 07:53:23"},
     * {"BASESTATIONID":221329,"BASESTATIONNAME":"浙江省温州市瓯海区辽前二路45号","LNG":120.679557,"LAT":27.986866,
     * "BASESATTIONTIME":"2017-08-24 07:53:06"},{"BASESTATIONID":138794,"BASESTATIONNAME":"稠泛村红绿灯路口","LNG":120.09331,
     * "LAT":27.696684,"BASESATTIONTIME":"2017-08-24 07:53:03"},{"BASESTATIONID":221843,
     * "BASESTATIONNAME":"浙江省温州市苍南县玉苍路","LNG":120.434867,"LAT":27.516375,"BASESATTIONTIME":"2017-08-24 07:52:46"},
     * {"BASESTATIONID":137732,"BASESTATIONNAME":"占家埠-水头交警大队","LNG":120.33995,"LAT":27.64566,
     * "BASESATTIONTIME":"2017-08-24 07:52:20"},{"BASESTATIONID":136008,"BASESTATIONNAME":"D玉高13-青松路49号",
     * "LNG":120.63533,"LAT":27.78655,"BASESATTIONTIME":"2017-08-24 07:51:30"},{"BASESTATIONID":137248,
     * "BASESTATIONNAME":"D莘高53-市场南路92号","LNG":120.67519,"LAT":27.77869,"BASESATTIONTIME":"2017-08-24 07:50:16"},
     * {"BASESTATIONID":221754,"BASESTATIONNAME":"浙江省温州市苍南县陇西路","LNG":120.550785,"LAT":27.567648,
     * "BASESATTIONTIME":"2017-08-24 07:49:36"},{"BASESTATIONID":136568,"BASESTATIONNAME":"D莘高35-东兴工业区车舟汽配边",
     * "LNG":120.68675,"LAT":27.77897,"BASESATTIONTIME":"2017-08-24 07:49:08"},{"BASESTATIONID":222219,
     * "BASESTATIONNAME":"浙江省温州市龙湾区东河路12号","LNG":120.81751,"LAT":27.967845,"BASESATTIONTIME":"2017-08-24 07:48:44"},
     * {"BASESTATIONID":177189,"BASESTATIONNAME":"D塘高09-新溪成溪路104国道","LNG":120.67026,"LAT":27.83495,
     * "BASESATTIONTIME":"2017-08-24 07:48:33"},{"BASESTATIONID":136795,"BASESTATIONNAME":"D上高12-三桥接线薛东路西",
     * "LNG":120.69005,"LAT":27.75719,"BASESATTIONTIME":"2017-08-24 07:48:06"},{"BASESTATIONID":142981,
     * "BASESTATIONNAME":"锐思特汽车酒店","LNG":120.674377,"LAT":27.996734,"BASESATTIONTIME":"2017-08-24 07:47:25"},
     * {"BASESTATIONID":134420,"BASESTATIONNAME":"锦江路-砖坦巷与划龙桥前路","LNG":120.6825,"LAT":27.99138,
     * "BASESATTIONTIME":"2017-08-24 07:46:53"},{"BASESTATIONID":221170,"BASESTATIONNAME":"浙江省温州市瓯海区今汇路",
     * "LNG":120.607136,"LAT":27.964239,"BASESATTIONTIME":"2017-08-24 07:46:25"},{"BASESTATIONID":136882,
     * "BASESTATIONNAME":"山门-红军公园门口","LNG":120.24077,"LAT":27.63815,"BASESATTIONTIME":"2017-08-24 07:46:15"},
     * {"BASESTATIONID":138150,"BASESTATIONNAME":"鳌江094-D荷莲路-路口","LNG":120.554228,"LAT":27.625803,
     * "BASESATTIONTIME":"2017-08-24 07:46:13"},{"BASESTATIONID":137182,"BASESTATIONNAME":"D场边高24-罗山大道陈岙村口",
     * "LNG":120.74566,"LAT":27.84258,"BASESATTIONTIME":"2017-08-24 07:42:58"},{"BASESTATIONID":137580,
     * "BASESTATIONNAME":"D南兴街-62号前51","LNG":120.2718,"LAT":27.16,"BASESATTIONTIME":"2017-08-24 07:42:09"}],
     * "SMARTCAREID":"6f606824743d48318c6283e8ef3cc77f"}
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

        private String SMARTCAREID;
        private List<MONITORDATALISTBean> MONITORDATALIST;

        public String getSMARTCAREID() {
            return SMARTCAREID;
        }

        public void setSMARTCAREID(String SMARTCAREID) {
            this.SMARTCAREID = SMARTCAREID;
        }

        public List<MONITORDATALISTBean> getMONITORDATALIST() {
            return MONITORDATALIST;
        }

        public void setMONITORDATALIST(List<MONITORDATALISTBean> MONITORDATALIST) {
            this.MONITORDATALIST = MONITORDATALIST;
        }

        public static class MONITORDATALISTBean {
            /**
             * BASESTATIONID : 221178
             * BASESTATIONNAME : 浙江省温州市瓯海区兴吕北路
             * LNG : 120.604764
             * LAT : 27.975517
             * BASESATTIONTIME : 2017-08-24 07:53:44
             */

            private int BASESTATIONID;
            private String BASESTATIONNAME;
            private double LNG;
            private double LAT;
            private String BASESATTIONTIME;

            public int getBASESTATIONID() {
                return BASESTATIONID;
            }

            public void setBASESTATIONID(int BASESTATIONID) {
                this.BASESTATIONID = BASESTATIONID;
            }

            public String getBASESTATIONNAME() {
                return BASESTATIONNAME;
            }

            public void setBASESTATIONNAME(String BASESTATIONNAME) {
                this.BASESTATIONNAME = BASESTATIONNAME;
            }

            public double getLNG() {
                return LNG;
            }

            public void setLNG(double LNG) {
                this.LNG = LNG;
            }

            public double getLAT() {
                return LAT;
            }

            public void setLAT(double LAT) {
                this.LAT = LAT;
            }

            public String getBASESATTIONTIME() {
                return BASESATTIONTIME;
            }

            public void setBASESATTIONTIME(String BASESATTIONTIME) {
                this.BASESATTIONTIME = BASESATTIONTIME;
            }
        }
    }
}
