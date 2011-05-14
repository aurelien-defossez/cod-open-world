#include "Map.h"
#include <queue>
#include <cmath>
#include <stdlib.h>
#include <algorithm>
#include "Building.h"
#include "OwnedBuilding.h"
#include "SugarDrop.h"
#include <iostream>
#include <time.h>
#include <fstream>
#include "Chest.h"

using namespace std;

struct AStarNode
{
	pair<int, int> pos;
	int cost;
	int estimated_cost;

	AStarNode(int i, int j, int c, int ec) :
		pos(make_pair(i, j)),
		cost(c),
		estimated_cost(ec)
	{
		// Do nothing
	}
	
	AStarNode(pair<int, int> p, int c, int ec) :
		pos(p),
		cost(c),
		estimated_cost(ec)
	{
		// Do nothing
	}
	
	bool operator>(const AStarNode& rhs) const
	{
		return estimated_cost > rhs.estimated_cost;
	}
};

typedef priority_queue<AStarNode, vector<AStarNode>, greater<AStarNode> >
				PriorityQueue;

Map::Map(SpecificCommander *commanderE)
{
	currentId = 0;
	dropping = false;
	commander = commanderE;
	countFruits = 0;
	aStarInitialized = false;
	objectsDropped = NULL;
	sugarUpdated = NULL;
}

Map::~Map()
{
	std::map<int,Entity* >::iterator it;
	for(it = mapIds.begin(); it != mapIds.end(); ++it)
	{
		delete it->second;
	}
	
	for(int i=0; i<listPlayers.size(); i++)
	{
		delete listPlayers[i];
	}
	if (architecture != NULL)
	{
	  delete architecture;
	}
	if (sugarUpdated != NULL)
	{
	  delete sugarUpdated;
	}
	if (objectsDropped != NULL)
	{
	  delete objectsDropped;
	}
	// Delete Pathfinding matrices
	if(aStarInitialized)
	{
		for(int i = 0; i < width; i++)
		{
			delete[] distances[i];
			delete[] visited[i];
			delete[] mapWalls[i];
		}
		
		delete[] distances;
		delete[] visited;
		delete[] mapWalls;
	}
}
void Map::setLimitCherry(int lim)
{
	limitCherry = lim;
}

void Map::setLimitKiwi(int lim)
{
	limitKiwi = lim;
}

void Map::setLimitNut(int lim)
{
	limitNut = lim;
}

int Map::getLimitCherry()
{
	return limitCherry;
}

int Map::getLimitKiwi()
{
	return limitKiwi;
}

int Map::getLimitNut()
{
	return limitNut;
}


bool Map::verifyNbFruit(int fruitType, Player *owner)
{
	if (fruitType == FRUIT_CHERRY)
	{
		return (owner->getCountCherry() < limitCherry);
	}
	if (fruitType == FRUIT_KIWI)
	{
		return (owner->getCountKiwi() < limitKiwi);
	}
	if (fruitType == FRUIT_NUT)
	{
		return (owner->getCountNut() < limitNut);
	}
}
void Map::dropSugarRandomly()
{
    int count = 0;
	Entity *sugarTree = getEntity(idSugarTree);
    while (count < COUNT_NEW_SUGAR_DROP)
    {
        Position position = getValidSquare(sugarTree->getPosition().first, sugarTree->getPosition().second, DISTANCE_OF_EJECTION);
		
		if (position.first != -1)
        {
            addSugarDrop(position.first, position.second, sugarEjected);
			count++;
        } else {
			break;
		}
    }
}

