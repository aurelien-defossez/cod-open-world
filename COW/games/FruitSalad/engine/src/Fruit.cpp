#include "Fruit.h"
#include <iostream>
#include "Weapon.h"
#include "Peeler.h"
#include "Loader.h"

Fruit::Fruit(std::pair<int,int> positionE, int idE, int typeFruitE, Player *ownerE) :
OwnedEntity(positionE, idE, typeFruitE, ownerE)
{
    counterAction = 0;
    if (typeFruitE == FRUIT_CHERRY)
    {
        life = 15;
        defense = 0;
        force = 4;
        range = 2;
        ammo = -1;
        capacity = 45;
        sugar = 0;
        speed = 6;

        maxLife = 15;
        maxDefense = 0;
        maxAmmo = -1;
        sugarWallet = 90;
    }
    else if (typeFruitE == FRUIT_KIWI)
    {
        life = 20;
        defense = 1;
        force = 2;
        range = 5;
        ammo = 42;
        capacity = 55;
        sugar = 0;
        speed = 4;

        maxLife = 20;
        maxDefense = 1;
        maxAmmo = 42;
        sugarWallet = 120;
    }
    else if (typeFruitE == FRUIT_NUT)
    {
        life = 25;
        defense = 2;
        force = 3;
        range = 1;
        ammo = -1;
        capacity = 65;
        sugar = 0;
        speed = 2;

        maxLife = 25;
        maxDefense = 2;
        maxAmmo = -1;
        sugarWallet = 60;
    }
    else
    {

    }
}

Fruit::~Fruit()
{
	for (int i=0; i<listEquipment.size(); i++)
    {
        delete listEquipment[i];
    }
}

void Fruit::move(int xD, int yD)
{
    position.first = xD;
    position.second = yD;
}

bool Fruit::attack(Fruit *fruit)
{
    if (ammo > -1)
    {
        ammo -= 1;
    }
    if (fruit->removeHP(force))
    {
        return true;
    }
    else
    {
        return false;
    }
}

bool Fruit::removeHP(int nbHP)
{
    int damage = nbHP - defense;
    if (damage > 0)
    {
        life -= damage;
        if (life <= 0)
        {
            return true;
        }
    }
    return false;
}

void Fruit::removeDefense(int nbDefense)
{
    if (defense > 0)
    {
        defense -= nbDefense;
        if (defense < 0)
        {
            defense = 0;
        }
    }
}

bool Fruit::hasMaxHP()
{
    return (life == maxLife);
}

bool Fruit::isHealthy()
{
    return ((hasMaxHP()) && (defense == maxDefense));
}

void Fruit::addHP(int nbHP)
{
    life += nbHP;
    if (life > maxLife)
    {
        life = maxLife;
    }
}

void Fruit::addDefense(int nbDefense)
{
    defense += nbDefense;
    if (defense > maxDefense)
    {
        defense = maxDefense;
    }
}

void Fruit::addSugar(int quantity)
{
    sugar += quantity;
    if (sugar > sugarWallet)
    {
        sugar = sugarWallet;
    }
}

void Fruit::removeSugar(int quantity)
{
    sugar -= quantity;
}

bool Fruit::hasSugarFull()
{
    return (sugar == sugarWallet);
}

void Fruit::resetAction()
{
    counterAction = 0;
}

void Fruit::useAction()
{
    counterAction += 1;
}

void Fruit::addEquipment(Equipment *equipment)
{ 
	listEquipment.push_back(equipment);
	capacity -= equipment->getWeight();
	updateSpeed();
}

void Fruit::removeEquipment(Equipment *equipment)
{
    int index = -1;
    for (int i=0; i<listEquipment.size(); i++)
    {
        if (listEquipment[i] == equipment)
        {
            index = i;
			capacity += listEquipment[i]->getWeight();
			updateSpeed();
            break;
        }
    }
    listEquipment.erase(listEquipment.begin()+index-1);
}

void Fruit::updateSpeed()
{
  switch (type) {
	case FRUIT_CHERRY :
	  if ((45-capacity)<20) speed = 6;
	  else if ((45-capacity)<30) speed = 5;
	  else if ((45-capacity)<35) speed = 4;
	  else if ((45-capacity)<39) speed = 3;
	  else if ((45-capacity)<42) speed = 2;
	  else speed = 1;
	  break;
	case FRUIT_KIWI :
	  if ((55-capacity)<35) speed = 4;
	  else if ((55-capacity)<45) speed = 3;
	  else if ((55-capacity)<51) speed = 2;
	  else speed = 1;
	  break;
	case FRUIT_NUT :
	  if ((65-capacity)<42) speed = 2;
	  else speed = 1;
	  break;
	default :
	  break;
  }
}

bool Fruit::hasActionLeft()
{
    return (counterAction < 2);
}

bool Fruit::hasAmmoLeft()
{
    return (maxAmmo == -1 || ammo > 0);
}

bool Fruit::hasEquipment(int id)
{
    for (int i=0; i<listEquipment.size(); i++)
    {
        if (listEquipment[i]->getId() == id)
        {
            return true;
        }
    }
    
    return false;
}

Equipment* Fruit::getEquipment(int id)
{
    for (int i=0; i<listEquipment.size(); i++)
    {
        if (listEquipment[i]->getId() == id)
        {
            return listEquipment[i];
        }
    }
    
    return NULL;
}

bool Fruit::useEquipment(Equipment *equipment, Entity *target)
{
    if (((equipment->getType() >= EQUIPMENT_TEA_SPOON) && (equipment->getType() <= EQUIPMENT_SALT_SNIPER)) || ((equipment->getType() == EQUIPMENT_JUICE_NEEDLE)))
    {
        Weapon *weapon = (Weapon*)equipment;
        return weapon->use(target);
    }
    else if (equipment->getType() == EQUIPMENT_PEELER)
    {
        Peeler *peeler = (Peeler*)equipment;
        return peeler->use(target);
    }
    else if (equipment->getType() == EQUIPMENT_RELOADER)
    {
        Loader *loader = (Loader*)equipment;
        return loader->use(target);
    }
    
    return false;
}

void Fruit::reload()
{
    ammo = maxAmmo;
}

int Fruit::hasPlaceLeft()
{
    return capacity;
}

int Fruit::getSugar()
{
    return sugar;
}

int Fruit::getLife()
{
    return life;
}

int Fruit::getDefense()
{
    return defense;
}

int Fruit::getSpeed()
{
    return speed;
}

int Fruit::getRange()
{
    return range;
}


std::string Fruit::printC()
{
    
    std::cout << "Position - " << position.first << "/" << position.second << std::endl;
	std::cout << "Id - " << id << std::endl;
	std::cout << "Life - " << life << std::endl;
	std::cout << "Speed - " << speed << std::endl;
	std::cout << "Sugar - " << sugar << std::endl;
	std::cout << "Bag - " << listEquipment.size() << std::endl;

    return "";
}
