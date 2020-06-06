package com.example.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum TrustLevel {
    RELIABLE("reliable"),
    DOUBTFUL("doubtful"),
    NOVICE("novice");

    private String levelOfTrust;

    TrustLevel(String levelOfTrust) {
        this.levelOfTrust = levelOfTrust;
    }

    public static TrustLevel getFromName(String name) {
        Optional<TrustLevel> trustLevel = Arrays
                .stream(values())
                .filter(x -> x.getLevelOfTrust().equalsIgnoreCase(name))
                .findFirst();
        if (trustLevel.isPresent()) {
            return trustLevel.get();
        } else {
            throw new UnsupportedOperationException("Unsupported trust level: " + name);
        }
    }

    public static Boolean isTrustLevel(String name) {
        Optional<TrustLevel> trustLevel = Arrays
                .stream(values())
                .filter(x -> x.getLevelOfTrust().equalsIgnoreCase(name))
                .findFirst();
        return trustLevel.isPresent();
    }

}
