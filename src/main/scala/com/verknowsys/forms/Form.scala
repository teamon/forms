package com.verknowsys.forms

abstract class Form[Entity](val entity: Option[Entity], params: Params) {
    def bind: Option[Entity]

    def fields: Seq[Field[_]]

    lazy val value = bind orElse entity

    abstract class Field[T](val name: String, getter: Entity => T) {
        def decode(param: String): Option[T]
        def encode(value: T): String = value.toString

        def value: Option[T] = params.get(name).flatMap(decode) orElse entity.map(getter)
    }

    def stringField(name: String, getter: Entity => String, default: Option[String] = None) =
        new Field[String](name, getter){
            def decode(param: String) = Some(param)
        }

    def intField(name: String, getter: Entity => Int, default: Option[Int] = None) =
        new Field[Int](name, getter){
            def decode(param: String) = try { Some(param.toInt) } catch { case ex: java.lang.NumberFormatException => None }
        }

}
