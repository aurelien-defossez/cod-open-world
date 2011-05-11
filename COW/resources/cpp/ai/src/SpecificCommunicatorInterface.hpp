#ifndef __SPECIFIC_COMMUNICATOR_INTERFACE_H__
#define __SPECIFIC_COMMUNICATOR_INTERFACE_H__

#include "Variant.hpp"

// -------------------------------------------------------------------------
// SpecificGameInterface interface
// -------------------------------------------------------------------------

class SpecificCommunicatorInterface {
public:
	virtual void performAiFunction(int functionId, int nbParameters,
		Variant parameters[]) = 0;
	virtual void stop() = 0;
};

#endif
