## Simple forms library for scala (codename: I-want-my-types)

This is yet another approach to make HTML forms suck less.
I created this small library because no existing one fits my needs.
It does one thing and (imho) it does it well.

## Data model
Anything. Surprised? Thats right, you can use whatever objects/classes you like, there are no boundries!
Yet, I designed that with scala's case classes in mind and that's why all examples are based on them.

## Data flow
The well known MVC/REST-like 'edit' action. (Based on my experience with Rails, but applies to Play! or Scalatra as well)

1. Fetch an object (database/api/whatever)
2. Render some HTML form with values populated from fetched object
3. Fill the form, submit
// Using MVC/REST as example, we are now on 'create' action
4. Fetch an object again (you know, stateless etc.)
5. Update object attributes
6. Validate the object
7. Save it if valid, render HTML otherwise

The Rails/Play! way of managing this is to declare fields and validations in model class and write form mostly by-hand (maybe using some helper functions, the point is the form has to be written in view (templates)).
The 'new' form is usually the same as the 'edit' one and it must be repeated in view. Other problem with model-centric approach is that sometimes you may want different validations for different cases and you end with weird logic in model that tries to handle each one.

I never like that.

My solution is simple - define Form object and let it do the hard work.

Getting back to data flow, there is something missing.
Rails is written in Ruby, and Ruby is dynamicly typed language. You can declare an object's field as Integer, set it to some String value and noone cares, it will be saved correctly most of the times. Most...
Ok, so Rails' way is: "get request params" -> "set fields" -> "validate"
Now let's talk about Scala. It's staticly typed language. You "cant" just assign String to an Integer field, you have to make conversion. And conversion can raise an exception, and exceptions are not my favourites.
I want my types!
Scala's way: "get request params" -> "TRY to set fields" -> "validate"
The keyword here is "TRY".

Some examples:
"1" - looks like a 'valid' Int (1)
"0" - same as above, Int(0)
"$%" - ???? What about this one? Ruby says it's 0 ("$%".to_i == 0). For me it is not an Int, it is _nothing_

other example: Public Key - is sure has its string representation, but the format is well defined, and any other is not a valid PublicKey.

Finishing this much too long introduction, all I want to say is that I extended the data flow a bit with safe type cast.

## Some scala
If you think about validation as a function _String => Boolean_ you still have String as a value
I propose two functions:
  converter: String => Option[T]
  validator: T => Boolean

There is no point in validating a String. I don't care about string, I want my types! If a string can't be converted to my type then for me it is invalid, no other validation required.

to be continued...
