package codes.recursive.repository

import codes.recursive.model.Person
import codes.recursive.model.PersonHobby
import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.annotation.EntityGraph
import io.micronaut.data.repository.CrudRepository
import org.hibernate.criterion.Distinct

import javax.persistence.ColumnResult
import javax.persistence.ConstructorResult
import javax.persistence.EntityManager
import javax.persistence.SqlResultSetMapping
import javax.transaction.Transactional

@CompileStatic
@Repository
abstract class PersonRepository implements CrudRepository<Person, Long> {

    private final EntityManager entityManager

    PersonRepository(EntityManager entityManager) {
        this.entityManager = entityManager
    }

    abstract PersonHobby savePersonHobby(PersonHobby personHobby)

    abstract List<Person> findByLastName(String lastName)

    @Join(value = "personHobbies", type = Join.Type.LEFT_FETCH) //results in n+1 queries
    abstract List<Person> findDistinct()

    @Query("select distinct p from Person p left join fetch p.personHobbies ph left join fetch ph.hobby h") //results in 1 query
    abstract List<Person> listPersons()

    @Transactional
    List<Map> nativeQuery() {
        javax.persistence.Query nativeQuery = entityManager.createNativeQuery("""
            select first_name, last_name
            from person
        """)
        return nativeQuery.getResultList()
    }

    @Transactional
    List<Person> nativeQueryMapped() {
        javax.persistence.Query nativeQuery = entityManager.createNativeQuery("""
            select id, first_name, last_name, date_created, last_updated
            from person
        """)
        return nativeQuery.getResultList()
    }

}
