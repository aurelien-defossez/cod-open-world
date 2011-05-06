#include "SpecificCommunicator.hpp"
#include "connector.hpp"
#include "Variant.hpp"
#include "FruitSaladEngine.hpp"
#include <iostream>

using namespace std;

/*
void setGame(FruitSaladEngine *game) {
	com.setGame(game);
}*/

SpecificCommunicator::SpecificCommunicator() {
	setCommunicator(this);
}

void SpecificCommunicator::setGame(FruitSaladEngine *game) {
	this->game = game;
}

void SpecificCommunicator::init(int nbParameters, char *parameters[]) {
	game->init(nbParameters, parameters);
}

void SpecificCommunicator::addAi(short aiId, char *aiName, char *playerName) {
	game->addAi(aiId, aiName, playerName);
}

void SpecificCommunicator::play() {
	game->play();
}

void SpecificCommunicator::endGame() {
	game->endGame();
}

void SpecificCommunicator::disqualifyAi(char *aiName, char *reason){
	game->disqualifyAi(aiName, reason);
}

Variant SpecificCommunicator::performGameFunction(int functionId, int nbParameters,
		Variant parameters[]) {
	switch(functionId) {
		case __GAME_API_FUNCTION_MOVE__:
			return intVariant(game->move(
				parameters[0].value.intValue,
				parameters[1].value.intValue,
				parameters[2].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_ATTACK__:
			game->attack(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_USE_EQUIPMENT__:
			game->useEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue,
				parameters[2].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_PICK_UP_EQUIPMENT__:
			game->pickUpEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_DROP_EQUIPMENT__:
			game->dropEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue,
				parameters[2].value.intValue,
				parameters[3].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_PICK_UP_SUGAR__:
			game->pickUpSugar(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_DROP_SUGAR__:
			game->dropSugar(
				parameters[0].value.intValue,
				parameters[1].value.intValue,
				parameters[2].value.intValue,
				parameters[3].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_OPEN_CHEST__:
			game->openChest(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_STOCK_SUGAR__:
			game->stockSugar(
				parameters[0].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_SELL_EQUIPMENT__:
			game->sellEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_BUY_EQUIPMENT__:
			game->buyEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_DRINK_JUICE__:
			game->drinkJuice(
				parameters[0].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_FRUCTIFY__:
			game->fructify(
				parameters[0].value.intValue,
				parameters[1].value.intValue,
				parameters[2].value.intValue,
				parameters[3].value.intValue
			);
			break;
		
		case __GAME_API_FUNCTION_DRAW_VITAMIN__:
			game->drawVitamin(
				parameters[0].value.intValue
			);
			break;
	}
	return intVariant(-42);
	
	/*
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
				IntMatrix1 matrix1 = toIntMatrix1(parameters[i].intMatrix1);
				
				cout << "[";
				for(int j = 0; j < matrix1.size(); j++) {
					cout << matrix1[j] << ",";
				}
				cout << "]";
				break;
			}
			
			case VARIANT_INT_MATRIX2:
			{
				IntMatrix2 matrix2 = toIntMatrix2(parameters[i].intMatrix2);
				
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
	}*/
}