#include "Loader.h"
#include "Weapon.h"
#include "Fruit.h"

Loader::Loader(std::pair<int,int> positionE, int idE, int typeEquipmentE) :
Equipment(positionE, idE, typeEquipmentE)
{

}

bool Loader::use(Entity *target)
{
    if (((target->getType() >= EQUIPMENT_TEA_SPOON) && (target->getType() <= EQUIPMENT_SALT_SNIPER)) || ((target->getType() == EQUIPMENT_JUICE_NEEDLE)))
    {
        Weapon *weapon = (Weapon*)target;
        weapon->reload();
    }
    else if (((target->getType() >= FRUIT_CHERRY) && (target->getType() <= FRUIT_NUT)))
    {
        Fruit *fruit = (Fruit*)target;
        fruit->reload();
    }
    return false;
}
