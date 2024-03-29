#ifndef HEADER_MAP
#define HEADER_MAP

#include "Player.h"
#include "Entity.h"
#include "Equipment.h"
#include "Fruit.h"
#include "Config.h"


class Map
{
	public:
		Map(SpecificCommander *commanderE);
		~Map();

        void addNewModification(int *newModif);
        void addUpdatedModificationObject(int *newModif);
        void addUpdatedModificationSugar(int *newModif);
        void addDeletedModification(int *newModif);
        void addMovedModification(int *newModif);
		
        void addSugar(Player *player, int quantity);
		
        int addSugarDrop(int x, int y, int quantity);
        void createPlayers(int nbPlayers);
        Equipment* createEquipment(int equipmentType, int x, int y);
        int createFruit(int fruitType, int x, int y, Player *owner);
        void createWalls(int x0, int y0, int x1, int y1);
		
        void addEntity(Entity *entity);
        void moveEntity(Entity *entity, int x, int y);
        void removeEntity(Entity *entity);
        Entity* getEntity(int id);

        int verifyId(int id);
        bool verifyBuilding(Fruit *fruit, int buildingType);
        bool verifyPosition(int x, int y);
        bool verifySource();
		
        bool contains(int x, int y);
        bool isWall(int x, int y);
        std::pair<int,int> getValidSquare(int x, int y, int distance);
        bool canHit(Fruit* fruit, int range, Fruit* target);
        bool detectObstacle(std::vector<std::pair<int,int> > positions);
		bool checkObstacle(int x, int y);
		int distanceBetween(Entity *entity, int x, int y, int maxDistance = -1);
		
        std::vector<std::pair<int,int> > drawLine(int x0, int y0, int x1, int y1);
		
		void dropSugarRandomly();
		void distributePossessions(int x, int y, Entity* entity);
		IntMatrix2* getArchitecture();
		IntMatrix2* getObjectsDropped();
		IntMatrix2* getSugarUpdated();
		bool verifyNbFruit(int fruitType, Player *owner);
		void setLimitCherry(int lim);
		void setLimitKiwi(int lim);
		void setLimitNut(int lim);
		int getLimitCherry();
		int getLimitKiwi();
		int getLimitNut();

        void setDimensions(int h, int w);
		void setConfig(int maxVitaminsE, int sugarEjectedE, int nbToursE);
		int getWidth();
        int getHeight();
		int getMaxVitamins();
        int getNbTours();
        void setCurrentId(int id);
        std::vector<Player*> getListPlayers();
		int getCurrentPlayer();
		void endTurn();

        std::string printC();

	protected:
        std::vector<Player*> listPlayers;
        std::multimap<std::pair<int,int>, Entity* > mapPositions;
        std::map<int,Entity*> mapIds;
		bool **mapWalls;
		IntMatrix2 *architecture;
		IntMatrix2 *objectsDropped;
		IntMatrix2 *sugarUpdated;
		std::vector<int*> objectsDroppedVector;
		std::vector<int*> sugarUpdatedVector;

        int currentId;
		bool dropping;
        int width;
        int height;
		int maxVitamins;
		int sugarEjected;
		int nbTours;
		
		int countFruits;
		int limitCherry;
		int limitKiwi;
		int limitNut;

        int idVitaminSource;
        int idSugarTree;
		SpecificCommander *commander;
		
		bool aStarInitialized;
		int **distances;
		bool **visited;
};


#endif
