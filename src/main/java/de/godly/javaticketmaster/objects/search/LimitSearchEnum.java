package de.godly.javaticketmaster.objects.search;

import lombok.Getter;

public enum LimitSearchEnum {

    YES("yes"), NO("no"), ONLY("only");

    @Getter
    private String paramId;
    LimitSearchEnum(String familyFriendly){
        this.paramId = familyFriendly;
    }

}
