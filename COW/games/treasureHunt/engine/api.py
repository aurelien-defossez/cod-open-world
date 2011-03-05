class Api(object):
	game = None
	
	def init(self, game):
		self.game = game
	
	#getMapSize
	def getMapSize(self, aiId):
		return self.game.getMapSize()
	
	#getPosition
	def getPosition(self, aiId) :
		return self.game.getPosition(aiId)
	
	#move
	def move(self, aiId, direction):
		self.game.move(aiId, direction)
	
	#peek
	def peek(self, aiId):
		return self.game.peek(aiId)
	
	#test
	def test(self, aiId, n):
		return n*2
