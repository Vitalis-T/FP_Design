package fp_design.observer_pattern

class Consolidator(observed: List[BankAccount]) extends Subscriber {
  observed.foreach(_.subscribe(this))

  private var total: Int = _
  compute()

  private def compute() = 
  	total = observed.map(_.currentBalance).sum
  
  // Whenever one of the BankAccount changes 'compute' is invoked again to recompute the total totalBalance.
  def handler(pub: Publisher) = compute()

  def totalBalance = total // an accessor method
}