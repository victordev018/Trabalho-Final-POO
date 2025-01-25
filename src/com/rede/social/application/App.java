package com.rede.social.application;
import com.rede.social.exception.global.AlreadyExistsError;
import com.rede.social.exception.global.NotFoundError;
import com.rede.social.exception.profileException.ProfileAlreadyActivatedError;
import com.rede.social.exception.profileException.ProfileAlreadyDeactivatedError;
import com.rede.social.exception.profileException.ProfileUnauthorizedError;
import com.rede.social.model.Profile;
import com.rede.social.util.IOUtil;

import java.util.List;

public class App {

    private SocialNetwork socialNetwork;
    private IOUtil ioUtil;

    public App(SocialNetwork socialNetwork, IOUtil ioUtil) {
        this.socialNetwork = socialNetwork;
        this.ioUtil = ioUtil;
    }

    // métodos relacionados ao gerenciamento de perfis
    public void addProfile() {
        String username = ioUtil.getText("> Insira o seu nome de usuario: ");
        String email = ioUtil.getText("> Insira o seu email: ");
        String photo = ioUtil.getText("> Insira uma foto(emogi): ");
        Profile newProfile = socialNetwork.createProfile(username, photo, email);
        try {
            socialNetwork.addProfile(newProfile);
        } catch (AlreadyExistsError e) {
            ioUtil.showError("!Ja existe perfil com este nome ou email!");
        }
    }

    public void findProfile() {
        ioUtil.showMessage(" -> Voce pode fazer a busca por: username ou email <- ");
        String searchTerm = ioUtil.getText("> Insira o username ou email: ");
        Profile profile;
        // tentando encontrar pelo username
        try {
            profile = socialNetwork.findProfileByUsername(searchTerm);
            ioUtil.showMessage("-> usuario encontrado: \n" + profile.toString());
        } catch (NotFoundError e) {
            // tentando encontrar pelo email
            try {
                profile = socialNetwork.findProfileByEmail(searchTerm);
                ioUtil.showMessage("-> usuario encontrado: \n" + profile.toString());
            } catch (NotFoundError ex) {
                ioUtil.showError("!Nao foi encontrado o usuario com esta informacao: " + searchTerm);
            }
        }
    }

    public void listAllProfile() {
        List<Profile> profiles = socialNetwork.listProfile();
        if (profiles.isEmpty()) {
            ioUtil.showError("!Nao existe perfis cadastrados!");
            return;
        }
        ioUtil.showMessage("-> Lista de perfis:");
        profiles.forEach(profile -> ioUtil.showMessage(profile.toString()));
    }

    public void enableProfile() {

        // verificando se existe perfis salvos
        if (socialNetwork.listProfile().isEmpty()) {
            ioUtil.showError("!Nao ha perfil cadastrado para poder ativar!");
            return;
        }
        listAllProfile();       // exibe lista de perfis
        String username = ioUtil.getText("> informe o username do perfil a ser ativado: ");
        try {
            socialNetwork.activateProfile(username);
        } catch (NotFoundError e) {
            ioUtil.showError("!Nao foi encontrado perfil com username: " + username);
        } catch (ProfileUnauthorizedError e) {
            ioUtil.showError("O perfil nao e do tipo avancado, por isso nao sera ativado!");
        } catch (ProfileAlreadyActivatedError e) {
            ioUtil.showError("O perfil ja esta ativo!");
        }

        ioUtil.showMessage("-> perfil ativo com sucesso <-");
    }

    public void disableProfile() {

        // verificando se existe perfis salvos
        if (socialNetwork.listProfile().isEmpty()) {
            ioUtil.showError("!Nao ha perfil cadastrado para poder ativar!");
            return;
        }
        listAllProfile();       // exibe lista de perfis
        String username = ioUtil.getText("> informe o username do perfil a ser desativado: ");
        try {
            socialNetwork.unactivateProfile(username);
        } catch (NotFoundError e) {
            ioUtil.showError("!Nao foi encontrado perfil com username: " + username);
        } catch (ProfileUnauthorizedError e) {
            ioUtil.showError("O perfil nao e do tipo avancado, por isso nao sera ativado!");
        } catch (ProfileAlreadyDeactivatedError e) {
            ioUtil.showError("!O perfil ja esta desativado!");
        }

        ioUtil.showMessage("-> perfil desativado com sucesso <-");
    }

    // métodos relacionado ao gerenciamento de publicações

    // métodos relacionado ao gerenciamento de solicitações

    // métodos relacionado ao gerenciamento de interações

}
