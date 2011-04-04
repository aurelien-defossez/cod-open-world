from java.lang import Boolean
from java.lang import Integer
from java.lang import Double
from java.lang import String
from jarray import array
from com import Variant

#Phases
PHASE_INIT = 2
PHASE_REINIT = 0
PHASE_PLAY = 1

#Contants
LEFT = 0
RIGHT = 1
UP = 2
DOWN = 3

class ApiCallDemux(object):
	api = None
	
	#API functions
	API_GET_MAP_SIZE = 1
	API_GET_POSITION = 2
	API_MOVE = 3
	API_PEEK = 4
	API_TEST = 5
	
	def init(self, api):
		self.api = api
	
	def performGameFunction(self, function, aiId, parameters):
		#Get map size
		if function == self.API_GET_MAP_SIZE:
			return array(self.api.getMapSize(aiId), 'i')
		
		#Get map position
		elif function == self.API_GET_POSITION:
			return array(self.api.getPosition(aiId), 'i')
		
		#Move
		elif function == self.API_MOVE:
			return Boolean(self.api.move(aiId, parameters[0]))
		
		#Peek
		elif function == self.API_PEEK:
			return Double(self.api.peek(aiId))
		
		#Test
		elif function == self.API_TEST:
			return Integer(self.api.test(aiId, parameters[0]))