void Map::distributePossessions(int x, int y, Entity* entity)
{
	objectsDroppedVector.clear();
	if (entity->getType() == CHEST)
	{
		Chest *chest = (Chest*) entity;
		
		for (int i=0; i<chest->getListEquipment().size(); i++)
		{
			Position position = getValidSquare(x, y, 1);
			
			std::vector<Equipment*> liste = chest->getListEquipment();
			if (position.first != -1)
			{
				chest->getListEquipment()[i]->setPosition(position.first, position.second);
				addEntity(chest->getListEquipment()[i]);
				commander->createEntity((liste[i]->getType()+41),liste[i]->getId());
				commander->moveEntity(liste[i]->getId(), 2 * position.first, 2 * position.second);
				int *newObject = new int[5];
				newObject[0] = liste[i]->getId();
				newObject[1] = liste[i]->getPosition().first;
				newObject[2] = liste[i]->getPosition().second;
				newObject[3] = liste[i]->getType();
				newObject[4] = liste[i]->getAmmo();
				objectsDroppedVector.push_back(newObject);
			} else {
				break;
			}
		}
	}
	else //fruit
	{
		Fruit* fruit = (Fruit*) entity;
		for (int i=0; i<fruit->getListEquipment().size(); i++)
		{
			Position position = getValidSquare(x, y, 1);
			
			std::vector<Equipment*> liste = fruit->getListEquipment();
			if (position.first != -1)
			{
				fruit->getListEquipment()[i]->setPosition(position.first, position.second);
				addEntity(fruit->getListEquipment()[i]);
				commander->createEntity((liste[i]->getType()+41),liste[i]->getId());
				commander->moveEntity(liste[i]->getId(), 2 * position.first, 2 * position.second);
				int *newObject = new int[5];
				newObject[0] = liste[i]->getId();
				newObject[1] = liste[i]->getPosition().first;
				newObject[2] = liste[i]->getPosition().second;
				newObject[3] = liste[i]->getType();
				newObject[4] = liste[i]->getAmmo();
				objectsDroppedVector.push_back(newObject);
			} else {
				break;
			}
		}
		int quantity = fruit->getSugar();
		int count = (int)((double)rand() / ((double)RAND_MAX) * 4 +1);
		int capacity; 
		dropping = true;
		sugarUpdatedVector.clear();
		for (int i=0; i<count; i++)
		{
			if (quantity == 0)
			{
			  break;
			}
			capacity = (int)((double)rand() / ((double)RAND_MAX) * quantity+1);
			
			Position position = getValidSquare(x, y, 1);
			
			if (position.first != -1)
			{
				if (i == count-1)
				{
				  addSugarDrop(position.first, position.second, quantity);
				} else {
				  addSugarDrop(position.first, position.second, capacity);
				  quantity -= capacity;
				}
			} else {
				break;
			}
		}
		dropping = false;
	}
}

IntMatrix2* Map::getObjectsDropped()
{
	if (objectsDropped != NULL)
	{
	  delete objectsDropped;
	}
	objectsDropped = new IntMatrix2(objectsDroppedVector.size(), 5);
	for (int i = 0; i < objectsDroppedVector.size(); i++)
    {
		objectsDropped->at(i,OBJECT_ID) = objectsDroppedVector[i][0];
		objectsDropped->at(i,OBJECT_X) = objectsDroppedVector[i][1];
		objectsDropped->at(i,OBJECT_Y) = objectsDroppedVector[i][2];
		objectsDropped->at(i,OBJECT_TYPE) = objectsDroppedVector[i][3];
		objectsDropped->at(i,OBJECT_INFO) = objectsDroppedVector[i][4];
    }
	return objectsDropped;
}

IntMatrix2* Map::getSugarUpdated()
{
	if (sugarUpdated != NULL)
	{
	  delete sugarUpdated;
	}
	sugarUpdated = new IntMatrix2(sugarUpdatedVector.size(), 2);
	for (int i = 0; i < sugarUpdatedVector.size(); i++)
    {
		sugarUpdated->at(i,OBJECT_ID) = sugarUpdatedVector[i][0];
		sugarUpdated->at(i,OBJECT_SUGAR) = sugarUpdatedVector[i][1];
    }
	return sugarUpdated;
}

IntMatrix2* Map::getArchitecture()
{
	architecture = new IntMatrix2(width, height);
	//Ajout des murs et des vides
	for (int i=0; i<width; i++)
	{
		for (int j=0; j<height; j++)
		{
			architecture->at(i, j) = (mapWalls[i][j]) ? WALL : NOTHING;
		}
	}
	
	Entity *building;
	int x;
	int y;
	//Ajout des owned buildings
	for(int i=0; i<listPlayers.size(); i++)
    {
        building = getEntity(listPlayers[i]->getIdFructificationTank());
		x = building->getPosition().first;
		y = building->getPosition().second;
		architecture->at(x, y) = BUILDING_FRUCTIFICATION_TANK;
		
		building = getEntity(listPlayers[i]->getIdSugarBowl());
		x = building->getPosition().first;
		y = building->getPosition().second;
		architecture->at(x, y) = BUILDING_SUGAR_BOWL;
		
		building = getEntity(listPlayers[i]->getIdJuiceBarrel());
		x = building->getPosition().first;
		y = building->getPosition().second;
		architecture->at(x, y) = BUILDING_JUICE_BARREL;
    }
	//ajout des neutral buildings
	building = getEntity(idVitaminSource);
	x = building->getPosition().first;
	y = building->getPosition().second;
	architecture->at(x, y) = BUILDING_VITAMIN_SOURCE;
	
	building = getEntity(idSugarTree);
	x = building->getPosition().first;
	y = building->getPosition().second;
	architecture->at(x, y) = BUILDING_SUGAR_TREE;
	
	return architecture;
}


