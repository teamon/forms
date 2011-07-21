package com.verknowsys.forms

import org.scalatest._
import org.scalatest.matchers._

case class User(name: String, age: Int)

class UserForm(entity: Option[User] = None, params: Params = Params.Empty) extends Form[User](entity, params) {
    def f = entity.map { _.copy _ } getOrElse User.apply _

    def bind = for {
        n <- name
        a <- age
    } yield f(n, a)

    val name = stringField("name", _.name)
    val age = intField("age", _.age)
    // val key = new PublicKeyField("key", _.key)

    def fields = name :: age :: Nil
}

class FormsTest extends FlatSpec with ShouldMatchers with OneInstancePerTest {
    it should "allow to be created without object" in {
        val form = new UserForm()
        form.value should be (None)
        form.name.value should be (None)
        form.age.value should be (None)
    }

    it should "allow to be created with object" in {
        val user = User("teamon", 20)
        val form = new UserForm(Some(user))
        form.value should be (Some(user))
        form.name.value should be (Some("teamon"))
    }

    it should "populate form without object with correct params" in {
        val form = new UserForm(None, Map("name" -> "dmilith", "age" -> "30"))
        form.name.value should be (Some("dmilith"))
        form.age.value should be (Some(30))
        form.value should be (Some(User("dmilith", 30)))
    }

    it should "populate form without object with missing age" in {
        val form = new UserForm(None, Map("name" -> "dmilith"))
        form.name.value should be (Some("dmilith"))
        form.age.value should be (None)
        form.value should be (None)
    }

    it should "populate form without object with missing name" in {
        val form = new UserForm(None, Map("age" -> "30"))
        form.name.value should be (None)
        form.age.value should be (Some(30))
        form.value should be (None)
    }

    it should "populate form without object with empty params" in {
        val form = new UserForm(None, Map())
        form.name.value should be (None)
        form.age.value should be (None)
        form.value should be (None)
    }

    it should "populate form with object with params" in {
        val user = User("teamon", 21)
        val form = new UserForm(Some(user), Map("name" -> "dmilith", "age" -> "30"))
        form.name.value should be (Some("dmilith"))
        form.age.value should be (Some(30))
        form.value should be (Some(User("dmilith", 30)))
    }

    it should "populate form with object with missing age" in {
        val user = User("teamon", 21)
        val form = new UserForm(Some(user), Map("name" -> "dmilith"))
        form.name.value should be (Some("dmilith"))
        form.age.value should be (Some(21))
        form.value should be (Some(User("dmilith", 21)))
    }

    it should "populate form with object with missing name" in {
        val user = User("teamon", 21)
        val form = new UserForm(Some(user), Map("age" -> "30"))
        form.name.value should be (Some("teamon"))
        form.age.value should be (Some(30))
        form.value should be (Some(User("teamon", 30)))
    }

    it should "populate form with object with empty params" in {
        val user = User("teamon", 21)
        val form = new UserForm(Some(user), Map())
        form.name.value should be (Some("teamon"))
        form.age.value should be (Some(21))
        form.value should be (Some(User("teamon", 21)))
    }
}
