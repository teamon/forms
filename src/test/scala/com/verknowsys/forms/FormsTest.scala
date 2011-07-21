package com.verknowsys.forms

import org.scalatest._
import org.scalatest.matchers._

case class User(name: String, age: Int)

class UserForm(entity: Option[T] = None) extends Form[User](entity) {
    val singleton = User
    type F = (String, Int, PublicKey) => User

    def bind(f: F) = f(name.get, age.get, key.get)

    val name = new StringField("name", _.name)
    val age = new IntField("age", _.age)
    val key = new PublicKeyField("key", _.key)

    def fields = name :: age :: key :: Nil
}

class FormsTest extends FlatSpec with ShouldMatchers with OneInstancePerTest {
    it should "allow to be created without parameters" in {
        val form = new UserForm()
    }
}
