#include "Peeler.h"

Peeler::Peeler(std::pair<int,int> positionE, int idE, int typeEquipmentE) :
Equipment(positionE, idE, typeEquipmentE)
{

}

bool Peeler::use(Entity *target)
{
    if ((target->getType() >= FRUIT_CHERRY) && (target->getType() <= FRUIT_NUT))
    {
        Fruit *fruit = (Fruit*)target;
        fruit->removeDefense(1);
    }
    return false;
}
