#ifndef HEADER_WEAPON
#define HEADER_WEAPON

#include "Equipment.h"

class Weapon : public Equipment
{
	public:
		Weapon(std::pair<int,int> positionE, int idE, int typeEquipmentE);
        bool use(Entity *target);
        void reload();
        std::string printC();

	protected:
        int attack;

};


#endif
