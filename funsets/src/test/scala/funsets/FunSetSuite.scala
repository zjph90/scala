package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  test("adding ints") {
    assert(1 + 2 === 3)
  }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s_even = (x => x%2 == 0): Set
    val s_odd = (x => x%2 == 1): Set
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("s_even contains evens") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s_even, 2), "s_even should contain 2")
      assert(!contains(s_even, 1), "s_even shouldn't contain 1")
    }
  }

  test("s_odd contains odds") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s_odd, 1), "s_odd should contain 1")
      assert(!contains(s_odd, 2), "s_odd shouldn't contain 2")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains only elements in both sets") {
    new TestSets {
      val s = intersect(s1, s_odd)
      assert(contains(s, 1), "One is odd!")
      assert(!contains(s, 2), "2 is not odd or in s1")
      assert(!contains(s, 3), "3 is not in s1")
    }
  }

  test("diff contains elements in first NOT in second") {
    new TestSets {
      val s = diff(s_odd, s1)
      assert(!contains(s, 1), "Shouldn't contain 1")
      assert(!contains(s, 2), "Shouldn't contain 2")
      assert(contains(s, 3), "Should contain 3")
    }
  }

  test("for all odds - are they odd?") {
    new TestSets {
      def is_odd(x: Int) = x%2 == 1
      val s = union(s_odd, s2)
      assert(forall(s_odd, is_odd), "Should all be odd")
      assert(!forall(s, is_odd), "Not all odd - has a 2")
    }
  }

  test("exits ") {
    new TestSets {
      def is_three(x: Int) = x == 3
      assert(exists(s_odd, is_three), "Three is odd")
      assert(!exists(s_even, is_three), "Three is not even")
    }
  }

  test("map") {
    new TestSets {
      val s = union(union(s1,s2),s3)
      val sqs = map(s, x => x*x)
      assert(contains(sqs, 9), "9 is a square")
      assert(!contains(sqs, 8), "8 is not a square")
      printSet(sqs)
    }
  }





}
