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
	
	// Display architecture
	cout << "== Architecture ==" << endl;
	cout << "{" << endl;
	for (int i = 0; i < architecture.size(); i++) {
		cout << "\t{";
		for (int j = 0; j < architecture[0].size(); j++) {
			cout << architecture[i][j] << ", ";
		}
		cout << "}," << endl;
	}
	cout << "}" << endl << endl;
	
	// Display fruits
	cout << "== Fruits ==" << endl;
	for (int i = 0; i < fruits.size(); i++) {
		cout << "Fruit: "
			<< api->decode(fruits[i][OBJECT_TYPE]) << " " << "id #"
			<< fruits[i][OBJECT_ID] << " at (" << fruits[i][OBJECT_X]
			<< ";" << fruits[i][OBJECT_Y] << ")" << endl;
	}
	cout << "Limits: " << limitCherry << " cherries, " << limitKiwi
		<< " kiwies, " << limitNut << " nuts" << endl << endl;
	
	// Display buildings
	cout << "== Buildings ==" << endl;
	for (int i = 0; i < buildings.size(); i++) {
		cout << "Building: "
			<< api->decode(buildings[i][OBJECT_TYPE]) << " " << "id #"
			<< buildings[i][OBJECT_ID] << " at ("
			<< buildings[i][OBJECT_X] << ";" << buildings[i][OBJECT_Y]
			<< ")" << endl;
	}
	cout << endl;
}

void Ai::playTurn(IntMatrix2 newObjects,
	IntMatrix1 deletedObjects, IntMatrix2 movedFruits,
	IntMatrix2 modifiedFruits, IntMatrix2 modifiedSugarDrops) {
	cout << "VitaminCpp: Play Turn..." << endl;
	
	// Display new objects
	cout << "== New objects ==" << endl;
	for (int i = 0; i < newObjects.size(); i++) {
		cout << "New object: "
			<< api->decode(newObjects[i][OBJECT_TYPE]) << " " << "id #"
			<< newObjects[i][OBJECT_ID] << " at ("
			<< newObjects[i][OBJECT_X] << ";"
			<< newObjects[i][OBJECT_Y] << ") ; info="
			<< newObjects[i][OBJECT_INFO] << endl;
	}
	cout << endl;
	
	// Display deleted objects
	cout << "== Deleted objects ==" << endl;
	for (int i = 0; i < deletedObjects.size(); i++) {
		cout << "Object: id #" << deletedObjects[i] << " deleted" << endl;
	}
	cout << endl;
	
	// Display moved fruits objects
	cout << "== Moved fruits ==" << endl;
	for (int i = 0; i < movedFruits.size(); i++) {
		cout << "Fruit: id #" << movedFruits[i][OBJECT_ID]
			<< " moved from (" << movedFruits[i][OBJECT_X] << ";"
			<< movedFruits[i][OBJECT_Y] << ") to ("
			<< movedFruits[i][OBJECT_NEW_X] << ";"
			<< movedFruits[i][OBJECT_NEW_Y] << ")" << endl;
	}
	cout <<  endl;
	
	// Display modified fruits objects
	cout << "== Modified fruits ==" << endl;
	for (int i = 0; i < modifiedFruits.size(); i++) {
		cout << "Fruit: id #" << modifiedFruits[i][OBJECT_ID]
			<< " now has " << modifiedFruits[i][OBJECT_LIFE] << " PV and "
			<< modifiedFruits[i][OBJECT_DEFENSE] << " Def" << endl;
	}
	cout << endl;
	
	// Display modified sugar drops objects
	cout << "== Modified sugar drops ==" << endl;
	for (int i = 0; i < modifiedSugarDrops.size(); i++) {
		cout << "Sugar drop: id #"
			<< modifiedSugarDrops[i][OBJECT_ID] << " now has "
			<< modifiedSugarDrops[i][OBJECT_SUGAR] << " sugar grains" << endl;
	}
	cout << endl;
	
	cout << "move = " << api->move(1, 2, 3) << endl;
}

void Ai::chestOpened(int chestId, IntMatrix2 equipments) {
	cout << "VitaminCpp: Open chest..." << endl;
}
