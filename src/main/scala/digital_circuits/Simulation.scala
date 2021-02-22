package digital_circuits.Simulation
/*
  We will consider an example of how assignments(they're represents stateful objects) and higher-order functions can be combined 
in interesting ways. We want to construct a digital circuit simulator.
  A discrete event simulator performs 'actions', specified by the user at a given 'moment'.
*/
abstract trait Simulation { 
  // An 'action' is a function that doesn’t take any parameters and which returns 'Unit' :
  type Action = () => Unit
  case class Event(time: Int, action: Action)

  private var agenda: List[Event] = List()

  private var curtime = 0
  /**
  * @return returns the current simulated times in the form of an integer
  */
  def currentTime : Int = curtime

  private def insert(ag: List[Event], item: Event): List[Event] = ag match {
    case first :: rest if (first.time <=  item.time) => first :: insert(rest, item)
    case _ => item :: ag
  }

  /**
  *@return registers an action to perform after a certain delay (relative to the current time, `currentTime`).
  */
  def afterDelay(delay: Int)(block: => Unit): Unit = {
    val item = Event(currentTime + delay, () => block)
    agenda = insert(agenda, item)
  }

  private def loop(): Unit = agenda match {
    case first :: rest => 
      agenda = rest
      curtime = first.time
      first.action() 
      loop()
    case Nil => 
  }

  /**
  *@return `run` performs the simulation until there are no more actions waiting.
  */
  def run(): Unit = {
    afterDelay(0) {
      println("*** simulation started, time = "+currentTime+" ***")
    }
    loop()
  }

}