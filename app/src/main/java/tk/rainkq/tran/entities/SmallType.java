package tk.rainkq.tran.entities;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class SmallType extends LitePalSupport implements Serializable {

    private Integer id;

    private Integer smallTypeId;

    private String typeName;

    private Integer bigTypeId;

    public Integer getSmallTypeId() {
        return smallTypeId;
    }

    public void setSmallTypeId(Integer smallTypeId) {
        this.smallTypeId = smallTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getBigTypeId() {
        return bigTypeId;
    }

    public void setBigTypeId(Integer bigTypeId) {
        this.bigTypeId = bigTypeId;
    }

    public SmallType() {
    }

    public SmallType(Integer smallTypeId, String typeName, Integer bigTypeId) {
        this.smallTypeId = smallTypeId;
        this.typeName = typeName;
        this.bigTypeId = bigTypeId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
