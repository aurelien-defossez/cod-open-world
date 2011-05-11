#ifndef HEADER_ENTITY
#define HEADER_ENTITY

#include "Config.h"
#include <stdlib.h>
#include <algorithm>

class Entity
{
	public:
		Entity(std::pair<int,int> positionE, int idE, int typeE);
        int getType();
        int getId();
        std::pair<int,int> getPosition();
        int maximumOffset(Entity *target);
        bool isNearby(Entity *target);
        void setPosition(int xN, int yN);
        bool isObstacle();

        std::string printC();

	protected:
        int x;
        int y;
        int id;
        int type;

};


#endif
