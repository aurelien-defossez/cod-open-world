#include "Weapon.h"
#include "Fruit.h"

Weapon::Weapon(std::pair<int,int> positionE, int idE, int typeEquipmentE) :
Equipment(positionE, idE, typeEquipmentE)
{
    if (type == EQUIPMENT_TEA_SPOON)
    {
        attack = 4;
    }
    else if (type == EQUIPMENT_TOOTHPICK)
    {
        attack = 6;
    }
    else if (type == EQUIPMENT_CUTTER)
    {
        attack = 8;
    }
    else if (type == EQUIPMENT_LIGHTER)
    {
        attack = 2;
    }
    else if (type == EQUIPMENT_LEMONER)
    {
        attack = 4;
    }
    else if (type == EQUIPMENT_SALT_SNIPER)
    {
        attack = 6;
    }
    else if (type == EQUIPMENT_JUICE_NEEDLE)
    {
        attack = -4;
    }
}

bool Weapon::use(Entity *target)
{
    if ((target->getType() >= 17) && (target->getType() <= 19))
    {
        Fruit *fruit = (Fruit*)target;
        if (ammo > -1)
        {
            ammo -= 1;
        }
        if (attack > 0)
        {
            return fruit->removeHP(attack);
        }
        else
        {
            fruit->addHP(attack*(-1));
            return false;
        }
    }
    else
    {
        return false;
    }
}

void Weapon::reload()
{
    ammo = maxAmmo;
}

std::string Weapon::printC()
{
    std::string str;
    std::stringstream out;
    str += "\nlistSize - "+out.str();
    out << position.first;
    str += "\nPosition - "+out.str();
    out << position.second;
    str += "/"+out.str();
    out << range;
    str += "\nrange - "+out.str();
    out << attack;
    str += "\nattack - "+out.str();
    out << ammo;
    str += "\nammo - "+out.str();
    out << cost;
    str += "\ncost - "+out.str();
    out << sellValue;
    str += "\nsellValue - "+out.str();
    out << ammoValue;
    str += "\nammoValue - "+out.str();
    out << weight;
    str += "\nweight - "+out.str();
    out << ammoWeight;
    str += "\nammoWeight - "+out.str();
    out << maxAmmo;
    str += "\nmaxAmmo - "+out.str();

    return str;
}
