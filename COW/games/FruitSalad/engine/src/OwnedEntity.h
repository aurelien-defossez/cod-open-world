#ifndef HEADER_OWNEDENTITY
#define HEADER_OWNEDENTITY

#include "Entity.h"
#include "Player.h"

class OwnedEntity : public Entity
{
	public:
		OwnedEntity(std::pair<int,int> positionE, int idE, int typeE, Player *ownerE);
        Player* getOwner();

	protected:
        Player *owner;

};

#endif
