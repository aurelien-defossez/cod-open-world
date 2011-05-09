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
	
	def displayGrid(self, x0, y0, x1, y1, xSpacing, ySpacing, color, temporary):
		self.factory.initParameters(8)
		self.factory.addInt(x0)
		self.factory.addInt(y0)
		self.factory.addInt(x1)
		self.factory.addInt(y1)
		self.factory.addInt(xSpacing)
		self.factory.addInt(ySpacing)
		self.factory.addInt(color)
		self.factory.addBool(temporary)
		self.factory.call(View.DISPLAY_GRID)
	
	def drawLine(self, x0, y0, x1, y1, color, temporary):
		self.factory.initParameters(6)
		self.factory.addInt(x0)
		self.factory.addInt(y0)
		self.factory.addInt(x1)
		self.factory.addInt(y1)
		self.factory.addInt(color)
		self.factory.addBool(temporary)
		self.factory.call(View.DRAW_LINE)
	
	def drawCircle(self, x, y, radius, samples, color, temporary):
		self.factory.initParameters(6)
		self.factory.addInt(x)
		self.factory.addInt(y)
		self.factory.addInt(radius)
		self.factory.addInt(samples)
		self.factory.addInt(color)
		self.factory.addBool(temporary)
		self.factory.call(View.DRAW_CIRCLE)
	
	def deleteTemporaryShapes(self):
		self.factory.initParameters(0)
		self.factory.call(View.DELETE_TEMPORARY_SHAPES)
	
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
		self.factory.addInt(dx)
		self.factory.addInt(dy)
		self.factory.call(View.MOVE_ENTITY)
	
	def rotateEntity(self, id, angle):
		self.factory.initParameters(2)
		self.factory.addInt(id)
		self.factory.addInt(angle)
		self.factory.call(View.ROTATE_ENTITY)
	
	