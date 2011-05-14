#ifndef HEADER_MAPMODIFICATIONS
#define HEADER_MAPMODIFICATIONS

#include "Config.h"

class MapModifications
{
	public:
		MapModifications();
		~MapModifications();
        void addNewModification(int *newModif);
        void addUpdatedModificationObject(int *newModif);
        void addUpdatedModificationSugar(int *newModif);
        void addDeletedModification(int *newModif);
        void addMovedModification(int *newModif);
		void reset();
		IntMatrix2* getNewObjects();
		IntMatrix1* getDeletedObjects();
		IntMatrix2* getMovedFruits();
		IntMatrix2* getModifiedFruits();
		IntMatrix2* getModifiedSugarDrops();

        std::string printC();

	protected:
        int nbNewObjects;
        std::vector<int* > newObjects;
		IntMatrix2 *matrixNewObjects;
        int nbDeletedObjects;
        std::vector<int*> deletedObjects;
		IntMatrix1 *matrixDeletedObjects;
        int nbMovedFruits;
        std::vector<int*> movedFruits;
		IntMatrix2 *matrixMovedFruits;
        int nbModifiedFruits;
        std::vector<int*> modifiedFruits;
		IntMatrix2 *matrixModifiedFruits;
        int nbModifiedSugarDrops;
        std::vector<int*> modifiedSugarDrops;
		IntMatrix2 *matrixModifiedSugarDrops;
};


#endif
