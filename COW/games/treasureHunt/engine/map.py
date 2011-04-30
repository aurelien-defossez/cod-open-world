from mapObject import *

class Map(object):
	map = None
	view = None
	w = 0
	h = 0
	hunters = {}
	treasures = {}
	nbTreasures = 4
	treasureId = 10
	found = False
	
	def __init__(self, view, w, h):
		self.view = view
		self.w = w
		self.h = h
		self.map = [[0 for col in range(h)] for row in range(w)]
		self.found = False
		
		self.treasures = [None for row in range(self.nbTreasures)]
		for i in range(len(self.treasures)):
			self.treasures[i] = Treasure(self.treasureId, self.view, self)
			self.treasureId = self.treasureId + 1
	
	def addHunter(self, hunterId):
		if len(self.hunters) == 0:
			startX = 0
			startY = 0
		elif len(self.hunters) == 1:
			startX = self.w - 1
			startY = self.h - 1
		elif len(self.hunters) == 2:
			startX = 0
			startY = self.h - 1
		elif len(self.hunters) == 3:
			startX = self.w - 1
			startY = 0
		else:
			startX = 0
			startY = 0
		
		self.hunters[hunterId] = Hunter(self.view, startX, startY, hunterId)
	
	def getSize(self):
		return [self.w, self.h]
	
	def getHunter(self, hunterId):
		return self.hunters[hunterId]
	
	def distanceToTreasure(self, hunter):
		minDistance = hunter.distanceTo(self.treasures[0])
		
		for treasure in self.treasures:
			minDistance = min(minDistance, hunter.distanceTo(treasure))
		
		return minDistance
	
	def peek(self, aiId):
		d = self.distanceToTreasure(self.getHunter(aiId))
		if d == 0:
			self.found = True
		
		return d
	
	def treasuresFound(self, aiId):
		if self.found:
			self.found = False
			nb = 0
			
			for i in range(len(self.treasures)):
				if self.getHunter(aiId).distanceTo(self.treasures[i]) == 0:
					self.treasures[i].delete()
					self.treasures[i] = Treasure(self.treasureId, self.view, self)
					self.treasureId = self.treasureId + 1
					nb = nb + 1
			
			return nb
		else:
			return 0
	