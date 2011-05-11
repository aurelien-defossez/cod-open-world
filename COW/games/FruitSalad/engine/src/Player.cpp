#include "Player.h"

Player::Player()
{
    mapModifications = new MapModifications();
    sugarQuantity = 0;
    vitaminsQuantity = 0;
    isCurrent = false;
	namePlayer = new char();
	nameIA = new char();
}

void Player::setInfos(short aiId, char *aiName, char *playerName)
{
  namePlayer = playerName;
  nameIA = aiName;
  idIA = aiId;
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

bool Player::verifyNbFruit(int fruitType)
{
    return true;
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
