package org.vironit.basumatarau.imWebApplication.model;

public abstract class MsgResource {
    private Long id;
    private String name;
    //todo serialization ID to be considered

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
