package com.tdr.familytreasure.entiy;

/**
 * Description：TODO
 * Create Time：2017/1/18 9:31
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class IsRegisterPhone {

    /**
     * ResultCode : 0
     * ResultText : 操作成功
     * DataTypeCode : null
     * TaskID : null
     * Content : {"Code":0,"Data":"该手机号可用"}
     */

    private String ResultCode;
    private String ResultText;
    private Object DataTypeCode;
    private Object TaskID;
    /**
     * Code : 0
     * Data : 该手机号可用
     */

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

    public Object getDataTypeCode() {
        return DataTypeCode;
    }

    public void setDataTypeCode(Object DataTypeCode) {
        this.DataTypeCode = DataTypeCode;
    }

    public Object getTaskID() {
        return TaskID;
    }

    public void setTaskID(Object TaskID) {
        this.TaskID = TaskID;
    }

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean Content) {
        this.Content = Content;
    }

    public static class ContentBean {
        private int Code;
        private String Data;

        public int getCode() {
            return Code;
        }

        public void setCode(int Code) {
            this.Code = Code;
        }

        public String getData() {
            return Data;
        }

        public void setData(String Data) {
            this.Data = Data;
        }
    }
}
