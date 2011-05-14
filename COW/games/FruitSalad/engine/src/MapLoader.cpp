#include"MapLoader.h"

#include "Player.h"
#include "Building.h"
#include "OwnedBuilding.h"
#include "Entity.h"
#include "SugarDrop.h"
#include "Equipment.h"
#include "Weapon.h"
#include "Loader.h"
#include "Peeler.h"
#include "Fruit.h"
#include <time.h>
#include <fstream>

#include <iostream>

MapLoader::MapLoader(Map *mapE, SpecificCommander *commanderE)
{
    map = mapE;
	commander = commanderE;
    currentId = 0;
}


void MapLoader::loadMap(const char *fic)
{
    std::ifstream fichier(fic, std::ios::in);  // on ouvre en lecture
        if(fichier)  // si l'ouverture a fonctionné
        {
                std::string height;
                int heightint;
                std::string width;
                int widthint;
				std::string limitFruit;
				int limitFruitint;
                int nbPlayers;
				
				std::string maxVitamins;
                int maxVitaminsint;
                std::string nbTours;
                int nbToursint;
				std::string sugarEjected;
				int sugarEjectedint;
				
                std::string letter;     //letter defining entity
                std::string x0;         //position X
                int x0int;
                std::string y0;         //positon Y
                int y0int;
                std::string x1;         //position X for end walls
                int x1int;
                std::string y1;         //position Y for end walls
                int y1int;
                std::string typeE;      //type of the entity
                int typeEint;
                std::string playerE;    //num of player
                int playerEint;
                std::string quantityE;  //quantity of sugar/equipment
                int quantityEint;

                std::istringstream variable; // used for String To Int

                int positionComma = 0;

                std::string contenu;  // déclaration d'une chaîne qui contiendra la ligne lue
                std::getline(fichier, contenu);
                std::getline(fichier, contenu); //get Dimensions
                positionComma = contenu.find_first_of(',');
                width = contenu.substr(0, positionComma);
                height = contenu.substr(positionComma+1);
                variable.clear();
                variable.str(width);
                variable >> widthint;
                variable.clear();
                variable.str(height);
                variable >> heightint;
                map->setDimensions(heightint, widthint);
				
				commander->displayGrid(0, 0, 2 * map->getWidth(), 2 * map->getHeight(), 2, 2, 0x303030, false);
				
                std::getline(fichier, contenu);
                std::getline(fichier, contenu); //get NbPlayers
                variable.clear();
                variable.str(contenu);
                variable >> nbPlayers;
                map->createPlayers(nbPlayers);
				
				std::getline(fichier, contenu);
				std::getline(fichier, contenu); //get Limits
				//LimitCherry
				positionComma = contenu.find_first_of(',');
				limitFruit = contenu.substr(0, positionComma);
				contenu = contenu.substr(positionComma+1);
				variable.clear();
				variable.str(limitFruit);
				variable >> limitFruitint;
				map->setLimitCherry(limitFruitint);
				//LimitKiwi
				positionComma = contenu.find_first_of(',');
				limitFruit = contenu.substr(0, positionComma);
				contenu = contenu.substr(positionComma+1);
				variable.clear();
				variable.str(limitFruit);
				variable >> limitFruitint;
				map->setLimitKiwi(limitFruitint);
				//LimitNut
				limitFruit = contenu;
				variable.clear();
				variable.str(limitFruit);
				variable >> limitFruitint;
				map->setLimitNut(limitFruitint);
				
				std::getline(fichier, contenu);
                std::getline(fichier, contenu); //get config
                positionComma = contenu.find_first_of(',');
                maxVitamins = contenu.substr(0, positionComma);
				contenu = contenu.substr(positionComma+1);
                positionComma = contenu.find_first_of(',');
                sugarEjected = contenu.substr(0,positionComma);
				nbTours = contenu.substr(positionComma+1);
                variable.clear();
                variable.str(maxVitamins);
                variable >> maxVitaminsint;
                variable.clear();
                variable.str(sugarEjected);
                variable >> sugarEjectedint;
                variable.clear();
                variable.str(nbTours);
                variable >> nbToursint;
                map->setConfig(maxVitaminsint, sugarEjectedint, nbToursint);
				
                while (std::getline(fichier, contenu))
                {  
				  
				  if (contenu[0] != '#') // if not comment
                    {
                        positionComma = contenu.find_first_of(',');
                        letter = contenu.substr(0, positionComma);
                        contenu = contenu.substr(positionComma+1);

                        if (letter == "W")
                        {
							positionComma = contenu.find_first_of(',');
                            x0 = contenu.substr(0, positionComma);
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            y0 = contenu.substr(0, positionComma);
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            x1 = contenu.substr(0, positionComma);
                            contenu = contenu.substr(positionComma+1);
                            y1 = contenu;
                            variable.clear();
                            variable.str(x0);
                            variable >> x0int;
                            variable.clear();
                            variable.str(y0);
                            variable >> y0int;
                            variable.clear();
                            variable.str(x1);
                            variable >> x1int;
                            variable.clear();
                            variable.str(y1);
                            variable >> y1int;
                            variable.clear();
                            map->createWalls(x0int, y0int, x1int, y1int);
                        }
                        else if (letter == "B")
                        {
                            positionComma = contenu.find_first_of(',');
                            typeE = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            playerE = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            x0 = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            y0 = contenu;
                            variable.clear();
                            variable.str(playerE);
                            variable >> playerEint;
                            variable.clear();
                            variable.str(x0);
                            variable >> x0int;
                            variable.clear();
                            variable.str(y0);
                            variable >> y0int;
                            variable.clear();
                            map->addEntity(createOwnedBuilding(typeE, playerEint, x0int, y0int));
                        }
                        else if (letter == "N")
                        {
                            positionComma = contenu.find_first_of(',');
                            typeE = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            x0 = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            y0 = contenu;
                            variable.clear();
                            variable.str(x0);
                            variable >> x0int;
                            variable.clear();
                            variable.str(y0);
                            variable >> y0int;
                            variable.clear();
                            map->addEntity(createBuilding(typeE, x0int, y0int));
                        }
                        else if (letter == "F")
                        {
                            positionComma = contenu.find_first_of(',');
                            typeE = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            playerE = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            x0 = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            y0 = contenu;
                            variable.clear();
                            variable.str(playerE);
                            variable >> playerEint;
                            variable.clear();
                            variable.str(x0);
                            variable >> x0int;
                            variable.clear();
                            variable.str(y0);
                            variable >> y0int;
                            variable.clear();
                            map->addEntity(createFruit(typeE, playerEint, x0int, y0int));
                        }
                        else if (letter == "E")
                        {
                            positionComma = contenu.find_first_of(',');
                            typeE = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            x0 = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            y0 = contenu;
                            variable.clear();
                            variable.str(x0);
                            variable >> x0int;
                            variable.clear();
                            variable.str(y0);
                            variable >> y0int;
                            variable.clear();
                            map->addEntity(createEquipment(typeE, x0int, y0int));
                        }
                        else if (letter == "S")
                        {
                            positionComma = contenu.find_first_of(',');
                            x0 = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            y0 = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            quantityE = contenu;
                            variable.clear();
                            variable.str(x0);
                            variable >> x0int;
                            variable.clear();
                            variable.str(y0);
                            variable >> y0int;
                            variable.clear();
                            variable.str(quantityE);
                            variable >> quantityEint;
                            variable.clear();
                            map->setCurrentId(currentId);
                            map->addSugarDrop(x0int, y0int, quantityEint);
                            currentId++;
                        }
                        else if (letter == "C")
                        {
                            positionComma = contenu.find_first_of(',');
                            x0 = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            positionComma = contenu.find_first_of(',');
                            y0 = contenu.substr(0, contenu.find_first_of(','));
                            contenu = contenu.substr(positionComma+1);
                            quantityE = contenu;
                            variable.clear();
                            variable.str(x0);
                            variable >> x0int;
                            variable.clear();
                            variable.str(y0);
                            variable >> y0int;
                            variable.clear();
                            variable.str(quantityE);
                            variable >> quantityEint;
                            variable.clear();
                            Chest* chest = (Chest*) createChest(x0int, y0int);
                            map->addEntity(chest);
                            for(int i=0; i<quantityEint; i++)
                            {
                                std::getline(fichier, contenu);
                                positionComma = contenu.find_first_of(',');
                                typeE = contenu.substr(0, positionComma);
                                contenu = contenu.substr(positionComma+1);
                                createChestEquipment(typeE, chest);
                            }
                        }
                        else
                        {
                        }
                    }
                }

                fichier.close();
                map->setCurrentId(currentId);
        }
        else
        {
            std::cerr << "Impossible d'ouvrir le fichier !" << std::endl;
        }
}


