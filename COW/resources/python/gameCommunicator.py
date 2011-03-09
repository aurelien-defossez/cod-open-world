from com.python import PyGameCommunicator
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
	
	def initGame(self):
		self.game.initGame(self, ViewApi(self))
	
	def addAi(self, aiId, playerName, aiName):
		self.game.addAi(aiId, playerName, aiName)
	
	def removeAi(self, aiId, reason):
		self.game.removeAi(aiId, reason)
	
	#Ends the game
	def endGame(self):
		self.game.endGame()
	
	#Plays a turn
	def play(self):
		self.game.play()
	
	#Executes an game API call
	def callGameApi(self, function, aiId, parameters):
		returnValue = self.apiDemux.callGameApi(function, aiId, parameters)
		if returnValue == None:
			return Variant()
		else:
			return Variant(returnValue)
	
	#Executes a view API call
	def callViewApi(self, function, parameters):
		self.framework.callViewApi(ApiCall(function, parameters))
	
	#Executes the only AI for the given phase
	def executeAi(self, aiId, phase):
		self.framework.executeAi(aiId, phase)
	
	def setFrame(self):
		self.framework.setFrame()
	
	def setScore(self, aiId, score):
		self.framework.setScore(aiId, score)
	
	def incrementScore(self, aiId, score):
		self.framework.incrementScore(aiId, score)
	