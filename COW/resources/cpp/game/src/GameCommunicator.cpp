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

void GameCommunicator::performGameFunction(int functionId, int nbParameters, Variant parameters[]) {
	cout << "Performing Game Function #" << functionId << " with parameters (";
	
	for(int i = 0; i < nbParameters; i++) {
		cout << "'";
		
		switch(functionId) {
			case VARIANT_BOOL:
				cout << parameters[i].boolValue;
				break;
			
			case VARIANT_INT:
				cout << parameters[i].intValue;
				break;
			
			case VARIANT_DOUBLE:
				cout << parameters[i].doubleValue;
				break;
			
			case VARIANT_STRING:
				cout << parameters[i].stringValue;
				break;
			
			case VARIANT_INT_MATRIX1:
			{
				IntMatrix1 matrix1 = toIntMatrix1(
					parameters[i].intMatrix1->values,
					parameters[i].intMatrix1->length);
				
				cout << "[";
				for(int j = 0; j < matrix1.size(); j++) {
					cout << matrix1[j] << ",";
				}
				cout << "]";
				break;
			}
			
			case VARIANT_INT_MATRIX2:
			{
				IntMatrix2 matrix2 = toIntMatrix2(
					parameters[i].intMatrix2->values,
					parameters[i].intMatrix2->length,
					parameters[i].intMatrix2->length2);
				
				cout << "[";
				for(int j = 0; j < matrix2.size(); j++) {
					cout << "[";
					for(int k = 0; k < matrix2[j].size(); k++) {
						cout << matrix2[j][k] << ", ";
					}
					cout << "],";
				}
				cout << "]";
				break;
			}
		}
		
		cout << "'";
		
		if(i < nbParameters - 1) {
			cout << ", ";
		}
	}
	
	cout << ")" << endl;
}