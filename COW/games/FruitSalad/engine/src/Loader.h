#ifndef HEADER_LOADER
#define HEADER_LOADER

#include "Equipment.h"
#include "Weapon.h"

class Loader : public Equipment
{
	public:
		Loader(std::pair<int,int> positionE, int idE, int typeEquipmentE);
        bool use(Entity *target);

	protected:

};


#endif
