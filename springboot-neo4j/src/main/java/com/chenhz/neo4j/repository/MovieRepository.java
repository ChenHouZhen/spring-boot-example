package com.chenhz.neo4j.repository;

import com.chenhz.neo4j.domain.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

/**
 */
public interface MovieRepository extends Neo4jRepository<Movie, Long>, CustomizedMovieRepository {

	Movie findByTitle(@Param("title") String title);

    Collection<Movie> findByRevenueGreaterThan(int revenue);

    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,r,a LIMIT {limit}")
    Collection<Movie> graph(@Param("limit") int limit);
}