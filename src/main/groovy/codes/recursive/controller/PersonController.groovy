package codes.recursive.controller

import codes.recursive.model.Person
import codes.recursive.repository.PersonRepository
import groovy.transform.CompileStatic
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException
import javax.validation.Valid

@CompileStatic
@Controller("/person")
class PersonController {

    private final PersonRepository personRepository

    PersonController(
            PersonRepository personRepository
    ) {
        this.personRepository = personRepository
    }

    @Get(uri="/", produces="text/plain")
    String index() {
        "Example Response"
    }

    @Get(uri="/listPersons", produces= MediaType.APPLICATION_JSON)
    HttpResponse listPersons() {
        return HttpResponse.ok(
                personRepository.listPersons()
        )
    }

    @Get(uri="/findAll", produces= MediaType.APPLICATION_JSON)
    HttpResponse findAll() {
        return HttpResponse.ok(
                personRepository.findDistinct()
        )
    }

    @Get(uri="/nativeQuery", produces= MediaType.APPLICATION_JSON)
    HttpResponse nativeQuery() {
        return HttpResponse.ok(
                personRepository.nativeQuery()
        )
    }

    @Get(uri="/nativeQuery2", produces= MediaType.APPLICATION_JSON)
    HttpResponse nativeQuery2() {
        return HttpResponse.ok(
                personRepository.nativeQuery2()
        )
    }

    @Post("/savePerson")
    HttpResponse savePerson(@Body @Valid Person person) {
        personRepository.save(person)
        return HttpResponse.created(
                person
        )
    }

    @Error(exception = ConstraintViolationException.class)
    Map<String, Object> onValidationException(HttpRequest request, ConstraintViolationException ex) {
        return [
                errors: ex.constraintViolations.collect { ConstraintViolation it ->
                    [
                            message: it.message,
                            path: it.propertyPath,
                    ]
                },
                body: request?.getBody(),
        ]
    }
}