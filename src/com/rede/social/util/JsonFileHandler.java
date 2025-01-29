package com.rede.social.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rede.social.model.Post;
import com.rede.social.model.Profile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileHandler {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Salva a lista de perfis em um arquivo JSON
     * @param profiles a lista de perfis a ser salva
     * @param fileName o nome do arquivo onde os perfis serão armazenados
     * @throws IOException se houver um erro ao escrever no arquivo
     */
    public static void saveProfilesToFile(List<Profile> profiles, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), profiles);
    }

    /**
     * Carrega a lista de perfis de um arquivo JSON
     * @param fileName o nome do arquivo de onde os perfis serão carregados
     * @return a lista de perfis carregados ou null se o arquivo não existir
     * @throws IOException se houver um erro ao ler o arquivo
     */
    public static List<Profile> loadProfilesFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Profile.class));
        }
        return null;
    }

    /**
     * Salva a lista de posts em um arquivo JSON
     * @param posts a lista de posts a ser salva
     * @param fileName o nome do arquivo onde os posts serão armazenados
     * @throws IOException se houver um erro ao escrever no arquivo
     */
    public static void savePostsToFile(List<Post> posts, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), posts);
    }

    /**
     * Carrega a lista de posts de um arquivo JSON
     * @param fileName o nome do arquivo de onde os posts serão carregados
     * @return a lista de posts carregados ou null se o arquivo não existir
     * @throws IOException se houver um erro ao ler o arquivo
     */
    public static List<Post> loadPostsFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            return objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Post.class));
        }
        return null;
    }
}
