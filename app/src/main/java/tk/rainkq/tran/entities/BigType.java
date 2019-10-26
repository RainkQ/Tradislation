package tk.rainkq.tran.entities;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class BigType extends LitePalSupport implements Serializable {

    private Integer id;

    private Integer bigTypeId;

    private String typeName;

    public Integer getBigTypeId() {
        return bigTypeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBigTypeId(Integer bigTypeId) {
        this.bigTypeId = bigTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigType() {
    }

    public BigType(int bigTypeId, String typeName) {
        this.bigTypeId = bigTypeId;
        this.typeName = typeName;
    }
}
