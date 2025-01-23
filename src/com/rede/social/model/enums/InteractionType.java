package com.rede.social.model.enums;

public enum InteractionType {
    LIKE("👍"),
    DISLIKE("👎"),
    LAUGH("😂"),
    SURPRISE("😲");

    private final String emoji;

    InteractionType(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }

    @Override
    public String toString() {
        return this.name() + " " + emoji;
    }
}
