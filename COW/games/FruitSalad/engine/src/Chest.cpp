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

void Chest::dropContent(Map *map)
{
    int count = 0;
    map->removeEntity(this);
    while (count < listEquipment.size())
    {
	  Position validPos = map->getValidSquare(position.first, position.second, 1);
        if ((validPos.first == -1) && (validPos.second == -1))
        {
            break;
        }
        listEquipment[count]->setPosition(validPos.first, validPos.second);
        map->addEntity(listEquipment[count]);
		count++;
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