void Map::addNewModification(int *newModif)
{
    for(int i=0; i<listPlayers.size(); i++)
    {
        if (!(listPlayers[i]->isCurrentPlayer()))
        {
            listPlayers[i]->addNewModification(newModif);
        }
    }
}

void Map::addUpdatedModificationObject(int *newModif)
{
    for(int i=0; i<listPlayers.size(); i++)
    {
        if (!(listPlayers[i]->isCurrentPlayer()))
        {
            listPlayers[i]->addUpdatedModificationObject(newModif);
        }
    }
}

void Map::addUpdatedModificationSugar(int *newModif)
{
    for(int i=0; i<listPlayers.size(); i++)
    {
        if (!(listPlayers[i]->isCurrentPlayer()))
        {
            listPlayers[i]->addUpdatedModificationSugar(newModif);
        }
    }
}

void Map::addDeletedModification(int *newModif)
{
    for(int i=0; i<listPlayers.size(); i++)
    {
        if (!(listPlayers[i]->isCurrentPlayer()))
        {
            listPlayers[i]->addDeletedModification(newModif);
        }
    }
}

void Map::addMovedModification(int *newModif)
{
    for(int i=0; i<listPlayers.size(); i++)
    {
        if (!(listPlayers[i]->isCurrentPlayer()))
        {
            listPlayers[i]->addMovedModification(newModif);
        }
    }
}

void Map::addSugar(Player *player, int quantity)
{
    player->addSugar(quantity);
}

int Map::addSugarDrop(int x, int y, int quantity)
{
    Position pos;
    pos.first = x;
    pos.second = y;

	
    std::multimap<std::pair<int,int>, Entity* >::iterator it;
	std::pair<std::multimap<std::pair<int,int>, Entity* >::iterator,std::multimap<std::pair<int,int>, Entity* >::iterator> range;
    range = mapPositions.equal_range(pos);
    for(it = range.first; it != range.second; ++it)
    {
        if (it->second->getType() == SUGAR_DROP)
        {
          SugarDrop *sugarDrop = (SugarDrop*)(it->second);
		  sugarDrop->addSugar(quantity);
		  int *modif = new int[2];
		  modif[0] = sugarDrop->getId();
		  modif[1] = sugarDrop->getCapacity();
		  addUpdatedModificationSugar(modif);
		  if (dropping)
		  {
			  sugarUpdatedVector.push_back(modif);
		  }
		  return sugarDrop->getId();
        }
    }
    
    SugarDrop *sugarDrop = new SugarDrop(pos, currentId, SUGAR_DROP, quantity);
	commander->createEntity(73, currentId);
	commander->moveEntity(currentId, 2 * x, 2 * y);
    currentId++;
    addEntity(sugarDrop);
	
	int *modif = new int[5];
    modif[0] = currentId-1;
    modif[1] = x;
    modif[2] = y;
	modif[3] = SUGAR_DROP;
	modif[4] = quantity;
    addNewModification(modif);
	if (dropping)
	{
		objectsDroppedVector.push_back(modif);
	}
    return sugarDrop->getId();
}

void Map::removeEntity(Entity *entity)
{
    std::multimap<std::pair<int,int>, Entity* >::iterator it;
    for(it = mapPositions.find(entity->getPosition()); it != mapPositions.end(); ++it)
    {
        if(it->second == entity)
        {
            mapPositions.erase(it);
            break;
        }
    }
    mapIds.erase(mapIds.find(entity->getId()));
	commander->deleteEntity(entity->getId());
}

