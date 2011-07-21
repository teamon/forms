package com.verknowsys.forms

class Field[E, T](val name: String, getter: E => T) { //}(val name: String, getter: (Entity) => T, default: T)(implicit val form: Form[_]){
//     // val value = (params(name) orElse entity) map getter getOrElse default
//
//     def decode(param: String): Option[T]
//     def encode(value: T): String = value.toString
}

// class StringField[E](name: String, getter: E => String, default: Option[String] = None) extends Field[E, String](name, getter){
//     def decode(param: String) = Some(param)
// }

// class IntField[E](name: String, getter: E => Int, default: Option[Int] = None) extends Field[E, Int](name, getter){
//     def decode(param: String) = try { Some(s.toInt) } catch { case java.lang.NumberFormatException => None }
// }

// class PublicKeyField[E](name: String, default: Some(E => PublicKey), default: Option[PublicKey] = None) extends Field[E, PublicKey](name, getter){
//     def decode(param: String) = KeyUtils.load(param)
// }
