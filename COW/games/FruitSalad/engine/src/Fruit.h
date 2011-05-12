#ifndef HEADER_FRUIT
#define HEADER_FRUIT

#include "Weapon.h"
#include "Peeler.h"
#include "Loader.h"
#include "OwnedEntity.h"

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
        void useAction();
        void addHP(int nbHP);
        bool removeHP(int nbHP);
        void addDefense(int nbDefense);
        void removeDefense(int nbDefense);
        void reload();
        void die();
        void resetAction();

        bool hasMaxHP();
        bool isHealthy();
        bool hasSugarFull();
        bool hasActionLeft();
        bool hasAmmoLeft();
        bool hasEquipment(Equipment *equipment);
        int hasPlaceLeft();
        int getSugar();
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
        int speed;

        int maxLife;
        int maxDefense;
        int maxAmmo;
        int sugarWallet;
        int counterAction;

        std::vector<Equipment*> listEquipment;
};


#endif
