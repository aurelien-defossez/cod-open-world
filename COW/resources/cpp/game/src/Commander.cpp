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
	addParameter(intVariant(timeout));
	makeCall();
}

void Commander::setScore(short aiId, int score) {
	prepareCall(__CALLBACK_FUNCTION_SET_SCORE__, 2);
	addParameter(intVariant(aiId));
	addParameter(intVariant(score));
	makeCall();
}

void Commander::incrementScore(short aiId, int increment) {
	prepareCall(__CALLBACK_FUNCTION_INCREMENT_SCORE__, 2);
	addParameter(intVariant(aiId));
	addParameter(intVariant(increment));
	makeCall();
}

void Commander::callAiFunction(short aiId, int functionId, int nbParameters,
			Variant parameters[]) {
	prepareCall(__CALLBACK_FUNCTION_CALL_AI_FUNCTION__, nbParameters + 2);
	addParameter(intVariant(aiId));
	addParameter(intVariant(functionId));
	
	for(int i = 0; i < nbParameters; i++) {
		addParameter(parameters[i]);
	}
	
	makeCall();
}
