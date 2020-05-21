package codes.recursive.repository

import codes.recursive.model.Person
import codes.recursive.model.PersonHobby
import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Join
import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import org.hibernate.Criteria
import org.hibernate.Session
import org.hibernate.transform.AliasToEntityMapResultTransformer

import javax.persistence.EntityManager
import javax.transaction.Transactional

@CompileStatic
@Repository
abstract class PersonRepository implements CrudRepository<Person, Long> {

    private final EntityManager entityManager

    PersonRepository(EntityManager entityManager) {
        this.entityManager = entityManager
    }

    Session getSession() {
        Session session = entityManager.unwrap(Session.class)
        return session
    }

    abstract PersonHobby savePersonHobby(PersonHobby personHobby)

    abstract List<Person> findByLastName(String lastName)

    @Join(value = "personHobbies", type = Join.Type.LEFT_FETCH) //results in n+1 queries
    abstract List<Person> findDistinct()

    @Query("select distinct p from Person p left join fetch p.personHobbies ph left join fetch ph.hobby h") //results in 1 query
    abstract List<Person> listPersons()

    @Transactional
    List<Person> nativeQuery() {
        javax.persistence.Query nativeQuery = getSession().createSQLQuery("""
            select 
                {p.*}, 
                {ph.*}, 
                {h.*}
            from person p 
                left join person_hobby ph
                    on p.id = ph.person_id
                left join hobby h
                    on h.id = ph.hobby_id
        """)
            .addEntity("p", Person.class)
            .addJoin("ph", "p.personHobbies")
            .addJoin("h", "ph.hobby")
            .addEntity("p", Person.class) // yeah, weird that you have to have this twice, and i can't remember where i read that this was necessary, but it doesn't work right without it
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
        return nativeQuery.getResultList()
    }

    @Transactional
    List nativeQuery2() {
        return getSession().createSQLQuery("select first_name, last_name from person")
        .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
        .list()
    }

}
