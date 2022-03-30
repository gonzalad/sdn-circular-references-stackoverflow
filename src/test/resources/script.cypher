CREATE (n:Type {id: 1, code: 'OPTION'});
CREATE (n:Type {id: 2, code: 'CAREER'});
CREATE (n:Type {id: 3, code: 'YEAR'});
MATCH (t1:Type {code: 'YEAR'}), (t2:Type {code: 'OPTION'})
MERGE (t1)-[:IS_ALLOWED]->(t2);
MATCH (t1:Type {code: 'OPTION'}), (t2:Type {code: 'YEAR'})
MERGE (t1)-[:IS_ALLOWED]->(t2);
MATCH (t1:Type {code: 'CAREER'}), (t2:Type {code: 'YEAR'})
MERGE (t1)-[:IS_ALLOWED]->(t2);
