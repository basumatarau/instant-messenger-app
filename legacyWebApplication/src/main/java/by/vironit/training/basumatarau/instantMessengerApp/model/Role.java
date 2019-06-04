package by.vironit.training.basumatarau.instantMessengerApp.model;

import java.util.Objects;

public class Role {
    private Integer id;
    private String name;

    public Role(){}

    private Role(RoleBuilder builder){
        this.name = builder.name;
    }

    public static class RoleBuilder{
        private String name;

        public RoleBuilder(){}

        public RoleBuilder name(String name){
            this.name = name;
            return this;
        }

        private void buildDataIntegrityCheck() throws InstantiationException {
            if(name == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for Role object instantiation");
            }
        }

        public Role build() throws InstantiationException {
            buildDataIntegrityCheck();
            return new Role(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
