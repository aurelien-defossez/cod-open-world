#include "Map.h"
#include <queue>
#include <cmath>

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
    nbSourceMiner = 0;
	commander = commanderE;
	countFruits = 0;
	aStarInitialized = false;
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
	
	// Delete Pathfinding matrices
	if(aStarInitialized)
	{
		for(int i = 0; i < height; i++)
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
            addSugarDrop(position.first, position.second, QUANTITY_SUGAR_EJECTED);
			count++;
        } else {
			break;
		}
    }
}
IntMatrix2 Map::getArchitecture()
{
	IntMatrix2 architecture = IntMatrix2(width, height);
	//Ajout des murs et des vides
	for (int i=0; i<width; i++)
	{
		for (int j=0; j<height; j++)
		{
			if (mapWalls[i,j])
			{
				architecture[i][j] = WALL;
			}
			else
			{
				architecture[i][j] = NOTHING;
			}
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
		architecture[x][y] = BUILDING_FRUCTIFICATION_TANK;
		building = getEntity(listPlayers[i]->getIdSugarBowl());
		x = building->getPosition().first;
		y = building->getPosition().second;
		architecture[x][y] = BUILDING_SUGAR_BOWL;
		building = getEntity(listPlayers[i]->getIdJuiceBarrel());
		x = building->getPosition().first;
		y = building->getPosition().second;
		architecture[x][y] = BUILDING_JUICE_BARREL;
    }
	//ajout des neutral buildings
	building = getEntity(idVitaminSource);
	x = building->getPosition().first;
	y = building->getPosition().second;
	architecture[x][y] = BUILDING_VITAMIN_SOURCE;
	building = getEntity(idSugarTree);
	x = building->getPosition().first;
	y = building->getPosition().second;
	architecture[x][y] = BUILDING_SUGAR_TREE;
	return architecture;
}
IntMatrix2 Map::getFruits(Player *owner)
{
	IntMatrix2 fruitMatrix = IntMatrix2(countFruits, 4);
	std::map<int,Entity* >::iterator it;
	int count = 0;
    for(it = mapIds.begin(); it != mapIds.end(); it++)
    {
        if ((it->second->getType() >= FRUIT_CHERRY) && (it->second->getType() <= FRUIT_NUT))
		{
			Fruit *fruit = (Fruit*)(it->second);
			if (fruit->getOwner() == owner)
			{
				fruitMatrix[count][OBJECT_ID] = it->second->getId();
				fruitMatrix[count][OBJECT_X] = it->second->getPosition().first;
				fruitMatrix[count][OBJECT_Y] = it->second->getPosition().second;
				fruitMatrix[count][OBJECT_TYPE] = it->second->getType();
				count++;
			}
		}
    }
	return fruitMatrix;
}
IntMatrix2 Map::getBuildings(Player *owner)
{
	IntMatrix2 buildingMatrix = IntMatrix2(3, 4);
	std::map<int,Entity* >::iterator it;
	int count = 0;
    for(it = mapIds.begin(); it != mapIds.end(); it++)
    {
        if ((it->second->getType() >= BUILDING_JUICE_BARREL) && (it->second->getType() <= BUILDING_FRUCTIFICATION_TANK))
		{
			OwnedBuilding *building = (OwnedBuilding*)(it->second);
			if (building->getOwner() == owner)
			{
				buildingMatrix[count][OBJECT_ID] = it->second->getId();
				buildingMatrix[count][OBJECT_X] = it->second->getPosition().first;
				buildingMatrix[count][OBJECT_Y] = it->second->getPosition().second;
				buildingMatrix[count][OBJECT_TYPE] = it->second->getType();
				count++;
			}
		}
    }
	return buildingMatrix;
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
	/*
	if (mapPositions.find(pos) != mapPositions.end())
	{
	  int size = mapPositions.count(pos);
	  for (int i=0; i<size; i++)
	  {
		
	  }
	}
	*/
    SugarDrop *sugarDrop = new SugarDrop(pos, currentId, SUGAR_DROP, quantity);
	commander->createEntity(73, currentId);
	commander->moveEntity(currentId, x, y);
    currentId++;
    addEntity(sugarDrop);
	/* TODO: envoyer une modification QUE si ya deja un tas de sucre, sinon c'est une nouvelle entité */
	int *modif = new int[5];
    modif[0] = currentId-1;
    modif[1] = x;
    modif[2] = y;
	modif[3] = SUGAR_DROP;
	modif[4] = quantity;
    addNewModification(modif);
	commander->setFrame();
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
		if (fruit->getOwner() == listPlayers[0])
		{
			countFruits++;
		}
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
	commander->moveEntity(entity->getId(), x-(entity->getPosition().first), y-(entity->getPosition().second));
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
	distances = new int*[height];
	visited = new bool*[height];
	mapWalls = new bool*[height];
	for(int i = 0; i < height; i++)
	{
		distances[i] = new int[width];
		visited[i] = new bool[width];
		mapWalls[i] = new bool[width];
		
		for(int j = 0; j < width; j++) {
			mapWalls[i][j] = false;
		}
	}
}

int Map::getWidth()
{
  return width;
}

int Map::getHeight()
{
  return height;
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
			commander->moveEntity(-1, i, j);
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
    return (mapIds[id]);
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
	commander->moveEntity(fruit->getId(), x, y);
    return fruit->getId();
}

bool Map::verifySource()
{
    if (nbSourceMiner == 3)
    {
        return false;
    }
    else
    {
        return true;
    }
}

void Map::addSourceMiner()
{
    nbSourceMiner++;
}

void Map::resetSourceMiner()
{
    nbSourceMiner = 0;
}

bool Map::canHit(Fruit* fruit, int range, Fruit* target)
{
	if (fruit->maximumOffset(target) > range)
	{
		std::cout << "maximumOffset" << std::endl;
	  return false;
	}
	else
	{
		std::vector<std::pair<int,int> > positions =
            drawLine(fruit->getPosition().first, fruit->getPosition().second,
                     target->getPosition().first, target->getPosition().second);
		if (detectObstacle(positions))
		{
			std::cout << "detectObstacle" << std::endl;
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
	
	// Initialize matrices
	for(int i = 0; i < height; i++)
	{
		for(int j = 0; j < width; j++)
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
					if(i != 0 && j != 0)
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
  resetSourceMiner();
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
  */
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
	
    return str;
}
