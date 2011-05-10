#include "Game.hpp"

#include "FruitSaladEngine.hpp"
#include "SpecificCommunicator.hpp"
#include "SpecificCommander.hpp"
#include <iostream>

using namespace std;

Game game = Game();

Game::Game() {
	communicator = new SpecificCommunicator(this);
	commander = new SpecificCommander();
	/*
	height = 12;
	width = 10;
	
	// Create empty architecture
	architecture = new int *[height];
	for(int i = 0; i < height; i++) {
		architecture[i] = new int[width];
		for(int j = 0; j < width; j++) {
			
			if(i == 0 || i == height - 1 || j == 0 || j == width - 1) {
				architecture[i][j] = WALL;
			} else {
				architecture[i][j] = NOTHING;
			}
		}
	}
	*/
}

Game::~Game() {
	delete commander;
	delete communicator;
	/*
	for(int i = 0; i < height; i++) {
		delete[] architecture[i];
	}
	delete[] architecture;
	*/
}

void Game::init(int nbParameters, char *parameters[]) {
	cout << "Initializing game with " << nbParameters << " parameters." << endl;
	
	for(int i = 0; i < nbParameters; i++) {
		cout << "Parameter #" << i << " = " << parameters[i] << endl;
	}
}

void Game::addAi(short aiId, char *aiName, char *playerName) {
	cout << "Adding AI '" << aiName << "' (" << aiId << ") ";
	cout << "of player " << playerName << endl;
}

