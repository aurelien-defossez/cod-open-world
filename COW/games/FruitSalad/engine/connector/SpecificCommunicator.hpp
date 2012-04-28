	#ifndef __SPECIFIC_COMMUNICATOR_H__
#define __SPECIFIC_COMMUNICATOR_H__

#include "SpecificCommunicatorInterface.hpp"
#include "FruitSaladEngine.hpp"
#include "Variant.hpp"

// -------------------------------------------------------------------------
// Game API functions IDs
// -------------------------------------------------------------------------

#define __GAME_API_FUNCTION_MOVE__ 1
#define __GAME_API_FUNCTION_ATTACK__ 2
#define __GAME_API_FUNCTION_USE_EQUIPMENT__ 3
#define __GAME_API_FUNCTION_PICK_UP_EQUIPMENT__ 4
#define __GAME_API_FUNCTION_DROP_EQUIPMENT__ 5
#define __GAME_API_FUNCTION_PICK_UP_SUGAR__ 6
#define __GAME_API_FUNCTION_DROP_SUGAR__ 7
#define __GAME_API_FUNCTION_OPEN_CHEST__ 8
#define __GAME_API_FUNCTION_STOCK_SUGAR__ 9
#define __GAME_API_FUNCTION_SELL_EQUIPMENT__ 10
#define __GAME_API_FUNCTION_BUY_EQUIPMENT__ 11
#define __GAME_API_FUNCTION_DRINK_JUICE__ 12
#define __GAME_API_FUNCTION_FRUCTIFY__ 13
#define __GAME_API_FUNCTION_DRAW_VITAMIN__ 14
#define __GAME_API_FUNCTION_WRITE_TEXT__ 15
#define __GAME_API_FUNCTION_WRITE_TEXT_AT__ 16
#define __GAME_API_FUNCTION_DRAW_LINE__ 17
#define __GAME_API_FUNCTION_DRAW_CIRCLE__ 18

// -------------------------------------------------------------------------
// SpecificCommunicator Class
// -------------------------------------------------------------------------

class SpecificCommunicator : public SpecificCommunicatorInterface {
private:
	FruitSaladEngine *game;

public:
	SpecificCommunicator(FruitSaladEngine *game);
	
	void init(int nbParameters, char *parameters[]);
	void addAi(short aiId, char *aiName, char *playerName);
	void play();
	void endGame();
	void aiTimedOut(short aiId);
	Variant performGameFunction(int functionId, int nbParameters,
		Variant parameters[]);
};

#endif
