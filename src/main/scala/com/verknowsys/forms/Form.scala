package com.verknowsys.forms

trait Validators {
    val NotEmpty: Validator[String] = s => if(s.isEmpty) Some("Must not be empty") else None
    def LessThan(v: Int): Validator[Int] = i => if(i < v) None else Some("Must be less than " + v)
    def GreaterThan(v: Int): Validator[Int] = i => if(i > v) None else Some("Must be greater than " + v)

    def LessThanEqual(v: Int): Validator[Int] = i => GreaterThan(i)(v)
    def GreaterThanEqual(v: Int): Validator[Int] = i => LessThan(i)(v)
}

abstract class Field[Entity, T](val name: String, getter: Entity => T, validators: Validator[T]*)(implicit val form: Form[Entity]) {
    def decode(param: String): Option[T]
    def encode(value: T): String = value.toString

    lazy val (value, errors) = calculateValue

    def calculateValue: (Option[T], Seq[String]) = {
        val value = form.params.get(name).map(decode) getOrElse form.entity.map(getter)
        val errors = value map { v => validators.view map {_(v)} collect { case Some(e) => e } } getOrElse Nil
        (value, errors)
    }

    def isValid = value.isDefined && errors.isEmpty
}

trait CommonFields {
    self: Form[_] =>

    def StringField(name: String, getter: Entity => String, validators: Validator[String]*)(implicit form: Form[Entity]) =
        new Field(name, getter, validators:_*){
            def decode(param: String) = Some(param)
        }

    def IntField(name: String, getter: Entity => Int, validators: Validator[Int]*)(implicit form: Form[Entity]) =
        new Field(name, getter, validators:_*){
            def decode(param: String) = try { Some(param.toInt) } catch { case ex: java.lang.NumberFormatException => None }
        }
}


abstract class Form[E](val entity: Option[E], val params: Params) extends Validators with CommonFields {
    type Entity = E

    def bind: Option[Entity]

    def fields: Seq[Field[Entity, _]]

    def isValid = fields.forall(_.isValid)

    def value = bind orElse entity

    implicit val self = this
}
