package digital_circuits
import digital_circuits.Circuits._
object Testing extends App {
  object sim extends Circuits with Parameters
  import sim._

  val inp1, inp2, sum, carry = new Wire
  halfAdder(inp1, inp2, sum, carry)
  probe("sum", sum)
  probe("carry", carry)

  inp1 setSignal true
  run()
  inp2 setSignal true
  run()
  inp1 setSignal false
  run()
}