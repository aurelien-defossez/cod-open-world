#ifndef HEADER_BUILDING
#define HEADER_BUILDING

#include "Entity.h"

class Building : public Entity
{
	public:
		Building(std::pair<int,int> positionE, int idE, int typeBuildingE);
        std::string printC();

	protected:
};


#endif
