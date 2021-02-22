package fp_design.Consolidator
import fp_design.publisher_subscriber._
import fp_design.BankAccount._


class Consolidator(observed: List[BankAccount]) extends Subscriber {
  observed.foreach(_.subscribe(this))

  private var total: Int = _
  sum()

  private def sum() = 
  	total = observed.map(_.currentBalance).sum
  
  def handler(pub: Publisher) = sum()

  def totalBalance = total
}