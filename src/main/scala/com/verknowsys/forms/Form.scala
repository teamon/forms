package com.verknowsys.forms

trait Validators {
    val NotEmpty: Validator[String] = s => if(s.isEmpty) Some("Must not be empty") else None
    def LessThan(v: Int): Validator[Int] = i => if(i < v) None else Some("Must be less than " + v)
    def GreaterThan(v: Int): Validator[Int] = i => if(i > v) None else Some("Must be greater than " + v)

    def LessThanEqual(v: Int): Validator[Int] = i => GreaterThan(i)(v)
    def GreaterThanEqual(v: Int): Validator[Int] = i => LessThan(i)(v)
}


abstract class Form[Entity](val entity: Option[Entity], params: Params) extends Validators {
    def bind: Option[Entity]

    def fields: Seq[Field[_]]

    def isValid = fields.forall(_.isValid)

    def value = bind orElse entity

    abstract class Field[T](val name: String, getter: Entity => T, validators: Validator[T]*) {
        def decode(param: String): Option[T]
        def encode(value: T): String = value.toString

        lazy val (value, errors) = calculateValue

        def calculateValue: (Option[T], List[String]) = {
            val value = params.get(name).map(decode) getOrElse entity.map(getter)
            val errors = value map { v => (List[String]() /: validators){ (list, f) => f(v).toList ::: list } } getOrElse Nil
            (value, errors)
        }

        def isValid = value.isDefined && errors.isEmpty
    }

    def stringField(name: String, getter: Entity => String, validators: Validator[String]*) =
        new Field[String](name, getter, validators:_*){
            def decode(param: String) = Some(param)
        }

    def intField(name: String, getter: Entity => Int, validators: Validator[Int]*) =
        new Field[Int](name, getter, validators:_*){
            def decode(param: String) = try { Some(param.toInt) } catch { case ex: java.lang.NumberFormatException => None }
        }

}