Entity* MapLoader::createBuilding(std::string typeE, int x0, int y0)
{
    int typeBuilding;
    if (typeE=="V") {typeBuilding = BUILDING_VITAMIN_SOURCE;}
    else if (typeE=="S") {typeBuilding = BUILDING_SUGAR_TREE;}
	
    Building *building = new Building(std::pair<int,int>(x0,y0), currentId, typeBuilding);
	commander->createEntity((typeBuilding+58),currentId);
	commander->moveEntity(currentId, 2 * x0, 2 * y0);
    currentId++;
    return building;
}

Entity* MapLoader::createOwnedBuilding(std::string typeE, int numPlayer, int x0, int y0)
{
    int typeBuilding;
	if (typeE=="J") {typeBuilding = BUILDING_JUICE_BARREL;}
    else if (typeE=="S") {typeBuilding = BUILDING_SUGAR_BOWL;}
    else if (typeE=="F") {typeBuilding = BUILDING_FRUCTIFICATION_TANK;}

    Player *owner = map->getListPlayers()[numPlayer];

    OwnedBuilding *building = new OwnedBuilding(std::pair<int,int>(x0,y0), currentId, typeBuilding, owner);
	commander->createEntity((typeBuilding+10*(numPlayer)),currentId);
	commander->moveEntity(currentId, 2 * x0, 2 * y0);
    currentId++;
    return building;
}

