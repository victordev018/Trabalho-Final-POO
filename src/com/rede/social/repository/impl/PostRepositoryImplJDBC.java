package com.rede.social.repository.impl;

import com.rede.social.database.DBConnection;
import com.rede.social.exception.database.DBException;
import com.rede.social.exception.global.NotFoundError;
import com.rede.social.model.AdvancedPost;
import com.rede.social.model.Post;
import com.rede.social.model.Profile;
import com.rede.social.repository.IPostRepository;
import com.rede.social.repository.IProfileRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepositoryImplJDBC implements IPostRepository {

    private Connection conn;
    private IProfileRepository profileRepository;

    public PostRepositoryImplJDBC(IProfileRepository profileRepository, Connection conn) {
        this.profileRepository = profileRepository;
        this.conn = conn;
    }

    @Override
    public void addPost(Post post) throws DBException {

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
              "INSERT INTO POST " +
                   "(?, ?, ?, ?, ?)"
            );

            ps.setInt(1, post.getId());
            ps.setString(2, post.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreatedAt()));
            ps.setString(4, post.getType());
            ps.setInt(5, post.getOwner().getId());

            int rowsAffected = ps.executeUpdate();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DBConnection.closeStatement(ps);
        }
    }

    @Override
    public Optional<Post> findPostById(Integer id) throws NotFoundError, DBException {

        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM POST" +
                         "WHERE ID = ?"
            );

            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(instantiatePost(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DBConnection.closeStatement(ps);
            DBConnection.closeResultSet(rs);
        }
    }

    @Override
    public List<Post> listPosts() throws DBException {
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM POST"
            );
            rs = ps.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (rs.next()) {
                posts.add(instantiatePost(rs));
            }
            return posts;

        } catch (SQLException | NotFoundError e) {
            throw new DBException(e.getMessage());
        } finally {
            DBConnection.closeStatement(ps);
            DBConnection.closeResultSet(rs);
        }
    }

    @Override
    public List<Post> listPostsByProfile(String usernameOwner) throws NotFoundError, DBException {
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            int idOwner = this.profileRepository.findProfileByUsername(usernameOwner).get().getId();
            ps = conn.prepareStatement(
                    "SELECT * FROM POST " +
                         "WHERE IDOWNER = ?"
            );
            ps.setInt(1, idOwner);

            rs = ps.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (rs.next()) {
                posts.add(instantiatePost(rs));
            }
            return posts;

        } catch (SQLException | NotFoundError e) {
            throw new DBException(e.getMessage());
        } finally {
            DBConnection.closeStatement(ps);
            DBConnection.closeResultSet(rs);
        }
    }

    private Post instantiatePost(ResultSet rs) throws SQLException, DBException, NotFoundError {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Post p = rs.getString("type").equals("PN") ? new Post() : new AdvancedPost();
        int ownerId = rs.getInt("ownerid");
        Profile owner = this.profileRepository.findProfileById(ownerId).get();
        p.setId(rs.getInt("id"));
        p.setContent(rs.getString("content"));
        p.setType(rs.getString("type"));
        p.setCreatedAt(LocalDateTime.parse(rs.getString("createdat")));
        p.setOwner(owner);
        return p;
    }
}
