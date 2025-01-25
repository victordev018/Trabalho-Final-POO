package com.rede.social.application;
import com.rede.social.exception.global.AlreadyExistsError;
import com.rede.social.exception.global.NotFoundError;
import com.rede.social.exception.profileException.ProfileAlreadyActivatedError;
import com.rede.social.exception.profileException.ProfileAlreadyDeactivatedError;
import com.rede.social.exception.profileException.ProfileUnauthorizedError;
import com.rede.social.model.AdvancedPost;
import com.rede.social.model.Interaction;
import com.rede.social.model.Post;
import com.rede.social.model.Profile;
import com.rede.social.model.enums.InteractionType;
import com.rede.social.util.IOUtil;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    private SocialNetwork socialNetwork;
    private IOUtil ioUtil;

    public App(SocialNetwork socialNetwork, IOUtil ioUtil) {
        this.socialNetwork = socialNetwork;
        this.ioUtil = ioUtil;
    }

    // métodos relacionados ao gerenciamento de perfis

    public void createProfile() {
        String username = ioUtil.getText("> Insira o seu nome de usuario: ");
        String email = ioUtil.getText("> Insira o seu email: ");
        String photo = ioUtil.getText("> Insira uma foto(emogi): ");
        int typeProfile = ioUtil.getInt("> tipo de perfil: (1-normal, 2-avançado): ");
        Profile newProfile = typeProfile == 1? socialNetwork.createProfile(username, photo, email) :
                socialNetwork.createAdvancedProfile(username, photo, email);
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

    public void createPost() {
        ioUtil.showMessage("-> informações do perfil <-");
        String username = ioUtil.getText("> insira o username: ");
        String email = ioUtil.getText("> insira o email: ");

        try {
            Profile foundByUsername = socialNetwork.findProfileByUsername(username);
            Profile foundByEmail = socialNetwork.findProfileByEmail(email);

            if (!(foundByUsername.getEmail().equals(foundByEmail.getEmail()) &&
                    foundByEmail.getUsername().equals(foundByUsername.getUsername()))) {
                ioUtil.showError("!As informações não são do mesmo perfil!");
                return;
            }

            String contentPost = ioUtil.getText("> conteudo do post: ");
            int typePost = ioUtil.getInt("> tipo do post: (1-normal, 2-avançado): ");
            Post newPost = typePost == 1 ? socialNetwork.createPost(contentPost, foundByUsername):
                    socialNetwork.createAdvancedPost(contentPost, foundByUsername);

            socialNetwork.addPost(newPost);
            ioUtil.showMessage("-> novo post adicionado com sucesso ao perfil de " + foundByUsername.getUsername());

        } catch (NotFoundError e) {
            ioUtil.showError(e.getMessage());
        }
    }

    public void listAllPosts() {
        List<Post> posts = socialNetwork.listPosts();
        if (posts.isEmpty()) {
            ioUtil.showMessage("!Nao ha posts cadastrados!");
            return;
        }

        ioUtil.showMessage("-> FEED com todos os posts <-");
        posts.forEach(this::showPost);
    }

    public void listPostByProfile() {
        ioUtil.showMessage("-> informações do perfil <-");
        String username = ioUtil.getText("> insira o username: ");
        String email = ioUtil.getText("> insira o email: ");

        try {
            Profile foundByUsername = socialNetwork.findProfileByUsername(username);
            Profile foundByEmail = socialNetwork.findProfileByEmail(email);

            List<Post> postsFromProfile = socialNetwork.listPostsByProfile(username);
            if (postsFromProfile.isEmpty()) {
                ioUtil.showMessage("!O perfil de " + username + " não possui nenhum post!");
                return;
            }
            ioUtil.showMessage("-> posts de " + username + ":");
            postsFromProfile.forEach(this::showPost);

        } catch (NotFoundError e) {
            ioUtil.showError(e.getMessage());
        }
    }

    private void showPost(Post post) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String postFormated;
        if (post instanceof AdvancedPost) {
            Map<InteractionType, Integer> interactionTypeIntegerMap = this.getQuantityInteractionType((AdvancedPost) post);
            postFormated = String.format("""
                ╔═════════╦══════════════════╦══════════════════╦═══════════════════════════════════════════╦════════════════════════════╗
                ║ <ID> %-2d ║ @%-15s ║ %-16s ║ %-40s  ║  %-2d-👍 %-2d-👎 %-2d-😂 %-2d-😲  ║
                ╚═════════╩══════════════════╩══════════════════╩═══════════════════════════════════════════╩════════════════════════════╝
                """, post.getId(), post.getOwner().getUsername(),
                    post.getCreatedAt().format(fmt), post.getContent(),
                    interactionTypeIntegerMap.get(InteractionType.LIKE),
                    interactionTypeIntegerMap.get(InteractionType.DISLIKE),
                    interactionTypeIntegerMap.get(InteractionType.LAUGH),
                    interactionTypeIntegerMap.get(InteractionType.SURPRISE));
            ioUtil.showMessage(postFormated);
            return;
        }

        postFormated = String.format("""
                ╔═════════╦══════════════════╦══════════════════╦═══════════════════════════════════════════╗
                ║ <ID> %-2d ║ @%-15s ║ %-16s ║ %-40s  ║
                ╚═════════╩══════════════════╩══════════════════╩═══════════════════════════════════════════╝
                """, post.getId(), post.getOwner().getUsername(),
                     post.getCreatedAt().format(fmt), post.getContent());
        ioUtil.showMessage(postFormated);
    }

    private Map<InteractionType, Integer> getQuantityInteractionType(AdvancedPost post) {
        List<Interaction> interactions = post.listInteractions();
        int like = 0, dislike = 0, laugh = 0, surprise = 0;
        for (Interaction interaction : interactions) {
            if (interaction.getType() == InteractionType.LIKE) {
                like++;
                continue;
            }
            if (interaction.getType() == InteractionType.DISLIKE) {
                dislike++;
                continue;
            }
            if (interaction.getType() == InteractionType.LAUGH) {
                laugh++;
                continue;
            }
            if (interaction.getType() == InteractionType.SURPRISE) {
                surprise++;
            }
        }
        Map<InteractionType, Integer> interactionTypeIntegerMap = Map.of(
                InteractionType.LIKE, like,
                InteractionType.DISLIKE, dislike,
                InteractionType.LAUGH, laugh,
                InteractionType.SURPRISE, surprise
        );
        return interactionTypeIntegerMap;
    }

    // métodos relacionado ao gerenciamento de solicitações

    // métodos relacionado ao gerenciamento de interações

}
