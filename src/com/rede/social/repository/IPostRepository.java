package com.rede.social.repository;

import com.rede.social.model.Post;
import com.rede.social.model.Profile;
import com.rede.social.exception.global.NotFoundError;
import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    /**
     * Método que adiciona uma instância de Post no repositório de Posts
     // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    void addPost(Post post);

    /**
     * Método que busca e retorna um post que possui o id fornecido
     * @param id o id do post a ser procurado
     * @return o post procurado, se encontrado
     * @throws NotFoundError no caso do post não ser encontrado
     // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    Optional<Post> findPostById(Integer id) throws NotFoundError;

    /**
     * Esse método retorna todos os posts armazenados e ordenados de modo decrescente
     * @return todos os posts criados em ordem decrescente
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    List<Post> listPosts();

    /**
     * Método que busca e retorna uma lista de posts ordenados de forma decrescente baseado no seu dono (uma instância da classe perfil)
     * @param usernameOwner uma string que representa o username do Perfil a ser buscado
     * @return Uma lista de posts pertencentes ao dono
     * @throws NotFoundError no caso de nao encontrar o perfil do dono dos posts
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    List<Post> listPostsByProfile(String usernameOwner) throws NotFoundError;
}
