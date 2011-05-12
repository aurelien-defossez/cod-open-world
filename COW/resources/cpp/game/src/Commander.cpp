#include "Commander.hpp"
#include "connector.hpp"
#include "Variant.hpp"

Commander::Commander() {
	setCommander(this);
}

void Commander::registerCallbacks(prepareCallCallback prepareCall,
			addParameterCallback addParameter, makeCallCallback makeCall) {
	this->prepareCall = prepareCall;
	this->addParameter = addParameter;
	this->makeCall = makeCall;
}

void Commander::setFrame() {
	prepareCall(__CALLBACK_FUNCTION_SET_FRAME__, 0);
	makeCall();
}

void Commander::setTimeout(int timeout) {
	prepareCall(__CALLBACK_FUNCTION_SET_TIMEOUT__, 1);
	addParameter(toVariant(timeout));
	makeCall();
}

void Commander::setScore(short aiId, int score) {
	prepareCall(__CALLBACK_FUNCTION_SET_SCORE__, 2);
	addParameter(toVariant(aiId));
	addParameter(toVariant(score));
	makeCall();
}

void Commander::incrementScore(short aiId, int increment) {
	prepareCall(__CALLBACK_FUNCTION_INCREMENT_SCORE__, 2);
	addParameter(toVariant(aiId));
	addParameter(toVariant(increment));
	makeCall();
}

void Commander::displayGrid(int x0, int y0, int x1, int y1, int xSpacing,
		int ySpacing, int color, bool temporary) {
	prepareCall(__CALLBACK_FUNCTION_DISPLAY_GRID__, 8);
	addParameter(toVariant(x0));
	addParameter(toVariant(y0));
	addParameter(toVariant(x1));
	addParameter(toVariant(y1));
	addParameter(toVariant(xSpacing));
	addParameter(toVariant(ySpacing));
	addParameter(toVariant(color));
	addParameter(toVariant(temporary));
	makeCall();
}

void Commander::drawLine(int x0, int y0, int x1, int y1, int color,
		bool temporary) {
	prepareCall(__CALLBACK_FUNCTION_DRAW_LINE__, 6);
	addParameter(toVariant(x0));
	addParameter(toVariant(y0));
	addParameter(toVariant(x1));
	addParameter(toVariant(y1));
	addParameter(toVariant(color));
	addParameter(toVariant(temporary));
	makeCall();
}

void Commander::drawCircle(int x, int y, int radius, int samples, int color,
	bool temporary) {
	prepareCall(__CALLBACK_FUNCTION_DRAW_CIRCLE__, 6);
	addParameter(toVariant(x));
	addParameter(toVariant(y));
	addParameter(toVariant(radius));
	addParameter(toVariant(samples));
	addParameter(toVariant(color));
	addParameter(toVariant(temporary));
	makeCall();
}

void Commander::deleteTemporaryShapes() {
	prepareCall(__CALLBACK_FUNCTION_DELETE_TEMPORARY_SHAPES__, 0);
	makeCall();
}

void Commander::createEntity(int definitionId, int id) {
	prepareCall(__CALLBACK_FUNCTION_CREATE_ENTITY__, 2);
	addParameter(toVariant(definitionId));
	addParameter(toVariant(id));
	makeCall();
}

void Commander::deleteEntity(int id) {
	prepareCall(__CALLBACK_FUNCTION_DELETE_ENTITY__, 1);
	addParameter(toVariant(id));
	makeCall();
}

void Commander::moveEntity(int id, int dx, int dy) {
	prepareCall(__CALLBACK_FUNCTION_MOVE_ENTITY__, 3);
	addParameter(toVariant(id));
	addParameter(toVariant(dx));
	addParameter(toVariant(dy));
	makeCall();
}

void Commander::rotateEntity(int id, int angle) {
	prepareCall(__CALLBACK_FUNCTION_ROTATE_ENTITY__, 2);
	addParameter(toVariant(id));
	addParameter(toVariant(angle));
	makeCall();
}

#include <iostream>

void Commander::callAiFunction(short aiId, int functionId, int nbParameters,
			Variant parameters[]) {
	std::cout << "callAiFunction" << std::endl;
	prepareCall(__CALLBACK_FUNCTION_CALL_AI_FUNCTION__, nbParameters + 2);
	addParameter(toVariant(aiId));
	addParameter(toVariant(functionId));
	
	for(int i = 0; i < nbParameters; i++) {
		addParameter(parameters[i]);
	}
	
	makeCall();
	std::cout << "callAiFunction over" << std::endl;
}
