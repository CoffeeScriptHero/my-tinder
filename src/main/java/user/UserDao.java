package user;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> getById(int id);
    Optional<User> getByEmail(String email);
    List<Message> getDialogue(int senderId, int receiverId);
    void addMessage(int senderId, int receiverId, String message);
    void add(String email, String name, String password, String cookieId) throws SQLException;
}
