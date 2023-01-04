package user;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final CollectionUserDao dao;

    public UserService(Connection conn) {
        this.dao = CollectionUserDao.getInstance(conn);
    }

    public void setMainUser(String cookieId) {
        dao.setMainUser(cookieId);
    }

    public void clearLikedProfiles() {
        dao.clearLikedProfiles();
    }

    public void saveLikedProfile(User user) {
        dao.saveLikedProfile(user);
    }

    public List<User> getLikedProfiles() {
        return dao.getLikedProfiles();
    }

    public User getMainUser() {
        return dao.getMainUser();
    }

    public boolean checkUserExists(int id) {
        return dao.checkUserExists(id);
    }

    public boolean checkUserExists(String email) {
        return dao.checkUserExists(email);
    }

    public Optional<User> getById(int id) {
        return dao.getById(id);
    }

    public Optional<User> getByEmail(String email) {
        return dao.getByEmail(email);
    }

    public void add(String email, String name, String password, String cookieId) {
        dao.add(email, name, password, cookieId);
    }

    public List<Message> getDialogue(int senderId, int receiverId) { return dao.getDialogue(senderId, receiverId); }

    public void addMessage(int senderId, int receiverId, String message) {
        dao.addMessage(senderId, receiverId, message);
    }
}