void Game::play() {
	cout << "Play..." << endl;
	/*
	cout << "setFrame" << endl;
	commander->setFrame();
	
	// Create fruits array
	int nbFruits = 3;
	IntMatrix2 fruits = IntMatrix2(nbFruits, 4);
	fruits[0][OBJECT_ID] = 1;
	fruits[0][OBJECT_X] = 8;
	fruits[0][OBJECT_Y] = 5;
	fruits[0][OBJECT_TYPE] = FRUIT_CHERRY;
	fruits[1][OBJECT_ID] = 2;
	fruits[1][OBJECT_X] = 8;
	fruits[1][OBJECT_Y] = 6;
	fruits[1][OBJECT_TYPE] = FRUIT_KIWI;
	fruits[2][OBJECT_ID] = 3;
	fruits[2][OBJECT_X] = 8;
	fruits[2][OBJECT_Y] = 7;
	fruits[2][OBJECT_TYPE] = FRUIT_NUT;
	
	// Create buildings array
	int nbBuildings = 3;
	IntMatrix2 buildings = IntMatrix2(nbBuildings, 4);
	buildings[0][OBJECT_ID] = 4;
	buildings[0][OBJECT_X] = 1;
	buildings[0][OBJECT_Y] = 1;
	buildings[0][OBJECT_TYPE] = BUILDING_JUICE_BARREL;
	buildings[1][OBJECT_ID] = 5;
	buildings[1][OBJECT_X] = 1;
	buildings[1][OBJECT_Y] = 2;
	buildings[1][OBJECT_TYPE] = BUILDING_SUGAR_BOWL;
	buildings[2][OBJECT_ID] = 6;
	buildings[2][OBJECT_X] = 1;
	buildings[2][OBJECT_Y] = 3;
	buildings[2][OBJECT_TYPE] = BUILDING_FRUCTIFICATION_TANK;
	
	IntMatrix2 *archPt = new IntMatrix2(height, width, architecture);
	
	cout << "Initializing AI #0" << endl;
	commander->initGame(0, archPt, &fruits, &buildings, 4, 5, 6);
	
	delete(archPt);
	*/
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
    if (fruit->distanceTo(position) >= fruit->getSpeed())
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
    if (!(map->canHit(fruit, target)))
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
    // equipment is a weapon and target is not fruit
    if ((map->verifyId(equipmentId) >= EQUIPMENT_TEA_SPOON) && (map->verifyId(equipmentId) <= EQUIPMENT_JUICE_NEEDLE)
        && (map->verifyId(targetId) != FRUIT_CHERRY) && (map->verifyId(targetId) != FRUIT_KIWI) && (map->verifyId(targetId) != FRUIT_NUT))
    {
        return NOT_VALID_TARGET;
    }
    // equipment is a reloader and target is not a weapon or a kiwi
    if ((map->verifyId(equipmentId) == EQUIPMENT_RELOADER) &&
        (map->verifyId(targetId) != FRUIT_KIWI) &&
        ((map->verifyId(equipmentId) < EQUIPMENT_TEA_SPOON) || (map->verifyId(equipmentId) > EQUIPMENT_JUICE_NEEDLE)))
    {
        return NOT_VALID_TARGET;
    }
    // Cast with the good Entity
    bool reloading = false;
    bool targetFruit = false;
    Entity *target;
    Equipment *equipment;
    // equipment is a weapon, target is a fruit
    if ((map->verifyId(equipmentId) >= EQUIPMENT_TEA_SPOON) && (map->verifyId(equipmentId) <= EQUIPMENT_JUICE_NEEDLE))
    {
        target = (Fruit*)(map->getEntity(targetId));
        equipment = (Weapon*)(map->getEntity(equipmentId));
        targetFruit = true;
    }
    // equipment is a reloader and target a kiwi
    else if ((map->verifyId(equipmentId) == EQUIPMENT_RELOADER) && (map->verifyId(targetId) == FRUIT_KIWI))
    {
        target = (Fruit*)(map->getEntity(targetId));
        equipment = (Loader*)(map->getEntity(equipmentId));
        reloading = true;
        targetFruit = true;
    }
    // equipment is a reloader, target is a weapon
    else
    {
        target = (Weapon*)(map->getEntity(targetId));
        equipment = (Loader*)(map->getEntity(equipmentId));
        reloading = true;
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
    if (targetFruit)
    {
        // target not in range
        if (fruit->maximumOffset(target) > equipment->getRange())
        {
            return TOO_FAR;
        }
        // target not visible
        if (!(map->canHit(fruit, (Fruit*)target)))
        {
            return OBSTACLE_PRESENT;
        }
    }
    // fruit has not the equipment
    if (fruit->hasEquipment((Equipment*)equipment) == false)
    {
        return NOT_EQUIPPED;
    }

	// use action
	fruit->useAction();

    // use equipment
    if (fruit->useEquipment((Equipment*)equipment, target) == false)
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
    Equipment *equipment = (Equipment*)(map->getEntity(equipmentId));
    // fruit has not the equipment
    if (fruit->hasEquipment(equipment) == false)
    {
        return NOT_EQUIPPED;
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
    if (fruit->distanceTo(position) > 1)
    {
        return TOO_FAR;
    }

	// use action
	fruit->useAction();

    // drop
    fruit->removeEquipment(equipment);
    equipment->setPosition(x,y);
	map->addEntity(equipment);

	//creation of modification for all players
    int *modif = new int[4];
    modif[0] = equipment->getId();
    modif[1] = x;
    modif[2] = y;
    modif[3] = equipment->getType();
    map->addNewModification(modif);

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
    fruit->addSugar(5);
    if (sugarDrop->removeSugar(5) == false)
    {
        return SOME_SUGAR_TAKEN;
    }
    map->removeEntity(sugarDrop);

    //creation of modification for all players
    int *modif = new int[2];
    modif[0] = sugarDrop->getId();
    modif[1] = sugarDrop->getCapacity();
    map->addUpdatedModificationSugar(modif);

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
    if (fruit->distanceTo(position) > 1)
    {
        return TOO_FAR;
    }

	// use action
	fruit->useAction();

    // drop
    fruit->removeSugar(quantity);
	int idSugar = map->addSugarDrop(x, y, quantity);

	//creation of modification for all players
    int *modif = new int[3];
    modif[0] = idSugar;
    modif[1] = x;
    modif[2] = y;
    map->addNewModification(modif);

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
    Equipment *equipment = (Equipment*)(map->getEntity(equipmentId));
    // fruit has not the equipment
    if (fruit->hasEquipment(equipment) == false)
    {
        return NOT_EQUIPPED;
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
    if (fruit->getOwner()->verifyNbFruit(fruitType) == false)
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
    if (fruit->getOwner()->hasEnough(20, 10) == NOT_ENOUGH_SUGAR)
    {
        return NOT_ENOUGH_SUGAR;
    }
    // not enough vitamins
    if (fruit->getOwner()->hasEnough(20, 10) == NOT_ENOUGH_VITAMIN)
    {
        return NOT_ENOUGH_VITAMIN;
    }

	// use action
	fruit->useAction();

    // add fruit
    int idF = map->createFruit(fruitType, x, y, fruit->getOwner());

    //creation of modification for all players
    int *modif = new int[3];
    modif[0] = idF;
    modif[1] = x;
    modif[2] = y;
    map->addNewModification(modif);

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
    fruit->getOwner()->addVitamins(5);
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
	cout << "drawLine(" << x1 << ", " << y1 << ", " << x2 << ", " << y2 << ", ";
	cout << color << ");" << endl;
	return OK;
}

int Game::drawCircle(int x, int y, int radius, int color) {
	cout << "drawCircle(" << x << ", " << y << ", " << radius << ", ";
	cout << color << ");" << endl;
	return OK;
}

int Game::colorSquare(int x, int y, int color) {
	cout << "colorSquare(" << x << ", " << y << ", " << color << ");" << endl;
	return OK;
}

void Game::sendOpenedChest(Chest *chest)
{
  int size = chest->getListEquipment().size();
  IntMatrix2 equipments = IntMatrix2(size, 4);
  int i=0;
  std::vector<Equipment*>::iterator it;
  for(it=chest->getListEquipment().begin(); it!=chest->getListEquipment().end();it++)
  {
	
	equipments[i][OBJECT_ID] = (*it)->getId();
	equipments[i][OBJECT_X] = (*it)->getPosition().first;
	equipments[i][OBJECT_Y] = (*it)->getPosition().second;
	equipments[i][OBJECT_TYPE] = (*it)->getType();
	i++;
  }
  commander->chestOpened(map->getCurrentPlayer(), chest->getId(), &equipments);
}

