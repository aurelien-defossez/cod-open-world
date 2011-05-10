#include "Map.h"

Map::Map()
{
    currentId = 0;
    nbSourceMiner = 0;
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
    std::pair<int,int> pos;
    pos.first = x;
    pos.second = y;
    SugarDrop *sugarDrop = new SugarDrop(pos, currentId, SUGAR_DROP, quantity);
    currentId++;
    addEntity(sugarDrop);
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
}

void Map::addEntity(Entity *entity)
{
    mapIds[entity->getId()] = entity;
    mapPositions.insert(std::pair<std::pair<int, int>, Entity*>(entity->getPosition(), entity));
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
}

std::pair<int,int> Map::getValidSquare(int x, int y, int distance)
{
    std::vector<std::pair<int,int> > validSquares;

    for (int i = x - distance; i <= x + distance; i++)
    {
        for (int j = y - distance; j <= y + distance; j++)
        {
            if ((contains(i, j)) && (verifyPosition(x,y)))
            {
                validSquares.push_back(std::pair<int, int>(i, j));
            }
        }
    }
    if (validSquares.size() == 0)
    {
        return std::pair<int,int>(-1,-1);
    }
    srand(time(NULL));
    int randSquare = (int)((double)rand() / ((double)RAND_MAX) * (validSquares.size()+1));
    return validSquares[randSquare];
}


void Map::setWidth(int w)
{
    width = w;
}

void Map::setHeight(int h)
{
    height = h;
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
            mapWalls.insert(std::pair<int,int>(i,j));
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
    return (mapWalls.find(std::pair<int, int>(x, y)) != mapWalls.end());
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

bool Map::canHit(Fruit* fruit, Fruit* target)
{
	if (fruit->maximumOffset(target) > fruit->getRange())
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

bool Map::detectObstacle(std::vector<std::pair<int,int> > positions)
{
	for (int i=0; i<positions.size(); i++)
	{
		if (mapWalls.find(positions[i]) != mapWalls.end())
		{
			return true;
		}
		else
		{
			std::map<std::pair<int,int>, Entity*>::iterator it;
			it = mapPositions.find(positions[i]);
			if (it != mapPositions.end())
			{
				Entity* entity = getEntity(it->second->getId());
				return entity->isObstacle();
			}
		}
	}
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
        out << it->second->getType();
        str += "\nmapIds - "+out.str();
        out.str("");
    }
    */

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

    /*
    std::set<std::pair<int,int> >::iterator it;
    for(it = mapWalls.begin(); it != mapWalls.end(); ++it)
    {
        out << it->first;;
        str += "\nmapWalls - "+out.str();
        out.str("");
        out << it->second;;
        str += "/"+out.str();
        out.str("");
    }
    */
    return str;
}
