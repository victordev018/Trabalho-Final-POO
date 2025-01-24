package com.rede.social.repository;

import com.rede.social.model.Profile;

import java.util.List;
import java.util.Optional;

public interface IProfileRepository {
    void addProfile(Profile profile);
    Optional<Profile> findProfileByEmail(String email);
    Optional<Profile> findProfileByUsername(String username);
    Optional<Profile> findProfileById(Integer id);
    List<Profile> getAllProfiles();
}
