package codes.recursive

import codes.recursive.model.Hobby
import codes.recursive.model.Person
import codes.recursive.model.PersonHobby
import codes.recursive.repository.HobbyRepository
import codes.recursive.repository.PersonRepository
import groovy.transform.CompileStatic
import io.micronaut.discovery.event.ServiceReadyEvent
import io.micronaut.runtime.event.annotation.EventListener

import javax.inject.Singleton

@Singleton
@CompileStatic
class Bootstrap {

    private final PersonRepository personRepository
    private final HobbyRepository hobbyRepository

    Bootstrap(
            PersonRepository personRepository,
            HobbyRepository hobbyRepository) {
        this.hobbyRepository = hobbyRepository
        this.personRepository = personRepository
    }

    @EventListener
    void startup(final ServiceReadyEvent event) {
        println "Service ready..."
        List<Person> demoUsers = personRepository.findByLastName("Demouser")
        if (demoUsers.size() == 0) {
            Person demoUser1 = new Person(firstName: "Person1", lastName: "Demouser")
            Person demoUser2 = new Person(firstName: "Person2", lastName: "Demouser")
            personRepository.save(demoUser1)
            personRepository.save(demoUser2)

            Hobby h1 = new Hobby(name: "Quilting")
            Hobby h2 = new Hobby(name: "Video Games", monthlyCost: 50.99)
            hobbyRepository.saveAll([h1, h2])

            PersonHobby ph1 = new PersonHobby(person: demoUser1, hobby: h1)
            PersonHobby ph2 = new PersonHobby(person: demoUser1, hobby: h2)
            personRepository.savePersonHobby(ph1)
            personRepository.savePersonHobby(ph2)

            println "Demo user created..."
        }
        println "Setup complete..."
    }
}
