#include "ApiConnector.hpp"
#include "connector.hpp"
#include "Variant.hpp"

// TODO TEMP
#include <iostream>
using namespace std;

ApiConnector::ApiConnector() {
	setApiConnector(this);
}

void ApiConnector::registerCallbacks(prepareCallCallback prepareCall,
			addParameterCallback addParameter, makeCallCallback makeCall) {
	this->prepareCall = prepareCall;
	this->addParameter = addParameter;
	this->makeCall = makeCall;
}

Variant ApiConnector::callGameFunction(int functionId, int nbParameters,
			Variant parameters[]) {
	prepareCall(functionId, nbParameters);
	
	for(int i = 0; i < nbParameters; i++) {
		addParameter(parameters[i]);
	}
	
	cout << "Making call..." << endl;
	int value = makeCall();
	cout << "Call made, value = " << value << endl;
	return toVariant(value);
}
