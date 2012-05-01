#ifndef __API_CONNECTOR_H__
#define __API_CONNECTOR_H__

#include "Variant.hpp"
#include <string>

// -------------------------------------------------------------------------
// Callback function types
// -------------------------------------------------------------------------

typedef int makeReturnCallCallback(int, int,
	Variant, Variant, Variant, Variant, Variant, Variant, Variant, Variant);

// -------------------------------------------------------------------------
// Commander class
// -------------------------------------------------------------------------

class ApiConnector {
protected:
	makeReturnCallCallback *makeCall;

public:
	ApiConnector();
	
	void registerCallbacks(makeReturnCallCallback makeCall);
	
	virtual std::string decode(int code) = 0;

protected:
	Variant callGameFunction(int function, int nbParameters,
			Variant parameters[]);
};

#endif
