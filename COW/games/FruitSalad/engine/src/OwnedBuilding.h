#ifndef HEADER_OWNEDBUILDING
#define HEADER_OWNEDBUILDING

#include "OwnedEntity.h"

class OwnedBuilding : public OwnedEntity
{
	public:
		OwnedBuilding(std::pair<int,int> positionE, int idE, int typeBuildingE, Player *ownerE);
        std::string printC();

	protected:

};


#endif
