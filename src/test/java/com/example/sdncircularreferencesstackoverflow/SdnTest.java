package com.example.sdncircularreferencesstackoverflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"debug=true"})
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
    void useRepository() {
        runScript("script.cypher");

        List<Type> types = repository.findAllTypes();

        assertThat(types).hasSize(3);
    }

    @Test
    void withMatriceLiberte() {
        Builder builder = new Builder();
        repository.saveAll(builder.getAllTypes());

        List<Type> types = repository.findAllTypes();

        assertThat(types).hasSize(11);
    }

    @Test
    void customBean() {
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

        assertThat(types).hasSize(11);
    }

    private void runScript(String script) {
        List<String> cypher = readScriptContent(script);
        cypher.forEach( c -> neo4jClient.query(c).run());
    }

    private List<String> readScriptContent(String script) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(script);
        if (is == null) {
            throw new RuntimeException("Script " + script + " doesn't exists");
        }
        String content = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        return Arrays.asList(content.split(";"));
    }

    private void deleteAll() {
        neo4jClient.query("MATCH (n) DELETE n").run();
    }


}
