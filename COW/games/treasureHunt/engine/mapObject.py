import random
import math
from apiCallDemux import *

class MapObject(object):
	view = None
	x = 0
	y = 0
	id = 0
	
	def __init__(self, view, x, y):
		self.view = view
		self.x = x
		self.y = y
	
	def getPosition(self):
		return [self.x, self.y]
	
	def distanceTo(self, target):
		dx = self.x - target.x;
		dy = self.y - target.y;
		return math.sqrt(dx * dx + dy * dy);
	
	def delete(self):
		self.view.deleteEntity(self.id)

class Hunter(MapObject):
	def __init__(self, view, x, y, id):
		self.view = view
		self.x = x
		self.y = y
		self.id = id
		
		self.view.createEntity(1, self.id)
		self.view.moveEntity(self.id, 10 * self.x, 10 * self.y)
	
	def move(self, direction):
		if direction == UP:
			self.y = self.y + 1
			self.view.moveEntity(self.id, 0, 10)
			#self.view.printText("Move up to "+str(self.x)+"/"+str(self.y)+"\n")
		elif direction == DOWN:
			self.y = self.y - 1
			self.view.moveEntity(self.id, 0, -10)
			#self.view.printText("Move down to "+str(self.x)+"/"+str(self.y)+"\n")
		elif direction == LEFT:
			self.x = self.x - 1
			self.view.moveEntity(self.id, -10, 0)
			#self.view.printText("Move left to "+str(self.x)+"/"+str(self.y)+"\n")
		elif direction == RIGHT:
			self.x = self.x + 1
			self.view.moveEntity(self.id, 10, 0)
			#self.view.printText("Move right to "+str(self.x)+"/"+str(self.y)+"\n")

class Treasure(MapObject):
	def __init__(self, id, view, map):
		self.id = id
		self.view = view
		self.x = random.randint(0, map.getSize()[0] - 1)
		self.y = random.randint(0, map.getSize()[1] - 1)
		self.view.printText("Treasure created at "+str(self.x)+"/"+str(self.y)+"\n")
		self.view.createEntity(2, self.id)
		self.view.moveEntity(self.id, self.x * 10, self.y * 10)