void Map::addEntity(Entity *entity)
{
    mapIds[entity->getId()] = entity;
    mapPositions.insert(std::pair<std::pair<int, int>, Entity*>(entity->getPosition(), entity));
	if ((entity->getType() >= FRUIT_CHERRY) && (entity->getType() <= FRUIT_NUT))
	{
		Fruit *fruit = (Fruit*) entity;
		int *infos = new int[4];
		infos[0] = fruit->getId();
		infos[1] = fruit->getPosition().first;
		infos[2] = fruit->getPosition().second;
		infos[3] = fruit->getType();
		fruit->getOwner()->addToListFruits(infos);
	}
	else if ((entity->getType() >= BUILDING_JUICE_BARREL) && (entity->getType() <= BUILDING_FRUCTIFICATION_TANK))
	{
		OwnedBuilding *building = (OwnedBuilding*) entity;
		if (entity->getType() == BUILDING_JUICE_BARREL)
		{
		  building->getOwner()->setIdJuiceBarrel(building->getId());
		}
		else if (entity->getType() == BUILDING_SUGAR_BOWL)
		{
		  building->getOwner()->setIdSugarBowl(building->getId());
		}
		else if (entity->getType() == BUILDING_FRUCTIFICATION_TANK)
		{
		  building->getOwner()->setIdFructificationTank(building->getId());
		}
		int *infos = new int[4];
		infos[0] = building->getId();
		infos[1] = building->getPosition().first;
		infos[2] = building->getPosition().second;
		infos[3] = building->getType();
		building->getOwner()->addToListBuildings(infos);
	}
	else if (entity->getType() == BUILDING_VITAMIN_SOURCE)
	{
		idVitaminSource = entity->getId();
	}
	else if (entity->getType() == BUILDING_SUGAR_TREE)
	{
		idSugarTree = entity->getId();
	}
}

void Map::moveEntity(Entity *entity, int x, int y)
{
    std::pair<int,int> pos;
    pos.first = x;
    pos.second = y;
    std::multimap<std::pair<int,int>, Entity* >::iterator it;
    for(it = mapPositions.find(entity->getPosition()); it != mapPositions.end(); ++it)
    {
        if(it->second == entity)
        {
            mapPositions.erase(it);
            break;
        }
    }
    mapPositions.insert(std::pair<std::pair<int, int>, Entity*>(std::pair<int,int>(x,y), entity));
	commander->moveEntity(entity->getId(), 2 * (x-(entity->getPosition().first)), 2 * (y-(entity->getPosition().second)));
}

std::pair<int,int> Map::getValidSquare(int x, int y, int distance)
{
    std::vector<std::pair<int,int> > validSquares;

    for (int i = x - distance; i <= x + distance; i++)
    {
        for (int j = y - distance; j <= y + distance; j++)
        {
            if (!checkObstacle(i,j))
            {
                validSquares.push_back(std::pair<int, int>(i, j));
            }
        }
    }
    if (validSquares.size() == 0)
    {
        return std::pair<int,int>(-1,-1);
    }
    
    int randSquare = (int)((double)rand() / ((double)RAND_MAX) * (validSquares.size()));
    return validSquares[randSquare];
}

void Map::setDimensions(int h, int w)
{
    height = h;
    width = w;
	
	// Initialise l'algo de Pathfinding
	aStarInitialized = true;
	distances = new int*[width];
	visited = new bool*[width];
	mapWalls = new bool*[width];
	for(int i = 0; i < width; i++)
	{
		distances[i] = new int[height];
		visited[i] = new bool[height];
		mapWalls[i] = new bool[height];
		
		for(int j = 0; j < height; j++) {
			mapWalls[i][j] = false;
		}
	}
}

void Map::setConfig(int maxVitaminsE, int sugarEjectedE, int nbToursE)
{
	maxVitamins = maxVitaminsE;
	sugarEjected = sugarEjectedE;
	nbTours = nbToursE;
}

int Map::getWidth()
{
  return width;
}

int Map::getHeight()
{
  return height;
}

int Map::getMaxVitamins()
{
  return maxVitamins;
}

int Map::getNbTours()
{
  return nbTours;
}

void Map::setCurrentId(int id)
{
    currentId = id;
}

std::vector<Player*> Map::getListPlayers()
{
    return listPlayers;
}

void Map::createWalls(int x0, int y0, int x1, int y1)
{
	for (int i=x0; i<=x1; i++)
    {
        for (int j=y0; j<=y1; j++)
        {
			mapWalls[i][j] = true;
			commander->createEntity(80, -1);
			commander->moveEntity(-1, 2 * i, 2 * j);
        }
    }
}


void Map::createPlayers(int nbPlayers)
{
    for (int i=0; i<nbPlayers; i++)
    {
        Player *player = new Player();
        listPlayers.push_back(player);
    }
}

int Map::verifyId(int id)
{
	if (mapIds.find(id) != mapIds.end())
    {
        return (mapIds[id]->getType());
    }
    return -1;
}

bool  Map::contains(int x, int y)
{
    if ((x >= 0) && (x <= width) && (y >= 0) && (y <= height))
    {
        return true;
    }
    return false;
}

