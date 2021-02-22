package fp_design

class BankAccount extends Publisher {
	private var balance: Int = 0
	def currentBalance: Int = balance

  def deposit(amount: Int): Unit = 
  	if (amount > 0) {
  		balance = balance + amount
  		publish()
  	}

  def withdraw(amount: Int): Unit = 
  	if (amount > 0 && amount <= balance) {
  		balance = balance - amount
  		publish()
  	} else throw new Error("insufficient funds")
}