package com.rede.social.repository.impl;

import com.rede.social.model.Profile;
import com.rede.social.repository.IProfileRepository;

import java.util.List;
import java.util.Optional;

public class ProfileRepositoryImplFile implements IProfileRepository {
    private final List<Profile> profiles;

    public ProfileRepositoryImplFile(List<Profile> profiles) {
        this.profiles = profiles;
    }

    @Override
    public void addProfile(Profile profile) {
        profiles.add(profile);
    }

    @Override
    public Optional<Profile> findProfileByEmail(String email) {
        for (Profile profile : profiles) {
            if (profile.getEmail().equals(email)) {
                return Optional.of(profile);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Profile> findProfileByUsername(String username) {
        for (Profile profile : profiles) {
            if (profile.getUsername().equals(username)) {
                return Optional.of(profile);
            }
        }
        return Optional.empty();
    }

    public Optional<Profile> findProfileById(Integer id) {
        for (Profile profile : profiles) {
            if (profile.getId() == id) {
                return Optional.of(profile);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profiles;
    }
}
