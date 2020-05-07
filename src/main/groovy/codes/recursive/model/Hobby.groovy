package codes.recursive.model

import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

@Entity
@CompileStatic
@Table(name = "hobby")
class Hobby {
    @Id
    @GeneratedValue
    Long id
    String name
    @NotNull
    @PositiveOrZero
    BigDecimal monthlyCost=0.00
}