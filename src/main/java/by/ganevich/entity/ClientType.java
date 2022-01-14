package by.ganevich.entity;

import java.util.Arrays;

public enum ClientType {
    INDIVIDUAL, INDUSTRIAL;

    public static ClientType getByOrdinal(int ordinal) {
        return Arrays.asList(ClientType.values()).stream().filter(pr -> ordinal == pr.ordinal()).findFirst().get();
    }
}
