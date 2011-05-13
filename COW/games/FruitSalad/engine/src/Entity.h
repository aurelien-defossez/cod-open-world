#ifndef HEADER_ENTITY
#define HEADER_ENTITY

#include "Config.h"
#include <stdlib.h>
#include <algorithm>

class Entity
{
	public:
		Entity(Position positionE, int idE, int typeE);
        int getType();
        int getId();
        Position getPosition();
        int maximumOffset(Entity *target);
        bool isNearby(Entity *target);
        void setPosition(int xN, int yN);
        bool isObstacle();

        std::string printC();

	protected:
        Position position;
        int id;
        int type;

};


#endif
