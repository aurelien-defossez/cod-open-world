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
}

Game::~Game() {
	delete(commander);
	delete(communicator);
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
	
	commander->setFrame();
	
	int **arch = new int*[3];
	for(int i = 0; i < 3; i++) {
		arch[i] = new int[4];
		for(int j = 0; j < 4; j++) {
			arch[i][j] = i * j;
		}
	}
	
	commander->initGame(0, 4, 3, StdIntMatrix2(arch, 3, 4));
	
	delete arch;
	
	/*
	int **architecture = new int*[4];
	
	for(int i = 0; i < 4; i++) {
		architecture[i] = new int[3];
		for(int j = 0; j < 3; j++) {
			architecture[i][j] = i * 3 + j;
		}
	}
	
	//StdIntMatrix2 *s = toStdIntMatrix2(architecture, 4, 3);
	
	StdIntMatrix2 *s = new StdIntMatrix2();
	s->length = 12;
	s->values = new int[12];
	for(int i = 0; i < 12; i++) {
		s->values[i] = i;
	}
	
	cout << "s check [";
	for(int i = 0; i < 12; i++) {
		cout << s->values[i] << ", ";
	}
	cout << "]" << endl;
	
	commander->initGame(0, 3, 4, s);
	
	cout << "OVER" << endl;

	delete(s);
	for(int i = 0; i < 4; i++) {
		delete(architecture[i]);
	}
	delete(architecture);*/
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
