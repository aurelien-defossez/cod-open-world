#include "Equipment.h"

Equipment::Equipment(std::pair<int,int> positionE, int idE, int typeEquipmentE) :
Entity(positionE, idE, typeEquipmentE)
{
    if (type == EQUIPMENT_TEA_SPOON)
    {
        range = 1;
        ammo = -1;
        cost = 10;
        sellValue = 6;
        ammoValue = 0;
        weight = 5;
        ammoWeight = 0;
        maxAmmo = -1;
    }
    else if (type == EQUIPMENT_TOOTHPICK)
    {
        range = 1;
        ammo = -1;
        cost = 20;
        sellValue = 12;
        ammoValue = 0;
        weight = 10;
        ammoWeight = 0;
        maxAmmo = -1;
    }
    else if (type == EQUIPMENT_CUTTER)
    {
        range = 2;
        ammo = -1;
        cost = 50;
        sellValue = 30;
        ammoValue = 0;
        weight = 15;
        ammoWeight = 0;
        maxAmmo = -1;
    }
    else if (type == EQUIPMENT_LIGHTER)
    {
        range = 4;
        ammo = 100;
        cost = 15;
        sellValue = 10;
        ammoValue = 0.02;
        weight = 12;
        ammoWeight = 0.01;
        maxAmmo = 100;
    }
    else if (type == EQUIPMENT_LEMONER)
    {
        range = 6;
        ammo = 25;
        cost = 35;
        sellValue = 20;
        ammoValue = 0.2;
        weight = 20;
        ammoWeight = 0.4;
        maxAmmo = 25;
    }
    else if (type == EQUIPMENT_SALT_SNIPER)
    {
        //A checker
        range = 10;
        ammo = 25;
        cost = 80;
        sellValue = 50;
        ammoValue = 1;
        weight = 30;
        ammoWeight = 2;
        maxAmmo = 25;
    }
    else if (type == EQUIPMENT_PEELER)
    {
        range = 2;
        ammo = -1;
        cost = 15;
        sellValue = 10;
        ammoValue = 0;
        weight = 5;
        ammoWeight = 0;
        maxAmmo = -1;
    }
    else if (type == EQUIPMENT_JUICE_NEEDLE)
    {
        range = 1;
        ammo = 10;
        cost = 30;
        sellValue = 25;
        ammoValue = 2;
        weight = 12;
        ammoWeight = 1;
        maxAmmo = 10;
    }
    else if (type == EQUIPMENT_RELOADER)
    {
        range = 1;
        ammo = 1;
        cost = 20;
        sellValue = 15;
        ammoValue = 10;
        weight = 12;
        ammoWeight = 10;
        maxAmmo = 1;
    }
}

bool Equipment::use(Entity *target)
{
    return false;
}

bool Equipment::hasAmmoLeft()
{
    if (maxAmmo == -1)
    {
        return true;
    }
    else if (ammo > 0)
    {
        return true;
    }
    else
    {
        return false;
    }
}

int Equipment::getWeight()
{
    return weight;
}

int Equipment::getSellValue()
{
    return sellValue;
}

int Equipment::getCost()
{
    return cost;
}

int Equipment::getRange()
{
    return range;
}

int Equipment::getAmmo()
{
	return ammo;
}
