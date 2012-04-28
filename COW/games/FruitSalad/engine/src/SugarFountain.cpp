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
        std::pair<int,int> sugarPosition = map->getValidSquare(position.first, position.second, 1);
        if ((sugarPosition.first == -1) && (sugarPosition.second == -1))
        {
            break;
        }
        map->addSugarDrop(sugarPosition.first, sugarPosition.second, 3);
    }
}