Entity* MapLoader::createFruit(std::string typeE, int numPlayer, int x0, int y0)
{
    int typeFruit;
    if (typeE=="C") {typeFruit = FRUIT_CHERRY;}
    else if (typeE=="K") {typeFruit = FRUIT_KIWI;}
    else if (typeE=="N") {typeFruit = FRUIT_NUT;}

    Player *owner = map->getListPlayers()[numPlayer];

    Fruit *fruit = new Fruit(std::pair<int,int>(x0,y0), currentId, typeFruit, owner);
	commander->createEntity((typeFruit-6+10*(numPlayer)),currentId);
	commander->moveEntity(currentId, 2 * x0, 2 * y0);
    currentId++;
	int *info = new int[5];
	info[0] = currentId-1;
	info[1] = x0;
	info[2] = y0;
	info[3] = typeFruit;
	info[4] = 0;
	map->addNewModification(info);
	
    return fruit;
}

Entity* MapLoader::createEquipment(std::string typeE, int x0, int y0)
{
    int typeEquipment;
    int type2;
    if (typeE=="N") {typeEquipment = EQUIPMENT_TEA_SPOON; type2=0;}
    else if (typeE=="T") {typeEquipment = EQUIPMENT_TOOTHPICK; type2=0;}
    else if (typeE=="C") {typeEquipment = EQUIPMENT_CUTTER; type2=0;}
    else if (typeE=="L") {typeEquipment = EQUIPMENT_LIGHTER; type2=0;}
    else if (typeE=="M") {typeEquipment = EQUIPMENT_LEMONER; type2=0;}
    else if (typeE=="S") {typeEquipment = EQUIPMENT_SALT_SNIPER; type2=0;}
    else if (typeE=="P") {typeEquipment = EQUIPMENT_PEELER; type2=1;}
    else if (typeE=="J") {typeEquipment = EQUIPMENT_JUICE_NEEDLE; type2=0;}
    else if (typeE=="R") {typeEquipment = EQUIPMENT_RELOADER; type2=2;}

    if (type2 == 0)
    {
        Weapon *equipment = new Weapon(std::pair<int,int>(x0,y0), currentId, typeEquipment);
		commander->createEntity((typeEquipment+41),currentId);
	commander->moveEntity(currentId, 2 * x0, 2 * y0);
        currentId++;
		int *info = new int[5];
		info[0] = currentId-1;
		info[1] = x0;
		info[2] = y0;
		info[3] = typeEquipment;
		info[4] = equipment->getAmmo();
		map->addNewModification(info);
        return equipment;
    }
    else if (type2 == 1)
    {
        Peeler *equipment = new Peeler(std::pair<int,int>(x0,y0), currentId, typeEquipment);
		commander->createEntity((typeEquipment+41),currentId);
	commander->moveEntity(currentId, 2 * x0, 2 * y0);
        currentId++;
		int *info = new int[5];
		info[0] = currentId-1;
		info[1] = x0;
		info[2] = y0;
		info[3] = typeEquipment;
		info[4] = equipment->getAmmo();
		map->addNewModification(info);
        return equipment;
    }
    else
    {
        Loader *equipment = new Loader(std::pair<int,int>(x0,y0), currentId, typeEquipment);
		commander->createEntity((typeEquipment+41),currentId);
	commander->moveEntity(currentId, 2 * x0, 2 * y0);
        currentId++;
		int *info = new int[5];
		info[0] = currentId-1;
		info[1] = x0;
		info[2] = y0;
		info[3] = typeEquipment;
		info[4] = equipment->getAmmo();
		map->addNewModification(info);
        return equipment;
    }
}

