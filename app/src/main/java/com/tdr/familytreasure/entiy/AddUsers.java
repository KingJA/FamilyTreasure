package com.tdr.familytreasure.entiy;

/**
 * Description：TODO
 * Create Time：2017/1/17 17:16
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddUsers {

    /**
     * ResultCode : 0
     * ResultText : 用户注册成功
     * DataTypeCode : null
     * TaskID : null
     * Content : {"OldPassword":null,"VerificationCodeID":"45df79d6-a4a2-4207-aed4-54bc8772066c","VerificationCode":"7590","UserID":"1d651280-e455-4170-a1e8-b2ae54c1512b","Phone":"18888888888","UserName":"","UserPassword":"","FaceID":null,"FaceBase":"","CreateTime":"2017-01-17 17:18:44","LastLoginTime":"0001-01-01 00:00:00","CityCode":null,"City":{"CityCode":null,"CityName":null,"ShortName":null,"FirstWord":null,"FullWord":null,"ParentCode":null,"CityType":0,"Sort":0,"IsValid":0},"Level":0,"Score":0,"Certification":0,"Certificationmode":0,"IsValid":1,"LoginIP":null,"IMEI":null,"ChannelID":null,"ChannelType":0,"token":"bb8866c04b3c4ed4ba45d5619391838b18888888888","InviteCode":0,"SpreadCode":0,"Source":0,"WechatId":null}
     */

    private String ResultCode;
    private String ResultText;
    private Object DataTypeCode;
    private Object TaskID;
    /**
     * OldPassword : null
     * VerificationCodeID : 45df79d6-a4a2-4207-aed4-54bc8772066c
     * VerificationCode : 7590
     * UserID : 1d651280-e455-4170-a1e8-b2ae54c1512b
     * Phone : 18888888888
     * UserName :
     * UserPassword :
     * FaceID : null
     * FaceBase :
     * CreateTime : 2017-01-17 17:18:44
     * LastLoginTime : 0001-01-01 00:00:00
     * CityCode : null
     * City : {"CityCode":null,"CityName":null,"ShortName":null,"FirstWord":null,"FullWord":null,"ParentCode":null,"CityType":0,"Sort":0,"IsValid":0}
     * Level : 0
     * Score : 0
     * Certification : 0
     * Certificationmode : 0
     * IsValid : 1
     * LoginIP : null
     * IMEI : null
     * ChannelID : null
     * ChannelType : 0
     * token : bb8866c04b3c4ed4ba45d5619391838b18888888888
     * InviteCode : 0
     * SpreadCode : 0
     * Source : 0
     * WechatId : null
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
        private Object OldPassword;
        private String VerificationCodeID;
        private String VerificationCode;
        private String UserID;
        private String Phone;
        private String UserName;
        private String UserPassword;
        private Object FaceID;
        private String FaceBase;
        private String CreateTime;
        private String LastLoginTime;
        private Object CityCode;
        /**
         * CityCode : null
         * CityName : null
         * ShortName : null
         * FirstWord : null
         * FullWord : null
         * ParentCode : null
         * CityType : 0
         * Sort : 0
         * IsValid : 0
         */

        private CityBean City;
        private int Level;
        private int Score;
        private int Certification;
        private int Certificationmode;
        private int IsValid;
        private Object LoginIP;
        private Object IMEI;
        private Object ChannelID;
        private int ChannelType;
        private String token;
        private int InviteCode;
        private int SpreadCode;
        private int Source;
        private Object WechatId;

        public Object getOldPassword() {
            return OldPassword;
        }

        public void setOldPassword(Object OldPassword) {
            this.OldPassword = OldPassword;
        }

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

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getUserPassword() {
            return UserPassword;
        }

        public void setUserPassword(String UserPassword) {
            this.UserPassword = UserPassword;
        }

        public Object getFaceID() {
            return FaceID;
        }

        public void setFaceID(Object FaceID) {
            this.FaceID = FaceID;
        }

        public String getFaceBase() {
            return FaceBase;
        }

        public void setFaceBase(String FaceBase) {
            this.FaceBase = FaceBase;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getLastLoginTime() {
            return LastLoginTime;
        }

        public void setLastLoginTime(String LastLoginTime) {
            this.LastLoginTime = LastLoginTime;
        }

        public Object getCityCode() {
            return CityCode;
        }

        public void setCityCode(Object CityCode) {
            this.CityCode = CityCode;
        }

        public CityBean getCity() {
            return City;
        }

        public void setCity(CityBean City) {
            this.City = City;
        }

        public int getLevel() {
            return Level;
        }

        public void setLevel(int Level) {
            this.Level = Level;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int Score) {
            this.Score = Score;
        }

        public int getCertification() {
            return Certification;
        }

        public void setCertification(int Certification) {
            this.Certification = Certification;
        }

        public int getCertificationmode() {
            return Certificationmode;
        }

        public void setCertificationmode(int Certificationmode) {
            this.Certificationmode = Certificationmode;
        }

        public int getIsValid() {
            return IsValid;
        }

        public void setIsValid(int IsValid) {
            this.IsValid = IsValid;
        }

        public Object getLoginIP() {
            return LoginIP;
        }

        public void setLoginIP(Object LoginIP) {
            this.LoginIP = LoginIP;
        }

        public Object getIMEI() {
            return IMEI;
        }

        public void setIMEI(Object IMEI) {
            this.IMEI = IMEI;
        }

        public Object getChannelID() {
            return ChannelID;
        }

        public void setChannelID(Object ChannelID) {
            this.ChannelID = ChannelID;
        }

        public int getChannelType() {
            return ChannelType;
        }

        public void setChannelType(int ChannelType) {
            this.ChannelType = ChannelType;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getInviteCode() {
            return InviteCode;
        }

        public void setInviteCode(int InviteCode) {
            this.InviteCode = InviteCode;
        }

        public int getSpreadCode() {
            return SpreadCode;
        }

        public void setSpreadCode(int SpreadCode) {
            this.SpreadCode = SpreadCode;
        }

        public int getSource() {
            return Source;
        }

        public void setSource(int Source) {
            this.Source = Source;
        }

        public Object getWechatId() {
            return WechatId;
        }

        public void setWechatId(Object WechatId) {
            this.WechatId = WechatId;
        }

        public static class CityBean {
            private Object CityCode;
            private Object CityName;
            private Object ShortName;
            private Object FirstWord;
            private Object FullWord;
            private Object ParentCode;
            private int CityType;
            private int Sort;
            private int IsValid;

            public Object getCityCode() {
                return CityCode;
            }

            public void setCityCode(Object CityCode) {
                this.CityCode = CityCode;
            }

            public Object getCityName() {
                return CityName;
            }

            public void setCityName(Object CityName) {
                this.CityName = CityName;
            }

            public Object getShortName() {
                return ShortName;
            }

            public void setShortName(Object ShortName) {
                this.ShortName = ShortName;
            }

            public Object getFirstWord() {
                return FirstWord;
            }

            public void setFirstWord(Object FirstWord) {
                this.FirstWord = FirstWord;
            }

            public Object getFullWord() {
                return FullWord;
            }

            public void setFullWord(Object FullWord) {
                this.FullWord = FullWord;
            }

            public Object getParentCode() {
                return ParentCode;
            }

            public void setParentCode(Object ParentCode) {
                this.ParentCode = ParentCode;
            }

            public int getCityType() {
                return CityType;
            }

            public void setCityType(int CityType) {
                this.CityType = CityType;
            }

            public int getSort() {
                return Sort;
            }

            public void setSort(int Sort) {
                this.Sort = Sort;
            }

            public int getIsValid() {
                return IsValid;
            }

            public void setIsValid(int IsValid) {
                this.IsValid = IsValid;
            }
        }
    }
}
