package com.tdr.familytreasure.entiy;

import java.io.Serializable;

/**
 * Description:TODO
 * Create Time:2017/8/3 9:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BindInfo implements Serializable{
    private String BINDXQCODE;
    private String BINDUNITNAME;
    private String DEVTYPE;
    private String DEVICEID;

    public String getBINDXQCODE() {
        return BINDXQCODE;
    }

    public void setBINDXQCODE(String BINDXQCODE) {
        this.BINDXQCODE = BINDXQCODE;
    }

    public String getBINDUNITNAME() {
        return BINDUNITNAME;
    }

    public void setBINDUNITNAME(String BINDUNITNAME) {
        this.BINDUNITNAME = BINDUNITNAME;
    }

    public String getDEVTYPE() {
        return DEVTYPE;
    }

    public void setDEVTYPE(String DEVTYPE) {
        this.DEVTYPE = DEVTYPE;
    }

    public String getDEVICEID() {
        return DEVICEID;
    }

    public void setDEVICEID(String DEVICEID) {
        this.DEVICEID = DEVICEID;
    }
}
