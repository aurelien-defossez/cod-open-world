#ifndef HEADER_MAPLOADER
#define HEADER_MAPLOADER

#include "Player.h"
#include "Building.h"
#include "OwnedBuilding.h"
#include "Entity.h"
#include "Chest.h"
#include "SugarDrop.h"
#include "Equipment.h"
#include "Fruit.h"
#include "Map.h"

#include "SpecificCommander.hpp"
#include <time.h>
#include <fstream>

#include <iostream>

class MapLoader
{
	public:
		MapLoader(Map *mapE, SpecificCommander *commanderE);
        void loadMap(char *fic);

        Entity* createBuilding(std::string typeE, int x0, int y0);
        Entity* createOwnedBuilding(std::string typeE, int numPlayer, int x0, int y0);
        Entity* createFruit(std::string typeE, int numPlayer, int x0, int y0);
        Entity* createEquipment(std::string typeE, int x0, int y0);
        void createChestEquipment(std::string typeE, Chest* chest);
        Entity* createChest(int x0, int y0);


	protected:
        Map *map;
		SpecificCommander *commander;
        int currentId;

};


#endif

