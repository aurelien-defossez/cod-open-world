#include "Player.h"

Player::Player()
{
    mapModifications = new MapModifications();
	matrixFruits = NULL;
	matrixBuildings = NULL;
    sugarQuantity = 0;
    vitaminsQuantity = 0;
    isCurrent = false;
}

void Player::setInfos(short aiId, char *aiName, char *playerName)
{
  namePlayer = playerName;
  nameIA = aiName;
  idIA = aiId;
}

void Player::addToListFruits(int *infos)
{
  listFruits.push_back(infos);
}

void Player::addToListBuildings(int *infos)
{
  listBuildings.push_back(infos);
}

IntMatrix2* Player::getMatrixFruits()
{
	if (matrixFruits != NULL)
	{
	  delete matrixFruits;
	}
	matrixFruits = new IntMatrix2(listFruits.size(), 5);
	for (int i = 0; i < listFruits.size(); i++)
    {
		matrixFruits->at(i,OBJECT_ID) = listFruits[i][0];
		matrixFruits->at(i,OBJECT_X) = listFruits[i][1];
		matrixFruits->at(i,OBJECT_Y) = listFruits[i][2];
		matrixFruits->at(i,OBJECT_TYPE) = listFruits[i][3];
		matrixFruits->at(i,OBJECT_INFO) = listFruits[i][4];
    }
	return matrixFruits;
}

IntMatrix2* Player::getMatrixBuildings()
{
	if (matrixBuildings != NULL)
	{
	  delete matrixBuildings;
	}
	matrixBuildings = new IntMatrix2(listBuildings.size(), 4);
	for (int i = 0; i < listBuildings.size(); i++)
    {
		matrixBuildings->at(i,OBJECT_ID) = listBuildings[i][0];
		matrixBuildings->at(i,OBJECT_X) = listBuildings[i][1];
		matrixBuildings->at(i,OBJECT_Y) = listBuildings[i][2];
		matrixBuildings->at(i,OBJECT_TYPE) = listBuildings[i][3];
    }
	return matrixBuildings;
}

bool Player::isCurrentPlayer()
{
    return isCurrent;
}

void Player::addNewModification(int *newModif)
{
    mapModifications->addNewModification(newModif);
}

void Player::addUpdatedModificationObject(int *newModif)
{
    mapModifications->addUpdatedModificationObject(newModif);
}

void Player::addUpdatedModificationSugar(int *newModif)
{
    mapModifications->addUpdatedModificationSugar(newModif);
}

void Player::addDeletedModification(int *newModif)
{
    mapModifications->addDeletedModification(newModif);
}

void Player::addMovedModification(int *newModif)
{
    mapModifications->addMovedModification(newModif);
}

void Player::addSugar(int nbSugar)
{
    sugarQuantity += nbSugar;
}

void Player::removeSugar(int nbSugar)
{
    sugarQuantity -= nbSugar;
}

void Player::addVitamins(int nbVitamins)
{
    vitaminsQuantity += nbVitamins;
}

void Player::removeVitamins(int nbVitamins)
{
    vitaminsQuantity -= nbVitamins;
}

int Player::hasEnough(int nbSugar, int nbVitamins)
{
    if (sugarQuantity < nbSugar)
    {
        return NOT_ENOUGH_SUGAR;
    }
    else if (vitaminsQuantity < nbVitamins)
    {
        return NOT_ENOUGH_VITAMIN;
    }
    else
    {
        return OK;
    }
}

int Player::getId()
{
  return idIA;
}

int Player::getIdSugarBowl()
{
    return idSugarBowl;
}

int Player::getIdFructificationTank()
{
    return idFructificationTank;
}

int Player::getIdJuiceBarrel()
{
    return idJuiceBarrel;
}

void Player::setIdSugarBowl(int id)
{
    idSugarBowl = id;
}

void Player::setIdFructificationTank(int id)
{
    idFructificationTank = id;
}

void Player::setIdJuiceBarrel(int id)
{
    idJuiceBarrel = id;
}

void Player::setCurrentPlayer(bool state)
{
    isCurrent = state;
}

void Player::resetMapModifications()
{
  mapModifications->reset();
}

void Player::setCounts(int countCherryE, int countKiwiE, int countNutE)
{
	countCherry = countCherryE;
	countKiwi = countKiwiE;
	countNut = countNutE;
}
int Player::getCountCherry()
{
	return countCherry;
}
int Player::getCountKiwi()
{
	return countKiwi;
}
int Player::getCountNut()
{
	return countNut;
}
IntMatrix2* Player::getNewObjects()
{
	return mapModifications->getNewObjects();
}
IntMatrix1* Player::getDeletedObjects()
{
	return mapModifications->getDeletedObjects();
}
IntMatrix2* Player::getMovedFruits()
{
	return mapModifications->getMovedFruits();
}
IntMatrix2* Player::getModifiedFruits()
{
	return mapModifications->getModifiedFruits();
}
IntMatrix2* Player::getModifiedSugarDrops()
{
	return mapModifications->getModifiedSugarDrops();
}
Player::~Player()
{
	mapModifications->reset();
	listBuildings.clear();
	listFruits.clear();
	if (matrixFruits != NULL)
	{
	  delete matrixFruits;
	}
	if (matrixBuildings != NULL)
	{
	  delete matrixBuildings;
	}
	delete mapModifications;
}
