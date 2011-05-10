#ifndef HEADER_PLAYER
#define HEADER_PLAYER

#include "MapModifications.h"
#include "Config.h"


class Player
{
	public:
		Player();
        void addNewModification(int *newModif);
        void addUpdatedModificationObject(int *newModif);
        void addUpdatedModificationSugar(int *newModif);
        void addDeletedModification(int *newModif);
        void addMovedModification(int *newModif);

        bool isCurrentPlayer();
        void addSugar(int nbSugar);
        void removeSugar(int nbSugar);
        void addVitamins(int nbVitamins);
        void removeVitamins(int nbVitamins);

        int hasEnough(int nbSugar, int nbVitamins);
        bool verifyNbFruit(int fruitType);
        int getIdSugarBowl();
        int getIdFructificationTank();
        int getIdJuiceBarrel();
        void setIdSugarBowl(int id);
        void setIdFructificationTank(int id);
        void setIdJuiceBarrel(int id);
        void setCurrentPlayer(bool state);
		void resetMapModifications();

	protected:
        MapModifications *mapModifications;

        int sugarQuantity;
        int vitaminsQuantity;
        bool isCurrent;

        int idSugarBowl;
        int idFructificationTank;
        int idJuiceBarrel;
};


#endif
