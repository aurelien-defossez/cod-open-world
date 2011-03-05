from com.python import PyAiCommunicator
from apiCallFactory import GameApiCallFactory

class aiCommunicator(PyAiCommunicator):
	connector = None
	ai = None
	
	def initCommunicator(self, connector, ai):
		self.connector = connector
		self.ai = ai
		global communicator
		communicator = self
	
	def init(self):
		self.ai.init()
	
	def execute(self, phase):
		self.ai.execute(phase)
	
	def stop(self):
		self.ai.stop()
	
	def callGameApi(self, call):
		return self.connector.callGameApi(call).getValue()
