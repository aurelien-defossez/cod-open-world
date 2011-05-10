
#include "Fruit.h"
#include "Player.h"
#include "Weapon.h"
#include "Loader.h"
#include "Building.h"
#include "OwnedBuilding.h"
#include "SugarDrop.h"
#include "Chest.h"
#include "MapModifications.h"
#include "Map.h"
#include "MapLoader.h"
#include "API.h"
#include <time.h>

#include <iostream>

int main(int argc, char *argv[])
{
    //Player *owner = new Player();
    //Player *owner2 = new Player();
    //Player *owner3 = new Player();
    //Fruit *cherry = new Fruit(std::pair<int,int>(0,0), 1, FRUIT_CHERRY, owner);
    //Fruit *kiwi = new Fruit(std::pair<int,int>(1,2), 2, FRUIT_KIWI, owner);
    //Weapon *weapon = new Weapon(std::pair<int,int>(0,1), 3, EQUIPMENT_LEMONER);
    //Loader *loader = new Loader(std::pair<int,int>(1,2), 4, EQUIPMENT_RELOADER);
    //Weapon *weapon2 = new Weapon(std::pair<int,int>(1,2), 5, EQUIPMENT_JUICE_NEEDLE);
    //Building *building = new Building(std::pair<int,int>(1,0), 6, BUILDING_SUGAR_BOWL);
    //OwnedBuilding *ownedBuilding = new OwnedBuilding(std::pair<int,int>(1,2), 7, BUILDING_SUGAR_BOWL, owner);
    //SugarDrop *drop = new SugarDrop(std::pair<int,int>(1,1), 8, SUGAR_DROP, 100);
    //MapModifications *mapModif = new MapModifications();

    //std::vector<Equipment*> list;
    //list.push_back(weapon);
    //list.push_back(loader);
    //Chest *chest = new Chest(std::pair<int,int>(1,2), 9, CHEST, list);


    Map *map = new Map();
    MapLoader *mapLoader = new MapLoader(map);
    mapLoader->loadMap();
    API *api = new API(map);
    map->getListPlayers()[0]->setCurrentPlayer(true);
    map->getListPlayers()[1]->setCurrentPlayer(true);
    map->printC();
    std::cout << "\nMove";
    std::cout << api->move(8,5,2);
    std::cout << "\nAttack";
    std::cout << api->attack(8,10);
    map->printC();

    //std::cout << map->printC();
    /*
    map->createFruit(FRUIT_CHERRY,2,2,owner);
    map->addEntity(weapon);
    map->addEntity(building);
    std::cout << map->getEntity(0)->printC();
    std::cout << map->getEntity(3)->printC();
    std::cout << map->getEntity(6)->printC();
    map->printC();
    */
    return 0;
}