bool Map::isWall(int x, int y)
{
    return mapWalls[x][y];
}

bool  Map::verifyPosition(int x, int y)
{
    // if it's a wall
    if (isWall(x,y))
    {
	  return false;
    }
    std::multimap<std::pair<int,int>, Entity* >::iterator it;
    std::pair<std::multimap<std::pair<int,int>, Entity* >::iterator,std::multimap<std::pair<int,int>, Entity* >::iterator> range;
    range = mapPositions.equal_range(std::pair<int,int>(x,y));
    for(it = range.first; it != range.second; ++it)
    {
        int type = it->second->getType();
		 
        if ((type == CHEST) ||
            ((type >= FRUIT_CHERRY) && (type <= FRUIT_NUT)) ||
            ((type >= BUILDING_VITAMIN_SOURCE) && (type <= BUILDING_FRUCTIFICATION_TANK)))
        {
            
		  return false;
        }
    }
    // case = NOTHING
    return true;
}

Entity* Map::getEntity(int id)
{
	std::map<int, Entity*>::iterator it = mapIds.find(id);
	return (it == mapIds.end()) ? NULL : it->second;
}

bool Map::verifyBuilding(Fruit *fruit, int buildingType)
{
    Entity *building;
    if (buildingType == BUILDING_VITAMIN_SOURCE)
    {
        building = getEntity(idVitaminSource);
    }
    else if (buildingType == BUILDING_FRUCTIFICATION_TANK)
    {
        building = getEntity(fruit->getOwner()->getIdFructificationTank());
    }
    else if (buildingType == BUILDING_JUICE_BARREL)
    {
        building = getEntity(fruit->getOwner()->getIdJuiceBarrel());
    }
    else //sugar bowl
    {
        building = getEntity(fruit->getOwner()->getIdSugarBowl());
    }
    return fruit->isNearby(building);
}

Equipment* Map::createEquipment(int equipmentType, int x, int y)
{
    std::pair<int,int> pos;
    pos.first = x;
    pos.second = y;
    Equipment *equipment = new Equipment(pos, currentId, equipmentType);
    currentId++;
    //ajouter à MapModifications
    return equipment;
}

int Map::createFruit(int fruitType, int x, int y, Player *owner)
{
    std::pair<int,int> pos;
    pos.first = x;
    pos.second = y;
    Fruit *fruit = new Fruit(pos, currentId, fruitType, owner);
    currentId++;
    addEntity(fruit);
	commander->createEntity((fruitType-6+10*(owner->getId())),fruit->getId());
	commander->moveEntity(fruit->getId(), 2 * x, 2 * y);
    return fruit->getId();
}

bool Map::canHit(Fruit* fruit, int range, Fruit* target)
{
	if (fruit->maximumOffset(target) > range)
	{
	  return false;
	}
	else
	{
		std::vector<std::pair<int,int> > positions =
            drawLine(fruit->getPosition().first, fruit->getPosition().second,
                     target->getPosition().first, target->getPosition().second);
		if (detectObstacle(positions))
		{
		  return false;
		}
	}
	return true;
}

bool Map::detectObstacle(std::vector<Position> positions)
{
	for (int i=0; i<positions.size(); i++)
	{
		Position p = positions[i];
		if (mapWalls[p.first][p.second])
		{
			return true;
		}
		else
		{
			std::map<Position, Entity*>::iterator it;
			it = mapPositions.find(p);
			if (it != mapPositions.end())
			{
				Entity* entity = getEntity(it->second->getId());
				return entity->isObstacle();
			}
		}
	}
}

bool Map::checkObstacle(int x, int y)
{
	// Outside map
	if(x < 0 || x >= height || y < 0 || y >= width)
	{
		return true;
	}
	
	// Create position
	pair<int, int> pos = make_pair(x, y);
	
	// Is wall
	if(mapWalls[x][y])
	{
		return true;
	}
	
	// Check for obstacles
	pair<multimap<pair<int,int>, Entity* >::iterator,
			 multimap<pair<int,int>, Entity* >::iterator>
	  range = mapPositions.equal_range(pos);
	multimap<pair<int,int>, Entity* >::iterator it;
	for(it = range.first; it != range.second; it++)
	{
		// Entity bloquing the way
		if(it->second->isObstacle())
		{
			return true;
		}
	}
	
	// Not an obstacle
	return false;
}

