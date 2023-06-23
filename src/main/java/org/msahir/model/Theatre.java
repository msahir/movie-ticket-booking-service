package org.msahir.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Theatre {

    private final String id;
    private final String name;
    private final List<Audi> audis;
    //Other theatre metadata.

    public Theatre(@NonNull final String id, @NonNull final String name) {
        this.id = id;
        this.name = name;
        this.audis = new ArrayList<>();
    }

    public void addAudi(@NonNull final Audi audi) {
        audis.add(audi);
    }
}
