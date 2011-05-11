#ifndef HEADER_SUGARDROP
#define HEADER_SUGARDROP

#include "Entity.h"

class SugarDrop : public Entity
{
	public:
		SugarDrop(std::pair<int,int> positionE, int idE, int typeE, int capacityE);
        bool removeSugar(int quantity);
        int getCapacity();
        std::string printC();

	protected:
        int capacity;

};


#endif
