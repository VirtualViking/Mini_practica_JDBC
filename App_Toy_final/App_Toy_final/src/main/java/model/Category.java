package model;

import java.util.Arrays;

public enum Category {
    FEMENINO(0),
    MASCULINO(1),
    UNISEX(2);

    private final int name;

    Category(int name) {
        this.name = name;
    }
    public int getName() {
        return name;
    }
    public  static Category fromName(int name) {
        return Arrays.stream(Category.values()).filter(c ->c.name ==(name)).findFirst().orElseThrow();
    }
}
