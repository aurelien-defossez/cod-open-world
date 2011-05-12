#ifndef HEADER_PLAYER
#define HEADER_PLAYER

#include "MapModifications.h"
#include "Config.h"


class Player
{
	public:
		Player();
		~Player();
		
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
		int getId();
        int getIdSugarBowl();
        int getIdFructificationTank();
        int getIdJuiceBarrel();
        void setIdSugarBowl(int id);
        void setIdFructificationTank(int id);
        void setIdJuiceBarrel(int id);
        void setCurrentPlayer(bool state);
		void setInfos(short aiId, char *aiName, char *playerName);
		void resetMapModifications();
		
		IntMatrix2 getNewObjects();
		IntMatrix1 getDeletedObjects();
		IntMatrix2 getMovedFruits();
		IntMatrix2 getModifiedFruits();
		IntMatrix2 getModifiedSugarDrops();
		void setCounts(int countCherryE, int countKiwiE, int countNutE);
		int getCountCherry();
		int getCountKiwi();
		int getCountNut();

	protected:
        MapModifications *mapModifications;

        int sugarQuantity;
        int vitaminsQuantity;
        bool isCurrent;
		char* namePlayer;
		char* nameIA;
		short idIA;

        int idSugarBowl;
        int idFructificationTank;
        int idJuiceBarrel;
		int countCherry;
		int countKiwi;
		int countNut;
};


#endif
