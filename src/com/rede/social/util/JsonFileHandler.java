package com.rede.social.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rede.social.model.Post;
import com.rede.social.model.Profile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveProfilesToFile(List<Profile> profiles, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), profiles);
    }

    public static List<Profile> loadProfilesFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Profile.class));
        }
        return null;
    }

    public static void savePostsToFile(List<Post> posts, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), posts);
    }

    public static List<Post> loadPostsFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Post.class));
        }
        return null;
    }
}
