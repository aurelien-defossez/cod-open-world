#include "SpecificCommunicator.hpp"
#include "connector.hpp"
#include "Variant.hpp"
#include "FruitSaladEngine.hpp"

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

Variant SpecificCommunicator::performGameFunction(int functionId,
		int nbParameters, Variant parameters[]) {
	switch(functionId) {
		case __GAME_API_FUNCTION_MOVE__:
			return toVariant(game->move(
				intValue(parameters[0]),
				intValue(parameters[1]),
				intValue(parameters[2])
			));
			break;
		
		case __GAME_API_FUNCTION_ATTACK__:
			return toVariant(game->attack(
				intValue(parameters[0]),
				intValue(parameters[1])
			));
			break;
		
		case __GAME_API_FUNCTION_USE_EQUIPMENT__:
			return toVariant(game->useEquipment(
				intValue(parameters[0]),
				intValue(parameters[1]),
				intValue(parameters[2])
			));
			break;
		
		case __GAME_API_FUNCTION_PICK_UP_EQUIPMENT__:
			return toVariant(game->pickUpEquipment(
				intValue(parameters[0]),
				intValue(parameters[1])
			));
			break;
		
		case __GAME_API_FUNCTION_DROP_EQUIPMENT__:
			return toVariant(game->dropEquipment(
				intValue(parameters[0]),
				intValue(parameters[1]),
				intValue(parameters[2]),
				intValue(parameters[3])
			));
			break;
		
		case __GAME_API_FUNCTION_PICK_UP_SUGAR__:
			return toVariant(game->pickUpSugar(
				intValue(parameters[0]),
				intValue(parameters[1])
			));
			break;
		
		case __GAME_API_FUNCTION_DROP_SUGAR__:
			return toVariant(game->dropSugar(
				intValue(parameters[0]),
				intValue(parameters[1]),
				intValue(parameters[2]),
				intValue(parameters[3])
			));
			break;
		
		case __GAME_API_FUNCTION_OPEN_CHEST__:
			return toVariant(game->openChest(
				intValue(parameters[0]),
				intValue(parameters[1])
			));
			break;
		
		case __GAME_API_FUNCTION_STOCK_SUGAR__:
			return toVariant(game->stockSugar(
				intValue(parameters[0])
			));
			break;
		
		case __GAME_API_FUNCTION_SELL_EQUIPMENT__:
			return toVariant(game->sellEquipment(
				intValue(parameters[0]),
				intValue(parameters[1])
			));
			break;
		
		case __GAME_API_FUNCTION_BUY_EQUIPMENT__:
			return toVariant(game->buyEquipment(
				intValue(parameters[0]),
				intValue(parameters[1])
			));
			break;
		
		case __GAME_API_FUNCTION_DRINK_JUICE__:
			return toVariant(game->drinkJuice(
				intValue(parameters[0])
			));
			break;
		
		case __GAME_API_FUNCTION_FRUCTIFY__:
			return toVariant(game->fructify(
				intValue(parameters[0]),
				intValue(parameters[1]),
				intValue(parameters[2]),
				intValue(parameters[3])
			));
			break;
		
		case __GAME_API_FUNCTION_DRAW_VITAMIN__:
			return toVariant(game->drawVitamin(
				intValue(parameters[0])
			));
			break;
		
		case __GAME_API_FUNCTION_WRITE_TEXT__:
			return toVariant(game->writeText(
				stringValue(parameters[0])
			));
			break;
		
		case __GAME_API_FUNCTION_WRITE_TEXT_AT__:
			return toVariant(game->writeTextAt(
				stringValue(parameters[0]),
				intValue(parameters[1]),
				intValue(parameters[2])
			));
			break;
		
		case __GAME_API_FUNCTION_DRAW_LINE__:
			return toVariant(game->drawLine(
				intValue(parameters[0]),
				intValue(parameters[1]),
				intValue(parameters[2]),
				intValue(parameters[3]),
				intValue(parameters[4])
			));
			break;
		
		case __GAME_API_FUNCTION_DRAW_CIRCLE__:
			return toVariant(game->drawCircle(
				intValue(parameters[0]),
				intValue(parameters[1]),
				intValue(parameters[2]),
				intValue(parameters[3])
			));
			break;
		
		default:
			return toVariant(-404);
	}
}