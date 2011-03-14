from map import Map

class Game(object):
	conn = None
	view = None
	ais = []
	map = None
	gameOver = False
	
	#Initialises the game
	def initGame(self, connector, view):
		self.conn = connector
		self.view = view
		self.map = Map(self.view, 40, 40)
		self.gameOver = False
		self.view.displayGrid(0, 0, 400, 400, 10, 10, 0x606060FF)
	
	#Sets the player score
	def setScore(self, aiId, score):
		self.connector.setScore(aiId, score)
	
	#Adds an AI
	def addAi(self, aiId, playerName, aiName):
		self.ais.append(aiId)
		self.map.addHunter(aiId)
	
	#Removes an AI
	def removeAi(self, aiId, reason):
		self.map.getHunter(aiId).delete()
		self.ais.remove(aiId)
	
	#Ends the game
	def endGame(self):
		self.gameOver = True
	
	#Plays the game
	def play(self):
		for numTurn in range(1000):
			for aiId in self.ais:
				self.conn.callAiFunction(aiId, PHASE_PLAY)
				nb = self.map.treasuresFound(aiId)
				if nb > 0:
					self.conn.incrementScore(aiId, nb)
					for aiId in self.ais:
						self.conn.callAiFunction(aiId, PHASE_REINIT)
			
			self.conn.setFrame()
			
			if self.gameOver:
				break
	
	#getMapSize
	def getMapSize(self):
		return self.map.getSize()
	
	#getPosition
	def getPosition(self, aiId) :
		return self.map.getHunter(aiId).getPosition()
	
	#move
	def move(self, aiId, direction):
		self.map.getHunter(aiId).move(direction)
	
	#peek
	def peek(self, aiId):
		return self.map.peek(aiId)
	