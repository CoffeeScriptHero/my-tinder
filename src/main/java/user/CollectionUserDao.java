package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CollectionUserDao implements UserDao {
    private static CollectionUserDao instance = null;
    private final String SELECT_BY_ID = "select * from users where id=?";
    private final String SELECT_BY_EMAIL = "select * from users where email=?";
    private final String SELECT_USER_BY_COOKIE = "select * from users where cookie_id=?";
    private final String SELECT_DIALOGUE = "select * from messages\n" +
            "where user_id_from=?\n" +
            "and user_id_to=?\n" +
            "union\n" +
            "select * from messages\n" +
            "where user_id_from=?\n" +
            "and user_id_to=?\n" +
            "order by date_time";
    private final String INSERT_MESSAGE = "insert into messages (user_id_from, user_id_to, message)" +
            "values (?, ?, ?)";
    private final String INSERT_USER = "insert into users (img, email, password, name, cookie_id)" +
            " values (?, ?, ?, ?, ?)";
    private final Connection conn;
    private final String[] images;
    private final List<User> likedProfiles;
    private User mainUser;

    public CollectionUserDao(Connection conn) {
        this.conn = conn;
        this.likedProfiles = new ArrayList<>();
        this.mainUser = new User();
        this.images = new String[]{
                "https://api.time.com/wp-content/uploads/2016/07/vice-president-joe-biden-national-ice-cream-day_01-web1.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Smiley.svg/1200px-Smiley.svg.png",
                "https://www.purina.com.my/sites/default/files/styles/ttt_image_510/public/2021-02/BREED%20Hero%20Mobile_0004_welsh_corgi_pembroke.jpg?itok=OUx5d899",
                "https://upload.wikimedia.org/wikipedia/commons/d/d8/Oxford_blue.png"
        };
    }

    public static CollectionUserDao getInstance(Connection conn) {
        if (instance == null) {
            instance = new CollectionUserDao(conn);
        }
        return instance;
    }

    private String getRandomImg() {
        return this.images[new Random().nextInt(this.images.length)];
    }

    public void setMainUser(String cookieId) {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_COOKIE)) {
            stmt.setString(1, cookieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    this.mainUser = new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("img"),
                            rs.getString("cookie_id")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void clearLikedProfiles() {
        likedProfiles.clear();
    }

    public void saveLikedProfile(User user) {
        likedProfiles.add(user);
    }

    public List<User> getLikedProfiles() {
        return likedProfiles;
    }

    public User getMainUser() {
        return mainUser;
    }

    public boolean checkUserExists(int id) {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean checkUserExists(String email) {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_EMAIL)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<User> getById(int id) {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(
                        new User(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("img")
                        )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_EMAIL)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(
                        new User(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("img"),
                                rs.getString("cookie_id"),
                                rs.getString("password")
                        )
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Message> getDialogue(int senderId, int receiverId) {
        List<Message> messages = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_DIALOGUE)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setInt(3, receiverId);
            stmt.setInt(4, senderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    messages.add(
                            new Message(
                                    rs.getInt("user_id_to"),
                                    rs.getInt("user_id_from"),
                                    rs.getString("message"),
                                    rs.getTimestamp("date_time").toLocalDateTime()
                            )
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return messages;
    }

    @Override
    public void addMessage(int senderId, int receiverId, String message) {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_MESSAGE)) {
            stmt.setInt(1, senderId);
            stmt.setInt(2, receiverId);
            stmt.setString(3, message);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void add(String email, String name, String password, String cookieId) {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_USER)) {
            stmt.setString(1, getRandomImg());
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, name);
            stmt.setString(5, cookieId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
