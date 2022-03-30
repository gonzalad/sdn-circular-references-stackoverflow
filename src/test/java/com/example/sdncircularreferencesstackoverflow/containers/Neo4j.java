package com.example.sdncircularreferencesstackoverflow.containers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

public class Neo4j {
    private static final Logger log = LoggerFactory.getLogger(Neo4j.class);
    private static final String NEO4J_VERSION = "4.3.7";
    private static final String NEO4J_DOCKER_IMAGE = "neo4j:" + NEO4J_VERSION;

    private static Neo4jContainer neo4jContainer;

    public static Neo4jContainer database() {
        if (neo4jContainer == null) {
            neo4jContainer = new Neo4jContainer(NEO4J_DOCKER_IMAGE).withAdminPassword(null);
            neo4jContainer.withLogConsumer(new Slf4jLogConsumer(log));
            neo4jContainer.start();
        }
        return neo4jContainer;
    }
}
