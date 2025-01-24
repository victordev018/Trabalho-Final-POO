package com.rede.social.repository;

import com.rede.social.exception.global.NotFoundError;
import com.rede.social.model.Profile;

import java.util.List;
import java.util.Optional;

public interface IProfileRepository {
    /**
     * Método que adiciona uma instância de Profile no repositório de Profiles
     * @param profile perfil a ser adicionado
    // TODO: adicionar throws ProfileAlreadyRegisteredError e atualizar documentação caso o perfil já exista no repositório de perfis
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    void addProfile(Profile profile);

    /**
     * Método que busca e retorna um perfil baseado na string que representa o email do perfil
     * @param email o email do perfil a ser buscado
     * @return o perfil buscado, se encontrado
     // TODO: adicionar throws NotFoundError e atualizar documentação caso o perfil não exista
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    Optional<Profile> findProfileByEmail(String email);

    /**
     * Método que busca e retorna um perfil baseado na string que representa o username do perfil
     * @param username o username do perfil a ser buscado
     * @return o perfil buscado, se encontrado
    // TODO: adicionar throws NotFoundError e atualizar documentação caso o perfil não exista
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    Optional<Profile> findProfileByUsername(String username);

    /**
     * Método que busca e retorna um perfil baseado no int que representa o id do perfil
     * @param id o id do perfil a ser buscado
     * @return o perfil buscado, se encontrado
    // TODO: adicionar throws NotFoundError e atualizar documentação caso o perfil não exista
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    Optional<Profile> findProfileById(Integer id);

    /**
     * Esse método é utilizado para retornar todos os perfis armazenados
     * @return todos os perfis criados
    // TODO: adicionar throws DBException e atualizar documentação caso haja erro na comunicação com o banco de dados
     */
    List<Profile> getAllProfiles();
}
