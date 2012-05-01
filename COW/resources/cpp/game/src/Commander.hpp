#ifndef __COMMANDER_H__
#define __COMMANDER_H__

#include "Variant.hpp"

// -------------------------------------------------------------------------
// Platform Callback functions IDs
// -------------------------------------------------------------------------

// Platform functions
#define __CALLBACK_FUNCTION_SET_FRAME__ 1
#define __CALLBACK_FUNCTION_SET_TIMEOUT__ 2
#define __CALLBACK_FUNCTION_SET_SCORE__ 3
#define __CALLBACK_FUNCTION_INCREMENT_SCORE__ 4
#define __CALLBACK_FUNCTION_CALL_AI_FUNCTION__ 5
#define __CALLBACK_FUNCTION_THROW_EXCEPTION__ 6
#define __CALLBACK_FUNCTION_STOP_AI__ 7

// View shapes functions
#define __CALLBACK_FUNCTION_DISPLAY_GRID__ 20
#define __CALLBACK_FUNCTION_DRAW_LINE__ 21
#define __CALLBACK_FUNCTION_DRAW_RECTANGLE__ 22
#define __CALLBACK_FUNCTION_DRAW_CIRCLE__ 23
#define __CALLBACK_FUNCTION_DELETE_TEMPORARY_SHAPES__ 30

// View entities functions
#define __CALLBACK_FUNCTION_CREATE_ENTITY__ 50
#define __CALLBACK_FUNCTION_DELETE_ENTITY__ 51
#define __CALLBACK_FUNCTION_MOVE_ENTITY__ 52
#define __CALLBACK_FUNCTION_ROTATE_ENTITY__ 53

// -------------------------------------------------------------------------
// Callback function types
// -------------------------------------------------------------------------

typedef void makeCallCallback(int, int,
	Variant, Variant, Variant, Variant, Variant, Variant, Variant, Variant);

// -------------------------------------------------------------------------
// Commander class
// -------------------------------------------------------------------------

class Commander {
protected:
	makeCallCallback *makeCall;

public:
	Commander();
	
	void registerCallbacks(makeCallCallback makeCall);
	void setFrame();
	void setTimeout(int timeout);
	void setScore(short aiId, int score);
	void incrementScore(short aiId, int increment);
	void stopAi(short aiId);
	void throwException(char *message);
	
	void displayGrid(int x0, int y0, int x1, int y1, int xSpacing, int ySpacing,
		int color, bool temporary);
	void drawLine(int x0, int y0, int x1, int y1, int color, bool temporary);
	void drawCircle(int x, int y, int radius, int samples, int color,
		bool temporary);
	void deleteTemporaryShapes();
	
	void createEntity(int definitionId, int id);
	void deleteEntity(int id);
	void moveEntity(int id, int dx, int dy);
	void rotateEntity(int id, int angle);

protected:
	void callAiFunction(short aiId, int function, int nbParameters,
			Variant parameters[]);

private:
	void doMakeCall(int functionId, int nbParameters,
			Variant parameters[]);
};

#endif
