package codes.recursive.model

import groovy.transform.CompileStatic
import groovy.transform.MapConstructor
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@CompileStatic
@Table(name = "person")
@MapConstructor(noArg = true)
class Person {
    @Id
    @GeneratedValue
    Long id
    String firstName
    String lastName
    @DateCreated
    Date dateCreated
    @DateUpdated
    Date lastUpdated
    @OneToMany(mappedBy = "person")
    Set<PersonHobby> personHobbies

    Person(Long id, String firstName, String lastName, Date dateCreated, Date lastUpdated, Set<PersonHobby> personHobbies) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.dateCreated = dateCreated
        this.lastUpdated = lastUpdated
        this.personHobbies = personHobbies
    }
}