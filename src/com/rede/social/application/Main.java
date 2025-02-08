package com.rede.social.application;

import com.rede.social.database.DBConnection;
import com.rede.social.exception.database.DBException;
import com.rede.social.repository.IPostRepository;
import com.rede.social.repository.IProfileRepository;
import com.rede.social.repository.impl.PostRepositoryImplFile;
import com.rede.social.repository.impl.PostRepositoryImplJDBC;
import com.rede.social.repository.impl.ProfileRepositoryImplFile;
import com.rede.social.repository.impl.ProfileRepositoryImplJDBC;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) throws DBException {

        DBConnection dbConnection = new DBConnection();
        Connection conn = dbConnection.getConnection();
        IProfileRepository profileRepositoryJDBC = new ProfileRepositoryImplJDBC(conn);
        IPostRepository postRepositoryJDBC = new PostRepositoryImplJDBC(profileRepositoryJDBC, conn);

        IProfileRepository profileRepository = new ProfileRepositoryImplFile();
        IPostRepository postRepository = new PostRepositoryImplFile(profileRepository);

        SocialNetwork socialNetwork = new SocialNetwork(postRepositoryJDBC, profileRepositoryJDBC);
        App app = new App(socialNetwork);
        app.run();

        dbConnection.closeConnection();
    }
}
