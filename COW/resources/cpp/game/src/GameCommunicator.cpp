#include "Variant.hpp"
#include "GameCommunicator.hpp"
#include <iostream>

using namespace std;

void GameCommunicator::init(int nbParameters, char *parameters[]) {
	cout << "Initializing game with " << nbParameters << " parameters." << endl;
	
	for(int i = 0; i < nbParameters; i++) {
		cout << "Parameter #" << i << " = " << parameters[i] << endl;
	}
}

void GameCommunicator::addAi(short aiId, char *aiName, char *playerName) {
	cout << "Adding AI '" << aiName << "' (" << aiId << ") of player " << playerName << endl;
}

void GameCommunicator::play() {
	cout << "Play..." << endl;
}

void GameCommunicator::endGame() {
	cout << "End game order received." << endl;
}

void GameCommunicator::disqualifyAi(char *aiName, char *reason){
	cout << "Disqualifying AI " << aiName << " because of " << reason << endl;
}

void GameCommunicator::performGameFunction(int functionId, int nbParameters, void *parameters[]) {
	cout << "Performing Game Function #" << functionId << " with parameters (";
	
	for(int i = 0; i < nbParameters; i++) {
		cout << "@" << parameters[i];
		cout << "'";
		
		switch(functionId) {
			case VARIANT_BOOL:
				cout << *((bool*)parameters[i]);
				break;
			
			case VARIANT_INT:
				cout << *((int*)parameters[i]);
				break;
			
			case VARIANT_DOUBLE:
				cout << *((double*)parameters[i]);
				break;
			
			case VARIANT_STRING:
				cout << *((char**)parameters[i]);
				break;
		}
		
		cout << "'";
		
		if(i < nbParameters - 1) {
			cout << ", ";
		}
	}
	
	cout << ")" << endl;
}