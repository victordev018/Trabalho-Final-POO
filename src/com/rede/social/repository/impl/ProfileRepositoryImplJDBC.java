package com.rede.social.repository.impl;

import com.rede.social.database.DBConnection;
import com.rede.social.exception.database.DBException;
import com.rede.social.exception.global.AlreadyExistsError;
import com.rede.social.exception.global.NotFoundError;
import com.rede.social.model.AdvancedProfile;
import com.rede.social.model.Profile;
import com.rede.social.repository.IProfileRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfileRepositoryImplJDBC implements IProfileRepository {

    private Connection conn;

    public ProfileRepositoryImplJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addProfile(Profile profile) throws AlreadyExistsError, DBException {

        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(
                    "insert into profile "
                            +"values (?, ?, ?, ?, ?, ?)"
            );

            ps.setInt(1, profile.getId());
            ps.setString(2, profile.getUsername());
            ps.setString(3, profile.getPhoto());
            ps.setString(4, profile.getEmail());
            ps.setBoolean(5, profile.getStatus());
            ps.setString(6, profile.getType());

            int rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e){
            throw new DBException(e.getMessage());
        }
        finally {
            DBConnection.closeStatement(ps);
        }
    }

    @Override
    public Optional<Profile> findProfileByEmail(String email) throws NotFoundError, DBException {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM PROFILE "+
                         "WHERE EMAIL=?"
            );

            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(instantiateProfile(rs));
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
    public Optional<Profile> findProfileByUsername(String username) throws NotFoundError, DBException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM PROFILE "+
                            "WHERE USERNAME=?"
            );

            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(instantiateProfile(rs));
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
    public Optional<Profile> findProfileById(Integer id) throws NotFoundError, DBException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM PROFILE "+
                            "WHERE id=?"
            );

            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(instantiateProfile(rs));
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
    public List<Profile> getAllProfiles() throws DBException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT * FROM PROFILE"
            );

            rs = ps.executeQuery();
            List<Profile> profiles = new ArrayList<>();
            while (rs.next()) {
                profiles.add(instantiateProfile(rs));
            }
            return profiles;

        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        } finally {
            DBConnection.closeStatement(ps);
            DBConnection.closeResultSet(rs);
        }
    }

    private Profile instantiateProfile(ResultSet rs) throws SQLException {
        Profile p = rs.getString("type").equals("PA") ? new AdvancedProfile() : new Profile();
        p.setId(rs.getInt("id"));
        p.setUsername(rs.getString("username"));
        p.setEmail(rs.getString("email"));
        p.setPhoto(rs.getString("photo"));
        p.setStatus(rs.getBoolean("status"));
        p.setType(rs.getString("type"));
        return p;
    }
}