int Map::distanceBetween(Entity *entity, int x, int y, int maxDistance)
{
	pair<int, int> start = entity->getPosition();
	pair<int, int> goal = make_pair(x, y);
	int sx = start.first;
	int sy = start.second;
	PriorityQueue heap;
	
	cout << "distanceBetween " << start.first << ";" << start.second << " and " << x << ";" << y << endl;
	
	// Initialize matrices
	for(int i = 0; i < width; i++)
	{
		for(int j = 0; j < height; j++)
		{
			distances[i][j] = -1;
			visited[i][j] = false;
		}
	}
	
	// Push start node
	heap.push(AStarNode(sx, sy, 0, 0));
	distances[sx][sy] = 0;
	
	// While there are possibilities to try ...
	while(!heap.empty())
	{
		// Retrieve Node
		AStarNode current = heap.top();
		heap.pop();
		int cx = current.pos.first;
		int cy = current.pos.second;
		
		// Visit square
		if(!visited[cx][cy])
		{
			visited[cx][cy] = true;
			
			// Path found, returning path length
			if(current.pos == goal)
			{
				return current.cost;
			}
			
			// Add neighbours
			for(int i = -1; i <= 1; i++)
			{
				for(int j = -1; j <= 1; j++)
				{
					if(!(i == 0 && j == 0))
					{
						int tx = cx + i;
						int ty = cy + j;
						
						// If this square is empty
						if(!checkObstacle(tx, ty))
						{
							int cost = distances[cx][cy] + 1;
							
							// If this square wat not yet explored or has a better path
							if(!visited[tx][ty]
							&& (distances[tx][ty] < 0 || cost < distances[tx][ty]))
							{
								distances[tx][ty] = cost;
								
								// Add node only if interesting
								if(maxDistance == -1 || cost <= maxDistance)
								{
									int hCost = max(abs(x - tx), abs(y - ty)); 
									heap.push(AStarNode(tx, ty, cost, hCost));
								}
							}
						}
					}
				}
			}
		}
	}
	
	return -1;
}

std::vector<std::pair<int,int> > Map::drawLine(int x0, int y0, int x1, int y1)
{
	std::vector<std::pair<int, int> > squares = std::vector<std::pair<int, int> >();
	// Deltas
	int dx = abs(x1 - x0);
	int dy = abs(y1 - y0);

	// Signs
	int sx = (x0 < x1) ? 1 : -1;
	int sy = (y0 < y1) ? 1 : -1;

	// Error
	int err = dx - dy;

	// Bresenham loop
	while(x0 != x1 || y0 != y1) {
		int err2 = 2 * err;

		if(err2 > -dy) {
			err -= dy;
			x0 += sx;
		}

		if(err2 < dx) {
			err += dx;
			y0 += sy;
		}

		// Add square positions only between the two points
		if((x0 != x1) || (y0 != y1)) {
			squares.push_back(std::pair<int, int>(x0, y0));
		}
	}

	return squares;
}

int Map::getCurrentPlayer()
{
  for(int i=0; i<listPlayers.size(); i++)
  {
	  if (listPlayers[i]->isCurrentPlayer())
	  {
		  return i;
	  }
  }
}

void Map::endTurn()
{
  std::map<int,Entity* >::iterator it;
  for(it = mapIds.begin(); it != mapIds.end(); ++it)
  {
	  if ((it->second->getType() >= FRUIT_CHERRY) && (it->second->getType() <= FRUIT_NUT))
	  {
		Fruit *fruit = (Fruit*) it->second;
		fruit->resetAction();
	  }
  }
}

std::string Map::printC()
{
    std::string str;
    /*
    std::stringstream out;
    out << mapIds.size();
    str += "\nmapIdsSize - "+out.str();
    out.str("");
    out << mapPositions.size();
    str += "\nmapPosSize - "+out.str();
    out.str("");
  
    std::map<int,Entity* >::iterator it;
    for(it = mapIds.begin(); it != mapIds.end(); ++it)
    {
        std::cout << "\nmapIds - " << it->second->getId() << it->second->getType();
    }
    

    std::multimap<std::pair<int,int>, Entity* >::iterator it2;
    for(it2 = mapPositions.begin(); it2 != mapPositions.end(); ++it2)
    {
        std::cout << "\nType=";
        std::cout << it2->second->getType();
        std::cout << " - Id=";
        std::cout << it2->second->getId();
        std::cout << " - ";
        std::cout << it2->second->getPosition().first;
        std::cout << "/";
        std::cout << it2->second->getPosition().second;
    }
	*/
    return str;
}
