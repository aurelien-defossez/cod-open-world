from lang.python import PyGameCommunicator
from viewApi import ViewApi
from com import ApiCall

class GameCommunicator(PyGameCommunicator):
	framework = None
	game = None
	apiDemux = None
	
	def init(self, framework, game, api, apiDemux):
		self.framework = framework
		self.game = game
		self.apiDemux = apiDemux
		self.apiDemux.init(api)
		api.init(game)
	
	def initGame(self, parameters):
		self.game.initGame(self, ViewApi(self), parameters)
	
	def addAi(self, aiId, playerName, aiName):
		self.game.addAi(aiId, playerName, aiName)
	
	def aiTimedOut(self, aiId):
		self.game.aiTimedOut(aiId)
	
	#Ends the game
	def endGame(self):
		self.game.endGame()
	
	#Plays a turn
	def play(self):
		self.game.play()
	
	#Executes an game API call
	def performGameFunction(self, function, aiId, parameters):
		returnValue = self.apiDemux.performGameFunction(function, aiId, parameters)
		if returnValue == None:
			return Variant()
		else:
			return Variant(returnValue)
	
	#Executes a view API call
	def callViewFunction(self, function, parameters):
		self.framework.callViewFunction(ApiCall(function, parameters))
	
	#Executes the only AI for the given phase
	def callAiFunction(self, aiId, phase):
		self.framework.callAiFunction(aiId, ApiCall(phase, 0))
	
	def setFrame(self):
		self.framework.setFrame()
	
	def setScore(self, aiId, score):
		self.framework.setScore(aiId, score)
	
	def incrementScore(self, aiId, score):
		self.framework.incrementScore(aiId, score)
	
	def setColor(self, aiId, color):
		self.framework.setColor(aiId, color)
	
	def stopAi(self, aiId):
		self.framework.stopAi(aiId)
	
	def throwException(self, message):
		self.framework.throwException(message)
	