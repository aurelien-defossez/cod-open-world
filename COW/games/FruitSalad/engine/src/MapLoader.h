#ifndef HEADER_MAPLOADER
#define HEADER_MAPLOADER


#include "Map.h"
#include "SpecificCommander.hpp"
#include "Chest.h"

class MapLoader
{
	public:
		MapLoader(Map *mapE, SpecificCommander *commanderE);
        void loadMap(const char *fic);

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

