package com.rede.social.repository;

import com.rede.social.model.Post;
import com.rede.social.model.Profile;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    /**
     * Método que adiciona uma instância de Post no repositório de Posts
     */
    void addPost(Post post);

    /**
     * Esse método retorna todos os posts armazenados
     * @return todos os posts criados
     // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
    // TODO: adicionar throws NotFoundErrror e atualizar documentação caso não tenha nenhum post criado
     */
    List<Post> getAllPosts();

    /**
     * Método que busca e retorna um post que possui o id fornecido
     * @param id o id do post a ser procurado
     * @return o post procurado, se encontrado
     // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
    // TODO: adicionar throws NotFoundErrror e atualizar documentação caso o post procurado não exista
     */
    Optional<Post> findPostById(Integer id);

    /**
     * Esse método retorna todos os posts armazenados e ordenados de modo decrescente
     * @return todos os posts criados em ordem decrescente
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
    // TODO: adicionar throws NotFoundErrror e atualizar documentação caso não tenha nenhum post criado
     */
    List<Post> listPosts();

    /**
     * Método que busca e retorna uma lista de posts ordenados de forma decrescente baseado no seu dono (uma instância da classe perfil)
     * @param owner uma instância da classe Perfil, que representa o dono dos posts
     * @return Uma lista de posts pertencentes ao dono, se existirem posts
    // TODO: adicionar throws NotFoundErrror e atualizar documentação caso não tenha nenhum post associado ao dono informado
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    List<Post> listPostsByProfile(Profile owner);
}
