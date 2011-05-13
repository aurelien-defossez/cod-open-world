#include "Game.hpp"

#include "FruitSaladEngine.hpp"
#include "SpecificCommunicator.hpp"
#include "SpecificCommander.hpp"
#include <iostream>
#include <string>
#include <stdlib.h>
#include <algorithm>
#include "MapLoader.h"
#include "Fruit.h"
#include "SugarDrop.h"
#include "Equipment.h"
#include "Weapon.h"
#include "Loader.h"
#include "Peeler.h"

using namespace std;

Game game = Game();

Game::Game() {
	communicator = new SpecificCommunicator(this);
	commander = new SpecificCommander();
	
	map = new Map(commander);
	mapLoaded = false;
	
    srand(time(NULL));
}

Game::~Game() {
	delete commander;
	delete communicator;
	delete map;
}

void Game::init(int nbParameters, char *parameters[]) {
	if(nbParameters == 0) {
		cout << "FruitSalad: missing parameter (map)" << endl;
	} else {
		MapLoader mapLoader = MapLoader(map, commander);
		string mapPath("games/FruitSalad/engine/maps/");
		mapPath.append(parameters[0]);
		mapLoader.loadMap(mapPath.c_str());
		mapLoaded = true;
	}
	
	drawLine(0, 0, 1, 0, COLOR_RED);
	drawLine(2, 3, 4, 42, COLOR_VIOLET);
	drawCircle(10, 10, 1, COLOR_GREEN);
	drawCircle(10, 10, 2, COLOR_ORANGE);
}

void Game::addAi(short aiId, char *aiName, char *playerName) {
	if(mapLoaded)
	{
		map->getListPlayers()[(int) aiId]->setInfos(aiId, aiName, playerName);
	}
}

void Game::play() {
	cout << "Play..." << endl;
	
	cout << "setFrame" << endl;
	commander->setFrame();
	

	if(mapLoaded)
	{
		
		int nbTours = 10;
		int currentPlayer = 0;
		int nbPlayers = map->getListPlayers().size();
		
		IntMatrix2 *archi = map->getArchitecture();
		int limitCherry = map->getLimitCherry();
		int limitKiwi = map->getLimitKiwi();
		int limitNut = map->getLimitNut();
		
		for (currentPlayer=0; currentPlayer<nbPlayers; currentPlayer++)
		{
			IntMatrix2 *fruits = map->getListPlayers()[currentPlayer]->getMatrixFruits();
			IntMatrix2 *buildings = map->getListPlayers()[currentPlayer]->getMatrixBuildings();
			commander->initGame(currentPlayer, archi, fruits, buildings, limitCherry, limitKiwi, limitNut);
		}

		
		for (int currentTour=0; currentTour<nbTours; currentTour++)
		{
			for (currentPlayer=0; currentPlayer<nbPlayers; currentPlayer++)
			{
				//On passe le joueur à joueur actif
				map->getListPlayers()[currentPlayer]->setCurrentPlayer(true);
				cout << "recuperation" << endl;
				//On récupère les données à lui fournir
				IntMatrix2 *newObjects = map->getListPlayers()[currentPlayer]->getNewObjects();
				IntMatrix1 *deletedObjects = map->getListPlayers()[currentPlayer]->getDeletedObjects();
				IntMatrix2 *movedFruits = map->getListPlayers()[currentPlayer]->getMovedFruits();
				IntMatrix2 *modifiedFruits = map->getListPlayers()[currentPlayer]->getModifiedFruits();
				IntMatrix2 *modifiedSugarDrops = map->getListPlayers()[currentPlayer]->getModifiedSugarDrops();
				cout << "resetModifs" << endl;
				//On reset les modifications qu'on vient de lui envoyer
				map->getListPlayers()[currentPlayer]->resetMapModifications();
				cout << "playTurn" << endl;
				//On le fait jouer
				commander->playTurn(currentPlayer, newObjects, deletedObjects, movedFruits, modifiedFruits, modifiedSugarDrops);
				cout << "player false" << endl;
				//On remet le joueur en passif
				map->getListPlayers()[currentPlayer]->setCurrentPlayer(false);
				
			}
			cout << "Fin boucle playTurn" << endl;
			//On remet le compteur d'action de tous les fruits à 0
			map->endTurn();
			cout << "endTurn fait" << endl;
			map->dropSugarRandomly();
			cout << "dropSugar Fait" << endl;
		}
	/*
	map->getListPlayers()[0]->setCurrentPlayer(true);
	//cout << move(8,2,2) << endl;
	//commander->setFrame();
	//cout << attack(9,11) << endl;
	//commander->setFrame();
	map->printC();
	//cout << pickUpEquipment(8,14) << endl;
	Fruit *fruit = (Fruit*) map->getEntity(8);
	cout << fruit->printC() << endl;
	//cout << useEquipment(8,14,11) << endl;
	//cout << fruit->printC() << endl;
	//fruit = (Fruit*) map->getEntity(11);
	//cout << fruit->printC() << endl;
	//fruit = (Fruit*) map->getEntity(8);
	//fruit->resetAction();
	//cout << dropEquipment(8,14,4,4) << endl;
	//cout << fruit->printC() << endl;
	//cout << pickUpSugar(8,15) << endl;
	//cout << pickUpSugar(8,15) << endl;
	//map->printC();
	//fruit->resetAction();
	//cout << dropSugar(8,2,3,3) << endl;
	//cout << fruit->printC() << endl;
	cout << openChest(8,14) << endl;
	map->printC();
	commander->setFrame();
	
	for (currentPlayer=0; currentPlayer<nbPlayers; currentPlayer++)
	{
		map->getListPlayers()[currentPlayer]->resetMapModifications();

	}
	*/
	}
}

