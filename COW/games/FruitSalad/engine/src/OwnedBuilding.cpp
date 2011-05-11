#include "OwnedBuilding.h"

OwnedBuilding::OwnedBuilding(std::pair<int,int> positionE, int idE, int typeBuildingE, Player *ownerE) :
OwnedEntity(positionE, idE, typeBuildingE, ownerE)
{

}

std::string OwnedBuilding::printC()
{
    std::string str;
    /*
    str += "\nPosition - "+std::string::number(x)+"/"+std::string::number(y);
    str += "\ntype - "+std::string::number(type);
    str += "\nid - "+std::string::number(id);
    */
    return str;
}
