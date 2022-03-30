package com.example.sdncircularreferencesstackoverflow;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node(labels = "Type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;
    private String code;
    @Relationship(type = "IS_ALLOWED")
    private List<Type> allowed = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void addAllowed(Type type) {
        allowed.add(type);
    }

    public List<Type> getAllowed() {
        return allowed;
    }

    public void setAllowed(List<Type> allowed) {
        this.allowed = allowed;
    }

    public Type code(String code) {
        this.code = code;
        return this;
    }

    public Type allowed(Type... type) {
        this.allowed.addAll(List.of(type));
        return this;
    }

    @Override
    public String toString() {
        return "Type{" +
                "code='" + code + '\'' +
                '}';
    }
}
