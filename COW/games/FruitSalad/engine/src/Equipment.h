#ifndef HEADER_EQUIPMENT
#define HEADER_EQUIPMENT

#include "Entity.h"

class Equipment : public Entity
{
	public:
		Equipment(std::pair<int,int> positionE, int idE, int typeEquipmentE);
        bool use(Entity *target);
        bool hasAmmoLeft();
        int getWeight();
        int getSellValue();
        int getCost();
        int getRange();

	protected:
        int range;
        int ammo;
        int cost;
        int sellValue;
        int ammoValue;
        int weight;
        int ammoWeight;

        int maxAmmo;
};


#endif
