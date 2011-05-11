#include "Ai.hpp"
#include "Variant.hpp"
#include <iostream>

using namespace std;

Ai ai = Ai();

Ai::Ai() {
	communicator = new SpecificCommunicator(this);
	api = new SpecificApiConnector();
}

Ai::~Ai() {
	delete api;
	delete communicator;
}

void Ai::stop() {
	cout << "VitaminCpp: Stopped." << endl;
}

// User-defined functions
void Ai::initGame(IntMatrix2 architecture,
	IntMatrix2 fruits, IntMatrix2 buildings,
	int limitCherry, int limitKiwi, int limitNut) {
	cout << "VitaminCpp: Initialize..." << endl;
}

void Ai::playTurn(IntMatrix2 newObjects,
	IntMatrix1 deletedObjects, IntMatrix2 movedFruits,
	IntMatrix2 modifiedFruits, IntMatrix2 modifiedSugarDrops) {
	cout << "VitaminCpp: Play Turn..." << endl;
}

void Ai::chestOpened(int chestId, IntMatrix2 equipments) {
	cout << "VitaminCpp: Open chest..." << endl;
}
