package codes.recursive.model

import com.fasterxml.jackson.annotation.JsonIgnore
import groovy.transform.CompileStatic

import javax.persistence.*

@Entity
@CompileStatic
@Table(name = "person_hobby")
class PersonHobby {
    @Id
    @GeneratedValue
    Long id
    @JsonIgnore
    @ManyToOne
    Person person
    @ManyToOne
    Hobby hobby
}