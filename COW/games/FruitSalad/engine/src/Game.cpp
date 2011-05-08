#include "Game.hpp"
#include "FruitSaladEngine.hpp"
#include "SpecificCommunicator.hpp"
#include "SpecificCommander.hpp"
#include <iostream>

using namespace std;

Game game = Game();

Game::Game() {
	communicator = new SpecificCommunicator(this);
	commander = new SpecificCommander();
	
	height = 12;
	width = 10;
	
	// Create empty architecture
	architecture = new int *[height];
	for(int i = 0; i < height; i++) {
		architecture[i] = new int[width];
		for(int j = 0; j < width; j++) {
			
			if(i == 0 || i == height - 1 || j == 0 || j == width - 1) {
				architecture[i][j] = WALL;
			} else {
				architecture[i][j] = NOTHING;
			}
		}
	}
}

Game::~Game() {
	delete commander;
	delete communicator;
	
	for(int i = 0; i < height; i++) {
		delete[] architecture[i];
	}
	delete[] architecture;
}

void Game::init(int nbParameters, char *parameters[]) {
	cout << "Initializing game with " << nbParameters << " parameters." << endl;
	
	for(int i = 0; i < nbParameters; i++) {
		cout << "Parameter #" << i << " = " << parameters[i] << endl;
	}
}

void Game::addAi(short aiId, char *aiName, char *playerName) {
	cout << "Adding AI '" << aiName << "' (" << aiId << ") ";
	cout << "of player " << playerName << endl;
}

void Game::play() {
	cout << "Play..." << endl;
	
	cout << "setFrame" << endl;
	commander->setFrame();
	
	// Create fruits array
	int nbFruits = 3;
	int **fruits = new int *[nbFruits];
	for(int i = 0; i < nbFruits; i++) {
		fruits[i] = new int[4];
	}
	fruits[0][OBJECT_ID] = 1;
	fruits[0][OBJECT_X] = 8;
	fruits[0][OBJECT_Y] = 5;
	fruits[0][OBJECT_TYPE] = FRUIT_CHERRY;
	fruits[1][OBJECT_ID] = 2;
	fruits[1][OBJECT_X] = 8;
	fruits[1][OBJECT_Y] = 6;
	fruits[1][OBJECT_TYPE] = FRUIT_KIWI;
	fruits[2][OBJECT_ID] = 3;
	fruits[2][OBJECT_X] = 8;
	fruits[2][OBJECT_Y] = 7;
	fruits[2][OBJECT_TYPE] = FRUIT_NUT;
	
	// Create buildings array
	int nbBuildings = 3;
	int **buildings = new int *[nbBuildings];
	for(int i = 0; i < nbBuildings; i++) {
		buildings[i] = new int[4];
	}
	buildings[0][OBJECT_ID] = 4;
	buildings[0][OBJECT_X] = 1;
	buildings[0][OBJECT_Y] = 1;
	buildings[0][OBJECT_TYPE] = BUILDING_JUICE_BARREL;
	buildings[1][OBJECT_ID] = 5;
	buildings[1][OBJECT_X] = 1;
	buildings[1][OBJECT_Y] = 2;
	buildings[1][OBJECT_TYPE] = BUILDING_SUGAR_BOWL;
	buildings[2][OBJECT_ID] = 6;
	buildings[2][OBJECT_X] = 1;
	buildings[2][OBJECT_Y] = 3;
	buildings[2][OBJECT_TYPE] = BUILDING_FRUCTIFICATION_TANK;
	
	cout << "Initializing AI #0" << endl;
	commander->initGame(0,
		StdIntMatrix2(architecture, height, width),
		StdIntMatrix2(fruits, nbFruits, 4),
		StdIntMatrix2(buildings, nbFruits, 4),
		4, 5, 6
	);
	
	// Delete allocated arrays
	for(int i = 0; i < nbFruits; i++) {
		delete[] fruits[i];
	}
	delete[] fruits;
	
	for(int i = 0; i < nbBuildings; i++) {
		delete[] buildings[i];
	}
	delete[] buildings;
}

void Game::endGame() {
	cout << "End game order received." << endl;
}

void Game::disqualifyAi(char *aiName, char *reason) {
	cout << "Disqualifying AI " << aiName << " because of " << reason << endl;
}

// User-defined functions
int Game::move(int fruitId, int x, int y) {
	cout << "move(" << fruitId << ", " << x << ", " << y << ");" << endl;
	return OK;
}

int Game::attack(int fruitId, int targetFruitId) {
	cout << "attack(" << fruitId << ", " << targetFruitId << ");" << endl;
	return OK;
}

int Game::useEquipment(int fruitId, int equipmentId, int targetId) {
	cout << "useEquipment(" << fruitId << ", " << equipmentId << ", ";
	cout << targetId << ");" << endl;
	return OK;
}

int Game::pickUpEquipment(int fruitId, int equipmentId) {
	cout << "pickUpEquipment(" << fruitId << ", " << equipmentId << ");" << endl;
	return OK;
}

int Game::dropEquipment(int fruitId, int equipmentId, int x, int y) {
	cout << "dropEquipment(" << fruitId << ", " << equipmentId;
	cout << ", " << x << ", " << y << ");" << endl;
	return OK;
}

int Game::pickUpSugar(int fruitId, int sugarDropId) {
	cout << "pickUpSugar(" << fruitId << ", " << sugarDropId << ");" << endl;
	return OK;
}

int Game::dropSugar(int fruitId, int quantity, int x, int y) {
	cout << "dropSugar(" << fruitId << ", " << quantity;
	cout << ", " << x << ", " << y << ");" << endl;
	return OK;
}

int Game::openChest(int fruitId, int chestId) {
	cout << "openChest(" << fruitId << ", " << chestId << ");" << endl;
	return OK;
}

int Game::stockSugar(int fruitId) {
	cout << "stockSugar(" << fruitId << ");" << endl;
	return OK;
}

int Game::sellEquipment(int fruitId, int equipmentId) {
	cout << "sellEquipment(" << fruitId << ", " << equipmentId << ");" << endl;
	return OK;
}

int Game::buyEquipment(int fruitId, int equipmentType) {
	cout << "buyEquipment(" << fruitId << ", " << equipmentType << ");" << endl;
	return OK;
}

int Game::drinkJuice(int fruitId) {
	cout << "drinkJuice(" << fruitId << ");" << endl;
	return OK;
}

int Game::fructify(int fruitId, int fruitType, int x, int y) {
	cout << "fructify(" << fruitId << ", " << fruitType;
	cout << ", " << x << ", " << y << ");" << endl;
	return OK;
}

int Game::drawVitamin(int fruitId) {
	cout << "drawVitamin(" << fruitId << ");" << endl;
	return OK;
}
