#ifndef HEADER_SUGARFOUNTAIN
#define HEADER_SUGARFOUNTAIN

#include "Building.h"
#include "SugarDrop.h"
#include "Map.h"

class SugarFountain : public Building
{
	public:
		SugarFountain(std::pair<int,int> positionE, int idE, int typeBuildingE);
        void dropSugarRandomly(Map* map);

	protected:

};


#endif
