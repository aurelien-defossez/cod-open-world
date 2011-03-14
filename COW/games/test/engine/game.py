import random

class Game(object):
	communicator = 0
	view = 0
	aiId = 0
	gameOver = False
	
	#Initialises the game
	def initGame(self, communicator, view):
		self.communicator = communicator
		self.view = view
		self.gameOver = False
	
	#Executes the only AI for the given phase
	def callAiFunction(self, phase):
		self.communicator.callAiFunction(self.aiId, phase)
	
	#Sets a key frame
	def setFrame(self):
		self.communicator.setFrame()
	
	#Sets the player score
	def setScore(self, score):
		self.communicator.setScore(self.aiId, score)
	
	#Adds an AI
	def addAi(self, aiId, playerName, aiName):
		self.aiId = aiId
	
	#Ends the game
	def endGame(self):
		print "Game over!"
		self.gameOver = True
	
	#Plays the game
	def play(self):
		#Game API tests
		self.callAiFunction(PHASE_TEST_BOOL)
		self.setFrame()
		self.callAiFunction(PHASE_TEST_INT)
		self.setFrame()
		self.callAiFunction(PHASE_TEST_DOUBLE)
		self.setFrame()
		self.callAiFunction(PHASE_TEST_STRING)
		self.setFrame()
		self.callAiFunction(PHASE_TEST_ARRAY)
		self.setFrame()
		self.callAiFunction(PHASE_TEST_VIEW)
		self.setFrame()
		
		#Turn test
		for i in range(100):
			self.callAiFunction(PHASE_TEST_TURN)
			self.printText("Turn "+str(i + 1))
			self.setScore(i)
			self.setFrame()
			
			if self.gameOver:
				break
	
	def xor(self, a, b):
		self.printText(str(a)+" xor "+str(b)+" = "+str((a and not b) or (not a and b)))
		return ((a and not b) or (not a and b))
	
	def plusplus(self, x):
		self.printText(str(x)+"++ = "+str(x + 1))
		return x + 1
	
	def divide10(self, x):
		self.printText(str(x)+" / 10 = "+str(x / 10))
		return x / 10
	
	def concat(self, s1, s2):
		self.printText(s1+"."+s2+" = "+s1+s2)
		return s1 + s2
	
	def sum(self, values):
		self.printText("sum("+str(values)+") = "+str(sum(values)))
		return sum(values)
	
	def count(self, nb):
		values = []
		for i in range(1, nb+1):
			values.append(i)
		
		self.printText("count("+str(nb)+") = "+str(values))
		return values
	
	def printText(self, text):
		self.view.printText(text+"\n")
	