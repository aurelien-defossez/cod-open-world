#ifndef __GAME_H__
#define __GAME_H__

#include "FruitSaladEngine.hpp"
#include "SpecificCommunicator.hpp"
#include "SpecificCommander.hpp"
#include "Config.h"
#include "Map.h"
#include "MapLoader.h"
#include "Fruit.h"
#include "Chest.h"

class Game : FruitSaladEngine {
private:
	SpecificCommunicator *communicator;
	SpecificCommander *commander;
	
	Map *map;
	bool mapLoaded;

public:
	// Constructor & Destructor
	Game();
	~Game();
	
	// Commons functions
	void init(int nbParameters, char *parameters[]);
	void addAi(short aiId, char *aiName, char *playerName);
	void play();
	void endGame();
	void disqualifyAi(char *aiName, char *reason);
	
	// User-defined functions
	int move(int fruitId, int x, int y);
	int attack(int fruitId, int targetFruitId);
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
	int fructify(int fruitId, int fruitType, int x, int y);
	int drawVitamin(int fruitId);
	int writeText(char *text);
	int writeTextAt(char *text, int x, int y);
	int drawLine(int x1, int y1, int x2, int y2, int color);
	int drawCircle(int x, int y, int radius, int color);
	int colorSquare(int x, int y, int color);
	
	void sendOpenedChest(Chest *chest);
};

#endif
