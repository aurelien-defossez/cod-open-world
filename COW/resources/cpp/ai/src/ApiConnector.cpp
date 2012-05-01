#include "ApiConnector.hpp"
#include "connector.hpp"
#include "Variant.hpp"
#include <math.h>

ApiConnector::ApiConnector() {
	setApiConnector(this);
}

void ApiConnector::registerCallbacks(makeReturnCallCallback makeCall) {
	this->makeCall = makeCall;
}

Variant ApiConnector::callGameFunction(int functionId, int nbParameters,
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
		
		return toVariant(makeReturnCall(
			functionId,
			nbParameters,
			parameter1,
			parameter2,
			parameter3,
			parameter4,
			parameter5,
			parameter6,
			parameter7,
			parameter8));
	}
}
