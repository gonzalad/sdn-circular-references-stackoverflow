package com.example.sdncircularreferencesstackoverflow;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Builder {

    private Type groupementType;
    private Map<String, Type> types = new HashMap<>();

    public Builder() {
        FORMATION();
        GROUPEMENT();
    }

    public Collection<Type> getAllTypes() {
        return types.values();
    }

    private Type FORMATION() {
        return type(new Type()
                .code("FORMATION")
                .allowed(ref("PARCOURS_TYPE"), ref("SPECIALITE"), ref("OPTION"), ref("ANNEE"), ref("SEMESTRE"), ref("UE"), ref("ENSEIGNEMENT"), ref("GROUPEMENT"))
        );
    }

    private Type ref(String code) {
        return types.computeIfAbsent(code, (c) -> new Type().code(c));
    }

    private Type type(Type type) {
        Type t = types.computeIfAbsent(type.getCode(), (c) -> type);
        t.setAllowed(type.getAllowed());
        return t;
    }

    private Type GROUPEMENT() {
        if (groupementType == null) {
            groupementType = type(new Type()
                    .code("GROUPEMENT")
            );
            groupementType.addAllowed(groupementType);
            groupementType.addAllowed(ref("PARCOURS_TYPE"));
            groupementType.addAllowed(ref("SPECIALITE"));
            groupementType.addAllowed(ref("OPTION"));
            groupementType.addAllowed(ref("ANNEE"));
            groupementType.addAllowed(ref("SEMESTRE"));
            groupementType.addAllowed(ref("UE"));
            groupementType.addAllowed(ref("EC"));
            groupementType.addAllowed(ref("ENSEIGNEMENT"));
        }
        return groupementType;
    }

    private Type PARCOURS_TYPE() {
        return type(new Type().code("PARCOURS-TYPE")
                .allowed(ANNEE(), SEMESTRE(), UE(), ENSEIGNEMENT(), GROUPEMENT())
        );
    }

    private Type SPECIALITE() {
        return type(new Type().code("SPECIALITE")
                .allowed(ANNEE(), SEMESTRE(), UE(), ENSEIGNEMENT(), GROUPEMENT())
        );
    }

    private Type OPTION() {
        return type(new Type().code("OPTION")
                .allowed(ANNEE(), SEMESTRE(), UE(), ENSEIGNEMENT(), GROUPEMENT())
        );
    }

    private Type ANNEE() {
        return type(new Type().code("ANNEE")
                .allowed(SEMESTRE(), UE(), ENSEIGNEMENT(), GROUPEMENT())
        );
    }

    private Type SEMESTRE() {
        return type(new Type().code("SEMESTRE")
                .allowed(UE(), ENSEIGNEMENT(), GROUPEMENT())
        );
    }

    private Type UE() {
        return type(new Type().code("UE")
                .allowed(EC(), ENSEIGNEMENT(), GROUPEMENT(), GROUPEMENT())
        );
    }

    private Type EC() {
        return type(new Type().code("EC")
                .allowed(ENSEIGNEMENT(), GROUPEMENT())
        );
    }

    private Type ENSEIGNEMENT() {
        return type(new Type().code("ENS")
                .allowed(COURS(), GROUPEMENT())
        );
    }

    private Type COURS() {
        return type(new Type().code("COURS")
                .allowed(COURS(), GROUPEMENT())
        );
    }
}
