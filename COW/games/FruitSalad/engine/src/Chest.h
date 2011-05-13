#ifndef HEADER_CHEST
#define HEADER_CHEST

#include "Equipment.h"
#include "Entity.h"
#include "Map.h"

class Chest : public Entity
{
	public:
		Chest(std::pair<int,int> positionE, int idE, int typeE);
		~Chest();
		
        void dropContent(Map *map);
        void addEquipment(Equipment *equipment);
		std::vector<Equipment*> getListEquipment();
		void clear();

        std::string printC();

	protected:
        std::vector<Equipment*> listEquipment;

};


#endif
