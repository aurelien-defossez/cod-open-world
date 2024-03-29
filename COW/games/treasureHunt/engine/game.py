from map import Map

class Game(object):
	conn = None
	view = None
	ais = []
	map = None
	gameOver = False
	
	#Initialises the game
	def initGame(self, connector, view, parameters):
		if(len(parameters) == 2):
			width = int(parameters[0])
			height = int(parameters[1])
		else:
			width = 42
			height = 42
		
		self.conn = connector
		self.view = view
		self.map = Map(self.view, width, height)
		self.gameOver = False
		self.view.displayGrid(0, 0, width * 10, height * 10, 10, 10, 0x303030, False)
	
	#Sets the player score
	def setScore(self, aiId, score):
		self.connector.setScore(aiId, score)
	
	#Adds an AI
	def addAi(self, aiId, playerName, aiName):
		self.ais.append(aiId)
		self.map.addHunter(aiId)
	
	#Removes an AI
	def aiTimedOut(self, aiId):
		self.map.getHunter(aiId).delete()
		self.ais.remove(aiId)
		self.conn.stopAi(aiId)
	
	#Ends the game
	def endGame(self):
		self.gameOver = True
	
	#Plays the game
	def play(self):
		for aiId in self.ais:
			self.conn.callAiFunction(aiId, PHASE_INIT)
		
		for numTurn in range(1000):
			for aiId in self.ais:
				self.conn.callAiFunction(aiId, PHASE_PLAY)
				nb = self.map.treasuresFound(aiId)
				if nb > 0:
					self.conn.incrementScore(aiId, nb)
					for aiId in self.ais:
						self.conn.callAiFunction(aiId, PHASE_REINIT)
			
			self.conn.setFrame()
			self.view.deleteTemporaryShapes()
			
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
	