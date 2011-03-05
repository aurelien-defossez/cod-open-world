class Api(object):
	game = None
	
	def init(self, game):
		self.game = game
	
	#xor
	def xor(self, aiId, a, b):
		return self.game.xor(a, b)
	
	#++
	def plusplus(self, aiId, x) :
		return self.game.plusplus(x)
	
	#/10
	def divide10(self, aiId, x):
		return self.game.divide10(x)
	
	#Concat
	def concat(self, aiId, s1, s2):
		return self.game.concat(s1, s2)
	
	#Sum
	def sum(self, aiId, values):
		return self.game.sum(values)
	
	#Count
	def count(self, aiId, nb):
		return self.game.count(nb)
	
	#Print
	def printText(self, aiId, text):
		return self.game.printText(text)