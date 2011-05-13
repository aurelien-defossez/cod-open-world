#include "SugarDrop.h"

SugarDrop::SugarDrop(std::pair<int,int> positionE, int idE, int typeE, int capacityE) :
Entity(positionE, idE, typeE)
{
    capacity = capacityE;
}

bool SugarDrop::removeSugar(int quantity)
{
    capacity -= quantity;
    if (capacity <= 0)
    {
        return true;
    }
    else
    {
        return false;
    }
}

int SugarDrop::getCapacity()
{
    return capacity;
}

std::string SugarDrop::printC()
{
    std::string str;
    std::stringstream out;
    out << position.first;
    str += "\nPosition - "+out.str();
    out << position.second;
    str += "\nPosition - "+out.str();
    out << type;
    str += "\ntype - "+out.str();
    out << id;
    str += "\nid - "+out.str();
    out << capacity;
    str += "\ncapacity - "+out.str();

    return str;
}
