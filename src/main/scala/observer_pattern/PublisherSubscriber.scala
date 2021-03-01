package fp_design.observer_pattern

trait Publisher {
	private var subscribers: Set[Subscriber] = Set()
	
	def subscribe(subscriber: Subscriber): Unit = 
		subscribers += subscriber

	def unsubscribe(subscriber: Subscriber): Unit = 
		subscribers -= subscriber

	def publish(): Unit = 
		subscribers.foreach(_.handler(this))
}

trait Subscriber {
	def handler(pub: Publisher): Unit
}