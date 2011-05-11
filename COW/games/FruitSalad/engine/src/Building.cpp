#include "Building.h"

Building::Building(std::pair<int,int> positionE, int idE, int typeBuildingE) :
Entity(positionE, idE, typeBuildingE)
{

}

std::string Building::printC()
{
    std::string str;
    std::stringstream out;
    out << x;
    str += "\nPosition - "+out.str();
    out.str("");
    out << y;
    str += "/"+out.str();
    out.str("");
    out << type;
    str += "\ntype - "+out.str();
    out.str("");
    out << id;
    str += "\nid - "+out.str();
    return str;
}
