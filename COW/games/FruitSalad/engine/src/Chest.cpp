#include "Chest.h"

Chest::Chest(std::pair<int,int> positionE, int idE, int typeE) :
Entity(positionE, idE, typeE)
{

}

void Chest::dropContent(Map *map)
{
    int count = 0;
    map->removeEntity(this);
    while (count < listEquipment.size())
    {
        std::pair<int,int> position = map->getValidSquare(x, y, 1);
        if ((position.first == -1) && (position.second == -1))
        {
            break;
        }
        listEquipment[count]->setPosition(position.first, position.second);
        map->addEntity(listEquipment.at(count));
    }
}

void Chest::addEquipment(Equipment *equipment)
{
    listEquipment.push_back(equipment);
}

std::string Chest::printC()
{
    std::string str;
    std::stringstream out;
    out << listEquipment.size();
    str += "\nlistSize - "+out.str();

    return str;
}

std::vector<Equipment*> Chest::getListEquipment()
{
  return listEquipment;
}
