package com.tdr.familytreasure.entiy;

/**
 * Description：TODO
 * Create Time：2017/1/17 13:05
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Login {

    /**
     * ResultCode : 0
     * ResultText : 用户登录成功
     * DataTypeCode : null
     * TaskID : null
     * Content : {"Realname":"潘志亮","IDCard":"330326198710110734","Sex":"男","Birthday":"1987-10-11 00:00:00","Nationality":null,"Address":"温州","Resideaddress":null,"Phone2":null,"Remark":null,"IDCardPhoto":null,"IDCardPhotoBase64":null,"DetailsCreateTime":"0001-01-01 00:00:00","UserID":"dea3b307-907e-4dcb-859b-c7b603f793b3","Phone":"13736350001","UserName":null,"UserPassword":"","FaceID":null,"FaceBase":"","CreateTime":"2016-09-26 15:35:19","LastLoginTime":"2001-01-01 00:00:00","CityCode":"3303","City":{"CityCode":"3303","CityName":"温州市","ShortName":"WZ","FirstWord":"W","FullWord":"WENZHOU","ParentCode":"33","CityType":2,"Sort":1,"IsValid":1},"Level":0,"Score":0,"Certification":0,"Certificationmode":0,"IsValid":1,"LoginIP":null,"IMEI":null,"ChannelID":"13065ffa4e0026d2b4a","ChannelType":1,"token":"f0e1156dfbcd4a97833cd4836e92cf73","InviteCode":0,"SpreadCode":1386,"Source":0,"WechatId":null}
     */

    private String ResultCode;
    private String ResultText;
    private Object DataTypeCode;
    private Object TaskID;
    /**
     * Realname : 潘志亮
     * IDCard : 330326198710110734
     * Sex : 男
     * Birthday : 1987-10-11 00:00:00
     * Nationality : null
     * Address : 温州
     * Resideaddress : null
     * Phone2 : null
     * Remark : null
     * IDCardPhoto : null
     * IDCardPhotoBase64 : null
     * DetailsCreateTime : 0001-01-01 00:00:00
     * UserID : dea3b307-907e-4dcb-859b-c7b603f793b3
     * Phone : 13736350001
     * UserName : null
     * UserPassword :
     * FaceID : null
     * FaceBase :
     * CreateTime : 2016-09-26 15:35:19
     * LastLoginTime : 2001-01-01 00:00:00
     * CityCode : 3303
     * City : {"CityCode":"3303","CityName":"温州市","ShortName":"WZ","FirstWord":"W","FullWord":"WENZHOU","ParentCode":"33","CityType":2,"Sort":1,"IsValid":1}
     * Level : 0
     * Score : 0
     * Certification : 0
     * Certificationmode : 0
     * IsValid : 1
     * LoginIP : null
     * IMEI : null
     * ChannelID : 13065ffa4e0026d2b4a
     * ChannelType : 1
     * token : f0e1156dfbcd4a97833cd4836e92cf73
     * InviteCode : 0
     * SpreadCode : 1386
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
        private String Realname;
        private String IDCard;
        private String Sex;
        private String Birthday;
        private Object Nationality;
        private String Address;
        private Object Resideaddress;
        private Object Phone2;
        private Object Remark;
        private Object IDCardPhoto;
        private Object IDCardPhotoBase64;
        private String DetailsCreateTime;
        private String UserID;
        private String Phone;
        private String UserName;
        private String UserPassword;
        private String FaceID;
        private String FaceBase;
        private String CreateTime;
        private String LastLoginTime;
        private String CityCode;
        /**
         * CityCode : 3303
         * CityName : 温州市
         * ShortName : WZ
         * FirstWord : W
         * FullWord : WENZHOU
         * ParentCode : 33
         * CityType : 2
         * Sort : 1
         * IsValid : 1
         */

        private CityBean City;
        private int Level;
        private int Score;
        private int Certification;
        private int Certificationmode;
        private int IsValid;
        private Object LoginIP;
        private Object IMEI;
        private String ChannelID;
        private int ChannelType;
        private String token;
        private int InviteCode;
        private int SpreadCode;
        private int Source;
        private Object WechatId;

        public String getRealname() {
            return Realname;
        }

        public void setRealname(String Realname) {
            this.Realname = Realname;
        }

        public String getIDCard() {
            return IDCard;
        }

        public void setIDCard(String IDCard) {
            this.IDCard = IDCard;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public Object getNationality() {
            return Nationality;
        }

        public void setNationality(Object Nationality) {
            this.Nationality = Nationality;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public Object getResideaddress() {
            return Resideaddress;
        }

        public void setResideaddress(Object Resideaddress) {
            this.Resideaddress = Resideaddress;
        }

        public Object getPhone2() {
            return Phone2;
        }

        public void setPhone2(Object Phone2) {
            this.Phone2 = Phone2;
        }

        public Object getRemark() {
            return Remark;
        }

        public void setRemark(Object Remark) {
            this.Remark = Remark;
        }

        public Object getIDCardPhoto() {
            return IDCardPhoto;
        }

        public void setIDCardPhoto(Object IDCardPhoto) {
            this.IDCardPhoto = IDCardPhoto;
        }

        public Object getIDCardPhotoBase64() {
            return IDCardPhotoBase64;
        }

        public void setIDCardPhotoBase64(Object IDCardPhotoBase64) {
            this.IDCardPhotoBase64 = IDCardPhotoBase64;
        }

        public String getDetailsCreateTime() {
            return DetailsCreateTime;
        }

        public void setDetailsCreateTime(String DetailsCreateTime) {
            this.DetailsCreateTime = DetailsCreateTime;
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

        public String getFaceID() {
            return FaceID;
        }

        public void setFaceID(String FaceID) {
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

        public String getCityCode() {
            return CityCode;
        }

        public void setCityCode(String CityCode) {
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

        public String getChannelID() {
            return ChannelID;
        }

        public void setChannelID(String ChannelID) {
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
            private String CityCode;
            private String CityName;
            private String ShortName;
            private String FirstWord;
            private String FullWord;
            private String ParentCode;
            private int CityType;
            private int Sort;
            private int IsValid;

            public String getCityCode() {
                return CityCode;
            }

            public void setCityCode(String CityCode) {
                this.CityCode = CityCode;
            }

            public String getCityName() {
                return CityName;
            }

            public void setCityName(String CityName) {
                this.CityName = CityName;
            }

            public String getShortName() {
                return ShortName;
            }

            public void setShortName(String ShortName) {
                this.ShortName = ShortName;
            }

            public String getFirstWord() {
                return FirstWord;
            }

            public void setFirstWord(String FirstWord) {
                this.FirstWord = FirstWord;
            }

            public String getFullWord() {
                return FullWord;
            }

            public void setFullWord(String FullWord) {
                this.FullWord = FullWord;
            }

            public String getParentCode() {
                return ParentCode;
            }

            public void setParentCode(String ParentCode) {
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
