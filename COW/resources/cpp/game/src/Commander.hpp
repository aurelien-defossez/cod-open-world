#ifndef __COMMANDER_H__
#define __COMMANDER_H__

#define __CALLBACK_FUNCTION_SET_FRAME__ 1
#define __CALLBACK_FUNCTION_SET_TIMEOUT__ 2
#define __CALLBACK_FUNCTION_SET_SCORE__ 3
#define __CALLBACK_FUNCTION_INCREMENT_SCORE__ 4
#define __CALLBACK_FUNCTION_CALL_AI_FUNCTION__ 5

#include "Variant.hpp"

typedef void prepareCallCallback(int, int);
typedef void addParameterCallback(Variant);
typedef void makeCallCallback();

class Commander {
protected:
	prepareCallCallback *prepareCall;
	addParameterCallback *addParameter;
	makeCallCallback *makeCall;

public:
	Commander();
	
	void registerCallbacks(prepareCallCallback prepareCall,
			addParameterCallback addParameter, makeCallCallback makeCall);
	void setFrame();
	void setTimeout(int timeout);
	void setScore(short aiId, int score);
	void incrementScore(short aiId, int increment);

protected:
	void callAiFunction(short aiId, int function, int nbParameters,
			Variant parameters[]);
};

#endif
