package com.tdr.familytreasure.entiy;

/**
 * Description：TODO
 * Create Time：2016/12/6 11:09
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SendCodeSms {

    /**
     * ResultCode : 0
     * ResultText : 验证码发送成功
     * DataTypeCode : SendCodeSms
     * TaskID : 1
     * Content : {"VerificationCodeID":"2079a5d4-bce1-4c67-8a50-a4a94f591387","VerificationCode":"5027","CodeType":1,"Phone":"18868269007","IsValid":1,"CreateTime":"2016-12-06 11:08:32"}
     */

    private String ResultCode;
    private String ResultText;
    private String DataTypeCode;
    private String TaskID;
    /**
     * VerificationCodeID : 2079a5d4-bce1-4c67-8a50-a4a94f591387
     * VerificationCode : 5027
     * CodeType : 1
     * Phone : 18868269007
     * IsValid : 1
     * CreateTime : 2016-12-06 11:08:32
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
        private String VerificationCodeID;
        private String VerificationCode;
        private int CodeType;
        private String Phone;
        private int IsValid;
        private String CreateTime;

        public String getVerificationCodeID() {
            return VerificationCodeID;
        }

        public void setVerificationCodeID(String VerificationCodeID) {
            this.VerificationCodeID = VerificationCodeID;
        }

        public String getVerificationCode() {
            return VerificationCode;
        }

        public void setVerificationCode(String VerificationCode) {
            this.VerificationCode = VerificationCode;
        }

        public int getCodeType() {
            return CodeType;
        }

        public void setCodeType(int CodeType) {
            this.CodeType = CodeType;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public int getIsValid() {
            return IsValid;
        }

        public void setIsValid(int IsValid) {
            this.IsValid = IsValid;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
