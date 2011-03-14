from java.lang import Boolean
from java.lang import Integer
from java.lang import Double
from java.lang import String
from jarray import zeros
from jarray import array
from com import Variant

class ApiCallFactory(object):
	connector = None
	parameters = None
	ctParameters = 0
	
	def __init__(self, connector):
		self.connector = connector
	
	def initParameters(self, nb):
		self.parameters = zeros(nb, Variant)
		self.ctParameters = 0
	
	def addBool(self, x):
		self.parameters[self.ctParameters] = Variant(Boolean(x))
		self.ctParameters += 1
	
	def addInt(self, x):
		self.parameters[self.ctParameters] = Variant(Integer(x))
		self.ctParameters += 1
	
	def addDouble(self, x):
		self.parameters[self.ctParameters] = Variant(Double(x))
		self.ctParameters += 1
	
	def addString(self, x):
		self.parameters[self.ctParameters] = Variant(String(x))
		self.ctParameters += 1
	
	def addBoolArray(self, values):
		self.parameters[self.ctParameters] = Variant(array(values, Boolean))
	
	def addIntArray(self, values):
		self.parameters[self.ctParameters] = Variant(array(values, Integer))
	
	def addDoubleArray(self, values):
		self.parameters[self.ctParameters] = Variant(array(values, Double))
	
	def addStringArray(self, values):
		self.parameters[self.ctParameters] = Variant(array(values, String))

class GameApiCallFactory(ApiCallFactory):
	
	def call(self, function):
		return self.connector.callGameFunction(function, self.parameters)

class ViewApiCallFactory(ApiCallFactory):
	
	def call(self, function):
		return self.connector.callViewFunction(function, self.parameters)
