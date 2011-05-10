#ifndef HEADER_API
#define HEADER_API

#include "Config.h"
#include "Map.h"
#include "Fruit.h"
#include "Chest.h"

class API
{
	public:
		API(Map* mapE);
        int move(int fruitId, int x, int y);
        int attack(int fruitId, int targetId);
        int useEquipment(int fruitId, int equipmentId, int targetId);
        int pickUpEquipment(int fruitId, int equipmentId);
        int dropEquipment(int fruitId, int equipmentId, int x, int y);
        int pickUpSugar(int fruitId, int sugarDropId);
        int dropSugar(int fruitId, int quantity, int x, int y);
        int openChest(int fruitId, int chestId);
        int stockSugar(int fruitId);
        int sellEquipment(int fruitId, int equipmentId);
        int buyEquipment(int fruitId, int equipmentType);
        int drinkJuice(int fruitId);
        int drawVitamin(int fruitId);
        int fructify(int fruitId, int fruitType, int x, int y);

	protected:
        Map *map;

};


#endif