void MapLoader::createChestEquipment(std::string typeE, Chest* chest)
{
    int typeEquipment;
    int type2;
    if (typeE=="N") {typeEquipment = EQUIPMENT_TEA_SPOON; type2=0;}
    else if (typeE=="T") {typeEquipment = EQUIPMENT_TOOTHPICK; type2=0;}
    else if (typeE=="C") {typeEquipment = EQUIPMENT_CUTTER; type2=0;}
    else if (typeE=="L") {typeEquipment = EQUIPMENT_LIGHTER; type2=0;}
    else if (typeE=="M") {typeEquipment = EQUIPMENT_LEMONER; type2=0;}
    else if (typeE=="S") {typeEquipment = EQUIPMENT_SALT_SNIPER; type2=0;}
    else if (typeE=="P") {typeEquipment = EQUIPMENT_PEELER; type2=1;}
    else if (typeE=="J") {typeEquipment = EQUIPMENT_JUICE_NEEDLE; type2=0;}
    else if (typeE=="R") {typeEquipment = EQUIPMENT_RELOADER; type2=2;}

    if (type2 == 0)
    {
        Weapon *equipment = new Weapon(std::pair<int,int>(-1,-1), currentId, typeEquipment);
        chest->addEquipment(equipment);
    }
    else if (type2 == 1)
    {
        Peeler *equipment = new Peeler(std::pair<int,int>(-1,-1), currentId, typeEquipment);
        chest->addEquipment(equipment);
    }
    else
    {
        Loader *equipment = new Loader(std::pair<int,int>(-1,-1), currentId, typeEquipment);
        chest->addEquipment(equipment);
    }
    currentId++;
}

Entity* MapLoader::createChest(int x0, int y0)
{
    Chest *chest = new Chest(std::pair<int,int>(x0,y0), currentId, CHEST);
	commander->createEntity(72,currentId);
	commander->moveEntity(currentId, 2 * x0, 2 * y0);
    currentId++;
	int *info = new int[5];
	info[0] = currentId-1;
	info[1] = x0;
	info[2] = y0;
	info[3] = CHEST;
	info[4] = 0;
	map->addNewModification(info);
    return chest;
}
