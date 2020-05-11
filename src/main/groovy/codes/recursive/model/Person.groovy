package codes.recursive.model

import groovy.transform.CompileStatic
import groovy.transform.MapConstructor
import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated

import javax.annotation.Nullable
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@CompileStatic
@Table(name = "person")
@MapConstructor(noArg = true)
class Person {
    @Id
    @GeneratedValue
    Long id
    @NotNull
    @Size(min = 3, max = 10)
    String firstName
    @NotNull
    String lastName
    @Nullable
    @Max(125L)
    int age
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