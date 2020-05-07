package codes.recursive.repository

import codes.recursive.model.Hobby
import groovy.transform.CompileStatic
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@CompileStatic
@Repository
abstract class HobbyRepository implements CrudRepository<Hobby, Long> {
}
