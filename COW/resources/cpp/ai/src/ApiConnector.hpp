#ifndef __API_CONNECTOR_H__
#define __API_CONNECTOR_H__

#include "Variant.hpp"
#include <string>

// -------------------------------------------------------------------------
// Callback function types
// -------------------------------------------------------------------------

typedef void prepareCallCallback(int, int);
typedef void addParameterCallback(Variant);
typedef int makeCallCallback();
typedef int makeCompleteCallCallback(int, int,
	Variant, Variant, Variant, Variant, Variant, Variant, Variant, Variant);

// -------------------------------------------------------------------------
// Commander class
// -------------------------------------------------------------------------

class ApiConnector {
protected:
	prepareCallCallback *prepareCall;
	addParameterCallback *addParameter;
	makeCallCallback *makeCall;
	makeCompleteCallCallback *makeCompleteCall;

public:
	ApiConnector();
	
	void registerCallbacks(prepareCallCallback prepareCall,
			addParameterCallback addParameter, makeCallCallback makeCall,
			makeCompleteCallCallback makeCompleteCall);
	
	virtual std::string decode(int code) = 0;

protected:
	Variant callGameFunction(int function, int nbParameters,
			Variant parameters[]);
};

#endif
