package com.verknowsys.forms

abstract class Form[Entity](val entity: Option[Entity] = None){
    val singleton: Any
    type Bind

    protected def bind(f: Bind): Entity
    // protected def binder: Bind = entity.map { _.copy _ } getOrElse { singleton.apply _ }

    // lazy val value = bind(binder)

    def fields: Seq[Field[Entity, _]]

    // def field[T](name: String, getter: (Entity) => T, default: T) = new Field(name, getter, default)
}

