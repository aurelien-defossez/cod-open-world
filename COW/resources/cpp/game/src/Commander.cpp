#include "Commander.hpp"
#include "connector.hpp"
#include "Variant.hpp"

Commander::Commander() {
	setCommander(this);
}

void Commander::registerCallbacks(makeCallCallback makeCall) {
	this->makeCall = makeCall;
}

void Commander::setFrame() {
	Variant parameters[0];
	parameters[0] = toVariant(fruitId);
	parameters[1] = toVariant(x);
	parameters[2] = toVariant(y);
	return intValue(callGameFunction(__GAME_API_FUNCTION_MOVE__, 3, parameters));
	
	doMakeCall(__CALLBACK_FUNCTION_SET_FRAME__, 0, NULL);
}

void Commander::setTimeout(int timeout) {
	Variant parameters[1];
	parameters[0] = toVariant(timeout);
	
	doMakeCall(__CALLBACK_FUNCTION_SET_TIMEOUT__, 1, parameters);
}

void Commander::setScore(short aiId, int score) {
	Variant parameters[2];
	parameters[0] = toVariant(aiId);
	parameters[1] = toVariant(score);
	
	doMakeCall(__CALLBACK_FUNCTION_SET_SCORE__, 2, parameters);
}

void Commander::incrementScore(short aiId, int increment) {
	Variant parameters[2];
	parameters[0] = toVariant(aiId);
	parameters[1] = toVariant(increment);
	
	doMakeCall(__CALLBACK_FUNCTION_INCREMENT_SCORE__, 2, parameters);
}

void Commander::stopAi(short aiId) {
	Variant parameters[1];
	parameters[0] = toVariant(aiId);
	
	doMakeCall(__CALLBACK_FUNCTION_STOP_AI__, 1, parameters);
}

void Commander::throwException(char *message) {
	Variant parameters[1];
	parameters[0] = toVariant(message);
	
	doMakeCall(__CALLBACK_FUNCTION_THROW_EXCEPTION__, 1, parameters);
}

void Commander::displayGrid(int x0, int y0, int x1, int y1, int xSpacing,
		int ySpacing, int color, bool temporary) {
	Variant parameters[8];
	parameters[0] = toVariant(x0);
	parameters[1] = toVariant(y0);
	parameters[2] = toVariant(x1);
	parameters[3] = toVariant(y1);
	parameters[4] = toVariant(xSpacing);
	parameters[5] = toVariant(ySpacing);
	parameters[6] = toVariant(color);
	parameters[7] = toVariant(temporary);
	
	doMakeCall(__CALLBACK_FUNCTION_DISPLAY_GRID__, 8, parameters);
}

void Commander::drawLine(int x0, int y0, int x1, int y1, int color,
		bool temporary) {
	Variant parameters[6];
	parameters[0] = toVariant(x0);
	parameters[1] = toVariant(y0);
	parameters[2] = toVariant(x1);
	parameters[3] = toVariant(y1);
	parameters[4] = toVariant(color);
	parameters[5] = toVariant(temporary);
	
	doMakeCall(__CALLBACK_FUNCTION_DRAW_LINE__, 6, parameters);
}

void Commander::drawCircle(int x, int y, int radius, int samples, int color,
	bool temporary) {
	Variant parameters[6];
	parameters[0] = toVariant(x);
	parameters[1] = toVariant(y);
	parameters[2] = toVariant(radius);
	parameters[3] = toVariant(samples);
	parameters[4] = toVariant(color);
	parameters[5] = toVariant(temporary);
	
	doMakeCall(__CALLBACK_FUNCTION_DRAW_CIRCLE__, 6, parameters);
}

void Commander::deleteTemporaryShapes() {
	doMakeCall(__CALLBACK_FUNCTION_DELETE_TEMPORARY_SHAPES__, 0, NULL);
}

void Commander::createEntity(int definitionId, int id) {
	Variant parameters[2];
	parameters[0] = toVariant(definitionId);
	parameters[1] = toVariant(id);
	
	doMakeCall(__CALLBACK_FUNCTION_CREATE_ENTITY__, 2, parameters);
}

void Commander::deleteEntity(int id) {
	Variant parameters[1];
	parameters[0] = toVariant(id);
	
	doMakeCall(__CALLBACK_FUNCTION_DELETE_ENTITY__, 1, parameters);
}

void Commander::moveEntity(int id, int dx, int dy) {
	Variant parameters[3];
	parameters[0] = toVariant(id);
	parameters[1] = toVariant(dx);
	parameters[2] = toVariant(dy);
	
	doMakeCall(__CALLBACK_FUNCTION_MOVE_ENTITY__, 3, parameters);
}

void Commander::rotateEntity(int id, int angle) {
	Variant parameters[2];
	parameters[0] = toVariant(id);
	parameters[1] = toVariant(angle);
	
	doMakeCall(__CALLBACK_FUNCTION_ROTATE_ENTITY__, 2, parameters);
}

void Commander::callAiFunction(short aiId, int functionId, int nbParameters,
			Variant aiParameters[]) {
	Variant parameters[nbParameters + 2];
	parameters[0] = toVariant(aiId);
	parameters[1] = toVariant(functionId);
	
	for(int i = 0; i < nbParameters; i++) {
		parameters[i + 2] = toVariant(aiParameters[i]);
	}
	
	doMakeCall(__CALLBACK_FUNCTION_CALL_AI_FUNCTION__, nbParameters + 2, parameters);
}

void Commander::doMakeCall(int functionId, int nbParameters,
			Variant parameters[]) {
	int nbCalls = floor(nbParameters / 8);
	
	for (int i = 0; i < nbCalls; ++i) {
		int remainingParameters = nbParameters - i * 8;
		
		Variant parameter1 = (remainingParameters > 0) ? parameters[0] : Variant();
		Variant parameter2 = (remainingParameters > 1) ? parameters[1] : Variant();
		Variant parameter3 = (remainingParameters > 2) ? parameters[2] : Variant();
		Variant parameter4 = (remainingParameters > 3) ? parameters[3] : Variant();
		Variant parameter5 = (remainingParameters > 4) ? parameters[4] : Variant();
		Variant parameter6 = (remainingParameters > 5) ? parameters[5] : Variant();
		Variant parameter7 = (remainingParameters > 6) ? parameters[6] : Variant();
		Variant parameter8 = (remainingParameters > 7) ? parameters[7] : Variant();
		
		makeCall(
			functionId,
			nbParameters,
			parameter1,
			parameter2,
			parameter3,
			parameter4,
			parameter5,
			parameter6,
			parameter7,
			parameter8);
	}
}
