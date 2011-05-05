#include "SpecificCommunicator.hpp"

#include <iostream>
#include "../../../../resources/cpp/game/src/connector.hpp"

using namespace std;

SpecificCommunicator com = SpecificCommunicator();

SpecificCommunicator::SpecificCommunicator() {
	setCommunicator(this);
}

void SpecificCommunicator::plop() {
	cout << "THIS IS SPAARTAAAAAA" << endl;
}
