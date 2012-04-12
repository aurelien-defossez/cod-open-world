#include "Ai.hpp"
#include "Variant.hpp"
#include <iostream>
#include <sys/time.h>
#include <unistd.h>
#include <chrono>

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
	int limitCherry, int limitKiwi, int limitNut,
	int vitaminGoal, int maxNbTurns) {
	cout << "VitaminCpp: Initialize..." << endl;
	cout << "Goal = " << vitaminGoal << " ; Max turns = " << maxNbTurns << endl;
	
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
	/*cout << "VitaminCpp: Play Turn..." << endl;
	
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
	
	cout << "== Test Game API ==" << endl;
	// Test game API functions
	testGameApi();*/
	/*
	struct timeval start, end;
	long mtime, seconds, useconds;    

	gettimeofday(&start, NULL);
	api->move(1, 42, 42);
	gettimeofday(&end, NULL);

	seconds  = end.tv_sec  - start.tv_sec;
	useconds = end.tv_usec - start.tv_usec;

	mtime = ((seconds) * 1000 + useconds/1000.0) + 0.5;
*/

	std::chrono::time_point<std::chrono::high_resolution_clock> t1 = std::chrono::high_resolution_clock::now();
	api->move(1, 42, 42);
	std::chrono::time_point<std::chrono::high_resolution_clock> t2 = std::chrono::high_resolution_clock::now();

	std::cout << "AI::Move done in " << std::chrono::duration_cast<std::chrono::microseconds > (t2 - t1).count() << "µs" << std::endl;
}

void Ai::mapUpdate(IntMatrix2 newObjects, IntMatrix2 modifiedSugarDrops) {
	/*cout << "VitaminCpp: Map update..." << endl;
	
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
	
	// Display modified sugar drops objects
	cout << "== Modified sugar drops ==" << endl;
	for (int i = 0; i < modifiedSugarDrops.size(); i++) {
		cout << "Sugar drop: id #"
			<< modifiedSugarDrops[i][OBJECT_ID] << " now has "
			<< modifiedSugarDrops[i][OBJECT_SUGAR] << " sugar grains" << endl;
	}
	cout << endl;*/
}

void Ai::testGameApi() {
	cout << "move: " + api->decode(api->move(1, 42, 42)) << endl;
	
	cout << "attack: " + api->decode(api->attack(1, 2)) << endl;
	
	cout << "useEquipment: "
		+ api->decode(api->useEquipment(1, 512, 1)) << endl;
	
	cout << "pickUpEquipment: "
		+ api->decode(api->pickUpEquipment(1, 57)) << endl;
	
	cout << "dropEquipment: "
		+ api->decode(api->dropEquipment(1, 72, 5, 6)) << endl;
	
	cout << "dropSugar: "
		+ api->decode(api->dropSugar(1, 10, 5, 6)) << endl;
	
	cout << "openChest: " + api->decode(api->openChest(1, 18)) << endl;
	
	cout << "stockSugar: " + api->decode(api->stockSugar(1)) << endl;
	
	cout << "sellEquipment: "
		+ api->decode(api->sellEquipment(1, 16)) << endl;
	
	cout << "buyEquipment: "
		+ api->decode(api->buyEquipment(1, EQUIPMENT_SALT_SNIPER)) << endl;
	
	cout << "drinkJuice: " + api->decode(api->drinkJuice(1)) << endl;
	
	cout << "fructify: "
		+ api->decode(api->fructify(FRUIT_CHERRY, 1, 4)) << endl;
	
	cout << "drawVitamin: " + api->decode(api->drawVitamin(1)) << endl;
	
	cout << "drawLine: "
		+ api->decode(api->drawLine(1, 1, 5, 8, COLOR_RED)) << endl;
	
	cout << "drawCircle: "
		+ api->decode(api->drawCircle(8, 9, 2, COLOR_BLUE)) << endl;
}
