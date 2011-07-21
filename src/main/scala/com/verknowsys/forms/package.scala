package com.verknowsys

package object forms {
    implicit def fieldToOption[E,T](field: Form[E]#Field[T]) = field.value

    type Params = Map[String, String]
    object Params {
        val Empty: Params = Map()
    }
}
