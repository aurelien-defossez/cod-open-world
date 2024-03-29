#ifndef HEADER_FRUIT
#define HEADER_FRUIT

#include "OwnedEntity.h"
#include "Equipment.h"

class Fruit : public OwnedEntity
{
	public:
		Fruit(std::pair<int,int> positionE, int idE, int typeFruitE, Player *ownerE);
		~Fruit();
		
        void move(int xD, int yD);
        bool attack(Fruit *fruit);
        bool useEquipment(Equipment *equipment, Entity *target);
        void addEquipment(Equipment *equipment);
        void removeEquipment(Equipment *equipment);
        void addSugar(int quantity);
        void removeSugar(int quantity);
        void addVitamins(int quantity);
        void removeVitamins(int quantity);
        void useAction();
        void addHP(int nbHP);
        bool removeHP(int nbHP);
        void addDefense(int nbDefense);
        void removeDefense(int nbDefense);
        void reload();
		void updateSpeed();
        void resetAction();

        bool hasMaxHP();
        bool isHealthy();
        bool hasSugarFull();
        bool hasVitaminsFull();
        bool hasActionLeft();
        bool hasAmmoLeft();
        bool hasEquipment(int id);
		Equipment* getEquipment(int id);
        std::vector<Equipment*> getListEquipment();
        int hasPlaceLeft();
        int getSugar();
        int getVitamins();
        int getLife();
        int getDefense();
        int getSpeed();
        int getRange();

        std::string printC();

	protected:
        int life;
        int defense;
        int force;
        int range;
        int ammo;
        int capacity;
        int sugar;
		int vitamins;
        int speed;

        int maxLife;
        int maxDefense;
        int maxAmmo;
        int sugarWallet;
		int vitaminsWallet;
        int counterAction;

        std::vector<Equipment*> listEquipment;
};


#endif
