from apiCallFactory import ViewApiCallFactory
from view import View

class ViewApi(object):
	factory = 0
	
	def __init__(self, connector):
		self.factory = ViewApiCallFactory(connector)
	
	def printText(self, text):
		self.factory.initParameters(1)
		self.factory.addString(text)
		self.factory.call(View.PRINT_TEXT)
	
	def displayGrid(self, x0, y0, x1, y1, xSpacing, ySpacing, color):
		self.factory.initParameters(7)
		self.factory.addDouble(x0)
		self.factory.addDouble(y0)
		self.factory.addDouble(x1)
		self.factory.addDouble(y1)
		self.factory.addDouble(xSpacing)
		self.factory.addDouble(ySpacing)
		self.factory.addInt(color)
		self.factory.call(View.DISPLAY_GRID)
	
	def createEntity(self, definitionId, id):
		self.factory.initParameters(2)
		self.factory.addInt(definitionId)
		self.factory.addInt(id)
		self.factory.call(View.CREATE_ENTITY)
	
	def deleteEntity(self, id):
		self.factory.initParameters(1)
		self.factory.addInt(id)
		self.factory.call(View.DELETE_ENTITY)
	
	def moveEntity(self, id, dx, dy):
		self.factory.initParameters(3)
		self.factory.addInt(id)
		self.factory.addDouble(dx)
		self.factory.addDouble(dy)
		self.factory.call(View.MOVE_ENTITY)
	
	def rotateEntity(self, id, angle):
		self.factory.initParameters(2)
		self.factory.addInt(id)
		self.factory.addDouble(angle)
		self.factory.call(View.ROTATE_ENTITY)
		