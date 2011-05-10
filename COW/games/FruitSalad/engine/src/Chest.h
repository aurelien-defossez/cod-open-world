#ifndef HEADER_CHEST
#define HEADER_CHEST

#include "Equipment.h"
#include "Entity.h"
#include "Map.h"

class Chest : public Entity
{
	public:
		Chest(std::pair<int,int> positionE, int idE, int typeE);
        void dropContent(Map *map);
        void addEquipment(Equipment *equipment);

        std::string printC();

	protected:
        std::vector<Equipment*> listEquipment;

};


#endif
