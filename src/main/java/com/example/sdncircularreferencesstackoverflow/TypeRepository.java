package com.example.sdncircularreferencesstackoverflow;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends Neo4jRepository<Type, Long> {

    //@formatter:off
    @Query("MATCH (nt:Type)\n"
            + "OPTIONAL MATCH (nt)-[raut:IS_ALLOWED]->(aut)\n"
            + "RETURN nt,\n"
            + "  collect(raut), collect(aut)\n"
    )
    //@formatter:on
    List<Type> findAllTypes();
}
