package com.rede.social.application;

import com.rede.social.repository.IPostRepository;
import com.rede.social.repository.IProfileRepository;
import com.rede.social.repository.impl.PostRepositoryImplFile;
import com.rede.social.repository.impl.ProfileRepositoryImplFile;

public class Main {
    public static void main(String[] args) {

        IProfileRepository profileRepository = new ProfileRepositoryImplFile();
        IPostRepository postRepository = new PostRepositoryImplFile(profileRepository);

        SocialNetwork socialNetwork = new SocialNetwork(postRepository, profileRepository);
        App app = new App(socialNetwork);
        app.run();
    }
}
