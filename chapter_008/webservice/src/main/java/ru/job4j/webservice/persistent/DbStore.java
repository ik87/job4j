package ru.job4j.webservice.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.webservice.models.Role;
import ru.job4j.webservice.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbStore implements Store {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private final static DbStore INSTANCE = new DbStore();

    @Override
    public boolean ifExist(User user) {
        String sql = "SELECT user_id, u.role_id, role, login, email, password, created " +
                "FROM users u JOIN roles r ON r.role_id = u.role_id WHERE user_id = ?";
        return !findBy(sql, x -> x.setInt(1, user.getId())).isEmpty();
    }

    private DbStore() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://127.0.0.1:5432/user_jsp");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("password");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
    }

    public static DbStore getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO Users(role_id, login, email, password, created) VALUES (?, ?, ?, ?, NOW())";
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, user.getRole().getRole());
            pstm.setString(2, user.getLogin());
            pstm.setString(3, user.getEmail());
            pstm.setString(3, user.getPassword());
            // pstm.setTimestamp(5, new Timestamp(user.getCreated()));
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET role_id = ?, login = ?, email = ?, password = ? WHERE user_id = ?";
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, user.getRole().getRole());
            pstm.setString(2, user.getLogin());
            pstm.setString(3, user.getEmail());
            pstm.setString(3, user.getPassword());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT user_id, u.role_id, role, login, email, password, created " +
                "FROM users u JOIN roles r ON r.role_id = u.role_id";
        return findBy(sql, null);
    }

    @Override
    public User findById(User user) {
        String sql = "SELECT user_id, u.role_id, role, login, email, password, created " +
                "FROM users u JOIN roles r ON r.role_id = u.role_id WHERE user_id = ?";
        List<User> result = findBy(sql, x -> x.setInt(1, user.getId()));
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public User findByLogin(User user) {
        String sql = "SELECT user_id, u.role_id, role, login, email, password, created " +
                "FROM users u JOIN roles r ON r.role_id = u.role_id WHERE login = ?";
        List<User> result = findBy(sql, x -> x.setInt(1, user.getId()));
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * General-purpose query method
     *
     * @param sql any sql query
     * @param ps  functionality interface the same Consumer but can throws SQLException or NULL
     * @return array items
     */
    private List<User> findBy(String sql, ConsumerX<PreparedStatement> ps) {
        List<User> users = new ArrayList<>();
        try (Connection connection = SOURCE.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (ps != null) {
                ps.accept(pstmt);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                Role role = new Role();
                user.setId(rs.getInt("user_id"));
                user.setLogin(rs.getString("login"));
                user.setEmail(rs.getString("login"));
                user.setPassword(rs.getString("email"));
                user.setCreated((rs.getTimestamp("created").getTime()));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * The same Consumer but can throws SQLException
     *
     * @param <T> for example  PreparedStatement
     */
    interface ConsumerX<T> {
        void accept(T obj) throws SQLException;
    }

    @Override
    public List<Role> getRoles() {
        return null;
    }
}
