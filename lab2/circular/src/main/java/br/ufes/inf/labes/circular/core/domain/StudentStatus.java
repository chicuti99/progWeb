package br.ufes.inf.labes.circular.core.domain;

public enum StudentStatus {
    REGULAR("regular"), IRREGULAR("irregular");

    private final String key;

    StudentStatus(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