void Game::endGame() {
	cout << "End game order received." << endl;
}

void Game::disqualifyAi(char *aiName, char *reason) {
	cout << "Disqualifying AI " << aiName << " because of " << reason << endl;
}

// User-defined functions
int Game::move(int fruitId, int x, int y) {
	// entity non defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // position not in the map
    if (map->contains(x,y) == false)
    {
        return NOT_VALID_POSITION;
    }
    // position not valid
    if (map->verifyPosition(x,y) == false)
    {
        return NOT_VALID_DESTINATION;
    }
    // target not in range
    std::pair<int,int> position;
    position.first = x;
    position.second = y;
    if (map->distanceBetween(fruit, x, y, fruit->getSpeed()) == -1)
    {
        return TOO_FAR;
    }

	// use action
	fruit->useAction();

    //creation of modification for all players
	int *modif = new int[5];
	modif[0] = fruit->getId();
	modif[1] = fruit->getPosition().first;
	modif[2] = fruit->getPosition().second;
	modif[3] = x;
	modif[4] = y;
	map->addMovedModification(modif);

    // move
    fruit->move(x, y);
	map->moveEntity(fruit, x, y);
	commander->setFrame();
    return OK;
}

int Game::attack(int fruitId, int targetFruitId) {
	// entity non defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // target is not a fruit
    if ((map->verifyId(targetFruitId) != FRUIT_CHERRY) && (map->verifyId(targetFruitId) != FRUIT_KIWI) && (map->verifyId(targetFruitId) != FRUIT_NUT))
    {
        return NOT_VALID_TARGET;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // cast in Fruit* of target
    Fruit *target = (Fruit*)(map->getEntity(targetFruitId));
    // target not in range
    if (fruit->maximumOffset(target) > fruit->getRange())
    {
        return TOO_FAR;
    }
    // target not visible
    if (!(map->canHit(fruit, fruit->getRange(), target)))
    {
        return OBSTACLE_PRESENT;
    }


	// use action
	fruit->useAction();

    // attack: hit
    if (fruit->attack(target) == false)
    {
        //creation of modification for all players
        int *modif = new int[3];
        modif[0] = target->getId();
        modif[1] = target->getLife();
        modif[2] = target->getDefense();
        map->addUpdatedModificationObject(modif);

        return HIT;
    }
    // attack: splatch
    else
    {
        //creation of modification for all players
        int *modif = new int[3];
        modif[0] = target->getId();
        modif[1] = target->getPosition().first;
        modif[2] = target->getPosition().second;
        map->addDeletedModification(modif);

        map->removeEntity(target);

        return SPLATCHED;
    }
}

int Game::useEquipment(int fruitId, int equipmentId, int targetId) {
	// entity not defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit has not the equipment
    if (fruit->hasEquipment(equipmentId) == false)
    {
        return NOT_EQUIPPED;
    }
    Equipment *equipment = fruit->getEquipment(equipmentId);
    // equipment is a weapon and target is not fruit
    if ((equipment->getType() >= EQUIPMENT_TEA_SPOON) && (equipment->getType() <= EQUIPMENT_JUICE_NEEDLE)
        && (map->verifyId(targetId) != FRUIT_CHERRY) && (map->verifyId(targetId) != FRUIT_KIWI) && (map->verifyId(targetId) != FRUIT_NUT))
    {
        return NOT_VALID_TARGET;
    }
    // equipment is a reloader and target is not a weapon or a kiwi
    if ((equipment->getType() == EQUIPMENT_RELOADER) &&
        (map->verifyId(targetId) != FRUIT_KIWI) &&
        ((equipment->getType() < EQUIPMENT_TEA_SPOON) || (equipment->getType() > EQUIPMENT_JUICE_NEEDLE)))
    {
        return NOT_VALID_TARGET;
    }
    // Cast with the good Entity
    bool reloading = false;
    bool targetFruit = false;
    Entity *target;
    // equipment is a weapon, target is a fruit
    if (((equipment->getType() >= EQUIPMENT_TEA_SPOON) && (equipment->getType() <= EQUIPMENT_SALT_SNIPER)) 
	  || (equipment->getType() == EQUIPMENT_JUICE_NEEDLE))
    {
		target = (Fruit*)(map->getEntity(targetId));
        equipment = (Weapon*)equipment;
        targetFruit = true;
    }
    // equipment is a reloader and target a kiwi
    else if ((equipment->getType() == EQUIPMENT_RELOADER) && (map->verifyId(targetId) == FRUIT_KIWI))
    {
		target = (Fruit*)(map->getEntity(targetId));
        equipment = (Loader*)equipment;
        reloading = true;
        targetFruit = true;
    }
    // equipment is a peeler
    else if (equipment->getType() == EQUIPMENT_PEELER)
    {
		target = (Fruit*)(map->getEntity(targetId));
        equipment = (Peeler*)equipment;
        targetFruit = true;
    }
    // equipment is a reloader, target is a weapon
    else
    {
		target = (Weapon*)(map->getEntity(targetId));
        equipment = (Loader*)equipment;
        reloading = true;
    }
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    if (targetFruit)
    {
        // target not in range
        if (fruit->maximumOffset(target) > equipment->getRange())
        {
            return TOO_FAR;
        }
        // target not visible
        if (!(map->canHit(fruit, equipment->getRange(), (Fruit*)target)))
        {
            return OBSTACLE_PRESENT;
        }
    }

	// use action
	fruit->useAction();

    // use equipment
    if (fruit->useEquipment(equipment, target) == false)
    {
        if (reloading == true)
        {
            map->removeEntity(equipment);
            return RELOADED;
        }
        else
        {
            //creation of modification for all players
            Fruit *fruitTarget = (Fruit*) target;
            int *modif = new int[3];
            modif[0] = fruitTarget->getId();
            modif[1] = fruitTarget->getLife();
            modif[2] = fruitTarget->getDefense();
            map->addUpdatedModificationObject(modif);

            return HIT;
        }
    }
    // splatch
    else
    {
        //creation of modification for all players
        int *modif = new int[3];
        modif[0] = target->getId();
        modif[1] = target->getPosition().first;
        modif[2] = target->getPosition().second;
        map->addDeletedModification(modif);

        map->removeEntity(target);

        return SPLATCHED;
    }
}

int Game::pickUpEquipment(int fruitId, int equipmentId) {
	// entity not defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // equipment is not an equipment
    if ((map->verifyId(equipmentId) < EQUIPMENT_TEA_SPOON) || (map->verifyId(equipmentId) > EQUIPMENT_RELOADER))
    {
        return NOT_EQUIPMENT;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    Equipment *equipment = (Equipment*)(map->getEntity(equipmentId));
    // target not in range
    if (!(fruit->isNearby(equipment)))
    {
        return TOO_FAR;
    }
    // check weight
    if (fruit->hasPlaceLeft() < equipment->getWeight())
    {
        return TOO_HEAVY;
    }

	// use action
	fruit->useAction();

    // add equipment
    fruit->addEquipment(equipment);
    map->removeEntity(equipment);

    //creation of modification for all players
    int *modif = new int[4];
    modif[0] = equipment->getId();
    modif[1] = equipment->getPosition().first;
    modif[2] = equipment->getPosition().second;
    modif[3] = equipment->getType();
    map->addDeletedModification(modif);
	commander->setFrame();

    return OK;
}

int Game::dropEquipment(int fruitId, int equipmentId, int x, int y) {
	// entity non defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit has not the equipment
    if (fruit->hasEquipment(equipmentId) == false)
    {
        return NOT_EQUIPPED;
    }
    Equipment *equipment = fruit->getEquipment(equipmentId);
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // position not in the map
    if (map->contains(x,y) == false)
    {
        return NOT_VALID_POSITION;
    }
    // position not valid
    if (map->verifyPosition(x,y) == false)
    {
        return NOT_VALID_DESTINATION;
    }
    // target not in range
    std::pair<int,int> position;
    position.first = x;
    position.second = y;
    if (map->distanceBetween(fruit, x, y, 1) == -1)
    {
        return TOO_FAR;
    }

	// use action
	fruit->useAction();

    // drop
    fruit->removeEquipment(equipment);
    equipment->setPosition(x,y);
	map->addEntity(equipment);
	commander->createEntity((equipment->getType()+41), equipment->getId());
	commander->moveEntity(equipment->getId(), 2 * equipment->getPosition().first, 2 * equipment->getPosition().second);

	//creation of modification for all players
    int *modif = new int[5];
    modif[0] = equipment->getId();
    modif[1] = x;
    modif[2] = y;
    modif[3] = equipment->getType();
	modif[4] = equipment->getAmmo();
    map->addNewModification(modif);
	commander->setFrame();

    return OK;
}

int Game::pickUpSugar(int fruitId, int sugarDropId) {
	// entity not defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // equipment is not an equipment
    if (map->verifyId(sugarDropId) != SUGAR_DROP)
    {
        return NOT_SUGAR_DROP;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    SugarDrop *sugarDrop = (SugarDrop*)(map->getEntity(sugarDropId));
    // target not in range
    if (!(fruit->isNearby(sugarDrop)))
    {
        return TOO_FAR;
    }
    // check weight
    if (fruit->hasSugarFull() == true)
    {
        return SUGAR_WALLET_FULL;
    }

	// use action
	fruit->useAction();

    // add sugar
    fruit->addSugar(1);
    if (sugarDrop->removeSugar(1) == false)
    {
		int *modif = new int[2];
		modif[0] = sugarDrop->getId();
		modif[1] = sugarDrop->getCapacity();
		map->addUpdatedModificationSugar(modif);  
		return SOME_SUGAR_TAKEN;
    }
    map->removeEntity(sugarDrop);

    //creation of modification for all players (delete ici)
    int *modif = new int[3];
	modif[0] = sugarDrop->getId();
	modif[1] = sugarDrop->getPosition().first;
	modif[2] = sugarDrop->getPosition().second;
	map->addDeletedModification(modif);
	
	delete sugarDrop;
	commander->setFrame();

    return ALL_SUGAR_TAKEN;
}

int Game::dropSugar(int fruitId, int quantity, int x, int y) {
	// entity non defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has not the equipment
    if (fruit->getSugar() < quantity)
    {
        return NOT_VALID_QUANTITY;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // position not in the map
    if (map->contains(x,y) == false)
    {
        return NOT_VALID_POSITION;
    }
    // position not valid
    if (map->verifyPosition(x,y) == false)
    {
        return NOT_VALID_DESTINATION;
    }
    // target not in range
    std::pair<int,int> position;
    position.first = x;
    position.second = y;
    if (map->distanceBetween(fruit, x, y, 1) == -1)
    {
        return TOO_FAR;
    }

	// use action
	fruit->useAction();

    // drop
    fruit->removeSugar(quantity);
	int idSugar = map->addSugarDrop(x, y, quantity);
	commander->setFrame();

    return OK;
}

int Game::openChest(int fruitId, int chestId) {
	// entity not defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // equipment is not an equipment
    if (map->verifyId(chestId) != CHEST)
    {
        return NOT_CHEST;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    Chest *chest = (Chest*)(map->getEntity(chestId));
    // target not in range
    if (!(fruit->isNearby(chest)))
    {
        return TOO_FAR;
    }

	// use action
	fruit->useAction();

    // add sugar
    chest->dropContent(map);
	sendOpenedChest(chest);
	commander->setFrame();
    return OK;
}

int Game::stockSugar(int fruitId) {
	// entity non defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // target not in range
    if (map->verifyBuilding(fruit, BUILDING_SUGAR_BOWL) == false)
    {
        return TOO_FAR;
    }

	// use action
	fruit->useAction();

    // stock
	map->addSugar(fruit->getOwner(), fruit->getSugar());
    fruit->removeSugar(fruit->getSugar());
    return OK;
}

int Game::sellEquipment(int fruitId, int equipmentId) {
	// entity non defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit has not the equipment
    if (fruit->hasEquipment(equipmentId) == false)
    {
        return NOT_EQUIPPED;
    }
    Equipment *equipment = fruit->getEquipment(equipmentId);
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // target not in range
    if (map->verifyBuilding(fruit, BUILDING_SUGAR_BOWL) == false)
    {
        return TOO_FAR;
    }

	// use action
	fruit->useAction();

    // drop
    fruit->removeEquipment(equipment);
	map->addSugar(fruit->getOwner(), equipment->getSellValue());
	delete equipment;
    return OK;
}


int Game::buyEquipment(int fruitId, int equipmentType) {
	// entity not defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // equipment is not an equipment
    if ((equipmentType < EQUIPMENT_TEA_SPOON) || (equipmentType > EQUIPMENT_RELOADER))
    {
        return INVALID_TYPE;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // target not in range
    if (map->verifyBuilding(fruit, BUILDING_SUGAR_BOWL) == false)
    {
        return TOO_FAR;
    }
    Equipment *equipment;
    equipment = map->createEquipment(equipmentType, -1, -1);
    // not enough sugar
    if (fruit->getOwner()->hasEnough(equipment->getCost(), 0) == false)
    {
        delete equipment;
        return NOT_ENOUGH_SUGAR;
    }
    // check weight
    if (fruit->hasPlaceLeft() < equipment->getWeight())
    {
        delete equipment;
        return TOO_HEAVY;
    }

	// use action
	fruit->useAction();

    // add equipment
    fruit->addEquipment(equipment);
    fruit->getOwner()->removeSugar(equipment->getCost());
    return OK;
}

int Game::drinkJuice(int fruitId) {
	// entity not defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // target not in range
    if (map->verifyBuilding(fruit, BUILDING_JUICE_BARREL) == false)
    {
        return TOO_FAR;
    }
    // healthy
    if (fruit->isHealthy())
    {
        return HEALTHY;
    }

	// use action
	fruit->useAction();

    // add equipment
    if (fruit->hasMaxHP())
    {
        fruit->addDefense(1);
        return DEFENSE_GAINED;
    }

    fruit->addHP(5);
    return LIFE_GAINED;
}

int Game::fructify(int fruitId, int fruitType, int x, int y) {
	// entity not defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // not a fruit type
    if ((fruitType < FRUIT_CHERRY) || (fruitType > FRUIT_NUT))
    {
        return INVALID_TYPE;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    if (map->verifyNbFruit(fruitType, fruit->getOwner()) == false)
    {
        return LIMIT_REACHED;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // position not in the map
    if (map->contains(x,y) == false)
    {
        return NOT_VALID_POSITION;
    }
    // position not valid
    if (map->verifyPosition(x,y) == false)
    {
        return NOT_VALID_DESTINATION;
    }
    // target not in range
    if (map->verifyBuilding(fruit, BUILDING_FRUCTIFICATION_TANK) == false)
    {
        return TOO_FAR;
    }
    // not enough sugar
    if (fruit->getOwner()->hasEnough(FRUCTIFICATION_SUGAR_QUANTITY, FRUCTIFICATION_VITAMINS_QUANTITY) == NOT_ENOUGH_SUGAR)
    {
        return NOT_ENOUGH_SUGAR;
    }
    // not enough vitamins
    if (fruit->getOwner()->hasEnough(FRUCTIFICATION_SUGAR_QUANTITY, FRUCTIFICATION_VITAMINS_QUANTITY) == NOT_ENOUGH_SUGAR)
    {
        return NOT_ENOUGH_VITAMIN;
    }

	// use action
	fruit->useAction();

    // add fruit
    int idF = map->createFruit(fruitType, x, y, fruit->getOwner());

    //creation of modification for all players
    int *modif = new int[5];
    modif[0] = idF;
    modif[1] = x;
    modif[2] = y;
	modif[3] = fruitType;
	modif[4] = 0;
    map->addNewModification(modif);
	commander->setFrame();

    return idF;
}

int Game::drawVitamin(int fruitId) {
	// entity not defined
    if (map->verifyId(fruitId) == -1)
    {
        return UNKNOWN_OBJECT;
    }
    // entity is not a fruit
    if ((map->verifyId(fruitId) != FRUIT_CHERRY) && (map->verifyId(fruitId) != FRUIT_KIWI) && (map->verifyId(fruitId) != FRUIT_NUT))
    {
        return NOT_FRUIT;
    }
    // cast in Fruit*
    Fruit *fruit = (Fruit*)(map->getEntity(fruitId));
    // fruit is not yours
    if (fruit->getOwner()->isCurrentPlayer() == false)
    {
        return NOT_OWNER;
    }
    // fruit has used his 2 actions
    if (fruit->hasActionLeft() == false)
    {
        return NO_MORE_ACTIONS;
    }
    // target not in range
    if (map->verifyBuilding(fruit, BUILDING_VITAMIN_SOURCE) == false)
    {
        return TOO_FAR;
    }
    // check weight
    if (map->verifySource() == false)
    {
        return SOURCE_FULL;
    }

	// use action
	fruit->useAction();

    // add sugar
    fruit->getOwner()->addVitamins(QUANTITY_VITAMINS_TAKEN);
    map->addSourceMiner();
    return OK;
}

int Game::writeText(char *text) {
	cout << "writeText(\"" << text << "\");" << endl;
	return OK;
}

int Game::writeTextAt(char *text, int x, int y) {
	cout << "writeTextAt(\"" << text << "\", " << x << ", " << y << ");" << endl;
	return OK;
}

int Game::drawLine(int x1, int y1, int x2, int y2, int color) {
	commander->drawLine(2 * x1 + 1, 2 * y1 + 1, 2 * x2 + 1, 2 * y2 + 1, getRGBColor(color), true);
	return OK;
}

int Game::drawCircle(int x, int y, int radius, int color) {
	commander->drawCircle(2 * x + 1, 2 * y + 1, radius, 16, getRGBColor(color), true);
	return OK;
}

void Game::sendOpenedChest(Chest *chest)
{
  int size = chest->getListEquipment().size();
  vector<Equipment*> liste = chest->getListEquipment();
  IntMatrix2 equipments = IntMatrix2(size, 5);
  for(int i=0; i<size;i++)
  {
	commander->createEntity((liste[i]->getType()+41),liste[i]->getId());
	commander->moveEntity(liste[i]->getId(), 2 * liste[i]->getPosition().first, 2 * liste[i]->getPosition().second);
	equipments[i][OBJECT_ID] = liste[i]->getId();
	equipments[i][OBJECT_X] = liste[i]->getPosition().first;
	equipments[i][OBJECT_Y] = liste[i]->getPosition().second;
	equipments[i][OBJECT_TYPE] = liste[i]->getType();
	equipments[i][OBJECT_INFO] = liste[i]->getAmmo();
  }
  commander->deleteEntity(chest->getId());
  commander->chestOpened(map->getCurrentPlayer(), chest->getId(), &equipments);
  chest->clear();
  delete chest;
}

int Game::getRGBColor(int color)
{
	switch(color)
	{
		case COLOR_BLACK:  return 0x000000;
		case COLOR_BLUE:   return 0x0000FF;
		case COLOR_GREEN:  return 0x00FF00;
		case COLOR_ORANGE: return 0xFFA500;
		case COLOR_RED:    return 0xFF0000;
		case COLOR_VIOLET: return 0x800080;
		case COLOR_WHITE:  return 0xFFFFFF;
		case COLOR_YELLOW: return 0xFFFF00;
		default:           return 0xFFFFFF;
	}
}
