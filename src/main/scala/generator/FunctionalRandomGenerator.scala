package generator
import RandomGenerator._

object RandomGenerator {
  trait Generator[+T] {
    self => // an alias for `this`

    def generate: T

    def map[U](f: T => U): Generator[U] = new Generator[U] {
        def generate: U = f(self.generate)
      }

    def flatMap[U](f: T => Generator[U]): Generator[U] = new Generator[U] {
      def generate: U = f(self.generate).generate
    }
  }

  /* What does this definition reselve to?
  Example: val booleans  = for (x <- integers) yield x > 0
  This example will expand to:
    val booleans = integers map { x => x > 0 } =====>

      val booleans = new Generator[Boolean] {
        def generate = (x: Int => x > 0)(integers.generate) ====>

        val booleans = new Genarator[Boolean] {
          def generate = integers.generate > 0
        }

      }
  */
  sealed trait Tree
  case class Inner(left: Tree, right: Tree) extends Tree
  case class Leaf(x: Int) extends Tree

  object Generator {
    // Corner cases for other generators
    val integers: Generator[Int] = new Generator[Int] {
      val rand = new java.util.Random
      def generate: Int = rand.nextInt()
    }

    val booleans: Generator[Boolean] = for (x <- integers) yield x > 0


    // Auxiliary functions
    def single[T](x: T): Generator[T] = new Generator[T] {
      def generate: T = x
    }

    def choose(lo: Int, hi: Int): Generator[Int] = 
      for (x <- integers) yield lo + x % (hi - lo)

    def oneOf[T](xs: T*): Generator[T] = 
      for (idx <- choose(0, xs.length)) yield xs(idx)

    // Lists of the integers generator
    def lists: Generator[List[Int]] = 
      for {
        isEmpty <- booleans
        list <- if (isEmpty) emptyLists else nonEmptyLists
      } yield list

    def emptyLists: Generator[List[Int]] = single(Nil)
    def nonEmptyLists: Generator[List[Int]] = 
      for {
        head <- integers
        tail <- lists
      } yield head :: tail

    // Trees generator
    def trees: Generator[Tree] = 
      for {
        isLeaf <- booleans
        tree <- if (isLeaf) leafs else inners
      } yield tree

    def leafs: Generator[Leaf] = 
      for {
        value <- integers
      } yield Leaf(value)

    def inners: Generator[Inner] = 
      for {
        l <- trees
        r <- trees
      } yield Inner(l, r)

    // Pairs generator
    def pairs[T, S](t: Generator[T], s: Generator[S]): Generator[(T, S)] = 
      t.flatMap { x => s.map { y => (x, y)} }
  }
}

// Using random generators
object RandomTestFunction extends App {
  import RandomGenerator.Generator._

  def test[T](gen: Generator[T], numInt: Int = 100)(test: T => Boolean): Unit = {
    for (i <- 0 until(numInt)) {
      val value = gen.generate
      assert(test(value), "test failed for " + value)
    }
  println("passed " + numInt + " tests")
  }

  test(pairs(lists, lists), 150) {
    case (xs, ys) => (xs ++ ys).length > xs.length
  }
}