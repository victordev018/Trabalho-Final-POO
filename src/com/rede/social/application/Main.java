package com.rede.social.application;

import com.rede.social.database.DBConnection;
import com.rede.social.exception.database.DBException;
import com.rede.social.repository.IPostRepository;
import com.rede.social.repository.IProfileRepository;
import com.rede.social.repository.impl.PostRepositoryImplFile;
import com.rede.social.repository.impl.ProfileRepositoryImplFile;
import com.rede.social.repository.impl.ProfileRepositoryImplJDBC;

public class Main {
    public static void main(String[] args) throws DBException {

//        DBConnection dbConnection = new DBConnection();
//        IProfileRepository profileRepositoryJDBC = new ProfileRepositoryImplJDBC(dbConnection.getConnection());

        IProfileRepository profileRepository = new ProfileRepositoryImplFile();
        IPostRepository postRepository = new PostRepositoryImplFile(profileRepository);

        SocialNetwork socialNetwork = new SocialNetwork(postRepository, profileRepository);
        App app = new App(socialNetwork);
        app.run();
    }
}
