#ifndef HEADER_MAPMODIFICATIONS
#define HEADER_MAPMODIFICATIONS

#include <vector>
#include <utility>
#include <map>
#include <string>

class MapModifications
{
	public:
		MapModifications();
        void addNewModification(int *newModif);
        void addUpdatedModificationObject(int *newModif);
        void addUpdatedModificationSugar(int *newModif);
        void addDeletedModification(int *newModif);
        void addMovedModification(int *newModif);
		void reset();

        std::string printC();

	protected:
        int nbNewObjects;
        std::vector<int* > newObjects;

        int nbDeletedObjects;
        std::vector<int*> deletedObjects;

        int nbMovedFruits;
        std::vector<int*> movedFruits;

        int nbModifiedFruits;
        std::vector<int*> modifiedFruits;

        int nbModifiedSugarDrops;
        std::vector<int*> modifiedSugarDrops;

};


#endif
