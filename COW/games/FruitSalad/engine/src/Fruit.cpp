#include "Fruit.h"

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

void Fruit::move(int xD, int yD)
{
    x = xD;
    y = yD;
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
            die();
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
    if (life == maxLife)
    {
        return true;
    }
    else
    {
        return false;
    }
}

bool Fruit::isHealthy()
{
    if ((hasMaxHP()) && (defense == maxDefense))
    {
        return true;
    }
    else
    {
        return false;
    }
}

void Fruit::addHP(int nbHP)
{
    if (life < maxLife)
    {
        life += nbHP;
        if (life > maxLife)
        {
            life = maxLife;
        }
    }
}

void Fruit::addDefense(int nbDefense)
{
    if (defense < maxDefense)
    {
        defense += nbDefense;
        if (defense > maxDefense)
        {
            defense = maxDefense;
        }
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
    if (sugar == sugarWallet)
    {
        return true;
    }
    else
    {
        return false;
    }
}

void Fruit::resetAction()
{
    counterAction = 0;
}

void Fruit::useAction()
{
    counterAction += 1;
}

void Fruit::die()
{
    //emit die
}

void Fruit::addEquipment(Equipment *equipment)
{
    listEquipment.push_back(equipment);
}

void Fruit::removeEquipment(Equipment *equipment)
{
    int index = -1;
    for (int i=0; i<listEquipment.size(); i++)
    {
        if (listEquipment[i] == equipment)
        {
            index = i;
            break;
        }
    }
    listEquipment.erase(listEquipment.begin()+index-1);
}

bool Fruit::hasActionLeft()
{
    if (counterAction == 2)
    {
        return false;
    }
    else
    {
        return true;
    }
}

bool Fruit::hasAmmoLeft()
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

bool Fruit::hasEquipment(Equipment *equipment)
{
    for (int i=0; i<listEquipment.size(); i++)
    {
        if (listEquipment[i] == equipment)
        {
            return true;
        }
    }
    return false;
}

bool Fruit::useEquipment(Equipment *equipment, Entity *target)
{
    if (((equipment->getType() >= 20) && (equipment->getType() <= 25)) || ((equipment->getType() == 27)))
    {
        Weapon *weapon = (Weapon*)equipment;
        return weapon->use(target);
    }
    else if (equipment->getType() == 26)
    {
        Peeler *peeler = (Peeler*)equipment;
        return peeler->use(target);
    }
    else if (equipment->getType() == 28)
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
    std::string str;
    std::stringstream out;
    out << x;
    str += "\nPosition - "+out.str();
    out << y;
    str += "\nPosition - "+out.str();
    out << id;
    str += "\nId - "+out.str();
    out << life;
    str += "\nLife - "+out.str();
    out << defense;
    str += "\ndefense - "+out.str();
    out << force;
    str += "\nforce - "+out.str();
    out << range;
    str += "\nrange - "+out.str();
    out << ammo;
    str += "\nammo - "+out.str();
    out << capacity;
    str += "\ncapacity - "+out.str();
    out << sugar;
    str += "\nsugar - "+out.str();
    out << speed;
    str += "\nspeed - "+out.str();
    out << maxLife;
    str += "\nmaxLife - "+out.str();
    out << maxDefense;
    str += "\nmaxDefense - "+out.str();
    out << maxAmmo;
    str += "\nmaxAmmo - "+out.str();
    out << sugarWallet;
    str += "\nsugarWallet - "+out.str();
    out << counterAction;
    str += "\ncounterAction - "+out.str();
    out << listEquipment.size();
    str += "\nbagSize - "+out.str();

    return str;
}
