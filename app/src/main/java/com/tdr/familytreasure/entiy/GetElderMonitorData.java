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
     * Content : {"MONITORDATALIST":[{"BASESTATIONID":"221178","BASESTATIONNAME":"浙江省温州市瓯海区兴吕北路","LNG":"120.604764",
     * "LAT":"27.975517","BASESATTIONTIME":"2017/8/24 7:53:44"},{"BASESTATIONID":"136701",
     * "BASESTATIONNAME":"D场边高21-西岙村门口","LNG":"120.735510","LAT":"27.848190","BASESATTIONTIME":"2017/8/24 7:53:23"},
     * {"BASESTATIONID":"221329","BASESTATIONNAME":"浙江省温州市瓯海区辽前二路45号","LNG":"120.679557","LAT":"27.986866",
     * "BASESATTIONTIME":"2017/8/24 7:53:06"},{"BASESTATIONID":"138794","BASESTATIONNAME":"稠泛村红绿灯路口",
     * "LNG":"120.093310","LAT":"27.696684","BASESATTIONTIME":"2017/8/24 7:53:03"},{"BASESTATIONID":"221843",
     * "BASESTATIONNAME":"浙江省温州市苍南县玉苍路","LNG":"120.434867","LAT":"27.516375","BASESATTIONTIME":"2017/8/24 7:52:46"},
     * {"BASESTATIONID":"220499","BASESTATIONNAME":"浙江省温州市鹿城区化工路","LNG":"120.588810","LAT":"28.034616",
     * "BASESATTIONTIME":"2017/8/24 7:52:27"},{"BASESTATIONID":"137732","BASESTATIONNAME":"占家埠-水头交警大队",
     * "LNG":"120.339950","LAT":"27.645660","BASESATTIONTIME":"2017/8/24 7:52:20"},{"BASESTATIONID":"134951",
     * "BASESTATIONNAME":"D江湾路\u2014桥下路交叉口","LNG":"120.596190","LAT":"28.027260","BASESATTIONTIME":"2017/8/24
     * 7:52:09"},{"BASESTATIONID":"142835","BASESTATIONNAME":"隔岸路131号","LNG":"120.652957","LAT":"28.003464",
     * "BASESATTIONTIME":"2017/8/24 7:51:52"},{"BASESTATIONID":"136008","BASESTATIONNAME":"D玉高13-青松路49号",
     * "LNG":"120.635330","LAT":"27.786550","BASESATTIONTIME":"2017/8/24 7:51:30"},{"BASESTATIONID":"141999",
     * "BASESTATIONNAME":"YH3荆州桥头-惠民亭边","LNG":"120.599640","LAT":"28.292880","BASESATTIONTIME":"2017/8/24 7:51:23"},
     * {"BASESTATIONID":"137248","BASESTATIONNAME":"D莘高53-市场南路92号","LNG":"120.675190","LAT":"27.778690",
     * "BASESATTIONTIME":"2017/8/24 7:50:16"},{"BASESTATIONID":"221754","BASESTATIONNAME":"浙江省温州市苍南县陇西路",
     * "LNG":"120.550785","LAT":"27.567648","BASESATTIONTIME":"2017/8/24 7:49:36"},{"BASESTATIONID":"142910",
     * "BASESTATIONNAME":"上陡门住宅区三组团15电杆","LNG":"120.694646","LAT":"28.006579","BASESATTIONTIME":"2017/8/24 7:49:33"},
     * {"BASESTATIONID":"136568","BASESTATIONNAME":"D莘高35-东兴工业区车舟汽配边","LNG":"120.686750","LAT":"27.778970",
     * "BASESATTIONTIME":"2017/8/24 7:49:08"},{"BASESTATIONID":"177074","BASESTATIONNAME":"鳌江059-D务洋村-办事处附近",
     * "LNG":"27.631350","LAT":"120.561210","BASESATTIONTIME":"2017/8/24 7:48:47"},{"BASESTATIONID":"222219",
     * "BASESTATIONNAME":"浙江省温州市龙湾区东河路12号","LNG":"120.817510","LAT":"27.967845","BASESATTIONTIME":"2017/8/24
     * 7:48:44"},{"BASESTATIONID":"177189","BASESTATIONNAME":"D塘高09-新溪成溪路104国道","LNG":"120.670260","LAT":"27.834950",
     * "BASESATTIONTIME":"2017/8/24 7:48:33"},{"BASESTATIONID":"134948","BASESTATIONNAME":"黎明中路278弄南出口",
     * "LNG":"120.676360","LAT":"28.014470","BASESATTIONTIME":"2017/8/24 7:48:19"},{"BASESTATIONID":"221954",
     * "BASESTATIONNAME":"浙江省温州市永嘉县广安路","LNG":"120.791881","LAT":"28.033468","BASESATTIONTIME":"2017/8/24 7:48:18"}],
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
        /**
         * MONITORDATALIST : [{"BASESTATIONID":"221178","BASESTATIONNAME":"浙江省温州市瓯海区兴吕北路","LNG":"120.604764",
         * "LAT":"27.975517","BASESATTIONTIME":"2017/8/24 7:53:44"},{"BASESTATIONID":"136701",
         * "BASESTATIONNAME":"D场边高21-西岙村门口","LNG":"120.735510","LAT":"27.848190","BASESATTIONTIME":"2017/8/24
         * 7:53:23"},{"BASESTATIONID":"221329","BASESTATIONNAME":"浙江省温州市瓯海区辽前二路45号","LNG":"120.679557",
         * "LAT":"27.986866","BASESATTIONTIME":"2017/8/24 7:53:06"},{"BASESTATIONID":"138794",
         * "BASESTATIONNAME":"稠泛村红绿灯路口","LNG":"120.093310","LAT":"27.696684","BASESATTIONTIME":"2017/8/24 7:53:03"},
         * {"BASESTATIONID":"221843","BASESTATIONNAME":"浙江省温州市苍南县玉苍路","LNG":"120.434867","LAT":"27.516375",
         * "BASESATTIONTIME":"2017/8/24 7:52:46"},{"BASESTATIONID":"220499","BASESTATIONNAME":"浙江省温州市鹿城区化工路",
         * "LNG":"120.588810","LAT":"28.034616","BASESATTIONTIME":"2017/8/24 7:52:27"},{"BASESTATIONID":"137732",
         * "BASESTATIONNAME":"占家埠-水头交警大队","LNG":"120.339950","LAT":"27.645660","BASESATTIONTIME":"2017/8/24
         * 7:52:20"},{"BASESTATIONID":"134951","BASESTATIONNAME":"D江湾路\u2014桥下路交叉口","LNG":"120.596190",
         * "LAT":"28.027260","BASESATTIONTIME":"2017/8/24 7:52:09"},{"BASESTATIONID":"142835",
         * "BASESTATIONNAME":"隔岸路131号","LNG":"120.652957","LAT":"28.003464","BASESATTIONTIME":"2017/8/24 7:51:52"},
         * {"BASESTATIONID":"136008","BASESTATIONNAME":"D玉高13-青松路49号","LNG":"120.635330","LAT":"27.786550",
         * "BASESATTIONTIME":"2017/8/24 7:51:30"},{"BASESTATIONID":"141999","BASESTATIONNAME":"YH3荆州桥头-惠民亭边",
         * "LNG":"120.599640","LAT":"28.292880","BASESATTIONTIME":"2017/8/24 7:51:23"},{"BASESTATIONID":"137248",
         * "BASESTATIONNAME":"D莘高53-市场南路92号","LNG":"120.675190","LAT":"27.778690","BASESATTIONTIME":"2017/8/24
         * 7:50:16"},{"BASESTATIONID":"221754","BASESTATIONNAME":"浙江省温州市苍南县陇西路","LNG":"120.550785","LAT":"27.567648",
         * "BASESATTIONTIME":"2017/8/24 7:49:36"},{"BASESTATIONID":"142910","BASESTATIONNAME":"上陡门住宅区三组团15电杆",
         * "LNG":"120.694646","LAT":"28.006579","BASESATTIONTIME":"2017/8/24 7:49:33"},{"BASESTATIONID":"136568",
         * "BASESTATIONNAME":"D莘高35-东兴工业区车舟汽配边","LNG":"120.686750","LAT":"27.778970","BASESATTIONTIME":"2017/8/24
         * 7:49:08"},{"BASESTATIONID":"177074","BASESTATIONNAME":"鳌江059-D务洋村-办事处附近","LNG":"27.631350",
         * "LAT":"120.561210","BASESATTIONTIME":"2017/8/24 7:48:47"},{"BASESTATIONID":"222219",
         * "BASESTATIONNAME":"浙江省温州市龙湾区东河路12号","LNG":"120.817510","LAT":"27.967845","BASESATTIONTIME":"2017/8/24
         * 7:48:44"},{"BASESTATIONID":"177189","BASESTATIONNAME":"D塘高09-新溪成溪路104国道","LNG":"120.670260",
         * "LAT":"27.834950","BASESATTIONTIME":"2017/8/24 7:48:33"},{"BASESTATIONID":"134948",
         * "BASESTATIONNAME":"黎明中路278弄南出口","LNG":"120.676360","LAT":"28.014470","BASESATTIONTIME":"2017/8/24
         * 7:48:19"},{"BASESTATIONID":"221954","BASESTATIONNAME":"浙江省温州市永嘉县广安路","LNG":"120.791881","LAT":"28.033468",
         * "BASESATTIONTIME":"2017/8/24 7:48:18"}]
         * SMARTCAREID : 6f606824743d48318c6283e8ef3cc77f
         */

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
             * BASESATTIONTIME : 2017/8/24 7:53:44
             */

            private String BASESTATIONID;
            private String BASESTATIONNAME;
            private String LNG;
            private String LAT;
            private String BASESATTIONTIME;

            public String getBASESTATIONID() {
                return BASESTATIONID;
            }

            public void setBASESTATIONID(String BASESTATIONID) {
                this.BASESTATIONID = BASESTATIONID;
            }

            public String getBASESTATIONNAME() {
                return BASESTATIONNAME;
            }

            public void setBASESTATIONNAME(String BASESTATIONNAME) {
                this.BASESTATIONNAME = BASESTATIONNAME;
            }

            public String getLNG() {
                return LNG;
            }

            public void setLNG(String LNG) {
                this.LNG = LNG;
            }

            public String getLAT() {
                return LAT;
            }

            public void setLAT(String LAT) {
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
