package by.vironit.training.basumatarau.instantMessengerApp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "chatroom_privileges", schema = "instant_messenger_db_schema")
public class ChatRoomPrivilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    public ChatRoomPrivilege(){}

    private ChatRoomPrivilege(ChatRoomPrivilegeBuilder builder){
        this.name = builder.name;
    }

    public static class ChatRoomPrivilegeBuilder {
        private String name;

        public ChatRoomPrivilegeBuilder(){}

        public ChatRoomPrivilegeBuilder name(String name){
            this.name = name;
            return this;
        }

        private void buildDataIntegrityCheck() throws InstantiationException {
            if(name == null){
                throw new InstantiationException(
                        "invalid or not sufficient data for ChatRoomPrivilege object instantiation");
            }
        }

        public ChatRoomPrivilege build() throws InstantiationException {
            buildDataIntegrityCheck();
            return new ChatRoomPrivilege(this);
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
        ChatRoomPrivilege that = (ChatRoomPrivilege) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
