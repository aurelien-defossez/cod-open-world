#include "OwnedEntity.h"

OwnedEntity::OwnedEntity(std::pair<int,int> positionE, int idE, int typeE, Player *ownerE) :
Entity(positionE, idE, typeE)
{
    owner = ownerE;
}

Player* OwnedEntity::getOwner()
{
    return owner;
}
