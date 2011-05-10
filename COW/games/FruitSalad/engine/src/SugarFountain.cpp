#include "SugarFountain.h"

SugarFountain::SugarFountain(std::pair<int,int> positionE, int idE, int typeBuildingE) :
Building(positionE, idE, typeBuildingE)
{

}

void SugarFountain::dropSugarRandomly(Map *map)
{
    int count = 0;
    while (count < 4)
    {
        std::pair<int,int> position = map->getValidSquare(x, y, 3);
        if ((position.first == -1) && (position.second == -1))
        {
            break;
        }
        map->addSugarDrop(position.first, position.second, 50);
    }
}
