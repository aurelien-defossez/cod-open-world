#ifndef HEADER_PEELER
#define HEADER_PEELER

#include "Equipment.h"

class Peeler : public Equipment
{
	public:
		Peeler(std::pair<int,int> positionE, int idE,int typeEquipmentE);
        bool use(Entity *target);

	protected:

};


#endif
