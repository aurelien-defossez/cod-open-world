#include "SpecificCommunicator.hpp"
#include "connector.hpp"
#include "Variant.hpp"
#include "FruitSaladEngine.hpp"
#include <iostream>

using namespace std;

SpecificCommunicator::SpecificCommunicator(FruitSaladEngine *game) {
	this->game = game;
	setCommunicator(this);
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
			return intVariant(game->attack(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_USE_EQUIPMENT__:
			return intVariant(game->useEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue,
				parameters[2].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_PICK_UP_EQUIPMENT__:
			return intVariant(game->pickUpEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_DROP_EQUIPMENT__:
			return intVariant(game->dropEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue,
				parameters[2].value.intValue,
				parameters[3].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_PICK_UP_SUGAR__:
			return intVariant(game->pickUpSugar(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_DROP_SUGAR__:
			return intVariant(game->dropSugar(
				parameters[0].value.intValue,
				parameters[1].value.intValue,
				parameters[2].value.intValue,
				parameters[3].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_OPEN_CHEST__:
			return intVariant(game->openChest(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_STOCK_SUGAR__:
			return intVariant(game->stockSugar(
				parameters[0].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_SELL_EQUIPMENT__:
			return intVariant(game->sellEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_BUY_EQUIPMENT__:
			return intVariant(game->buyEquipment(
				parameters[0].value.intValue,
				parameters[1].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_DRINK_JUICE__:
			return intVariant(game->drinkJuice(
				parameters[0].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_FRUCTIFY__:
			return intVariant(game->fructify(
				parameters[0].value.intValue,
				parameters[1].value.intValue,
				parameters[2].value.intValue,
				parameters[3].value.intValue
			));
			break;
		
		case __GAME_API_FUNCTION_DRAW_VITAMIN__:
			return intVariant(game->drawVitamin(
				parameters[0].value.intValue
			));
			break;
		
		default:
			return intVariant(-404);
	}
}