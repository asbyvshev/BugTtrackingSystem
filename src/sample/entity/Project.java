package sample.entity;

import sample.entity.base.AdditionalTable;
import sample.entity.base.BaseEntity;

public class Project extends BaseEntity implements AdditionalTable {
    private String name;

    public Project(int id, String name) {
        setId(id);
        this.name = name;
    }

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
