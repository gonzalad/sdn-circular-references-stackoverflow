package com.example.sdncircularreferencesstackoverflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SdnTest extends AbstractIT {

    @Autowired
    private Neo4jClient neo4jClient;

    @Autowired
    private TypeRepository repository;

    @BeforeEach
    void setUp() {
        deleteAll();
    }

    @Test
    void findOk() {
        Type formation = new Type().code("FORMATION");
        Type parcoursType = new Type().code("PARCOURS-TYPE");
        formation.allowed(parcoursType);
        List<Type> toSave = List.of(
                formation,
                parcoursType
        );
        repository.saveAll(toSave);

        List<Type> types = repository.findAllTypes();

        assertThat(types).hasSize(2);
    }

    @Test
    void findWithCircularReferenceThrowsStackOverflowException() {
        Type formation = new Type().code("FORMATION");
        Type groupement = new Type().code("GROUPEMENT");
        formation.allowed(groupement);
        groupement.allowed(groupement);
        List<Type> toSave = List.of(
                formation,
                groupement
        );
        repository.saveAll(toSave);

        List<Type> types = repository.findAllTypes();

        assertThat(types).isNotEmpty();
    }

    @Test
    void findMoreComplexSampleWithCircularReferenceThrowsStackOverflowException() {
        Type formation = new Type().code("FORMATION");
        Type parcoursType = new Type().code("PARCOURS-TYPE");
        Type annee = new Type().code("ANNEE");
        Type groupement = new Type().code("GROUPEMENT");
        Type ue = new Type().code("UE");
        Type ec = new Type().code("EC");
        formation.allowed(parcoursType, groupement, annee, ue);
        groupement.allowed(parcoursType);
        groupement.allowed(annee);
        groupement.allowed(groupement);
        parcoursType.allowed(annee);
        parcoursType.allowed(groupement);
        annee.allowed(groupement);
        annee.allowed(ue);
        ue.allowed(ec);
        List<Type> toSave = List.of(
                formation,
                parcoursType,
                groupement,
                annee,
                ue,
                ec
        );
        repository.saveAll(toSave);

        List<Type> types = repository.findAllTypes();

        assertThat(types).isNotEmpty();
    }

    private void deleteAll() {
        neo4jClient.query("MATCH (n) DETACH DELETE n").run();
    }
}
