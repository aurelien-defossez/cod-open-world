#include "Chest.h"
#include <iostream>

Chest::Chest(Position positionE, int idE, int typeE) :
Entity(positionE, idE, typeE)
{

}

Chest::~Chest()
{
	for (int i=0; i<listEquipment.size(); i++)
    {
        delete listEquipment[i];
    }
}


void Chest::addEquipment(Equipment *equipment)
{
    listEquipment.push_back(equipment);
}

std::string Chest::printC()
{
   for (int i=0; i<listEquipment.size(); i++)
    {
        std::cout << listEquipment[i]->getId() << std::endl;
    }

    return "";
}

std::vector<Equipment*> Chest::getListEquipment()
{
  return listEquipment;
}

void Chest::clear()
{
  listEquipment.clear();
}
