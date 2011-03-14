from java.lang import Boolean
from java.lang import Integer
from java.lang import Double
from java.lang import String
from jarray import array
from com import Variant

#Phases
PHASE_TEST_BOOL = 1
PHASE_TEST_INT = 2
PHASE_TEST_DOUBLE = 3
PHASE_TEST_STRING = 4
PHASE_TEST_ARRAY = 5
PHASE_TEST_VIEW = 6
PHASE_TEST_TURN = 7

class ApiCallDemux(object):
	api = None
	
	#API functions
	API_XOR = 01
	API_PLUS_PLUS = 11
	API_DIVIDE_10 = 21
	API_CONCAT = 31
	API_SUM = 41
	API_COUNT = 42
	API_PRINT_TEXT = 51
	
	def init(self, api):
		self.api = api
	
	def performGameFunction(self, function, aiId, parameters):
		#Xor
		if function == self.API_XOR:
			return Boolean(self.api.xor(aiId, parameters[0], parameters[1]))
		
		#++
		elif function == self.API_PLUS_PLUS:
			return Integer(self.api.plusplus(aiId, parameters[0]))
		
		#/10
		elif function == self.API_DIVIDE_10:
			return Double(self.api.divide10(aiId, parameters[0]))
		
		#Concat
		elif function == self.API_CONCAT:
			return String(self.api.concat(aiId, parameters[0], parameters[1]))
		
		#Sum
		elif function == self.API_SUM:
			return Integer(self.api.sum(aiId, parameters[0]))
		
		#Count
		elif function == self.API_COUNT:
			return array(self.api.count(aiId, parameters[0]), Integer)
		
		#Print
		elif function == self.API_PRINT_TEXT:
			self.api.printText(aiId, parameters[0])
