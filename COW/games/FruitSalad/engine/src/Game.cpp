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
	commander->initGame(42, 100);
	commander->setFrame();
	commander->setTimeout(1000);
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
